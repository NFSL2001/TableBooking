package wia2007.project.tablebooking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.sqlite.db.SimpleSQLiteQuery;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import wia2007.project.tablebooking.database.TableBookingDatabase;

public class BookingDetails extends AppCompatActivity {
    int booking_id;
    String custName;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.TVBookingDetailsAct);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Booking Details");

        TextView BCustName = findViewById(R.id.BCustName);
        TextView TVBPhone = findViewById(R.id.TVBPhone);
        TextView TVBEmail = findViewById(R.id.TVBEmail);
        TextView TVBTable = findViewById(R.id.TVBTable);
        TextView TVBDate = findViewById(R.id.TVBDate);
        TextView TVBTime = findViewById(R.id.TVBTime);
        TextView TVBRemark = findViewById(R.id.TVBRemark);
        TextView CancelStatus = findViewById(R.id.CancelStatus);

        TextView TVBookingId = findViewById(R.id.TVID);
        Button BtnRejectBooking = findViewById(R.id.BtnRejectBooking);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            booking_id = extras.getInt("bookingID");
            custName = extras.getString("CustomerName");
            String custPhone = extras.getString("CustomerPhone");
            String custEmail = extras.getString("CustomerEmail");
            String remark = extras.getString("Remark");
            String tableId = extras.getString("TableName");
            String date = extras.getString("Date");
            String time = extras.getString("Time");
            Boolean bookingOver = extras.getBoolean("BookingOver");
            String status = extras.getString("status");

            BCustName.setText(custName);
            TVBPhone.setText(custPhone);
            TVBEmail.setText(custEmail);
            TVBTable.setText(tableId);
            TVBDate.setText(date);
            TVBTime.setText(time);
            if (remark.equals("") || remark.equals(null)) {
                TVBRemark.setHint("No Remark");
            } else {
                TVBRemark.setText(remark);
            }
            TVBookingId.setText(Integer.toString(booking_id));
            if(bookingOver) {
                BtnRejectBooking.setVisibility(View.GONE);
                if("Cancelled".equalsIgnoreCase(status)) {
                    CancelStatus.setText("Cancelled");
                    CancelStatus.setVisibility(View.VISIBLE);
                }else{
                    CancelStatus.setVisibility(View.VISIBLE);
                    CancelStatus.setText("Completed");
                }
            }else {
                BtnRejectBooking.setVisibility(View.VISIBLE);
                CancelStatus.setVisibility(View.GONE);
            }
        }

        BtnRejectBooking.setOnClickListener(call_reject);

        Button BtnOrderMenu = findViewById(R.id.BtnOrderMenu);
        BtnOrderMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.OrderMenuFragment);
                fragment.getView().setVisibility(View.VISIBLE);
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener call_reject = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putInt("customer_id", TableBookingDatabase.getDatabase(getApplicationContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("SELECT customer_id FROM Booking WHERE booking_id = "+booking_id)).get(0).getCustomer_id());
            bundle.putString("name",TableBookingDatabase.getDatabase(getApplicationContext()).bookingDAO().getName(new SimpleSQLiteQuery("SELECT restaurant_name FROM restaurant WHERE restaurant_id = "+getSharedPreferences("admin",MODE_PRIVATE).getInt("userID",-1))));
            RejectBookingPopUp rejectBookingPopUp = new RejectBookingPopUp();
            rejectBookingPopUp.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().add(R.id.RejectReasonLayout,rejectBookingPopUp,null).commit();
        }
    };
}