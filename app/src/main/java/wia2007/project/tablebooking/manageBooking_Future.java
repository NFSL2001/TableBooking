package wia2007.project.tablebooking;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.invitable.R;


public class manageBooking_Future extends AppCompatActivity {

    TextView Name, Date, TableID, TableSize, RestaurantName, Time, Request;
    FoodListAdapter foodListAdapter;
    Button EditBookingButton, CancelBookingButton, UpBackButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_manage_booking__future);

        Name = findViewById(R.id.manageBooking_Future_name);
        Date = findViewById(R.id.manageBooking_Future_date);
        TableID = findViewById(R.id.manageBooking_Future_table);
        TableSize = findViewById(R.id.manageBooking_Future_person);
        RestaurantName = findViewById(R.id.manageBooking_Future_restaurantName);
        Time = findViewById(R.id.manageBooking_Future_time);
        Request = findViewById(R.id.manageBooking_Future_request);

        EditBookingButton = findViewById(R.id.manageBooking_Future_editBookingButton);
        CancelBookingButton = findViewById(R.id.manageBooking_Future_cancelBookingButton);
        UpBackButton = findViewById(R.id.manageBooking_Future_backButton);

        EditBookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        CancelBookingButton.setOnClickListener(new View.OnClickListener() {
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