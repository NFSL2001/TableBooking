package wia2007.project.tablebooking;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.sqlite.db.SimpleSQLiteQuery;

import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import wia2007.project.tablebooking.database.TableBookingDatabase;


public class ManageBookingFutureActivity extends AppCompatActivity {

    TextView Name, DateText, TableID, TimeText, Request,RestaurantContact, RestaurantAddress,TVTotal,bookingCancelled;
    RecyclerView FoodList;
    FoodListAdapter foodListAdapter;
    Button CancelBookingButton;
    int bookingID;
    String restName;
    @SuppressLint({"Range", "SetTextI18n"})
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_booking);

        int customerID = this.getSharedPreferences("user",MODE_PRIVATE).getInt("userID",-1);
        restName = getIntent().getStringExtra("restName");
        bookingID = getIntent().getIntExtra("bookingID", 0);
        boolean bookingOver = getIntent().getBooleanExtra("bookingOver",false);
        String status = getIntent().getStringExtra("status");
        //get action bar
        Toolbar toolbar = findViewById(R.id.manageBooking_TB);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(restName);

        Name = findViewById(R.id.manageBooking_Future_name);
        DateText = findViewById(R.id.manageBooking_Future_date);
        TableID = findViewById(R.id.manageBooking_Future_table);
        TimeText = findViewById(R.id.manageBooking_Future_time);
        Request = findViewById(R.id.manageBooking_Future_request);
        RestaurantAddress = findViewById(R.id.RestaurantAddress);
        RestaurantContact = findViewById(R.id.RestaurantContact);
        TVTotal = findViewById(R.id.TVTotal);
        bookingCancelled = findViewById(R.id.bookingCancelled);

        CancelBookingButton = findViewById(R.id.manageBooking_Future_cancelBookingButton);
        if(bookingOver) {
            CancelBookingButton.setVisibility(View.GONE);
            if("Cancelled".equalsIgnoreCase(status)){
                bookingCancelled.setVisibility(View.VISIBLE);
                bookingCancelled.setText("Cancelled");
            }else{
                bookingCancelled.setVisibility(View.VISIBLE);
                bookingCancelled.setText("Completed");
            }
        }else {
            CancelBookingButton.setVisibility(View.VISIBLE);
            bookingCancelled.setVisibility(View.GONE);
        }
        List<ShowFoodOrderList> foodList = new ArrayList<>();
        Cursor cursor = TableBookingDatabase.getDatabase(this).bookingContainMenuDAO().getFoodOrder(bookingID);
        String menuName;
        int booking_id, quantity;
        double totalPrice;
        while (cursor.moveToNext()) {
            booking_id = cursor.getInt(cursor.getColumnIndex("booking_id"));
            quantity = cursor.getInt(cursor.getColumnIndex("quantity"));
            totalPrice = cursor.getDouble(cursor.getColumnIndex("Total_cost"));
            menuName = cursor.getString(cursor.getColumnIndex("menu_name"));

            ShowFoodOrderList showFoodOrderList = new ShowFoodOrderList(booking_id, menuName, quantity, totalPrice);
            foodList.add(showFoodOrderList);
        }
        Cursor cursorBook = TableBookingDatabase.getDatabase(this).bookingDAO().getBookingById(bookingID);
        String startTime = "           ", endTime ="           ";
        String remark ="", restaurant_address = "", contact_number = "",tableName = "";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy");
        int size = 0;

        while(cursorBook.moveToNext()){
            tableName = cursorBook.getString(cursorBook.getColumnIndex("TableName"));
            size = cursorBook.getInt(cursorBook.getColumnIndex("size"));
            startTime = cursorBook.getString(cursorBook.getColumnIndex("start_time"));
            endTime = cursorBook.getString(cursorBook.getColumnIndex("end_time"));
            remark = cursorBook.getString(cursorBook.getColumnIndex("remark"));
            restaurant_address = cursorBook.getString(cursorBook.getColumnIndex("address"));
            contact_number = cursorBook.getString(cursorBook.getColumnIndex("contact_number"));
        }

        Name.setText(TableBookingDatabase.getDatabase(this).customerDAO().getCustomerById(customerID).get(0).getName());
        try {
            DateText.setText(df2.format(df.parse(startTime.substring(0,10))));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        TimeText.setText(startTime.substring(11)+"-"+endTime.substring(11));
        Request.setText(remark);
        TableID.setText(tableName +", "+size + " People Table" );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            contact_number = "<b>Restaurant Contact</b><br>"+contact_number;
            restaurant_address = "<b>Restaurant Address</b><br>"+restaurant_address;
            RestaurantContact.setText(Html.fromHtml(contact_number));
            RestaurantAddress.setText(Html.fromHtml(restaurant_address));
        }

        FoodList = findViewById(R.id.manageBooking_Future_foodList);
        foodListAdapter = new FoodListAdapter(this,foodList);
        FoodList.setLayoutManager(new LinearLayoutManager(this));
        FoodList.setAdapter(foodListAdapter);
        TVTotal.setText(foodListAdapter.grandTotal());

        CancelBookingButton.setOnClickListener(call_reject);
    }


    private View.OnClickListener call_reject = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putInt("restaurant_id",TableBookingDatabase.getDatabase(getApplicationContext()).bookingDAO().getId(new SimpleSQLiteQuery("SELECT restaurant.restaurant_id FROM Booking INNER JOIN `table` ON Booking.table_id = `table`.table_id INNER JOIN restaurant ON restaurant.restaurant_id =  `table`.restaurant_id WHERE booking_id = "+bookingID)));
            bundle.putString("name",TableBookingDatabase.getDatabase(getApplicationContext()).bookingDAO().getName(new SimpleSQLiteQuery("SELECT customer.name FROM booking INNER JOIN customer ON booking.customer_id = customer.customer_id WHERE booking_id ="+bookingID)));
            RejectBookingPopUp rejectBookingPopUp = new RejectBookingPopUp();
            rejectBookingPopUp.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().add(R.id.RejectReasonLayout2,rejectBookingPopUp,null).commit();
        }
    };

    @Override
    public boolean onOptionsItemSelected(@NonNull android.view.MenuItem item) {
        switch (item.getItemId()) {
            // map toolbar back button same as system back button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}