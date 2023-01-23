package wia2007.project.tablebooking.dao;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SimpleSQLiteQuery;

import java.util.List;

import wia2007.project.tablebooking.entity.Booking;

@Dao
public interface BookingDAO {
    @Update
    public void updateBookings(Booking ...bookings);

    @Insert
    public void insertBookings(Booking ...bookings);

    @Delete
    public void deleteBookings(Booking ...bookings);

    @Query("SELECT Booking_id,start_time,End_time,Remark,T.Name AS TableName,T.size, R.restaurant_name, R.contact_number,R.address FROM Booking B INNER JOIN Restaurant R, `Table` T ON T.restaurant_id = R.restaurant_id AND B.Table_id=T.Table_id WHERE booking_id = :id")
    public Cursor getBookingById(Integer id);

    @Query("SELECT restaurant_name, start_time, booking_id _id,status FROM Booking JOIN `Table` USING (table_id) JOIN Restaurant USING (restaurant_id) WHERE customer_id = :customerId ORDER BY start_time DESC, status ASC")
    public Cursor getBookingRestaurantByCustomer(Integer customerId);

    @Query("SELECT restaurant_name, start_time, booking_id _id,status FROM Booking JOIN `Table` USING (table_id) JOIN Restaurant USING (restaurant_id) WHERE customer_id = :customerId ORDER BY restaurant_name, status ASC")
    public Cursor getBookingRestaurantByCustomerOrderByName(Integer customerId);

    @Query("SELECT Booking_id,start_time,End_time,Remark,T.Name AS TableName, C.name AS CustName,Mobile_number,Email,status FROM Booking B INNER JOIN Customer C, `Table` T ON B.Customer_id = C.Customer_id AND B.Table_id=T.Table_id WHERE Restaurant_id=:restaurant_id ORDER BY " +
            "CASE WHEN :sortCondition = 0 THEN C.name END COLLATE NOCASE ASC, " +
            "CASE WHEN :sortCondition = 1 THEN start_time END COLLATE NOCASE ASC," +
            "CASE WHEN :sortCondition = 2 THEN T.name END COLLATE NOCASE ASC," +
            "CASE WHEN :sortCondition = 3 THEN C.name END COLLATE NOCASE DESC, " +
            "CASE WHEN :sortCondition = 4 THEN start_time END COLLATE NOCASE DESC," +
            "CASE WHEN :sortCondition = 5 THEN T.name END COLLATE NOCASE DESC, status ASC")
    Cursor getBookingsList(int restaurant_id,int sortCondition);

    @Query("UPDATE Booking SET status = 'Cancelled' WHERE booking_id = :booking_id;")
    public void rejectBooking(int booking_id);

    @Query("Select Distinct substr(start_time,0,5) FROM Booking INNER JOIN `table` ON `table`.table_id = Booking.table_id WHERE restaurant_id = :restaurant_id")
    public String[] selectYear(int restaurant_id);

    @RawQuery
    List<Booking> rawQuery(SimpleSQLiteQuery query);

    @RawQuery
    Integer getId(SimpleSQLiteQuery query);

    @RawQuery
    String getName(SimpleSQLiteQuery query);

    @RawQuery
    Booking insert(SimpleSQLiteQuery query);
}
