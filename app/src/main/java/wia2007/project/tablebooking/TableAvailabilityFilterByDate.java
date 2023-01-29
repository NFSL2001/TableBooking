package wia2007.project.tablebooking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class TableAvailabilityFilterByDate extends AppCompatActivity {

    DatePicker datePicker;
    TimePicker timePickerS, timePickerE;
    Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_availability_filter_by_date);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Check Table Availability");

        datePicker = findViewById(R.id.datepicker);
        timePickerS = findViewById(R.id.timepickerS);
        timePickerE = findViewById(R.id.timepickerE);
        confirm = findViewById(R.id.butConfirm);

        timePickerS.is24HourView();

        confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1;
                int year = datePicker.getYear();
                String date = year + "-";
                if (month < 10) {
                    date += "0" + month + "-";
                } else {
                    date += month + "-";
                }
                if (day < 10) {
                    date += "0" + day;
                } else {
                    date += day;
                }

                int hour = timePickerS.getCurrentHour();
                int minute = timePickerS.getCurrentMinute();
                String Stime = "";
                if (hour < 10) {
                    Stime = "0" + hour + ":";
                } else {
                    Stime = hour + ":";
                }
                if (minute < 10) {
                    Stime += "0" + minute;
                } else {
                    Stime += minute;
                }

                int hourE = timePickerE.getCurrentHour();
                int minuteE = timePickerE.getCurrentMinute();
                String Etime = "";
                if (hourE < 10) {
                    Etime = "0" + hourE + ":";
                } else {
                    Etime = hourE + ":";
                }
                if (minute < 10) {
                    Etime += "0" + minuteE;
                } else {
                    Etime += minuteE;
                }

                if (hourE < hour) {
                    Toast.makeText(getApplicationContext(),"End Time cannot be earlier than the Start Time",Toast.LENGTH_SHORT).show();
                }else if(hourE == hour && minuteE < minute){
                    Toast.makeText(getApplicationContext(),"End Time cannot be earlier than the Start Time",Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(TableAvailabilityFilterByDate.this, TableAvailabilityList.class);
                    intent.putExtra("RestaurantID", getIntent().getExtras().getInt("RestaurantID"));
                    intent.putExtra("keyDate", date);
                    intent.putExtra("startTime", Stime);
                    intent.putExtra("endTime", Etime);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}