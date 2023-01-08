package wia2007.project.tablebooking;

import android.content.Intent;
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


import wia2007.project.tablebooking.dao.BookingContainMenuDAO;
import wia2007.project.tablebooking.dao.BookingDAO;
import wia2007.project.tablebooking.dao.CustomerDAO;
import wia2007.project.tablebooking.database.TableBookingDatabase;


public class CheckBookingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView Name, Date, TableID, TableSize, RestaurantName, Time, Price;
    RecyclerView FoodList;
    FoodListAdapter foodListAdapter;
    EditText Request, PhoneNum2, Email;
    Button ConfirmButton, BackButton, CancelButton;
    Spinner PhoneNum1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_check_booking);

        TableBookingDatabase db = TableBookingDatabase.getDatabase(getApplicationContext());
        CustomerDAO customerDAO = db.customerDAO();
        BookingContainMenuDAO BCMDAO = db.bookingContainMenuDAO();


        Name = findViewById(R.id.check_booking_name);
        Date = findViewById(R.id.check_booking_date);
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
//        Date.setText();
//        Time.setText();
//        RestaurantName.setText();
//        TableID.setText();
//        TableSize.setText();

        ArrayAdapter<CharSequence> phoneNumAdapter = ArrayAdapter.createFromResource(this, R.array.phoneNumbers, android.R.layout.simple_spinner_item);
        phoneNumAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        PhoneNum1.setAdapter(phoneNumAdapter);
        PhoneNum1.setOnItemSelectedListener(this);

        ConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
}