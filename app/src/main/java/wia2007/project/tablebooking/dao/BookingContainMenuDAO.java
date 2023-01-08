package wia2007.project.tablebooking.dao;

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
}