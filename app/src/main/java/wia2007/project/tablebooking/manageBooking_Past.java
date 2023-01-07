package wia2007.project.tablebooking;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.invitable.R;


public class manageBooking_Past extends AppCompatActivity {

    TextView Name, Date, TableID, TableSize, RestaurantName, Time, Request;
    FoodListAdapter foodListAdapter;
    Button BookAgainButton, UpBackButton;

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