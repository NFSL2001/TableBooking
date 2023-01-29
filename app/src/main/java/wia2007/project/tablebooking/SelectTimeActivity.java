package wia2007.project.tablebooking;

import static java.lang.String.valueOf;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class SelectTimeActivity extends AppCompatActivity {

    NumberPicker AdultNumberPicker, ChildrenNumberPicker, DurationNumberPicker, StartTimeSelector1, StartTimeSelector2;
    CalendarView DateSelector;
    Button NextButton, CancelButton;
    int AdultVal, ChildrenVal, restaurantID, customerID, hour;
    String Date, StartHour, StartMinute, EndHour,name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_time);

        Intent intent = getIntent();
        restaurantID = intent.getExtras().getInt("resID");
        name = intent.getStringExtra("name");
        SharedPreferences sharedPreferences = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        customerID = sharedPreferences.getInt("userID",-1);

        AdultNumberPicker = findViewById(R.id.select_time_adultNumberPicker);
        ChildrenNumberPicker = findViewById(R.id.select_time_childrenNumberPicker);
        DurationNumberPicker = findViewById(R.id.select_time_durationNumberPicker);
        StartTimeSelector1 = findViewById(R.id.select_time_numberPicker1);
        StartTimeSelector2 = findViewById(R.id.select_time_numberPicker2);

        DateSelector = findViewById(R.id.select_time_dateView);

        NextButton = findViewById(R.id.select_time_nextButton);
        CancelButton = findViewById(R.id.select_time_cancelButton);

        AdultNumberPicker.setMinValue(1);
        ChildrenNumberPicker.setMinValue(0);
        DurationNumberPicker.setMinValue(1);

        AdultNumberPicker.setMaxValue(8);
        ChildrenNumberPicker.setMaxValue(8);
        DurationNumberPicker.setMaxValue(4);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            AdultNumberPicker.setTextSize(50f);
            ChildrenNumberPicker.setTextSize(50f);
            DurationNumberPicker.setTextSize(50f);
            StartTimeSelector1.setTextSize(50f);
            StartTimeSelector2.setTextSize(50f);
            StartTimeSelector2.setTextSize(50f);

        }


        StartTimeSelector1.setMinValue(8);
        StartTimeSelector1.setMaxValue(20);

        StartTimeSelector2.setMinValue(0);
        StartTimeSelector2.setMaxValue(59);
        NumberPicker.Formatter formatter = new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%02d", i);
            }
        };

        StartTimeSelector1.setFormatter(formatter);
        StartTimeSelector2.setFormatter(formatter);

        DateSelector.setMinDate((new Date().getTime()));
        Calendar cal = Calendar.getInstance(); //Get the Calendar instance
        cal.add(Calendar.MONTH,2);//Three months from now
        Date date = cal.getTime();
        DateSelector.setMaxDate(date.getTime());

        DateSelector.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                int month = i1+1;
                String monthstr = "";
                if (month < 10)
                    monthstr = "0" + month;
                else
                    monthstr = valueOf(month);
                Date = i + "-" + monthstr + "-" + i2;
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
                if(Date == null){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date = sdf.format(new Date().getTime());
                }

                String startString = Date +" "+ StartHour + ":" + StartMinute+":00";
                String endString = Date +" "+ EndHour + ":" + StartMinute+":00";

                Calendar cal = Calendar.getInstance(); //Get the Calendar instance
                cal.add(Calendar.HOUR,2);
                Date date = cal.getTime();
                Date compareDate = new Date();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    compareDate = df.parse(startString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(compareDate.before(date)){
                    System.out.println(compareDate);
                    System.out.println(date);
                    Toast.makeText(getApplicationContext(),"Booking time should be at least 2 hour from current time",Toast.LENGTH_LONG).show();
                }else{
                    openNextActivity(customerID, restaurantID, numPeople,startString,endString,numPeople,name);
                }
            }
        });

        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelActivity();
            }
        });

    }

    public void openNextActivity(int customerID, int restaurantID, int tableSize, String startString, String endString,int numPeople,String name) {
        Intent intent = new Intent(this, SelectTableActivity.class);
        intent.putExtra("numPeople",numPeople);
        intent.putExtra("cusID", customerID);
        intent.putExtra("name",name);
        intent.putExtra("resID", restaurantID);
        intent.putExtra("tSize", tableSize);
        intent.putExtra("startString",startString);
        intent.putExtra("endString",endString);
        startActivity(intent);
    }

    public void cancelActivity() {
        finish();
    }


}
