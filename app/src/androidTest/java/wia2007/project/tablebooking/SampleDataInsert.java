package wia2007.project.tablebooking;

import android.content.Context;
import android.util.Log;

import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;

import java.sql.Date;

import wia2007.project.tablebooking.dao.BookingContainMenuDAO;
import wia2007.project.tablebooking.dao.BookingDAO;
import wia2007.project.tablebooking.dao.CustomerDAO;
import wia2007.project.tablebooking.dao.MenuDAO;
import wia2007.project.tablebooking.dao.NotificationDAO;
import wia2007.project.tablebooking.dao.RestaurantDAO;
import wia2007.project.tablebooking.dao.TableDAO;
import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.Booking;
import wia2007.project.tablebooking.entity.BookingContainMenu;
import wia2007.project.tablebooking.entity.Cuisine;
import wia2007.project.tablebooking.entity.Customer;
import wia2007.project.tablebooking.entity.MenuItem;
import wia2007.project.tablebooking.entity.Notification;
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
        BookingDAO bookingDAO = database.bookingDAO();
        NotificationDAO notificationDAO = database.notificationDAO();
        BookingContainMenuDAO bookingContainMenuDAO = database.bookingContainMenuDAO();

        {
            customerDAO.insertCustomers(new Customer(
                    "lion77",
                    "77noiL77",
                    "Peter Griffin",
                    "01160895030",
                    "lion77@gmail.com",
                    Customer.GENDER_MALE,
                    new Date(System.currentTimeMillis())));

            bookingDAO.insert(new SimpleSQLiteQuery("INSERT INTO booking(table_id,customer_id,start_time,end_time,remark,status)" +
                    "VALUES(1,1,'2019-12-23 09:30:00','2019-12-23 10:30:00','More Sauce','Completed')"));
            bookingDAO.insert(new SimpleSQLiteQuery("INSERT INTO booking(table_id,customer_id,start_time,end_time,remark,status)" +
                    "VALUES(15,1,'2020-03-08 19:00:00','2020-03-08 21:00:00','','Cancelled')"));
            bookingDAO.insert(new SimpleSQLiteQuery("INSERT INTO booking(table_id,customer_id,start_time,end_time,remark,status)" +
                    "VALUES(50,1,'2021-08-31 21:30:00','2021-08-31 22:30:00','','Cancelled')"));
            bookingDAO.insert(new SimpleSQLiteQuery("INSERT INTO booking(table_id,customer_id,start_time,end_time,remark,status)" +
                    "VALUES(7,1,'2022-12-23 08:00:00','2022-12-23 10:00:00','','Completed')"));
            bookingDAO.insert(new SimpleSQLiteQuery("INSERT INTO booking(table_id,customer_id,start_time,end_time,remark,status)" +
                    "VALUES(26,1,'2023-02-23 09:30:00','2023-02-23 10:30:00','','Accepted')"));
            bookingDAO.insert(new SimpleSQLiteQuery("INSERT INTO booking(table_id,customer_id,start_time,end_time,remark,status)" +
                    "VALUES(44,1,'2023-01-30 09:30:00','2023-01-30 10:30:00','','Accepted')"));
            bookingDAO.insert(new SimpleSQLiteQuery("INSERT INTO booking(table_id,customer_id,start_time,end_time,remark,status)" +
                    "VALUES(27,1,'2023-01-01 20:30:00','2023-01-01 22:30:00','','Completed')"));
            bookingDAO.insert(new SimpleSQLiteQuery("INSERT INTO booking(table_id,customer_id,start_time,end_time,remark,status)" +
                    "VALUES(28,1,'2023-01-01 20:30:00','2023-01-01 22:30:00','','Completed')"));
            bookingDAO.insert(new SimpleSQLiteQuery("INSERT INTO booking(table_id,customer_id,start_time,end_time,remark,status)" +
                    "VALUES(26,1,'2022-12-23 09:30:00','2022-12-23 10:30:00','','Completed')"));
            bookingDAO.insert(new SimpleSQLiteQuery("INSERT INTO booking(table_id,customer_id,start_time,end_time,remark,status)" +
                    "VALUES(26,1,'2022-12-01 09:30:00','2022-12-01 10:30:00','','Completed')"));
            bookingDAO.insert(new SimpleSQLiteQuery("INSERT INTO booking(table_id,customer_id,start_time,end_time,remark,status)" +
                    "VALUES(27,1,'2022-12-01 09:30:00','2022-12-01 10:30:00','','Completed')"));
            bookingDAO.insert(new SimpleSQLiteQuery("INSERT INTO booking(table_id,customer_id,start_time,end_time,remark,status)" +
                    "VALUES(29,1,'2022-12-01 09:30:00','2022-12-01 10:30:00','','Completed')"));
            bookingDAO.insert(new SimpleSQLiteQuery("INSERT INTO booking(table_id,customer_id,start_time,end_time,remark,status)" +
                    "VALUES(28,1,'2023-01-01 20:30:00','2023-01-01 21:00:00','','Cancelled')"));
            bookingDAO.insert(new SimpleSQLiteQuery("INSERT INTO booking(table_id,customer_id,start_time,end_time,remark,status)" +
                    "VALUES(28,1,'2023-01-02 20:30:00','2023-01-02 21:30:00','','Cancelled')"));


            notificationDAO.insertNotification(
                    new Notification("You make a booking in <b>KFC</b><br>Date & Time: <b>2019-12-23 09:30:00-10:30:00</b><br>Table: <b>A4</b>",1,-1),
                    new Notification("<b>Peter Griffin</b> make a booking on <b>2019-12-23 09:30:00-10:30:00</b><br>(Table: <b>A4</b>)",-1,1),
                    new Notification("You make a booking in <b>Makan Kitchen @ DoubleTree by Hilton Hotel Melaka</b><br>Date & Time: <b>2020-03-08 19:00:00-21:00:00</b><br>Table: <b>B2</b>",1,-1),
                    new Notification("<b>Peter Griffin</b> make a booking on <b>2020-03-08 19:00:00-21:00:00</b><br>(Table: <b>B2</b>)",-1,3),
                    new Notification("You cancelled the order of <b>Makan Kitchen @ DoubleTree by Hilton Hotel Melaka</b><br><b>(B2, 2020-03-08 19:00:00-21:00:00)",1,-1),
                    new Notification("<b>Peter Griffin</b> cancelled the order<br><b>(B2, 2020-03-08 19:00:00-21:00:00)",-1,3),
                    new Notification("You make a booking in <b>De.Wan 1958 by Chef Wan Kuala Lumpur (The LINC KL)</b><br>Date & Time: <b>2021-08-31 21:30:00-22:30:00</b><br>Table: <b>A1</b>",1,-1),
                    new Notification("<b>Peter Griffin</b> make a booking on <b>2021-08-31 21:30:00-22:30:00</b><br>(Table: <b>A1</b>)",-1,5),
                    new Notification("<b>De.Wan 1958 by Chef Wan Kuala Lumpur (The LINC KL)</b> cancelled your order<br><b>(A1, 2021-08-31 21:30:00-22:30:00)</b>",1,-1),
                    new Notification("You cancelled the order from <b>Peter Griffin</b><br><b>(A1,2021-08-31 21:30:00-22:30:00)</b>",-1,5),
                    new Notification("You make a booking in <b>Atmosphere 360</b><br>Date & Time: <b>2022-12-23 08:00:00-10:00:00</b><br>Table: <b>X1</b>",1,-1),
                    new Notification("<b>Peter Griffin</b> make a booking on <b>2022-12-23 08:00:00-10:00:00</b><br>(Table: <b>X1</b>)",-1,2),
                    new Notification("You make a booking in <b>The 19th SUZUKI SHOTEN @ Publika</b><br>Date & Time: <b>2023-02-23 09:30:00-10:30:00</b><br>Table: <b>A1</b>",1,-1),
                    new Notification("<b>Peter Griffin</b> make a booking on <b>2023-02-23 09:30:00-10:30:00</b><br>(Table: <b>A1</b>)",-1,4),
                    new Notification("You make a booking in <b>The 19th SUZUKI SHOTEN @ Publika</b><br>Date & Time: <b>2023-01-30 09:30:00-10:30:00</b><br>Table: <b>F1</b>",1,-1),
                    new Notification("<b>Peter Griffin</b> make a booking on <b>2023-02-23 09:30:00-10:30:00</b><br>(Table: <b>F1</b>)",-1,4),
                    new Notification("You make a booking in <b>The 19th SUZUKI SHOTEN @ Publika</b><br>Date & Time: <b>2023-01-01 20:30:00-22:30:00</b><br>Table: <b>A2</b>",1,-1),
                    new Notification("<b>Peter Griffin</b> make a booking on <b>2023-01-01 20:30:00-22:30:00</b><br>(Table: <b>A2</b>)",-1,4),
                    new Notification("You make a booking in <b>The 19th SUZUKI SHOTEN @ Publika</b><br>Date & Time: <b>2023-01-01 20:30:00-22:30:00</b><br>Table: <b>A3</b>",1,-1),
                    new Notification("<b>Peter Griffin</b> make a booking on <b>2023-01-01 20:30:00-22:30:00</b><br>(Table: <b>A3</b>)",-1,4),
                    new Notification("You make a booking in <b>The 19th SUZUKI SHOTEN @ Publika</b><br>Date & Time: <b>2022-12-23 09:30:00-10:30:00</b><br>Table: <b>A1</b>",1,-1),
                    new Notification("<b>Peter Griffin</b> make a booking on <b>2022-12-23 09:30:00-10:30:00</b><br>(Table: <b>A1</b>)",-1,4),
                    new Notification("You make a booking in <b>The 19th SUZUKI SHOTEN @ Publika</b><br>Date & Time: <b>2022-12-01 09:30:00-10:30:00</b><br>Table: <b>A1</b>",1,-1),
                    new Notification("<b>Peter Griffin</b> make a booking on <b>2022-12-01 09:30:00-10:30:00</b><br>(Table: <b>A1</b>)",-1,4),
                    new Notification("You make a booking in <b>The 19th SUZUKI SHOTEN @ Publika</b><br>Date & Time: <b>2022-12-01 09:30:00-10:30:00</b><br>Table: <b>A2</b>",1,-1),
                    new Notification("<b>Peter Griffin</b> make a booking on <b>2022-12-01 09:30:00-10:30:00</b><br>(Table: <b>A2</b>)",-1,4),
                    new Notification("You make a booking in <b>The 19th SUZUKI SHOTEN @ Publika</b><br>Date & Time: <b>2022-12-01 09:30:00-10:30:00</b><br>Table: <b>A4</b>",1,-1),
                    new Notification("<b>Peter Griffin</b> make a booking on <b>2022-12-01 09:30:00-10:30:00</b><br>(Table: <b>A4</b>)",-1,4),
                    new Notification("You make a booking in <b>The 19th SUZUKI SHOTEN @ Publika</b><br>Date & Time: <b>2023-01-01 20:30:00-21:00:00</b><br>Table: <b>A3</b>",1,-1),
                    new Notification("<b>Peter Griffin</b> make a booking on <b>2023-01-01 20:30:00-21:00:00</b><br>(Table: <b>A3</b>)",-1,4),
                    new Notification("<b>The 19th SUZUKI SHOTEN @ Publika</b> cancelled your order<br><b>(A3, 2023-01-01 20:30:00-21:00:00)</b>",1,-1),
                    new Notification("You cancelled the order from <b>Peter Griffin</b><br><b>(A3,2023-01-01 20:30:00-21:00:00)</b>",-1,4),
                    new Notification("You make a booking in <b>The 19th SUZUKI SHOTEN @ Publika</b><br>Date & Time: <b>2023-01-02 20:30:00-21:30:00</b><br>Table: <b>A3</b>",1,-1),
                    new Notification("<b>Peter Griffin</b> make a booking on <b>2023-01-02 20:30:00-21:30:00</b><br>(Table: <b>A3</b>)",-1,4),
                    new Notification("You cancelled the order of <b>The 19th SUZUKI SHOTEN @ Publika</b><br><b>(A3, 2020-03-08 19:00:00-21:00:00)",1,-1),
                    new Notification("<b>Peter Griffin</b> cancelled the order<br><b>(A3, 2023-01-02 20:30:00-21:30:00)",-1,4)
            );

            bookingContainMenuDAO.insertContains(
                    new BookingContainMenu(5,37,1),
                    new BookingContainMenu(6,38,1),
                    new BookingContainMenu(7,38,1),
                    new BookingContainMenu(8,48,5),
                    new BookingContainMenu(8,44,3),
                    new BookingContainMenu(8,43,1),
                    new BookingContainMenu(9,40,1),
                    new BookingContainMenu(9,46,2),
                    new BookingContainMenu(10,42,2),
                    new BookingContainMenu(11,38,3),
                    new BookingContainMenu(12,39,2),
                    new BookingContainMenu(13,41,1),
                    new BookingContainMenu(13,45,1),
                    new BookingContainMenu(14,47,1)
                    );

        }

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

        { //Makan Kitchen @ DoubleTree by Hilton Hotel Melaka
            Restaurant restaurant = new Restaurant(
                    "HiltonMelaka",
                    "HiltonMelaka123",
                    "Makan Kitchen @ DoubleTree by Hilton Hotel Melaka",
                    "+6062223333",
                    (float)50,
                    "Level 12, DoubleTree by Hilton Hotel Melaka, Hatten City, Jalan Melaka Raya 23, 75000 Melaka.",
                    "Mon - Fri:\n" +
                            "Breakfast: 6:00am - 10:30am\n" +
                            "\n" +
                            "Sat - Sun, Public Holidays:\n" +
                            "Breakfast: 6:00am - 11:00am\n" +
                            "\n" +
                            "Daily:\n" +
                            "Dinner: 6:30pm - 10:00pm",
                    "MasterCard, Visa, AMEX",
                    "",
                    null,
                    null,
                    "https://eatdrinkhilton.com/hotel/doubletree-melaka/",
                    Cuisine.CHINESE,
                    "Indulge in an authentic regional Malaysian dining experience at Makan Kitchen in the DoubleTree by Hilton Melaka. Live interactive show kitchens showcase the enormous diversity of Malaysian cuisine and you will experience the journey from the raw ingredients to the pan and plate. The restaurant's se veral cooking stations feature iconic specialties from Malay, Peranakan, Portuguese, Chinese, and Indian cooking traditions, giving a true flavor of the area's local cuisines.",
                    "https://cf.bstatic.com/xdata/images/hotel/max1280x900/221840922.jpg?k=a5a615534e1436b8763178f9368ac7f7ff10e1d1efcce5da3fe6f5ed717809de&o=&hp=1"
            );
            restaurant.setImages(
                    "https://d16jvv1mxapgw7.cloudfront.net/cover_makan_kitchen_doubletree_hilton_melaka.jpg",
                    "https://d3do4qdmxa1ui8.cloudfront.net/mk_mkzmm_env2.jpg");
            long restaurantId = restaurantDAO.insertRestaurants(restaurant)[0];

            tableDAO.insertTables(new Table((int) restaurantId, "A1", 2),
                    new Table((int) restaurantId, "A2", 2),
                    new Table((int) restaurantId, "A3", 2),
                    new Table((int) restaurantId, "A4", 2),
                    new Table((int) restaurantId, "B1", 3),
                    new Table((int) restaurantId, "B2", 3),
                    new Table((int) restaurantId, "B3", 3),
                    new Table((int) restaurantId, "C1", 4),
                    new Table((int) restaurantId, "C2", 4),
                    new Table((int) restaurantId, "C3", 4),
                    new Table((int) restaurantId, "D1", 5),
                    new Table((int) restaurantId, "D2", 5),
                    new Table((int) restaurantId, "D3", 5),
                    new Table((int) restaurantId, "D4", 5),
                    new Table((int) restaurantId, "D5", 5),
                    new Table((int) restaurantId, "D6", 5)
            );

            menuDAO.insertMenus(
                    new MenuItem(
                            "Hot & Sweet ‘Pisang Goreng’",
                            16f,
                            "Coconut battered banana fritter, hippie pandanus, sweet peanut butter and Chilli-soya dip",
                            (int) restaurantId, "AXIS LOUNGE", ""),
                    new MenuItem(
                            "Cheesy Potato Wedges",
                            18f,
                            "",
                            (int) restaurantId, "AXIS LOUNGE", ""),
                    new MenuItem(
                            "‘Half & Half’ Fries",
                            18f,
                            "Sesame coated & tempura fried straight-cut Idaho Potato, Chilli-Cheese-Mayo dip",
                            (int) restaurantId, "AXIS LOUNGE", ""),
                    new MenuItem(
                            "Selection Of Premium Artisan Gelato And Sorbet (2 Scoop)",
                            18f,
                            "After Eight ( Mint Chocolate )\n" +
                                    "Vanigalia Del Madagascar ( Vanilla )\n" +
                                    "Strawberry Yogurt\n" +
                                    "Belgian Chocolate\n" +
                                    "Cookies and Cream\n" +
                                    "Passion Fruit sorbet\n" +
                                    "Lemon Sorbet",
                            (int) restaurantId, "AXIS LOUNGE", "https://eatdrinkhilton.com/wp-content/uploads/2022/03/al_mlk2.jpg"),
                    new MenuItem(
                            "Aglio olio (V)",
                            28f,
                            "Capsicum, tomato cherry, garlic, parsley and chili flakes",
                            (int) restaurantId, "TOSCA ITALIAN TRATTORIA", "https://eatdrinkhilton.com/wp-content/uploads/2022/03/Untitled-2-1.jpg"),
                    new MenuItem(
                            "Pollo di Primavera (C) ",
                            55f,
                            "(Cooking time 1/2hr)\n" +
                                    "Brick oven roasted honey glazed spring chicken, \n" +
                                    "home-fried potato and green chili-coriander risotto",
                            (int) restaurantId, "TOSCA ITALIAN TRATTORIA", ""),
                    new MenuItem(
                            "Selection Of Premium Artisan Gelato And Sorbetto (2 Scoops) ",
                            18f,
                            "Handmade ice cream and sorbet of your choice\n" +
                                    "• After Eight (Mint-chocolate) - Passion Fruit Sorbet (V)\n" +
                                    "• Lemon Sorbet (V) - Cookies & Cream \n" +
                                    "• Vaniglia Del Madagascar (Vanilla) - Belgian Chocolate\n" +
                                    "• Strawberry yoghurt",
                            (int) restaurantId, "TOSCA ITALIAN TRATTORIA", ""),
                    new MenuItem(
                            "Trattoria Special- Sharing (Serves 2) Carbonade Valdostana (C)",
                            160f,
                            "Chef’s special an aromatic vacuum cooked hickory\n" +
                                    "smoked short rib of beef on creamy polenta\n" +
                                    "Roesti potato with sour cream and chives \n" +
                                    "Roasted medley vegetables & mushroom fritto\n" +
                                    "Its own jus, chimichurri sauce \n" +
                                    "(Pre Order Only)\n",
                            (int) restaurantId, "TOSCA ITALIAN TRATTORIA", ""),
                    new MenuItem(
                            "Little Indian Station",
                            0f,
                            "o Roti Pratha\n" +
                                    "o Dhall Curry \n" +
                                    "o Fish curry",
                            (int) restaurantId, "MAKAN KITCHEN", ""),
                    new MenuItem(
                            "Porridge",
                            0f,
                            "Plain/ Chicken/ Fish congee \n" +
                                    "Served with condiments, soya sauce & sesame oil",
                            (int) restaurantId, "MAKAN KITCHEN", ""),
                    new MenuItem(
                            "Nyonya Style Fried rice",
                            0f,
                            "",
                            (int) restaurantId, "MAKAN KITCHEN", ""),
                    new MenuItem(
                            "Fried Noodle of the Day",
                            0f,
                            "",
                            (int) restaurantId, "MAKAN KITCHEN", ""),
                    new MenuItem(
                            "Herbal Tea Leaf Egg",
                            0f,
                            "",
                            (int) restaurantId, "MAKAN KITCHEN", ""),
                    new MenuItem(
                            "Assorted Pao",
                            0f,
                            "",
                            (int) restaurantId, "MAKAN KITCHEN", ""),
                    new MenuItem(
                            "Butter Plain Croissant",
                            0f,
                            "",
                            (int) restaurantId, "MAKAN KITCHEN", ""),
                    new MenuItem(
                            "Assorted Dim Sum",
                            0f,
                            "",
                            (int) restaurantId, "MAKAN KITCHEN", ""),
                    new MenuItem(
                            "Pain Au Chocolate",
                            0f,
                            "",
                            (int) restaurantId, "MAKAN KITCHEN", "")
            );
        }

        { //The 19th SUZUKI SHOTEN @ Publika
            Restaurant restaurant = new Restaurant(
                    "SuzukiShoten19",
                    "Suzuki19",
                    "The 19th SUZUKI SHOTEN @ Publika",
                    "+60362114994",
                    (float)350,
                    "A3-G2-6 Publika, No.1, Jalan Dutamas 1, Solaris Dutamas, 50480 Kuala Lumpur, Malaysia.",
                    "Tuesday - Sunday: 6:00PM - 10:00PM",
                    "Mastercard, Visa",
                    "",
                    null,
                    null,
                    "http://suzuki-shoten.com.my/shops/publika.html",
                    Cuisine.JAPANESE,
                    "The 19th SUZUKI SHOTEN offers private space where customers can enjoy Sushi and Washoku Omakase course prepared by Japanese chef. Here, you can have the best Omakase dining experience with curated Japanese rice, fresh seafood imported directly from Tokyo's Toyosu market, dashimaki tamago, and more. Also, the unique sake pairing curated by our sake specialist is a must-try, so don’t miss it! Visit us for an affordable yet authentic Omakase in a casual atmosphere.",
                    "https://d16jvv1mxapgw7.cloudfront.net/cover_the_19th_suzuki_shoten_oct2022_202210210210.jpeg"
            );
            restaurant.setImages(
                    "https://discoverkl.com/wp-content/uploads/sites/20/2019/02/Feature-Image-3-1024x511.png",
                    "https://discoverkl.com/wp-content/uploads/sites/20/2019/02/19-Suzuki.png",
                    "https://discoverkl.com/wp-content/uploads/sites/20/2019/02/Shuseki.jpg",
                    "https://discoverkl.com/wp-content/uploads/sites/20/2019/02/Oribe-1.jpg",
                    "https://discoverkl.com/wp-content/uploads/sites/20/2019/02/sakekami_products_b_49_800x.jpg");
            long restaurantId = restaurantDAO.insertRestaurants(restaurant)[0];

            tableDAO.insertTables(new Table((int) restaurantId, "A1", 1),
                    new Table((int) restaurantId, "A2", 1),
                    new Table((int) restaurantId, "A3", 1),
                    new Table((int) restaurantId, "A4", 1),
                    new Table((int) restaurantId, "B1", 2),
                    new Table((int) restaurantId, "B2", 2),
                    new Table((int) restaurantId, "B3", 2),
                    new Table((int) restaurantId, "C1", 3),
                    new Table((int) restaurantId, "C2", 3),
                    new Table((int) restaurantId, "C3", 3),
                    new Table((int) restaurantId, "D1", 4),
                    new Table((int) restaurantId, "D2", 4),
                    new Table((int) restaurantId, "D3", 4),
                    new Table((int) restaurantId, "D4", 4),
                    new Table((int) restaurantId, "D5", 4),
                    new Table((int) restaurantId, "D6", 4),
                    new Table((int) restaurantId, "E1", 5),
                    new Table((int) restaurantId, "E2", 5),
                    new Table((int) restaurantId, "F1", 6),
                    new Table((int) restaurantId, "F2", 6),
                    new Table((int) restaurantId, "G1", 7),
                    new Table((int) restaurantId, "G2", 7),
                    new Table((int) restaurantId, "H1", 8),
                    new Table((int) restaurantId, "H2", 8)
            );

            menuDAO.insertMenus(
                    new MenuItem(
                            "Tuna Sashimi",
                            72f,
                            "Limited Quantity",
                            (int) restaurantId, "LUNCH", ""),
                    new MenuItem(
                            "Special Horse Mackerel\n" +
                                    "Merinated Soya Sauce",
                            55f,
                            "",
                            (int) restaurantId, "LUNCH", ""),
                    new MenuItem(
                            "Mapo Tohu Lunch Set",
                            29f,
                            "",
                            (int) restaurantId, "LUNCH", ""),
                    new MenuItem(
                            "Trout Salmon Two Taste Don",
                            45f,
                            "",
                            (int) restaurantId, "LUNCH", ""),
                    new MenuItem(
                            "Stir Fried Chicken & Veggie Lunch Set",
                            28f,
                            "",
                            (int) restaurantId, "LUNCH", ""),
                    new MenuItem(
                            "2 Choice Karaage Lunch Set ",
                            29f,
                            "",
                            (int) restaurantId, "LUNCH", ""),
                    new MenuItem(
                            "Shoten Original Ramen",
                            29f,
                            "",
                            (int) restaurantId, "LUNCH", ""),
                    new MenuItem(
                            "生本鮪 赤身",
                            15f,
                            "",
                            (int) restaurantId, "Sushi A La Carte", "https://i.unu.edu/media/ourworld.unu.edu-en/article/2254/debate-2-0-will-you-eat-the-last-bluefin.jpg"),
                    new MenuItem(
                            "活き〆鯛",
                            15f,
                            "",
                            (int) restaurantId, "Sushi A La Carte", ""),
                    new MenuItem(
                            "平目昆布〆",
                            30f,
                            "",
                            (int) restaurantId, "Sushi A La Carte", ""),
                    new MenuItem(
                            "炙りノドグロ",
                            50f,
                            "",
                            (int) restaurantId, "Sushi A La Carte", ""),
                    new MenuItem(
                            "むらさき雲丹",
                            65f,
                            "",
                            (int) restaurantId, "Sushi A La Carte", "https://shop.r10s.jp/f013331-shiriuchi/cabinet/item3/pp077_2.jpg")
            );
        }
        { //De.Wan 1958 by Chef Wan Kuala Lumpur (The LINC KL)
            Restaurant restaurant = new Restaurant(
                    "De.WanLINCKL",
                    "De.WanLINCKL1958",
                    "De.Wan 1958 by Chef Wan Kuala Lumpur (The LINC KL)",
                    "+60102070383",
                    (float) 70,
                    "Lot 2-2, Level 2, The LINC KL, No. 360, Jalan Tun Razak, 50400 Kuala Lumpur",
                    "Monday - Friday:\n" +
                            "Lunch: 1st Session: 11:00am/11:30am / 2nd Session: 12:30pm/1:00pm (last call 2:00pm)\n" +
                            "Afternoon Tea: 2:30pm (last call 3:30pm)\n" +
                            "Dinner: 1st Session: 6:00pm/6:30pm / 2nd Session: 8:00pm/8:30pm (last call 9:30pm)\n" +
                            "\n" +
                            "Saturday, Sunday & Public Holiday:\n" +
                            "Lunch: 1st Session: 11:00am/11:30am / 2nd Session: 1:00pm/1:30pm (last call 5:00pm)\n" +
                            "Afternoon Tea: 1st Session: 3:00pm / 2nd Session: 4:00pm (last call 5:00pm)\n" +
                            "Dinner: 1st Session: 6:00pm/6:30pm / 2nd Session: 8:00pm/8:30pm (last call 9:30pm)",
                    "MasterCard, Visa, AMEX, JCB, UnionPay",
                    "",
                    null,
                    null,
                    "https://dewan.space/",
                    Cuisine.MALAYSIAN,
                    "De.Wan 1958 by Chef Wan is located on Level 2 of THE LINC, an upcoming lifestyle retail centre in town. Inspired by greenery, the mall is designed to bring visitors back to nature with its luscious green plants and decorations. The interior design concept of the restaurant comes from the context of Chef Wan’s fun and cheery character. Colours from a tropical palette are chosen to evoke the feel of sun, freshness and cool vibes in an urban setting. Our entire restaurant and event hall can accommodate up to 450 seated dining, or 600 in theatre style setting, or 650 standing guests and fully fitted with audio-visual equipment, and stage. When the occasion calls for, we can provide security, valet parking services as well as red carpet entryway to make your VVIPs feel extra special.",
                    "https://d16jvv1mxapgw7.cloudfront.net/cover_dewan_the_linc_kl_feb2021.png"
            );
            restaurant.setImages(
                    "https://d3do4qdmxa1ui8.cloudfront.net/de_wan_dish1_thumb.jpg",
                    "https://d3do4qdmxa1ui8.cloudfront.net/de_wan_dish5_thumb.jpg",
                    "https://d3do4qdmxa1ui8.cloudfront.net/de_wan_by_chef_wan_env1_thumb.jpg",
                    "https://d3do4qdmxa1ui8.cloudfront.net/de_wan_by_chef_wan_env2_thumb.jpg",
                    "https://d1zjeba1okg8co.cloudfront.net/1667957684dewan_food_menu_nov2022.jpg");
            long restaurantId = restaurantDAO.insertRestaurants(restaurant)[0];

            tableDAO.insertTables(new Table((int) restaurantId, "A1", 1),
                    new Table((int) restaurantId, "A2", 1),
                    new Table((int) restaurantId, "A3", 1),
                    new Table((int) restaurantId, "A4", 1),
                    new Table((int) restaurantId, "B1", 2),
                    new Table((int) restaurantId, "B2", 2),
                    new Table((int) restaurantId, "B3", 2),
                    new Table((int) restaurantId, "C1", 3),
                    new Table((int) restaurantId, "C2", 3),
                    new Table((int) restaurantId, "C3", 3),
                    new Table((int) restaurantId, "D1", 4),
                    new Table((int) restaurantId, "D2", 4),
                    new Table((int) restaurantId, "D3", 4),
                    new Table((int) restaurantId, "D4", 4),
                    new Table((int) restaurantId, "D5", 4),
                    new Table((int) restaurantId, "D6", 4),
                    new Table((int) restaurantId, "E1", 5),
                    new Table((int) restaurantId, "E2", 5),
                    new Table((int) restaurantId, "F1", 6),
                    new Table((int) restaurantId, "F2", 6),
                    new Table((int) restaurantId, "G1", 7),
                    new Table((int) restaurantId, "G2", 7),
                    new Table((int) restaurantId, "H1", 8),
                    new Table((int) restaurantId, "H2", 8)
            );

            menuDAO.insertMenus(
                    new MenuItem(
                            "DE.WAN YEE SANG WITH ABALONE",
                            188f,
                            "Available from 14th Jan to 15th Feb 2023",
                            (int) restaurantId, "CHEF WAN'S PROSPERITY YEE SANG", "https://trysmartbite.imgix.net/images/food/c63eb260-3d9c-4adf-82a8-fddefb38e8a3.jpg?auto=format,compress&fit=crop&dpr=2"),
                    new MenuItem(
                            "DE.WAN YEE SANG WITH SMOKED SALMON",
                            118f,
                            "Available from 14th Jan to 15th Feb 2023",
                            (int) restaurantId, "CHEF WAN'S PROSPERITY YEE SANG", "https://trysmartbite.imgix.net/images/food/7880aae1-c40a-42a4-976c-83920e267900.jpg?auto=format,compress&fit=crop&dpr=2"),
                    new MenuItem(
                            "DE.WAN YEE SANG WITH SNOW PEAR",
                            88f,
                            "Available from 14th Jan to 15th Feb 2023",
                            (int) restaurantId, "CHEF WAN'S PROSPERITY YEE SANG", "https://trysmartbite.imgix.net/images/food/ac530392-759b-4443-b356-dbc8e1c9ea35.jpg?auto=format,compress&fit=crop&dpr=2"),
                    new MenuItem(
                            "De Wan Family Set A (4-5 pax)",
                            458f,
                            "Appetizers\n" +
                                    "Satay Ayam with Condiments\n" +
                                    "Kerabu Pomelo with crispy Prawn Cheeks\n\n" +
                                    "Mains\n" +
                                    "Live Seabass Asam Pedas\n" +
                                    "Kambing Kuzi\n" +
                                    "Ayam Dara Panggang Istimewa\n" +
                                    "Sotong Kari Hijau Telur Asin\n" +
                                    "Peria Goreng Ikan Masin\n\n" +
                                    "Rice\n" +
                                    "Nasi Pandan Delima OR Nasi Telur Terengganu OR Nasi Putih\n\n" +
                                    "Desserts\n" +
                                    "Fresh Tropical Fruits",
                            (int) restaurantId, "Family Set", "https://trysmartbite.imgix.net/images/food/ab7c82d8-6ab7-4cad-8c00-13b2710d62c7.png?auto=format,compress&fit=crop&dpr=2"),
                    new MenuItem(
                            "De Wan Family Set B (4-5 pax)",
                            688f,
                            "Appetizers\n" +
                                    "Kerabu Pucuk Paku with Kerang\n" +
                                    "Ayam/Daging/Mix Satay with Condiments\n\n" +
                                    "Soup\n" +
                                    "Sup Ketam Singgang & Homemade Roti\n\n" +
                                    "Mains\n" +
                                    "Live Jenahak Bakar (Whole)\n" +
                                    "Ayam Kapitan\n" +
                                    "Daging Rendang Tok Wan\n" +
                                    "Udang Sambal Petai\n" +
                                    "Stir-fried Broccoli with soft Beancurd\n\n" +
                                    "Rice\n\n" +
                                    "Nasi Pandan Delima OR Nasi Telur Terengganu OR Nasi Putih\n\n" +
                                    "Desserts\n" +
                                    "Fresh Tropical Fruits\n" +
                                    "Custard Nangka Madu",
                            (int) restaurantId, "Family Set", "https://trysmartbite.imgix.net/images/food/3cbecec5-4b1e-4b3a-bf6a-ff8b3982acb6.png?auto=format,compress&fit=crop&dpr=2"),
                    new MenuItem(
                            "Daging Rendang Tok Wan",
                            88f,
                            "Premium beef tenderloin stewed in creamy coconut, and infused with the rich flavour of local spices, aromatic kerisik, and fresh grated coconut.",
                            (int) restaurantId, "Mains (Sharing)", ""),
                    new MenuItem(
                            "Ayam Kapitan",
                            48f,
                            "An Aromatic chicken curry dish, cooked with a unique mixture of local herbs and spices. The perfect amalgamation of our multicultural Malaysia roots.",
                            (int) restaurantId, "Mains (Sharing)", ""),
                    new MenuItem(
                            "Chef Wan's Ayam Kampung Goreng Lengkuas (Half)",
                            78f,
                            "Organic free-range chicken, cut into perfect pieces, carefully boiled and fried in spicy and fragrant galangal paste,\n" +
                                    "\n" +
                                    "Served with crunchy kruk kruk and generously topped with flavourful belado sauce",
                            (int) restaurantId, "Mains (Sharing)", "https://trysmartbite.imgix.net/images/food/683f8be2-c5b2-427d-b30d-157cab84ff98.jpg?auto=format,compress&fit=crop&dpr=2"),
                    new MenuItem(
                            "Daging Salai Lemak Cili Padi",
                            88f,
                            "Smoked beef tenderloin, slow-cooked in creamy coconut milk and aromatic spice paste, flavoured with hot chili padi and fresh turmeric leaves.",
                            (int) restaurantId, "Mains (Sharing)", ""),
                    new MenuItem(
                            "Kambing Kurma",
                            78f,
                            "Tender Australian lamb shank simmered in a creamy spicy sauce, steeped with rich aromas and flavoured with coriander and cumin.",
                            (int) restaurantId, "Mains (Sharing)", ""),
                    new MenuItem(
                            "Cruzan Macadamia Mini",
                            28f,
                            "",
                            (int) restaurantId, "Mini Cakes (Available at The Linc Only)", "https://trysmartbite.imgix.net/images/food/cb840abc-3e06-4e5c-8613-dfaaa08359f3.jpg?auto=format,compress&fit=crop&dpr=2"),
                    new MenuItem(
                            "Original Choc Mini",
                            28f,
                            "",
                            (int) restaurantId, "Mini Cakes (Available at The Linc Only)", "https://trysmartbite.imgix.net/images/food/7a441c67-341d-4f8b-9bea-c1878896aa55.jpg?auto=format,compress&fit=crop&dpr=2")
            );
        }

        { //FLOUR Restaurant
            Restaurant restaurant = new Restaurant(
                    "FLOUR",
                    "FLOUR2023",
                    "FLOUR Restaurant",
                    "+60129600053",
                    (float)900,
                    "No. 12 & 14, Jalan Kamuning, Off Jalan Imbi, 55100 Kuala Lumpur, Wilayah Persekutuan Kuala Lumpur",
                    "Wed - Mon: 6:00pm - 10:00pm\n" +
                            "\n" +
                            "Closed on Tuesdays",
                    "Mastercard, Visa, AMEX, GrabPay",
                    "",
                    null,
                    null,
                    "https://flourrestaurant.com.my/",
                    Cuisine.INDIAN,
                    "FLOUR® showcases a new, modern fine Indian cuisine by redefining, recreating, and reinventing through deep knowledge and technique of application of spices and herbs. Located at the heart of the bustling city of Kuala Lumpur, our customers come from all around the world, be it United States of Amer ica, Europe, North Asia, South East Asia, to local Malaysians. Come to appreciate, get exposed, and learn about our culinary creations. Enjoy our hospitality and ambience with French touch for an unforgettable meal.",
                    "https://d16jvv1mxapgw7.cloudfront.net/cover_flour_restaurant_aug2022_202208290520.jpg"
            );
            restaurant.setImages(
                    "https://d3do4qdmxa1ui8.cloudfront.net/flour_restaurant_env4_aug2022_thumb_202208290538.jpg",
                    "https://d3do4qdmxa1ui8.cloudfront.net/flour_restaurant_dish2_aug2022_thumb_202208290535.jpg",
                    "https://d3do4qdmxa1ui8.cloudfront.net/flour_restaurant_dish3_aug2022_thumb_202208290535.jpg",
                    "https://d3do4qdmxa1ui8.cloudfront.net/flour_restaurant_dish4_aug2022_thumb_202208290535.jpg",
                    "https://d3do4qdmxa1ui8.cloudfront.net/flour_restaurant_env6_aug2022_thumb_202208290540.jpg");
            long restaurantId = restaurantDAO.insertRestaurants(restaurant)[0];

            tableDAO.insertTables(new Table((int) restaurantId, "A1", 1),
                    new Table((int) restaurantId, "A2", 1),
                    new Table((int) restaurantId, "A3", 1),
                    new Table((int) restaurantId, "A4", 1),
                    new Table((int) restaurantId, "B1", 2),
                    new Table((int) restaurantId, "B2", 2),
                    new Table((int) restaurantId, "B3", 2),
                    new Table((int) restaurantId, "C1", 3),
                    new Table((int) restaurantId, "C2", 3),
                    new Table((int) restaurantId, "C3", 3),
                    new Table((int) restaurantId, "D1", 4),
                    new Table((int) restaurantId, "D2", 4),
                    new Table((int) restaurantId, "D3", 4),
                    new Table((int) restaurantId, "D4", 4),
                    new Table((int) restaurantId, "D5", 4),
                    new Table((int) restaurantId, "D6", 4),
                    new Table((int) restaurantId, "E1", 5),
                    new Table((int) restaurantId, "E2", 5),
                    new Table((int) restaurantId, "F1", 6),
                    new Table((int) restaurantId, "F2", 6),
                    new Table((int) restaurantId, "G1", 7),
                    new Table((int) restaurantId, "G2", 7),
                    new Table((int) restaurantId, "H1", 8),
                    new Table((int) restaurantId, "H2", 8)
            );

            menuDAO.insertMenus(
                    new MenuItem(
                            "आरंभ AARRAMBH",
                            650f,
                            "Origins, Part 1.",
                            (int) restaurantId, "AARRAMBH", ""),
                    new MenuItem(
                            "START.",
                            0f,
                            "Sago vada, scallop tartare, caviar.\n" +
                                    "Fafda, jalebi, wasabi.",
                            (int) restaurantId, "AARRAMBH", ""),
                    new MenuItem(
                            "LENTILS & ROOT.",
                            0f,
                            "Split moong lentils, caviar, onion cream.",
                            (int) restaurantId, "AARRAMBH", ""),
                    new MenuItem(
                            "VEGETABLE FARM.",
                            0f,
                            "Single farm french beans, asparagus, hung curd, juanita cherry tomatoes, greek cheese, raspberry purée, tamarind, herbs.",
                            (int) restaurantId, "AARRAMBH", ""),
                    new MenuItem(
                            "FISH.",
                            0f,
                            "Salmon, béarnaise, zucchini, buttered prawn.",
                            (int) restaurantId, "AARRAMBH", ""),
                    new MenuItem(
                            "MANGO LASSI.",
                            0f,
                            "Creamed mango pulp, caviar.",
                            (int) restaurantId, "AARRAMBH", ""),
                    new MenuItem(
                            "BIRD.",
                            0f,
                            "Charcoal roasted spring chicken, morel mushroom sauce, Japanese pumpkin, caviar.",
                            (int) restaurantId, "AARRAMBH", ""),
                    new MenuItem(
                            "PUMPKIN.",
                            0f,
                            "Baked butternut squash, Japanese pumpkin, pumpkin seeds and Jerusalem artichoke sauce, orange, basil oil.",
                            (int) restaurantId, "AARRAMBH", ""),
                    new MenuItem(
                            "CATTLE.",
                            0f,
                            "Charcoal grilled organic lamb, cucumber mint gel, spiced jus, king oyster mushroom, thepla.",
                            (int) restaurantId, "AARRAMBH", ""),
                    new MenuItem(
                            "WHITE APRICOT.",
                            0f,
                            "",
                            (int) restaurantId, "AARRAMBH", ""),
                    new MenuItem(
                            "END.",
                            0f,
                            "Cognac / masala cha / coffee.",
                            (int) restaurantId, "AARRAMBH", "")
            );
        }

        { //Rama V Fine Thai Cuisine
            Restaurant restaurant = new Restaurant(
                    "RamaVFineThai",
                    "RamaVFine2023",
                    "Rama V Fine Thai Cuisine",
                    "+60321432663",
                    (float) 45,
                    "No. 5, Jalan U Thant, Kuala Lumpur.",
                    "Daily: 12:00pm - 2:30pm, 6:00pm - 9:30pm",
                    "MasterCard, Visa, AMEX, JCB, UnionPay",
                    "",
                    null,
                    null,
                    "http://www.ramav.com.my/",
                    Cuisine.THAI,
                    "Experience the gracious elegance that is Rama V. A Thai Restaurant set amongst a magnificent pool of lotus blossoms, quietly tucked away from the busy district of Kuala Lumpur. Our innovative chefs cook up a blend of authentic and modern Thai dishes and present their creations as a feast not only fo r your palate but for your eyes. Herbs and natural seasonings are used to create a variety of colours and sensations in taste that makes a meal at Rama V memorable, exciting and enjoyable. So, head down to start your gastronomical culinary journey of Thai and soak in our cozy and laid-back ambience for an escape from everyday hustle and bustle.",
                    "https://d16jvv1mxapgw7.cloudfront.net/cover_ramav_jalanuthant_1.jpg"
            );
            restaurant.setImages(
                    "https://d3do4qdmxa1ui8.cloudfront.net/ramav_env4.jpg",
                    "https://d3do4qdmxa1ui8.cloudfront.net/ramav_env3_thumb.jpg",
                    "https://d3do4qdmxa1ui8.cloudfront.net/ramav_dish12_thumb.jpg",
                    "https://d3do4qdmxa1ui8.cloudfront.net/ramav_dish11_thumb.jpg",
                    "https://d3do4qdmxa1ui8.cloudfront.net/ramav_dish13_thumb.jpg");
            long restaurantId = restaurantDAO.insertRestaurants(restaurant)[0];

            tableDAO.insertTables(new Table((int) restaurantId, "A1", 1),
                    new Table((int) restaurantId, "A2", 1),
                    new Table((int) restaurantId, "A3", 1),
                    new Table((int) restaurantId, "A4", 1),
                    new Table((int) restaurantId, "B1", 2),
                    new Table((int) restaurantId, "B2", 2),
                    new Table((int) restaurantId, "B3", 2),
                    new Table((int) restaurantId, "C1", 3),
                    new Table((int) restaurantId, "C2", 3),
                    new Table((int) restaurantId, "C3", 3),
                    new Table((int) restaurantId, "D1", 4),
                    new Table((int) restaurantId, "D2", 4),
                    new Table((int) restaurantId, "D3", 4),
                    new Table((int) restaurantId, "D4", 4),
                    new Table((int) restaurantId, "D5", 4),
                    new Table((int) restaurantId, "D6", 4),
                    new Table((int) restaurantId, "E1", 5),
                    new Table((int) restaurantId, "E2", 5),
                    new Table((int) restaurantId, "F1", 6),
                    new Table((int) restaurantId, "F2", 6),
                    new Table((int) restaurantId, "G1", 7),
                    new Table((int) restaurantId, "G2", 7),
                    new Table((int) restaurantId, "H1", 8),
                    new Table((int) restaurantId, "H2", 8)
            );

            menuDAO.insertMenus(
                    new MenuItem(
                            "Rama 1",
                            160f,
                            "Suitable for 2 pax",
                            (int) restaurantId, "RAMA I", ""),
                    new MenuItem(
                            "Appetiser",
                            0f,
                            "Tangy Papaya Salad with Golden Stuffed Spring Roll",
                            (int) restaurantId, "RAMA I", ""),
                    new MenuItem(
                            "Soup",
                            0f,
                            "Rama V Red Tom Tam Kai",
                            (int) restaurantId, "RAMA I", ""),
                    new MenuItem(
                            "Main",
                            0f,
                            "Steamed Whole Seabass with Chilli Lime Sauce\n" +
                                    "Green Curry Chicken\n" +
                                    "Stir-fried Morning Glory with Shrimp Paste\n" +
                                    "Steamed Thai Jasmine Rice",
                            (int) restaurantId, "RAMA I", ""),
                    new MenuItem(
                            "Dessert",
                            0f,
                            "Chef's Dessert of the Day",
                            (int) restaurantId, "RAMA I", ""),
                    new MenuItem(
                            "Rama II",
                            178f,
                            "Suitable of 2 pax",
                            (int) restaurantId, "RAMA II", ""),
                    new MenuItem(
                            "Appetiser",
                            0f,
                            "Thai Fish Cake with Mango Salad and Crispy Fish",
                            (int) restaurantId, "RAMA II", ""),
                    new MenuItem(
                            "Soup",
                            0f,
                            "Rama V Red Tom Yam Kai",
                            (int) restaurantId, "RAMA II", ""),
                    new MenuItem(
                            "Main",
                            0f,
                            "Deep-fried Whole Seabass in Sweet Sour Spicy Sauce\n" +
                                    "Red Curry Chicken with Bamboo Shoot\n" +
                                    "Rama V Wok Fried Mixed Vegetables\n" +
                                    "Steamed Thai Jasmine Rice",
                            (int) restaurantId, "RAMA II", ""),
                    new MenuItem(
                            "Dessert",
                            0f,
                            "Chef's Dessert of the Day",
                            (int) restaurantId, "RAMA II", "")
            );
        }

        { //Pavilions Lounge @ Sheraton Imperial KL
            Restaurant restaurant = new Restaurant(
                    "PavilionsLounge",
                    "PavilionsLounge2023",
                    "Pavilions Lounge @ Sheraton Imperial KL",
                    "03-2717 9027",
                    (float) 100,
                    "129, Jalan Sultan Ismail, Chow Kit, 50250 Kuala Lumpur, Wilayah Persekutuan Kuala Lumpur, Malaysia",
                    "Daily: 09:00-01:00",
                    "Master Card, Visa",
                    "",
                    null,
                    "Child Friendly",
                    "http://www.pavilionslounge.com/",
                    Cuisine.HIGH_TEA_BUFFET,
                    "With its intimate and charming setting, Pavilions Lounge provides an ideal venue for a sensual yet cosy evening gathering with your family and friends.In addition to the relaxing ambiance and delightful menu, our talented musician will entertain you with soothing live tunes as you enjoy the evening hours at Pavilions Lounge.",
                    "https://ninjafound.com/wp-content/uploads/2019/05/ninjafound.com-Pavilions-Lounge-@-Sheraton-Imperial-Kuala-Lumpur-food-9.jpg"
            );
            restaurant.setImages(
                    "https://ninjafound.com/wp-content/uploads/2019/05/ninjafound.com-Pavilions-Lounge-@-Sheraton-Imperial-Kuala-Lumpur-food-8.jpg",
                    "https://ninjafound.com/wp-content/uploads/2019/05/ninjafound.com-Pavilions-Lounge-@-Sheraton-Imperial-Kuala-Lumpur-food-7.jpg",
                    "https://ninjafound.com/wp-content/uploads/2019/05/ninjafound.com-Pavilions-Lounge-@-Sheraton-Imperial-Kuala-Lumpur-food-1.jpg",
                    "https://ninjafound.com/wp-content/uploads/2019/05/ninjafound.com-Pavilions-Lounge-@-Sheraton-Imperial-Kuala-Lumpur-food-2.jpg",
                    "https://ninjafound.com/wp-content/uploads/2019/05/ninjafound.com-Pavilions-Lounge-@-Sheraton-Imperial-Kuala-Lumpur-food-4.jpg");
            long restaurantId = restaurantDAO.insertRestaurants(restaurant)[0];

            tableDAO.insertTables(new Table((int) restaurantId, "A1", 1),
                    new Table((int) restaurantId, "A2", 1),
                    new Table((int) restaurantId, "A3", 1),
                    new Table((int) restaurantId, "A4", 1),
                    new Table((int) restaurantId, "B1", 2),
                    new Table((int) restaurantId, "B2", 2),
                    new Table((int) restaurantId, "B3", 2),
                    new Table((int) restaurantId, "C1", 3),
                    new Table((int) restaurantId, "C2", 3),
                    new Table((int) restaurantId, "C3", 3),
                    new Table((int) restaurantId, "D1", 4),
                    new Table((int) restaurantId, "D2", 4),
                    new Table((int) restaurantId, "D3", 4),
                    new Table((int) restaurantId, "D4", 4),
                    new Table((int) restaurantId, "D5", 4),
                    new Table((int) restaurantId, "D6", 4),
                    new Table((int) restaurantId, "E1", 5),
                    new Table((int) restaurantId, "E2", 5),
                    new Table((int) restaurantId, "F1", 6),
                    new Table((int) restaurantId, "F2", 6),
                    new Table((int) restaurantId, "G1", 7),
                    new Table((int) restaurantId, "G2", 7),
                    new Table((int) restaurantId, "H1", 8),
                    new Table((int) restaurantId, "H2", 8)
            );

            menuDAO.insertMenus(
                    new MenuItem(
                            "Quesadilla",
                            52f,
                            "Toasted tortilla filled with grilled chicken, bell pepper, onion, crispy iceberg, guacamole, tomato salsa and chips",
                            (int) restaurantId, "Bar Snack Menu", ""),
                    new MenuItem(
                            "Imperial Long Chicken",
                            53f,
                            "Sundried tomato bun, caramelized onion sweet relish, honey mustard mayonnaise and spicy wedges",
                            (int) restaurantId, "Bar Snack Menu", ""),
                    new MenuItem(
                            "Swiss Cheese Plate",
                            54f,
                            "Brie, cheddar edam ball, emmentale served with dried fruit, grape and cracker",
                            (int) restaurantId, "Bar Snack Menu", ""),
                    new MenuItem(
                            "Club Sandwich",
                            55f,
                            "Grilled smoked beef brisket, chicken breast, egg, tomato, mayonnaise lettuce, fries, toasted ona white bread",
                            (int) restaurantId, "Bar Snack Menu", ""),
                    new MenuItem(
                            "Prime Beef Burger",
                            57f,
                            "Sesame bun, pickled dill, grilled onion, vine tomato and steak fries",
                            (int) restaurantId, "Bar Snack Menu", ""),
                    new MenuItem(
                            "Steak Sandwich",
                            58f,
                            "Beef steak on potato bread with fresh tomato, caramelized onion",
                            (int) restaurantId, "Bar Snack Menu", ""),
                    new MenuItem(
                            "Green Apple",
                            29f,
                            "",
                            (int) restaurantId, "Fresh Juices", ""),
                    new MenuItem(
                            "Watermelon",
                            29f,
                            "",
                            (int) restaurantId, "Fresh Juices", ""),
                    new MenuItem(
                            "Carrot",
                            29f,
                            "",
                            (int) restaurantId, "Fresh Juices", ""),
                    new MenuItem(
                            "Orange",
                            29f,
                            "",
                            (int) restaurantId, "Fresh Juices", ""),
                    new MenuItem(
                            "Pineapple",
                            29f,
                            "",
                            (int) restaurantId, "Fresh Juices", "")
            );
        }
        Log.d("dataInsert()", "Data insertion done.");
    }
}
