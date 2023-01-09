package wia2007.project.tablebooking;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;

import java.sql.Date;

import wia2007.project.tablebooking.dao.CustomerDAO;
import wia2007.project.tablebooking.dao.MenuDAO;
import wia2007.project.tablebooking.dao.RestaurantDAO;
import wia2007.project.tablebooking.dao.TableDAO;
import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.Cuisine;
import wia2007.project.tablebooking.entity.Customer;
import wia2007.project.tablebooking.entity.MenuItem;
import wia2007.project.tablebooking.entity.Restaurant;
import wia2007.project.tablebooking.entity.Table;

public class SampleDataInsert {
    @Test
    public void dateInsert() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        TableBookingDatabase database = TableBookingDatabase.getDatabase(appContext);

        CustomerDAO customerDAO = database.customerDAO();
        customerDAO.insertCustomers(new Customer(
                "lion77",
                "77noiL77",
                "Peter Griffin",
                "01160895030",
                "lion77@gmail.com",
                Customer.GENDER_MALE,
                new Date(System.currentTimeMillis())));

        RestaurantDAO restaurantDAO = database.restaurantDAO();
        Restaurant restaurant = new Restaurant(
                "KFCAdmin",
                "fingerLickin66",
                "KFC",
                "01110687471",
                20.0f,
                "FSKTM UM", null, null, null, null, null, null,
                Cuisine.WESTERN, null,
                "https://play-lh.googleusercontent.com/MQDfTBh4VBrD4MQt5hX4b26OnGb9l57_pBWaBFw-mvfrfwOY9aHcwgF2mtDKvE0W-Bw=w240-h480-rw");
        long restaurantId = restaurantDAO.insertRestaurants(restaurant)[0];

        TableDAO tableDAO = database.tableDAO();
        tableDAO.insertTables(new Table((int) restaurantId, "A4", 4), new Table((int) restaurantId, "C2", 2));

        MenuDAO menuDAO = database.menuDAO();
        menuDAO.insertMenus(
                new MenuItem(
                        "Dinner Plate",
                        22.49f,
                        "3 Original Recipe Chicken, 1 Whipped Potato (4oz), 1 Coleslaw (4oz), 1 Butterscotch Bun, 1 Coca-Cola (M)",
                        (int) restaurantId, "CHICKEN", "https://kfc.com.my/media/catalog/product/d/i/dinner-plate-combo-butterscotch_1_1.jpg?quality=80&bg-color=255%2C255%2C255&fit=cover&height=1200&width=960&auto=webp&format=pjpg"),
                new MenuItem(
                        "Golden Egg Buddy Meal",
                        36.99f,
                        "2 Original Recipe Chicken, 2 Golden Egg Burger, 1 Cheezy Wedges (L)",
                        (int)restaurantId, "BOX MEALS", "https://kfc.com.my/media/catalog/product/g/o/golden-egg-buddy-meal_1.jpg?quality=80&bg-color=255%2C255%2C255&fit=cover&height=1200&width=960&auto=webp&format=pjpg"));
    }
}
