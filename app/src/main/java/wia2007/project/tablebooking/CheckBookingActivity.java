package wia2007.project.tablebooking;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.sql.Time;

import wia2007.project.tablebooking.converter.TimeConverter;
import wia2007.project.tablebooking.dao.BookingContainMenuDAO;
import wia2007.project.tablebooking.dao.CustomerDAO;
import wia2007.project.tablebooking.dao.RestaurantDAO;
import wia2007.project.tablebooking.dao.TableDAO;
import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.Booking;
import wia2007.project.tablebooking.entity.BookingContainMenu;
import wia2007.project.tablebooking.entity.Customer;
import wia2007.project.tablebooking.entity.Restaurant;
import wia2007.project.tablebooking.entity.Table;


public class CheckBookingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView Name, DateText, TableID, TableSize, RestaurantName, Time, Price;
    RecyclerView FoodList;
    FoodListAdapter foodListAdapter;
    EditText Request, PhoneNum2, Email;
    Button ConfirmButton, BackButton, CancelButton;
    Spinner PhoneNum1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_booking);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        int customerID = getIntent().getIntExtra("cusID", 0);
        int restaurantID = getIntent().getIntExtra("resID", 0);
        int tableSize = getIntent().getIntExtra("tSize", 0);
        long startTime = getIntent().getIntExtra("sTime", 0);
        long endTime = getIntent().getIntExtra("eTime", 0);
        int tID = getIntent().getIntExtra("tableID", 0);
        int mID = getIntent().getIntExtra("menuID", 0);

        TableBookingDatabase db = TableBookingDatabase.getDatabase(getApplicationContext());
        CustomerDAO customerDAO = db.customerDAO();
        BookingContainMenuDAO BCMDAO = db.bookingContainMenuDAO();
        RestaurantDAO restaurantDAO = db.restaurantDAO();
        TableDAO tableDAO = db.tableDAO();

        List<Customer> customerList;
        List<BookingContainMenu> BCMList;
        List<Restaurant> restaurantList = restaurantDAO.getRestaurantById(restaurantID);
        List<Table> tableList = tableDAO.getTableById(tID);

        Timestamp startTS = new Timestamp(startTime);
        Timestamp endTS = new Timestamp(endTime);

        Time startT = new Time(startTime);
        Time endT = new Time(endTime);

        String[] Date = startTS.toString().split(" ");
        String[] Date2 = endTS.toString().split(" ");

        List<Booking> bookingResult = new ArrayList<>();

        Name = findViewById(R.id.check_booking_name);
        DateText = findViewById(R.id.check_booking_date);
        TableID = findViewById(R.id.check_booking_table);
        TableSize = findViewById(R.id.check_booking_people);
        RestaurantName = findViewById(R.id.check_booking_textTitle);
        Time = findViewById(R.id.check_booking_time);
        Price = findViewById(R.id.check_booking_price);

        Request = findViewById(R.id.check_booking_requestEdit);
        PhoneNum2 = findViewById(R.id.check_booking_phoneNumberEdit);
        Email = findViewById(R.id.check_booking_emailEdit);

        ConfirmButton = findViewById(R.id.check_booking_nextButton);
        BackButton = findViewById(R.id.check_booking_backButton);
        CancelButton = findViewById(R.id.check_booking_cancelButton);

        PhoneNum1 = findViewById(R.id.check_booking_spinnerPhoneNumber);

        FoodList = findViewById(R.id.check_booking_foodList);

//        foodListAdapter = new FoodListAdapter();
//        FoodList.setAdapter(foodListAdapter);

//        Name.setText();
        DateText.setText(Date[0]);
        Time.setText(Date[1] + " " + Date2[1]);
        RestaurantName.setText(restaurantList.get(0).getRestaurant_name());
        TableID.setText(tableList.get(0).getName());
        TableSize.setText(tableSize + " People");

        ArrayAdapter<CharSequence> phoneNumAdapter = ArrayAdapter.createFromResource(this, R.array.phoneNumbers, android.R.layout.simple_spinner_item);
        phoneNumAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        PhoneNum1.setAdapter(phoneNumAdapter);
        PhoneNum1.setOnItemSelectedListener(this);

        ConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookingResult.get(0).setTable_id(tID);
                bookingResult.get(0).setCustomer_id(customerID);
                bookingResult.get(0).setStart_time(startT);
                bookingResult.get(0).setEnd_time(endT);
                bookingResult.get(0).setRemark(Request.toString());

                db.bookingDAO().insertBookings((Booking) bookingResult);
                openNextActivity();
            }
        });

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPreviousActivity();
            }
        });

        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelActivity();
            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void openNextActivity() {
        Intent intent = new Intent(this, ManageBookingFutureActivity.class);
        startActivity(intent);
    }

    public void openPreviousActivity() {
        Intent backIntent = new Intent(this, PreOrderFoodActivity.class);
        startActivity(backIntent);
    }

    public void cancelActivity() {
        Intent backIntent = new Intent(this, MainMenuFragment.class);
        startActivity(backIntent);
    }
}