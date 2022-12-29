package wia2007.project.tablebooking.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import wia2007.project.tablebooking.converter.DateConverter;
import wia2007.project.tablebooking.converter.TimeConverter;
import wia2007.project.tablebooking.dao.BookingContainMenuDAO;
import wia2007.project.tablebooking.dao.BookingDAO;
import wia2007.project.tablebooking.dao.CustomerDAO;
import wia2007.project.tablebooking.dao.MenuDAO;
import wia2007.project.tablebooking.dao.RestaurantDAO;
import wia2007.project.tablebooking.dao.TableDAO;
import wia2007.project.tablebooking.entity.Booking;
import wia2007.project.tablebooking.entity.BookingContainMenu;
import wia2007.project.tablebooking.entity.Customer;
import wia2007.project.tablebooking.entity.Menu;
import wia2007.project.tablebooking.entity.Restaurant;
import wia2007.project.tablebooking.entity.Table;

@Database(entities = {Booking.class, BookingContainMenu.class, Customer.class, Menu.class, Restaurant.class, Table.class}, version = 1, exportSchema = false)
@TypeConverters({TimeConverter.class, DateConverter.class})
public abstract class TableBookingDatabase extends RoomDatabase {
    public abstract BookingContainMenuDAO bookingContainMenuDAO();
    public abstract BookingDAO bookingDAO();
    public abstract CustomerDAO customerDAO();
    public abstract MenuDAO menuDAO();
    public abstract RestaurantDAO restaurantDAO();
    public abstract TableDAO tableDAO();

    private static volatile TableBookingDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static TableBookingDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TableBookingDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TableBookingDatabase.class, "table_booking_database").build();
                }
            }
        }
        return INSTANCE;
    }
}
