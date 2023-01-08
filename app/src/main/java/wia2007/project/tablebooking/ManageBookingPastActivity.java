package wia2007.project.tablebooking;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import wia2007.project.tablebooking.R;

import java.util.List;

import wia2007.project.tablebooking.dao.BookingDAO;
import wia2007.project.tablebooking.dao.CustomerDAO;
import wia2007.project.tablebooking.dao.TableDAO;
import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.Booking;
import wia2007.project.tablebooking.entity.Customer;
import wia2007.project.tablebooking.entity.Table;


public class ManageBookingPastActivity extends AppCompatActivity {

    TextView Name, Date, TableID, TableSize, RestaurantName, Time, Request;
    RecyclerView FoodList;
    FoodListAdapter foodListAdapter;
    Button BookAgainButton, UpBackButton;
    int bookingID, customerID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_manage_booking__past);

        Name = findViewById(R.id.manageBooking_Past_name);
        Date = findViewById(R.id.manageBooking_Past_date);
        TableID = findViewById(R.id.manageBooking_Past_table);
        TableSize = findViewById(R.id.manageBooking_Past_person);
        RestaurantName = findViewById(R.id.manageBooking_Past_restaurantName);
        Time = findViewById(R.id.manageBooking_Past_time);
        Request = findViewById(R.id.manageBooking_Past_request);

        BookAgainButton = findViewById(R.id.manageBooking_Past_bookButton);
        UpBackButton = findViewById(R.id.manageBooking_Past_backButton);

        TableBookingDatabase database = TableBookingDatabase.getDatabase(getApplicationContext());

        BookingDAO bookingDAO = database.bookingDAO();
        CustomerDAO customerDAO = database.customerDAO();
        TableDAO tableDAO = database.tableDAO();

        List<Booking> bookingList = bookingDAO.getBookingById(bookingID);
        List<Customer> customerList = customerDAO.getCustomerById(customerID);
        List<Table> tableList = tableDAO.getTableById(bookingList.get(0).getTable_id());

        Name.setText(customerList.get(0).getUser_name());
        TableID.setText(bookingList.get(0).getTable_id());
        TableSize.setText(tableList.get(0).getSize());
        Request.setText(bookingList.get(0).getRemark());
        RestaurantName.setText(tableList.get(0).getRestaurant_id());
        Date.setText();
        Time.setText();

//        foodListAdapter = new FoodListAdapter();
//        FoodList.setAdapter(foodListAdapter);

        BookAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        UpBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



    }

}