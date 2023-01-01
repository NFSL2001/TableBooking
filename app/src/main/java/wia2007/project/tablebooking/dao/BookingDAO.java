package wia2007.project.tablebooking.dao;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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

    @Query("SELECT * FROM Booking WHERE booking_id = :id")
    public List<Booking> getBookingById(Integer id);

    @Query("SELECT * FROM Booking WHERE customer_id = :customerId")
    public List<Booking> getBookingByCustomer(Integer customerId);

    @Query("SELECT * FROM Booking WHERE customer_id = :restaurantId")
    public List<Booking> getBookingByRestaurant(Integer restaurantId);

    @Query("SELECT Booking_id,start_time,End_time,Remark,Name, User_name,Mobile_number,Email FROM Booking B INNER JOIN Customer C, `Table` T ON B.Customer_id = C.Customer_id AND B.Table_id=T.Table_id WHERE Restaurant_id=:restaurant_id ORDER BY " +
            "CASE WHEN :sortCondition = 0 THEN user_name END COLLATE NOCASE ASC, " +
            "CASE WHEN :sortCondition = 1 THEN start_time END COLLATE NOCASE ASC," +
            "CASE WHEN :sortCondition = 2 THEN name END COLLATE NOCASE ASC," +
            "CASE WHEN :sortCondition = 3 THEN user_name END COLLATE NOCASE DESC, " +
            "CASE WHEN :sortCondition = 4 THEN start_time END COLLATE NOCASE DESC," +
            "CASE WHEN :sortCondition = 5 THEN name END COLLATE NOCASE DESC")
    Cursor getBookingsList(int restaurant_id,int sortCondition);

    @Query("DELETE FROM Booking WHERE booking_id = :booking_id;")
    public void rejectBooking(int booking_id);

}
