package wia2007.project.tablebooking.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import wia2007.project.tablebooking.entity.BookingContainMenu;
import wia2007.project.tablebooking.entity.Menu;

@Dao
public interface BookingContainMenuDAO {
    @Insert
    public void insertContains(BookingContainMenu...contains);

    @Delete
    public void deleteContains(BookingContainMenu ...contains);

    @Query("SELECT * FROM Contain WHERE booking_id = :bookingId")
    public List<BookingContainMenu> getContainsByBookingId(Integer bookingId);

    @Query("SELECT Menu. * FROM Contain JOIN Menu USING (menu_id) WHERE booking_id = :booking_id")
    public LiveData<List<Menu>> getMenuByBookingId(Integer booking_id);
}
