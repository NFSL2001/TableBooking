package wia2007.project.tablebooking;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.NumberPicker;

import com.example.invitable.R;

public class SelectTimeActivity extends AppCompatActivity {
    public static final String EXTRA_NUMBER = "wia2007.project.tablebooking.EXTRA_NUMBER";
    public static final String DATE_STRING = "wia2007.project.tablebooking.DATE_STRING";
    public static final String DATE_VALUE = "wia2007.project.tablebooking.DATE_VALUE";

    NumberPicker AdultNumberPicker, ChildrenNumberPicker, DurationNumberPicker;
    CalendarView DateSelector;
    SelectTimeButtonAdapter TimeButtonAdapter;
    Button NextButton, CancelButton;
    int AdultVal, ChildrenVal;
    Integer Person, Duration;
    String Date;
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

        Date = "1 January 2022";
        DateValue = DateSelector.getDate();

        DateSelector.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                Date = dateFormatChanger(i, i1, i2);
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
                Duration = i1;
            }
        });

//        AdultNumberPicker.setMaxValue(8-ChildrenVal);
//        ChildrenNumberPicker.setMaxValue(8-AdultVal);

        NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Person = ChildrenVal + AdultVal;
                openNextActivity(Person, Duration, Date, DateValue);
            }
        });

        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    public void openNextActivity(int person, int duration, String date, long dateVal) {
        getIntent().putExtra(EXTRA_NUMBER, person);
        getIntent().putExtra(EXTRA_NUMBER, duration);
        getIntent().putExtra(DATE_STRING, date);
        getIntent().putExtra(DATE_VALUE, dateVal);

        Intent intent = new Intent(this, SelectTableActivity.class);
        startActivity(intent);
    }

    public String dateFormatChanger(int year, int month, int day) {
        String b = "January";
        switch (month) {
            case 1 :
                b = "January";
                break;
            case 2 :
                b = "February";
                break;
            case 3 :
                b = "March";
                break;
            case 4 :
                b = "April";
                break;
            case 5 :
                b = "May";
                break;
            case 6 :
                b = "June";
                break;
            case 7 :
                b = "July";
                break;
            case 8 :
                b = "August";
                break;
            case 9 :
                b = "September";
                break;
            case 10 :
                b = "October";
                break;
            case 11 :
                b = "November";
                break;
            case 12 :
                b = "December";
                break;
        }
        String a = day + " " + b + " " + year;
        return a;
    }


}