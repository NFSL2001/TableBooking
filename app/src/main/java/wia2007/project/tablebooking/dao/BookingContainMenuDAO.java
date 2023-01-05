package wia2007.project.tablebooking.dao;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import wia2007.project.tablebooking.entity.BookingContainMenu;
import wia2007.project.tablebooking.entity.Customer;

@Dao
public interface BookingContainMenuDAO {
    @Insert
    public void insertContains(BookingContainMenu ...contains);

    @Delete
    public void deleteContains(BookingContainMenu ...contains);

    @Query("SELECT * FROM Contain WHERE booking_id = :bookingId")
    public List<BookingContainMenu> getContainsByBookingId(Integer bookingId);

    @Query("SELECT B.Booking_id, M.Menu_name, BM.Quantity, BM.Quantity*M.Price AS Total_cost FROM Booking B INNER JOIN MenuItem M, Contain BM ON B.Booking_id = BM.Booking_id AND BM.Menu_id = M.Menu_id WHERE B.Booking_id = :bookingId")
    Cursor getFoodOrder(int bookingId);

    @Query("DELETE FROM Contain WHERE booking_id = :booking_id;")
    public void rejectBooking(int booking_id);
}
