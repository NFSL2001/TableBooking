package wia2007.project.tablebooking.dao;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SimpleSQLiteQuery;

import java.util.ArrayList;
import java.util.List;

import wia2007.project.tablebooking.BookingRestaurant;
import wia2007.project.tablebooking.entity.Booking;

@Dao
public interface BookingDAO {
    @Update
    public void updateBookings(Booking ...bookings);

    @Insert
    public void insertBookings(Booking ...bookings);

    @Delete
    public void deleteBookings(Booking ...bookings);

    @Query("SELECT * FROM Booking WHERE booking_id = :id")
    public List<Booking> getBookingById(Integer id);

    @Query("SELECT * FROM Booking WHERE customer_id = :customerId")
    public List<Booking> getBookingByCustomer(Integer customerId);

    @Query("SELECT * FROM Booking WHERE customer_id = :restaurantId")
    public List<Booking> getBookingByRestaurant(Integer restaurantId);

    @Query("SELECT Restaurant.*, start_time, booking_id FROM Booking JOIN `Table` USING (table_id) JOIN Restaurant USING (restaurant_id) WHERE customer_id = :customerId ORDER BY start_time DESC")
    public LiveData<List<BookingRestaurant>> getBookingRestaurantByCustomer(Integer customerId);

    @Query("SELECT Restaurant.*, start_time, booking_id FROM Booking JOIN `Table` USING (table_id) JOIN Restaurant USING (restaurant_id) WHERE customer_id = :customerId ORDER BY restaurant_name")
    public LiveData<List<BookingRestaurant>> getBookingRestaurantByCustomerOrderByName(Integer customerId);

    @Query("SELECT Booking_id,start_time,End_time,Remark,T.Name AS TableName, C.name AS CustName,Mobile_number,Email FROM Booking B INNER JOIN Customer C, `Table` T ON B.Customer_id = C.Customer_id AND B.Table_id=T.Table_id WHERE Restaurant_id=:restaurant_id ORDER BY " +
            "CASE WHEN :sortCondition = 0 THEN C.name END COLLATE NOCASE ASC, " +
            "CASE WHEN :sortCondition = 1 THEN start_time END COLLATE NOCASE ASC," +
            "CASE WHEN :sortCondition = 2 THEN T.name END COLLATE NOCASE ASC," +
            "CASE WHEN :sortCondition = 3 THEN C.name END COLLATE NOCASE DESC, " +
            "CASE WHEN :sortCondition = 4 THEN start_time END COLLATE NOCASE DESC," +
            "CASE WHEN :sortCondition = 5 THEN T.name END COLLATE NOCASE DESC")
    Cursor getBookingsList(int restaurant_id,int sortCondition);

    @Query("DELETE FROM Booking WHERE booking_id = :booking_id;")
    public void rejectBooking(int booking_id);

    @Query("Select Distinct substr(start_time,0,5) FROM Booking")
    public String[] selectYear();

    @RawQuery
    List<Booking> rawQuery(SimpleSQLiteQuery query);

    @RawQuery
    Booking insert(SimpleSQLiteQuery query);
}
