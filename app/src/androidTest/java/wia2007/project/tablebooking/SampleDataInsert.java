package wia2007.project.tablebooking;

import android.content.Context;
import android.util.Log;

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
    public void dataInsert() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        TableBookingDatabase database = TableBookingDatabase.getDatabase(appContext);

        CustomerDAO customerDAO = database.customerDAO();
        RestaurantDAO restaurantDAO = database.restaurantDAO();
        TableDAO tableDAO = database.tableDAO();
        MenuDAO menuDAO = database.menuDAO();

        customerDAO.insertCustomers(new Customer(
                "lion77",
                "77noiL77",
                "Peter Griffin",
                "01160895030",
                "lion77@gmail.com",
                Customer.GENDER_MALE,
                new Date(System.currentTimeMillis())));

        { // KFC
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

            tableDAO.insertTables(new Table((int) restaurantId, "A4", 4), new Table((int) restaurantId, "C2", 2));

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
                            (int) restaurantId, "BOX MEALS", "https://kfc.com.my/media/catalog/product/g/o/golden-egg-buddy-meal_1.jpg?quality=80&bg-color=255%2C255%2C255&fit=cover&height=1200&width=960&auto=webp&format=pjpg")
            );
        }

        { // Atmosphere
            Restaurant restaurant = new Restaurant(
                    "Atmosphere360",
                    "12345678",
                    "Atmosphere 360",
                    "012-3456789",
                    (float) 105.3,
                    "TH02, Menara Kuala Lumpur, 2, Jalan Puncak, 50250 Kuala Lumpur",
                    "",
                    "Cash only",
                    "No parking",
                    null,
                    null,
                    "www.atmosphere360.com",
                    Cuisine.DINNER_BUFFET,
                    "Atmosphere 360 is a modern and elegant revolving restaurant situated 282m above ground level located at the tallest tower at the tallest tower in Southeast Asia - KL Tower.\\n\\nIndulge in modern authentic Malaysian and International cuisine at Atmosphere 360. Housed in the world’s 7th tallest tower - the KL Tower, its splendid atmosphere and impeccable service offer you one of a king luxurious fine dining experience that will leave a lasting mark in your heart.",
                    "https://cdn.myfunnow.com/imgs/branch/cover%2Fatmosphere%20(2)_6861e3.jpg"
            );
            restaurant.setImages(
                    "https://www.berjayahotel.com/sites/default/files/atmosphere%20360%20restaurant_0.jpg",
                    "https://media-cdn.tripadvisor.com/media/attractions-splice-spp-720x480/09/4e/d6/c7.jpg",
                    "https://res.klook.com/image/upload/fl_lossy.progressive,q_85/c_fill,w_680/v1605695943/blog/lnbvbyt5hqvkn92vuhfk.jpg");
            long restaurantId = restaurantDAO.insertRestaurants(restaurant)[0];

            tableDAO.insertTables(new Table((int) restaurantId, "1", 4),
                    new Table((int) restaurantId, "2", 2),
                    new Table((int) restaurantId, "3", 6),
                    new Table((int) restaurantId, "4", 2),
                    new Table((int) restaurantId, "X1", 8),
                    new Table((int) restaurantId, "X2", 8),
                    new Table((int) restaurantId, "X3", 8)
            );

            menuDAO.insertMenus(
                    new MenuItem(
                            "Full Course",
                            175.00f,
                            "",
                            (int) restaurantId, "Buffet Dinner", "https://b.zmtcdn.com/data/pictures/2/18178302/0e13c16ab764b57bcfbca2ee4e1b1588_featured_v2.jpg"),
                    new MenuItem(
                            "Fresh Seafood Counter",
                            0f,
                            "Shucked Oyster, Boiled Black Tiger Prawnsand Half Green Mussels Oy\n" +
                                    "Lemon Wedges, Chopped Onion, Capers, Tabasco And Chilli Padi Shoyu",
                            (int) restaurantId, "Buffet Dinner", ""),
                    new MenuItem(
                            "Sashimi Counter",
                            0f,
                            "Wasabi, Shoyu, Chuka Wakame And Pickle Gari",
                            (int) restaurantId, "Buffet Dinner", ""),
                    new MenuItem(
                            "Fresh Leafy Salads with Dressings, Tossed Saladsand Cold Cuts",
                            0f,
                            "Baby Romaine Lettuces, Lolla Rossa, Lolla Bionda, Rocket Salad, Frisee, Butterhead\n" +
                                    "Lettuces, Oak Leaf, Mache Lettuces, Tatsoi, Radicchio, Baby Spinach (V)",
                            (int) restaurantId, "Buffet Dinner", ""),
                    new MenuItem(
                            "Cheese Board of The Day",
                            0f,
                            "Gouda, Brie, Cheddar, Feta Cheese, Camembert, Parmigiano Reggianoand Mozzarella\n" +
                                    "Pairwith Dry Fruits, Cream Crackers, Bread Sticks And Cold Cuts Of The Day",
                            (int) restaurantId, "Buffet Dinner", ""),
                    new MenuItem(
                            "Asian and Malaysian Tossed Salad",
                            0f,
                            "Tauhu Sumbatwith Homemade Chilli Sauce, Tempeh Goreng, Vegetable Crackers( V),\n" +
                                    "Gado-gado, Ipoh Chicken Salad, Kerabu Mangga, Thai Beef Noodle Salad, Kerabu Daun\n" +
                                    "Selom, Kerabu Kaki Ayam, Kerabu Ikan Bilis, Fish Crackers, Prawn Crackers",
                            (int) restaurantId, "Buffet Dinner", ""),
                    new MenuItem(
                            "Full Course",
                            199.99f,
                            "",
                            (int) restaurantId, "Buffet Lunch", "https://storage.travelog.com/353700/Lunch_icon.png"),
                    new MenuItem(
                            "Appetizer",
                            0f,
                            " Smoked Salmon, Beef Salami, Chicken Ham, Herb Terrine Salmon and Cream Cheese Terrine",
                            (int) restaurantId, "Buffet Lunch", ""),
                    new MenuItem(
                            "Salad Station",
                            0f,
                            "Hawaiian Chicken Pineapple Salad, Seafood Pasta Salad, Mayo-chicken Pasta Salad,\n" +
                                    "Tuna, Nicoise Salad, Ipoh Chicken Salad, Thai Beef Salad, Siamese Young Mango\n" +
                                    "Salad, Kerabu Kaki Ayam, Kerabu Ikan Bilis, Fish Crackers",
                            (int) restaurantId, "Buffet Lunch", ""),
                    new MenuItem(
                            "Sushi Counter",
                            0f,
                            "Uramaki, Temaki and Maki",
                            (int) restaurantId, "Buffet Lunch", ""),
                    new MenuItem(
                            "Soup",
                            0f,
                            "Cream of Wild Mushroom",
                            (int) restaurantId, "Buffet Lunch", ""),
                    new MenuItem(
                            "Noodles Station",
                            0f,
                            "Curry Mee and Hor Fun Noodle Soup",
                            (int) restaurantId, "Buffet Lunch", ""),
                    new MenuItem(
                            "Drinks",
                            0f,
                            "Coffee or Tea",
                            (int) restaurantId, "Buffet Lunch", ""),
                    new MenuItem(
                            "Merlot",
                            150f,
                            "Red wine full bottle (RM35/glass)",
                            (int) restaurantId, "Wine", "https://whisky.my/cdn-cgi/image/width=1000,height=1000,fit=crop,quality=80,format=auto,onerror=redirect,metadata=none/wp-content/uploads/LAPOSTOLLE-Grand-Selection-Merlot.jpg"),
                    new MenuItem(
                            "Carbenet Sauvignon",
                            150f,
                            "Red wine full bottle (RM35/glass)",
                            (int) restaurantId, "Wine", "https://whisky.my/cdn-cgi/image/width=1000,height=1000,fit=crop,quality=80,format=auto,onerror=redirect,metadata=none/wp-content/uploads/PENFOLD-Koonunga-Hill-Cabernet-Sauvignon.jpg"),
                    new MenuItem(
                            "Pinot Girgio",
                            150f,
                            "White wine full bottle (RM35/glass)",
                            (int) restaurantId, "Wine", "https://www.vantagewine.com.my/image/cache/catalog/product/wine/australian/haselgrove/first_cut_pinot_grigio_800-800x800.jpg"),
                    new MenuItem(
                            "Chardonnay",
                            150f,
                            "White wine full bottle (RM35/glass)",
                            (int) restaurantId, "Wine", "https://winebros.my/wp-content/uploads/2020/09/WOLF-BLASS-BILYARA-CHARDONNAY.jpeg")
            );
        }

        Log.d("dataInsert()", "Data insertion done.");
    }
}
