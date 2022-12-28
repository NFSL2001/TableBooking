package wia2007.project.tablebooking.dao;

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
}
