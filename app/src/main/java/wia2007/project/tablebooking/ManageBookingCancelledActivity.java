package wia2007.project.tablebooking;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import wia2007.project.tablebooking.R;

import java.util.List;

import wia2007.project.tablebooking.dao.BookingDAO;
import wia2007.project.tablebooking.dao.CustomerDAO;
import wia2007.project.tablebooking.dao.TableDAO;
import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.Booking;
import wia2007.project.tablebooking.entity.Customer;
import wia2007.project.tablebooking.entity.Table;


public class ManageBookingCancelledActivity extends AppCompatActivity {

    TextView RestaurantName, Name, Date, Time, Request, TableID, TableSize;
    RecyclerView FoodList;
    FoodListAdapter foodListAdapter;
    Button BackButton;

    int bookingID, customerID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_manage_booking__cancelled);

        Name = findViewById(R.id.manageBooking_Cancelled_name);
        Date = findViewById(R.id.manageBooking_Cancelled_date);
        Time = findViewById(R.id.manageBooking_Cancelled_time);
        TableID = findViewById(R.id.manageBooking_Cancelled_table);
        TableSize = findViewById(R.id.manageBooking_Cancelled_person);
        Request = findViewById(R.id.manageBooking_Cancelled_request);
        RestaurantName = findViewById(R.id.manageBooking_Cancelled_restaurantName);

        BackButton = findViewById(R.id.manageBooking_Cancelled_backButton);

        FoodList = findViewById(R.id.manageBooking_Cancelled_foodList);

        foodListAdapter = new FoodListAdapter();
        FoodList.setAdapter(foodListAdapter);

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

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

}