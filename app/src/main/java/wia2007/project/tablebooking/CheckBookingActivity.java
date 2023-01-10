package wia2007.project.tablebooking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.sqlite.db.SimpleSQLiteQuery;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.sql.Time;
import java.util.Map;

import wia2007.project.tablebooking.converter.TimeConverter;
import wia2007.project.tablebooking.dao.BookingContainMenuDAO;
import wia2007.project.tablebooking.dao.CustomerDAO;
import wia2007.project.tablebooking.dao.RestaurantDAO;
import wia2007.project.tablebooking.dao.TableDAO;
import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.Booking;
import wia2007.project.tablebooking.entity.BookingContainMenu;
import wia2007.project.tablebooking.entity.Customer;
import wia2007.project.tablebooking.entity.MenuBaseData;
import wia2007.project.tablebooking.entity.MenuItem;
import wia2007.project.tablebooking.entity.Restaurant;
import wia2007.project.tablebooking.entity.Table;


public class CheckBookingActivity extends AppCompatActivity {

    TextView Name, DateText, TableID, TableSize, RestaurantName, Time, Price;
    RecyclerView FoodList;
    FoodListAdapter foodListAdapter;
    EditText Request, PhoneNum2, Email,PhoneNum1;
    Button ConfirmButton, BackButton, CancelButton;
    Map<MenuItem,Integer> food = new HashMap<>();
    List<Integer> menuId = new ArrayList<>();
    List<Integer> quantity = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_booking);

        if(!"Yes".equals(getIntent().getExtras().getString("Skip"))){
            Map<Integer,Integer> map = MenuAdapter2.getMap();
            List<Integer> key = new ArrayList<>(map.keySet());
            List<Integer> values = new ArrayList<>(map.values());
            for(int i = 0; i<map.size();i++){
                if(values.get(i) != 0){
                    food.put(TableBookingDatabase.getDatabase(this).menuDAO().getMenuById(key.get(i)).get(0),values.get(i));
                    menuId.add(key.get(i));
                    quantity.add(values.get(i));
                }
            }

            foodListAdapter = new FoodListAdapter(this,food);
            FoodList = findViewById(R.id.check_booking_foodList);
            FoodList.setLayoutManager(new LinearLayoutManager(this));
            FoodList.setAdapter(foodListAdapter);
        }

        int customerID = getIntent().getIntExtra("cusID", 0);
        int restaurantID = getIntent().getIntExtra("resID", 0);
        int tableSize = getIntent().getIntExtra("tSize", 0);
        long startTime = getIntent().getIntExtra("sTime", 0);
        long endTime = getIntent().getIntExtra("eTime", 0);
        int tID = getIntent().getIntExtra("tableID", 0);
        int numPeople = getIntent().getIntExtra("numPeople",1);
        String startString = getIntent().getStringExtra("startString");
        String endString = getIntent().getStringExtra("endString");

        TableBookingDatabase db = TableBookingDatabase.getDatabase(getApplicationContext());
        CustomerDAO customerDAO = db.customerDAO();
        RestaurantDAO restaurantDAO = db.restaurantDAO();
        TableDAO tableDAO = db.tableDAO();

        List<Restaurant> restaurantList = restaurantDAO.getRestaurantById(restaurantID);
        List<Table> tableList = tableDAO.getTableById(tID);

        Timestamp startTS = new Timestamp(startTime);
        Timestamp endTS = new Timestamp(endTime);

        Time startT = new Time(startTime);
        Time endT = new Time(endTime);

        String[] Date = startTS.toString().split(" ");
        String[] Date2 = endTS.toString().split(" ");

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
        PhoneNum1.setText("+60");
        PhoneNum2.setText(customerDAO.getCustomerById(customerID).get(0).getMobile_number());
        Email.setText(customerDAO.getCustomerById(customerID).get(0).getEmail());
        Price.setText(getIntent().getExtras().getString("Price","RM 0.00"));
        Name.setText(customerDAO.getCustomerById(customerID).get(0).getName());
        DateText.setText(startString.substring(0,11));
        Time.setText(startString.substring(11) + " - " +endString.substring(11));
        RestaurantName.setText(restaurantList.get(0).getRestaurant_name());
        TableID.setText(tableList.get(0).getName());
        TableSize.setText(numPeople+ " People");


        ConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.bookingDAO().insertBookings(new Booking(tID,customerID,startT,endT,Request.getText().toString()));
                int booking_id = db.bookingDAO().rawQuery(new SimpleSQLiteQuery("SELECT * FROM booking ORDER BY booking_id DESC LIMIT 1;")).get(0).getBooking_id();
                for(int i = 0; i<menuId.size();i++){
                   db.bookingContainMenuDAO().insertContains(new BookingContainMenu(booking_id,menuId.get(i),quantity.get(i)));
                }
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

    public void openNextActivity() {
        Intent intent = new Intent(this, ManageBookingFutureActivity.class);
        startActivity(intent);
    }

    public void openPreviousActivity() {
        finish();
//        Intent backIntent = new Intent(this, PreOrderFoodActivity.class);
//        startActivity(backIntent);
    }

    public void cancelActivity() {
        Intent backIntent = new Intent(this, MainMenuFragment.class);
        startActivity(backIntent);
    }
}