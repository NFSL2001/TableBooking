package wia2007.project.tablebooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

public class TableAvailabilityFilterByDate extends AppCompatActivity {

    DatePicker datePicker;
    TimePicker timePicker;
    Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_availability_filter_by_date);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("View Availability");

        datePicker = findViewById(R.id.datepicker);
        timePicker = findViewById(R.id.timepicker);
        confirm = findViewById(R.id.butConfirm);

        timePicker.is24HourView();

        confirm.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                int  day =  datePicker.getDayOfMonth();
                int  month = datePicker.getMonth()+1;
                int year = datePicker.getYear();
                String date = day+"/"+month+"/"+year;

                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();
                String  time = hour+": "+minute;

                Intent intent = new Intent(TableAvailabilityFilterByDate.this, TableAvailabilityList.class);
                intent.putExtra("keydate", date);
                intent.putExtra("keytime", time);
                startActivity(intent);
            }
        });


    }
}