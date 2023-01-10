package wia2007.project.tablebooking;

import static java.lang.String.valueOf;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.NumberPicker;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import wia2007.project.tablebooking.dao.RestaurantDAO;
import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.Restaurant;


public class SelectTimeActivity extends AppCompatActivity {

    NumberPicker AdultNumberPicker, ChildrenNumberPicker, DurationNumberPicker, StartTimeSelector1, StartTimeSelector2;
    CalendarView DateSelector;
    Button NextButton, CancelButton;
    int AdultVal, ChildrenVal, Person, restaurantID = 0, customerID, hour;
    long StartTime, EndTime;

    String Date, StartHour, StartMinute, EndHour;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_time);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        AdultNumberPicker = findViewById(R.id.select_time_adultNumberPicker);
        ChildrenNumberPicker = findViewById(R.id.select_time_childrenNumberPicker);
        DurationNumberPicker = findViewById(R.id.select_time_durationNumberPicker);
        StartTimeSelector1 = findViewById(R.id.select_time_numberPicker1);
        StartTimeSelector2 = findViewById(R.id.select_time_numberPicker2);

        DateSelector = findViewById(R.id.select_time_dateView);

        NextButton = findViewById(R.id.select_time_nextButton);
        CancelButton = findViewById(R.id.select_time_cancelButton);

        AdultNumberPicker.setMinValue(0);
        ChildrenNumberPicker.setMinValue(0);
        DurationNumberPicker.setMinValue(1);

        AdultNumberPicker.setMaxValue(8);
        ChildrenNumberPicker.setMaxValue(8);
        DurationNumberPicker.setMaxValue(4);

        TableBookingDatabase db = TableBookingDatabase.getDatabase(getApplicationContext());
        RestaurantDAO restaurantDAO = db.restaurantDAO();
        List<Restaurant> restaurantList = restaurantDAO.getRestaurantById(restaurantID);

        StartTimeSelector1.setMinValue(8);
        StartTimeSelector1.setMaxValue(20);

        StartTimeSelector2.setMinValue(0);
        StartTimeSelector2.setMaxValue(59);

        DateSelector.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String Date = i + "-" + i1 + "-" + i2 + " ";
            }
        });

        StartTimeSelector1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                hour = i1;
                if (i1 < 10)
                    StartHour = "0" + i1;
                else
                    StartHour = valueOf(i1);
            }
        });

        StartTimeSelector2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                if (i1 < 10)
                    StartMinute = "0" + i1;
                else
                    StartMinute = valueOf(i1);
            }
        });

        AdultNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                AdultVal = i1;
                AdultNumberPicker.setMaxValue(8 - ChildrenVal);
            }
        });

        ChildrenNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                ChildrenVal = i1;
                ChildrenNumberPicker.setMaxValue(8 - AdultVal);
            }
        });

        DurationNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                EndHour = valueOf(hour + i1);
            }
        });

        NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date = sdf.format(new java.util.Date(DateSelector.getDate()));

                int numPeople = AdultNumberPicker.getValue() + ChildrenNumberPicker.getValue();

                if (StartTimeSelector1.getValue() < 10)
                    StartHour = "0" + StartTimeSelector1.getValue();
                else
                    StartHour = valueOf(StartTimeSelector1.getValue());

                if (StartTimeSelector2.getValue() < 10)
                    StartMinute = "0" + StartTimeSelector2.getValue();
                else
                    StartMinute = valueOf(StartTimeSelector2.getValue());

                int temp = StartTimeSelector1.getValue() + DurationNumberPicker.getValue();
                if (temp < 10)
                    EndHour = "0" + temp;
                else
                    EndHour = valueOf(temp);

                String startString = Date +" "+ StartHour + ":" + StartMinute+":00";
                String endString = Date +" "+ EndHour + ":" + StartMinute+":00";
                Timestamp startTS = Timestamp.valueOf(startString);
                Timestamp endTS = Timestamp.valueOf(endString);
                StartTime = startTS.getTime();
                EndTime = endTS.getTime();
                Person = ChildrenVal + AdultVal;
                openNextActivity(customerID, restaurantID, Person, StartTime, EndTime,startString,endString,numPeople);
            }
        });

        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelActivity();
            }
        });

    }

    public void openNextActivity(int customerID, int restaurantID, int tableSize, long startTime, long endTime, String startString, String endString,int numPeople) {
        Intent intent = new Intent(this, SelectTableActivity.class);
        intent.putExtra("numPeople",numPeople);
        intent.putExtra("cusID", customerID);
        intent.putExtra("resID", restaurantID);
        intent.putExtra("tSize", tableSize);
        intent.putExtra("sTime", startTime);
        intent.putExtra("eTime", endTime);
        intent.putExtra("startString",startString);
        intent.putExtra("endString",endString);
        startActivity(intent);
    }

    public void cancelActivity() {
        Intent backIntent = new Intent(this, MainMenuFragment.class);
        startActivity(backIntent);
    }


}
