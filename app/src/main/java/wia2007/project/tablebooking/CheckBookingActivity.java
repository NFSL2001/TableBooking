package wia2007.project.tablebooking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.sqlite.db.SimpleSQLiteQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wia2007.project.tablebooking.dao.CustomerDAO;
import wia2007.project.tablebooking.dao.RestaurantDAO;
import wia2007.project.tablebooking.dao.TableDAO;
import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.BookingContainMenu;
import wia2007.project.tablebooking.entity.MenuItem;
import wia2007.project.tablebooking.entity.Notification;
import wia2007.project.tablebooking.entity.Restaurant;
import wia2007.project.tablebooking.entity.Table;


public class CheckBookingActivity extends AppCompatActivity {

    TextView Name, DateText, TableID, RestaurantName, Time, Price, PhoneNum, Email;
    RecyclerView FoodList;
    FoodListAdapter foodListAdapter;
    EditText Request;
    Button ConfirmButton, BackButton, CancelButton;
    Map<MenuItem, Integer> food = new HashMap<>();
    List<Integer> menuId = new ArrayList<>();
    List<Integer> quantity = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_booking);

        if (!"Yes".equals(getIntent().getExtras().getString("Skip"))) {
            Map<Integer, Integer> map = MenuAdapter2.getMap();
            List<Integer> key = new ArrayList<>(map.keySet());
            List<Integer> values = new ArrayList<>(map.values());
            for (int i = 0; i < map.size(); i++) {
                if (values.get(i) != 0) {
                    food.put(TableBookingDatabase.getDatabase(this).menuDAO().getMenuById(key.get(i)).get(0), values.get(i));
                    menuId.add(key.get(i));
                    quantity.add(values.get(i));
                }
            }

            foodListAdapter = new FoodListAdapter(this, food);
            FoodList = findViewById(R.id.check_booking_foodList);
            FoodList.setLayoutManager(new LinearLayoutManager(this));
            FoodList.setAdapter(foodListAdapter);
        }

        int customerID = getIntent().getIntExtra("cusID", 0);
        int restaurantID = getIntent().getIntExtra("resID", 0);
        int tableSize = getIntent().getIntExtra("tSize", 0);
        int tID = getIntent().getIntExtra("tableID", 0);
        String startString = getIntent().getStringExtra("startString");
        String endString = getIntent().getStringExtra("endString");

        TableBookingDatabase db = TableBookingDatabase.getDatabase(getApplicationContext());
        CustomerDAO customerDAO = db.customerDAO();
        RestaurantDAO restaurantDAO = db.restaurantDAO();
        TableDAO tableDAO = db.tableDAO();

        List<Restaurant> restaurantList = restaurantDAO.getRestaurantById(restaurantID);
        List<Table> tableList = tableDAO.getTableById(tID);


        Name = findViewById(R.id.check_booking_name);
        DateText = findViewById(R.id.check_booking_date);
        TableID = findViewById(R.id.check_booking_table);
        RestaurantName = findViewById(R.id.check_booking_textTitle);
        Time = findViewById(R.id.check_booking_time);
        Price = findViewById(R.id.check_booking_price);


        Request = findViewById(R.id.check_booking_requestEdit);
        PhoneNum = findViewById(R.id.check_booking_phoneNumberEdit);
        Email = findViewById(R.id.check_booking_emailEdit);

        ConfirmButton = findViewById(R.id.check_booking_nextButton);
        BackButton = findViewById(R.id.check_booking_backButton);
        CancelButton = findViewById(R.id.check_booking_cancelButton);

        PhoneNum.setText(customerDAO.getCustomerById(customerID).get(0).getMobile_number());
        Email.setText(customerDAO.getCustomerById(customerID).get(0).getEmail());
        Price.setText(getIntent().getExtras().getString("Price", "RM 0.00"));
        Name.setText(customerDAO.getCustomerById(customerID).get(0).getName());
        DateText.setText(startString.substring(0, 11));
        String timeInterval = startString.substring(11) + " - " + endString.substring(11);
        Time.setText(timeInterval);
        RestaurantName.setText(restaurantList.get(0).getRestaurant_name());

        TableID.setText(tableList.get(0).getName() + ", " + tableSize + " People");

        timeInterval = startString + " - " + endString.substring(11);
        String finalTimeInterval = timeInterval;
        ConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = "INSERT INTO Booking (Table_id,Customer_id,start_time,end_time,Remark,Status)VALUES('" + tID + "','" + customerID + "','" + startString.substring(0, 16) + "','" + endString.substring(0, 16) + "','" + Request.getText().toString() + "','Accepted');";
                db.bookingDAO().insert(new SimpleSQLiteQuery(query));
                int booking_id = db.bookingDAO().rawQuery(new SimpleSQLiteQuery("SELECT * FROM booking ORDER BY booking_id DESC LIMIT 1;")).get(0).getBooking_id();
                for (int i = 0; i < menuId.size(); i++) {
                    db.bookingContainMenuDAO().insertContains(new BookingContainMenu(booking_id, menuId.get(i), quantity.get(i)));
                }
                String notification = "You make a booking in <b>"+restaurantList.get(0).getRestaurant_name()+"</b><br>Date & Time: <b>"+ finalTimeInterval +"</b><br>Table: <b>"+tableList.get(0).getName()+"</br>";
                String notificationRestaurant = "<b>"+customerDAO.getCustomerById(customerID).get(0).getName()+"</b>"+" make a booking on <b>"+ finalTimeInterval +"</b><br>(Table: <b>"+tableList.get(0).getName()+"</b>)";
                db.notificationDAO().insertNotification(new Notification(notification,customerID,-1));
                db.notificationDAO().insertNotification(new Notification(notificationRestaurant,-1,restaurantID));
                Toast.makeText(getApplicationContext(),"Book Successfully",Toast.LENGTH_SHORT).show();
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
        ItemAdapter.selectedTable.clear();
        MenuAdapter2.map.clear();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openPreviousActivity() {
        finish();
    }

    public void cancelActivity() {
        MenuAdapter2.map.clear();
        ItemAdapter.selectedTable.clear();
        Intent backIntent = new Intent(this, RestaurantMainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        backIntent.putExtra("ID", getIntent().getIntExtra("resID", 0));
        backIntent.putExtra("name", getIntent().getStringExtra("name"));
        startActivity(backIntent);
    }
}