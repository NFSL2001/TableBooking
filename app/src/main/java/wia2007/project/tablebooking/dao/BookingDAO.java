package wia2007.project.tablebooking.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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

    @Query("SELECT Restaurant.*, start_time, booking_id FROM Booking JOIN `Table` USING (table_id) JOIN Restaurant USING (restaurant_id) WHERE customer_id = :customerId ORDER BY start_time DESC")
    public LiveData<List<BookingRestaurant>> getBookingRestaurantByCustomer(Integer customerId);

    @Query("SELECT Restaurant.*, start_time, booking_id FROM Booking JOIN `Table` USING (table_id) JOIN Restaurant USING (restaurant_id) WHERE customer_id = :customerId ORDER BY restaurant_name")
    public LiveData<List<BookingRestaurant>> getBookingRestaurantByCustomerOrderByName(Integer customerId);

    @Query("SELECT * FROM Booking WHERE customer_id = :restaurantId")
    public List<Booking> getBookingByRestaurant(Integer restaurantId);
}
