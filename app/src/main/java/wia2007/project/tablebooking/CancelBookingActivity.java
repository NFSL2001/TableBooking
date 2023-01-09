package wia2007.project.tablebooking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Timestamp;
import java.util.List;

import wia2007.project.tablebooking.converter.TimeConverter;
import wia2007.project.tablebooking.dao.BookingContainMenuDAO;
import wia2007.project.tablebooking.dao.BookingDAO;
import wia2007.project.tablebooking.dao.CustomerDAO;
import wia2007.project.tablebooking.dao.RestaurantDAO;
import wia2007.project.tablebooking.dao.TableDAO;
import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.Booking;
import wia2007.project.tablebooking.entity.BookingContainMenu;
import wia2007.project.tablebooking.entity.Customer;
import wia2007.project.tablebooking.entity.Restaurant;
import wia2007.project.tablebooking.entity.Table;


public class CancelBookingActivity extends AppCompatActivity {

    TextView Name, TableSize, DateText, TimeText, TableID, RestaurantName;
    Button CancelBookingButton, BackButton;
    Spinner CancelReason;
    RecyclerView foodList;
    FoodListAdapter foodListAdapter;

    int bookingID;
    long startTime, endTime;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_booking);

        Name = findViewById(R.id.cancel_booking_name);
        TableSize = findViewById(R.id.cancel_booking_people);
        DateText = findViewById(R.id.cancel_booking_date);
        TimeText = findViewById(R.id.cancel_booking_time);
        TableID = findViewById(R.id.cancel_booking_table);
        RestaurantName = findViewById(R.id.cancel_booking_textTitle);


        CancelBookingButton = findViewById(R.id.cancel_booking_nextButton);
        BackButton = findViewById(R.id.cancel_booking_backButton);

        CancelReason = findViewById(R.id.cancel_booking_spinner);

        foodList = findViewById(R.id.cancel_booking_foodList);

        TableBookingDatabase db = TableBookingDatabase.getDatabase(getApplicationContext());
        BookingDAO bookingDAO = db.bookingDAO();
        CustomerDAO customerDAO = db.customerDAO();
        TableDAO tableDAO = db.tableDAO();
        RestaurantDAO restaurantDAO = db.restaurantDAO();

        List<Booking> bookingList = bookingDAO.getBookingById(bookingID);
        List<Customer> customerList = customerDAO.getCustomerById(bookingList.get(0).getCustomer_id());
        List<Table> tableList = tableDAO.getTableById(bookingList.get(0).getTable_id());
        List<Restaurant> restaurantList = restaurantDAO.getRestaurantById(tableList.get(0).getRestaurant_id());


        ArrayAdapter<CharSequence> CancelReasonAdapter = ArrayAdapter.createFromResource(this, R.array.cancel_reason, android.R.layout.simple_spinner_item);
        CancelReasonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CancelReason.setAdapter(CancelReasonAdapter);

        startTime = TimeConverter.timeToTimestamp(bookingList.get(0).getStart_time());
        endTime = TimeConverter.timeToTimestamp(bookingList.get(0).getEnd_time());

        Timestamp startTS = new Timestamp(startTime);
        Timestamp endTS = new Timestamp(endTime);

        String[] Date = startTS.toString().split(" ");
        String[] Date2 = endTS.toString().split(" ");

        Name.setText(customerList.get(0).getUser_name());
        TableID.setText(bookingList.get(0).getTable_id());
        TableSize.setText(tableList.get(0).getSize());
        RestaurantName.setText(restaurantList.get(0).getRestaurant_name());
        DateText.setText(Date[0]);
        TimeText.setText(Date[1] + " " + Date2[1]);

        CancelBookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //remove booking id, go to home
                //send notification to admin
            }
        });

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPreviousActivity();
            }
        });

    }

    public void openPreviousActivity() {
        Intent backIntent = new Intent(this, ManageBookingFutureActivity.class);
        startActivity(backIntent);
    }

}