package wia2007.project.tablebooking;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import wia2007.project.tablebooking.converter.TimeConverter;
import wia2007.project.tablebooking.dao.BookingContainMenuDAO;
import wia2007.project.tablebooking.dao.BookingDAO;
import wia2007.project.tablebooking.dao.CustomerDAO;
import wia2007.project.tablebooking.dao.MenuDAO;
import wia2007.project.tablebooking.dao.RestaurantDAO;
import wia2007.project.tablebooking.dao.TableDAO;
import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.Booking;
import wia2007.project.tablebooking.entity.BookingContainMenu;
import wia2007.project.tablebooking.entity.Customer;
import wia2007.project.tablebooking.entity.Menu;
import wia2007.project.tablebooking.entity.MenuItem;
import wia2007.project.tablebooking.entity.Restaurant;
import wia2007.project.tablebooking.entity.Table;


public class ManageBookingCancelledActivity extends AppCompatActivity {

    TextView RestaurantName, Name, DateText, TimeText, Request, TableID, TableSize;
    RecyclerView FoodList;
    FoodListAdapter foodListAdapter;
    Button BackButton;

    long startTime, endTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_booking__cancelled);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        int bookingID = getIntent().getIntExtra("bookID", 0);
        int customerID = getIntent().getIntExtra("cusID", 0);
        int restaurantID = getIntent().getIntExtra("resID", 0);

        Name = findViewById(R.id.manageBooking_Cancelled_name);
        DateText = findViewById(R.id.manageBooking_Cancelled_date);
        TimeText = findViewById(R.id.manageBooking_Cancelled_time);
        TableID = findViewById(R.id.manageBooking_Cancelled_table);
        TableSize = findViewById(R.id.manageBooking_Cancelled_person);
        Request = findViewById(R.id.manageBooking_Cancelled_request);
        RestaurantName = findViewById(R.id.manageBooking_Cancelled_restaurantName);

        BackButton = findViewById(R.id.manageBooking_Cancelled_backButton);

        FoodList = findViewById(R.id.manageBooking_Cancelled_foodList);


        TableBookingDatabase database = TableBookingDatabase.getDatabase(getApplicationContext());
        BookingDAO bookingDAO = database.bookingDAO();
        CustomerDAO customerDAO = database.customerDAO();
        TableDAO tableDAO = database.tableDAO();
        RestaurantDAO restaurantDAO = database.restaurantDAO();
        MenuDAO menuDAO = database.menuDAO();
        BookingContainMenuDAO BCMDAO = database.bookingContainMenuDAO();

        List<Booking> bookingList = bookingDAO.getBookingById(bookingID);
        List<Customer> customerList = customerDAO.getCustomerById(customerID);
        List<Table> tableList = tableDAO.getTableById(bookingList.get(0).getTable_id());
        List<Restaurant> restaurantList = restaurantDAO.getRestaurantById(restaurantID);
        List<BookingContainMenu> BCMList = BCMDAO.getContainsByBookingId(bookingID);
        List<MenuItem> menuList = menuDAO.getMenuByRestaurant(restaurantID);

        List<Integer> MenuIDList= new ArrayList<>();
        List<String> MenuNameList = new ArrayList<>();
        List<Integer> MenuPriceList = new ArrayList<>();

        for(int i = 0; i < BCMList.size(); i++) {
            MenuIDList.add(BCMList.get(i).getMenu_id());
        }

        for(int i = 0; i < menuList.size(); i++) {
            if (MenuIDList.get(i) == menuList.get(i).getMenu_id()) {
                MenuNameList.add(menuList.get(i).getMenu_name());
                MenuPriceList.add(Math.round(menuList.get(i).getPrice()));
            }
        }


        startTime = TimeConverter.timeToTimestamp(bookingList.get(0).getStart_time());
        endTime = TimeConverter.timeToTimestamp(bookingList.get(0).getEnd_time());

        Timestamp startTS = new Timestamp(startTime);
        Timestamp endTS = new Timestamp(endTime);

        String[] Date = startTS.toString().split(" ");
        String[] Date2 = endTS.toString().split(" ");

        foodListAdapter = new FoodListAdapter(this, BCMList, MenuNameList, MenuPriceList);
        FoodList.setAdapter(foodListAdapter);

        Name.setText(customerList.get(0).getUser_name());
        TableID.setText(bookingList.get(0).getTable_id());
        TableSize.setText(tableList.get(0).getSize());
        Request.setText(bookingList.get(0).getRemark());
        RestaurantName.setText(restaurantList.get(0).getRestaurant_name());
        DateText.setText(Date[0]);
        TimeText.setText(Date[1] + " " + Date2[1]);

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelActivity();
            }
        });

    }

    public void cancelActivity() {
        Intent backIntent = new Intent(this, MainMenuFragment.class);
        startActivity(backIntent);
    }

}