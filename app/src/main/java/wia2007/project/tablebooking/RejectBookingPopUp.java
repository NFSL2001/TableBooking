package wia2007.project.tablebooking;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.List;

import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.Booking;
import wia2007.project.tablebooking.entity.Notification;
import wia2007.project.tablebooking.fragment.BookingFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RejectBookingPopUp#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RejectBookingPopUp extends Fragment {
    int bookingId,customerId,restaurantId;
    String name;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public RejectBookingPopUp() {
        // Required empty public constructor
    }

    public static RejectBookingPopUp newInstance(String param1, String param2) {
        RejectBookingPopUp fragment = new RejectBookingPopUp();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_reject_booking_pop_up, container, false);

        Bundle bundle = getActivity().getIntent().getExtras();
        if(bundle != null){
            bookingId=bundle.getInt("bookingID");
        }

        Bundle bundle1 = getArguments();
        if(bundle1 != null){
            restaurantId = bundle1.getInt("restaurant_id");
            customerId = bundle1.getInt("customer_id");
            name = bundle1.getString("name");
        }

        SharedPreferences user = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences admin = getContext().getSharedPreferences("admin", Context.MODE_PRIVATE);
        int custId = user.getInt("userID",-1);
        int restId = admin.getInt("userID",-1);

        Button BtnConfirmReject = view.findViewById(R.id.BtnConfirmReject);
        BtnConfirmReject.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("Range")
            @Override
            public void onClick(View v) {
                view.setVisibility(View.INVISIBLE);
                TableBookingDatabase.getDatabase(view.getContext()).bookingDAO().rejectBooking(bookingId);
                if(custId == -1){
                    String restaurant_name = TableBookingDatabase.getDatabase(view.getContext()).restaurantDAO().getRestaurantById(restId).get(0).getRestaurant_name();
                    Cursor cursorBook = TableBookingDatabase.getDatabase(view.getContext()).bookingDAO().getBookingById(bookingId);
                    String startTime = "           ", endTime ="           ";
                    String tableName = "";
                    while(cursorBook.moveToNext()){
                        tableName = cursorBook.getString(cursorBook.getColumnIndex("TableName"));
                        startTime = cursorBook.getString(cursorBook.getColumnIndex("start_time"));
                        endTime = cursorBook.getString(cursorBook.getColumnIndex("end_time"));
                    }
                    String notification = "<b>"+restaurant_name+"</b> cancelled your order<br><b>("+tableName+", "+startTime+"-"+endTime.substring(11)+")</b>";
                    String notificationRestaurant = "You cancelled the order from <b>"+name+"</b><br><b>("+tableName+", "+startTime+"-"+endTime.substring(11)+")</b>";
                    TableBookingDatabase.getDatabase(view.getContext()).notificationDAO().insertNotification(new Notification(notification,customerId,-1));
                    TableBookingDatabase.getDatabase(view.getContext()).notificationDAO().insertNotification(new Notification(notificationRestaurant,-1,restId));
                }
                if(restId == -1){
                    String restaurant_name = TableBookingDatabase.getDatabase(view.getContext()).restaurantDAO().getRestaurantById(restaurantId).get(0).getRestaurant_name();
                    Cursor cursorBook = TableBookingDatabase.getDatabase(view.getContext()).bookingDAO().getBookingById(bookingId);
                    String startTime = "           ", endTime ="           ";
                    String tableName = "";
                    while(cursorBook.moveToNext()){
                        tableName = cursorBook.getString(cursorBook.getColumnIndex("TableName"));
                        startTime = cursorBook.getString(cursorBook.getColumnIndex("start_time"));
                        endTime = cursorBook.getString(cursorBook.getColumnIndex("end_time"));
                    }
                    String notification = "<b>"+name+"</b> cancelled the order<br><b>("+tableName+", "+startTime+"-"+endTime.substring(11)+")</b>";
                    String notificationRestaurant = "You cancelled the order of <b>"+restaurant_name+"</b><br><b>("+tableName+", "+startTime+"-"+endTime.substring(11)+")</b>";
                    TableBookingDatabase.getDatabase(view.getContext()).notificationDAO().insertNotification(new Notification(notificationRestaurant,custId,-1));
                    TableBookingDatabase.getDatabase(view.getContext()).notificationDAO().insertNotification(new Notification(notification,-1,restaurantId));
                }
                if(getActivity().getClass().getName().equalsIgnoreCase("wia2007.project.tablebooking.ManageBookingFutureActivity"))
                    startActivity(new Intent(getContext(),MainActivity.class));
                else{
                    getActivity().setResult(Activity.RESULT_OK);
                    getActivity().finish();
                }
            }
        });

        Button closeFragment = view.findViewById(R.id.BtnCloseReject);
        closeFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setVisibility(View.INVISIBLE);
            }
        });

        return view;
    }
}