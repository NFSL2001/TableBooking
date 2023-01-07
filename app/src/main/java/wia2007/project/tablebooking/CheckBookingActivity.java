package wia2007.project.tablebooking;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.invitable.R;

import wia2007.project.tablebooking.dao.BookingDAO;
import wia2007.project.tablebooking.dao.CustomerDAO;
import wia2007.project.tablebooking.database.TableBookingDatabase;


public class CheckBookingActivity extends AppCompatActivity {

    TextView Name, Date, TableID, TableSize, RestaurantName, Time, Price;
    FoodListAdapter foodListAdapter;
    EditText Request, PhoneNum2, Email;
    Button ConfirmButton, BackButton, CancelButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_check_booking);

        TableBookingDatabase db = TableBookingDatabase.getDatabase(getApplicationContext());
        CustomerDAO customerDAO = db.customerDAO();
        BookingDAO bookingDAO = db.bookingDAO();

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

//        Name.setText();
//        Date.setText();
//        Time.setText();
//        RestaurantName.setText();
//        TableID.setText();
//        TableSize.setText();

        ConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

}