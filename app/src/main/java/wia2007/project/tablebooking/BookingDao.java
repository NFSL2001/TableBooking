//package wia2007.project.tablebooking;
//
//import android.database.Cursor;
//
//import androidx.room.Dao;
//import androidx.room.Insert;
//import androidx.room.Query;
//import androidx.room.Update;
//
//import java.util.List;
//
//
//@Dao
//public interface BookingDao {
//
//    @Query("SELECT Booking_id,Time,Remarks,Name, User_name,Mobile_number,Email,Status FROM Booking_table B INNER JOIN Customer_table C, Table_table T ON B.Customer_id = C.Customer_id AND B.Table_id=T.Table_id WHERE Restaurant_id=:restaurant_id;")
//    Cursor getBookingsList(int restaurant_id);
//
//    @Query("SELECT B.Booking_id, M.Menu_name, BM.Quantity, BM.Quantity*M.Price AS Total_cost FROM Booking_table B INNER JOIN Menu_table M, BOOKING_MENU_TABLE BM ON B.Booking_id = BM.Booking_id AND BM.Menu_id = M.Menu_id WHERE B.Booking_id = :bookingId")
//    Cursor getFoodOrder(int bookingId);
//
//    @Query("DELETE FROM Booking_table")
//    void truncateTheList();
//
//    @Query("UPDATE Booking_table SET Status=:status, Cancel_reason = :reason WHERE Booking_id=:bookingId;")
//    void updateStatus(int bookingId, String status, String reason);
//
//}
