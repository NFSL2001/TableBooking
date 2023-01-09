package wia2007.project.tablebooking;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Timestamp;
import java.util.List;

import wia2007.project.tablebooking.converter.TimeConverter;
import wia2007.project.tablebooking.dao.BookingDAO;
import wia2007.project.tablebooking.dao.CustomerDAO;
import wia2007.project.tablebooking.dao.RestaurantDAO;
import wia2007.project.tablebooking.dao.TableDAO;
import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.Booking;
import wia2007.project.tablebooking.entity.Customer;
import wia2007.project.tablebooking.entity.Restaurant;
import wia2007.project.tablebooking.entity.Table;


public class ManageBookingFutureActivity extends AppCompatActivity {

    TextView Name, DateText, TableID, TableSize, RestaurantName, TimeText, Request;
    RecyclerView FoodList;
    FoodListAdapter foodListAdapter;
    Button EditBookingButton, CancelBookingButton, UpBackButton;
    int bookingID, customerID;
    long startTime, endTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_booking__future);

        Name = findViewById(R.id.manageBooking_Future_name);
        DateText = findViewById(R.id.manageBooking_Future_date);
        TableID = findViewById(R.id.manageBooking_Future_table);
        TableSize = findViewById(R.id.manageBooking_Future_person);
        RestaurantName = findViewById(R.id.manageBooking_Future_restaurantName);
        TimeText = findViewById(R.id.manageBooking_Future_time);
        Request = findViewById(R.id.manageBooking_Future_request);

        EditBookingButton = findViewById(R.id.manageBooking_Future_editBookingButton);
        CancelBookingButton = findViewById(R.id.manageBooking_Future_cancelBookingButton);
        UpBackButton = findViewById(R.id.manageBooking_Future_backButton);

        TableBookingDatabase database = TableBookingDatabase.getDatabase(getApplicationContext());

        BookingDAO bookingDAO = database.bookingDAO();
        CustomerDAO customerDAO = database.customerDAO();
        TableDAO tableDAO = database.tableDAO();
        RestaurantDAO restaurantDAO = database.restaurantDAO();

        List<Booking> bookingList = bookingDAO.getBookingById(bookingID);
        List<Customer> customerList = customerDAO.getCustomerById(customerID);
        List<Table> tableList = tableDAO.getTableById(bookingList.get(0).getTable_id());
        List<Restaurant> restaurantList = restaurantDAO.getRestaurantById(tableList.get(0).getRestaurant_id());

        startTime = TimeConverter.timeToTimestamp(bookingList.get(0).getStart_time());
        endTime = TimeConverter.timeToTimestamp(bookingList.get(0).getEnd_time());

        Timestamp startTS = new Timestamp(startTime);
        Timestamp endTS = new Timestamp(endTime);

        String[] Date = startTS.toString().split(" ");
        String[] Date2 = endTS.toString().split(" ");

        Name.setText(customerList.get(0).getUser_name());
        TableID.setText(bookingList.get(0).getTable_id());
        TableSize.setText(tableList.get(0).getSize());
        Request.setText(bookingList.get(0).getRemark());
        RestaurantName.setText(restaurantList.get(0).getRestaurant_name());
        DateText.setText(Date[0]);
        TimeText.setText(Date[1] + " " + Date2[1]);

        FoodList = findViewById(R.id.manageBooking_Future_foodList);

//        foodListAdapter = new FoodListAdapter();
//        FoodList.setAdapter(foodListAdapter);

        EditBookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        CancelBookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelBooking(bookingID);
            }
        });

        UpBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }


    public void cancelBooking(int bookingID) {
        getIntent().putExtra("bookingID", bookingID);

        Intent intent = new Intent(this, CancelBookingActivity.class);
        startActivity(intent);
    }

    public void openPreviousActivity() {
        Intent backIntent = new Intent(this, PreOrderFoodActivity.class);
        startActivity(backIntent);
    }

}