package wia2007.project.tablebooking;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.NumberPicker;

import wia2007.project.tablebooking.R;
import wia2007.project.tablebooking.dao.RestaurantDAO;
import wia2007.project.tablebooking.dao.TableDAO;
import wia2007.project.tablebooking.database.TableBookingDatabase;


public class SelectTimeActivity extends AppCompatActivity {
    public static final String EXTRA_NUMBER = "wia2007.project.tablebooking.EXTRA_NUMBER";

    NumberPicker AdultNumberPicker, ChildrenNumberPicker, DurationNumberPicker;
    CalendarView DateSelector;
    Button NextButton, CancelButton;
    int AdultVal, ChildrenVal, Person, StartTime, EndTime;
    long DateValue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_select_time);

        AdultNumberPicker = findViewById(R.id.select_time_adultNumberPicker);
        ChildrenNumberPicker = findViewById(R.id.select_time_childrenNumberPicker);
        DurationNumberPicker = findViewById(R.id.select_time_durationNumberPicker);

        DateSelector = findViewById(R.id.select_time_dateView);

        NextButton = findViewById(R.id.select_time_nextButton);
        CancelButton = findViewById(R.id.select_time_cancelButton);

        AdultNumberPicker.setMinValue(0);
        ChildrenNumberPicker.setMinValue(0);
        DurationNumberPicker.setMinValue(0);

        AdultNumberPicker.setMaxValue(8);
        ChildrenNumberPicker.setMaxValue(8);
        DurationNumberPicker.setMaxValue(4);

        TableBookingDatabase db = TableBookingDatabase.getDatabase(getApplicationContext());
        RestaurantDAO restaurantDAO = db.restaurantDAO();

        DateSelector.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                DateValue = DateSelector.getDate();
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
                EndTime = i1;
            }
        });

//        AdultNumberPicker.setMaxValue(8-ChildrenVal);
//        ChildrenNumberPicker.setMaxValue(8-AdultVal);

        NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Person = ChildrenVal + AdultVal;
                openNextActivity(Person, StartTime, EndTime, DateValue);
            }
        });

        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    public void openNextActivity(int tableSize, int startTime, int endTime, long dateVal) {
            getIntent().putExtra(EXTRA_NUMBER, tableSize);
            getIntent().putExtra(EXTRA_NUMBER, startTime);
            getIntent().putExtra(EXTRA_NUMBER, endTime);
            getIntent().putExtra(EXTRA_NUMBER, dateVal);

        Intent intent = new Intent(this, SelectTableActivity.class);
        startActivity(intent);
    }


}