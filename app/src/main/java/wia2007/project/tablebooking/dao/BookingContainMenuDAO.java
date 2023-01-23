package wia2007.project.tablebooking.dao;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import wia2007.project.tablebooking.DownloadPDF;
import wia2007.project.tablebooking.entity.BookingContainMenu;

@Dao
public interface BookingContainMenuDAO {
    @Insert
    public void insertContains(BookingContainMenu... contains);

    @Delete
    public void deleteContains(BookingContainMenu... contains);

    @Query("SELECT * FROM Contain WHERE booking_id = :bookingId")
    public List<BookingContainMenu> getContainsByBookingId(Integer bookingId);

    @Query("SELECT B.Booking_id, M.Menu_name, BM.Quantity, BM.Quantity*M.Price AS Total_cost FROM Booking B INNER JOIN MenuItem M, Contain BM ON B.Booking_id = BM.Booking_id AND BM.Menu_id = M.Menu_id WHERE B.Booking_id = :bookingId")
    Cursor getFoodOrder(int bookingId);

    @Query("DELETE FROM Contain WHERE booking_id = :booking_id;")
    public void rejectBooking(int booking_id);

    @Query("WITH Dist AS (SELECT M.menu_name, M.price, C.quantity FROM menuitem M, Contain C, Booking B WHERE M.menu_id = C.menu_id AND M.restaurant = :restaurant_id AND status != 'Cancelled' AND C.booking_id = B.booking_id AND substr(start_time,0,5) = :year AND substr(start_time,6,2) = :month) " +
            "SELECT menu_name, price,SUM(quantity) AS Quantity, price*SUM(quantity) AS Total " +
            "FROM   Dist " +
            "GROUP  BY menu_name " +
            "ORDER BY Total DESC;")
    public List<DownloadPDF.saveFoodData> calculateFoodOrder(int restaurant_id, String year, String month);

    @Query("WITH Dist AS (SELECT M.menu_name, M.price, C.quantity FROM menuitem M, Contain C, Booking B WHERE M.menu_id = C.menu_id AND M.restaurant = :restaurant_id AND status != 'Cancelled' AND C.booking_id = B.booking_id AND substr(start_time,0,5) = :year) " +
            "SELECT menu_name, price,SUM(quantity) AS Quantity, price*SUM(quantity) AS Total " +
            "FROM   Dist " +
            "GROUP  BY menu_name " +
            "ORDER BY Total DESC;")
    public List<DownloadPDF.saveFoodData> calculateFoodOrder(int restaurant_id, String year);
}
