package wia2007.project.tablebooking;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class BookingsAdapter extends ArrayAdapter {
    List list = new ArrayList();
    Intent intent = new Intent(getContext(), BookingDetails.class);
    public BookingsAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public void add(ShowBookingsList showBookingsList) {
        list.add(showBookingsList);
        super.add(showBookingsList);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        BookingsHolder bookingsHolder;
        if(row == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.display_booking_list_row,parent,false);
            bookingsHolder = new BookingsHolder();
            bookingsHolder.TVTableID = row.findViewById(R.id.TVTableId);
            bookingsHolder.TVBookedCustName = row.findViewById(R.id.TVBookedCustName);
            bookingsHolder.TVBookingDate = row.findViewById(R.id.TVBookingDate);
            bookingsHolder.TVBookingTime = row.findViewById(R.id.TVBookingTime);
            row.setTag(bookingsHolder);
        }else{
            bookingsHolder = (BookingsHolder) row.getTag();
        }

        ShowBookingsList showBookingsList = (ShowBookingsList) getItem(position);
        String tableName= showBookingsList.getTable_name();
        String customerName = showBookingsList.getCustName();
        Integer bookingId = showBookingsList.getBooking_id();
        Time startTime = showBookingsList.getStartTime();
        Time endTime = showBookingsList.getEndTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        String start_time = simpleDateFormat.format(startTime);
        String end_time = simpleDateFormat.format(endTime);
        String time_interval = start_time.substring(11)+" - "+end_time.substring(11);
        String date = start_time.substring(0,10);

        bookingsHolder.TVTableID.setText(tableName);
        bookingsHolder.TVBookedCustName.setText(customerName);
        bookingsHolder.TVBookingDate.setText(date);
        bookingsHolder.TVBookingTime.setText(time_interval);

        Button BtnViewBooking = row.findViewById(R.id.BtnViewBooking);
        BtnViewBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String customerPhone = showBookingsList.getCustMobile();
                String customerEmail = showBookingsList.getCustEmail();
                String remark = showBookingsList.getRemark();

                intent.putExtra("CustomerName",customerName);
                intent.putExtra("CustomerPhone",customerPhone);
                intent.putExtra("CustomerEmail",customerEmail);
                intent.putExtra("Remark",remark);
                intent.putExtra("TableName",tableName);
                intent.putExtra("Date",date);
                intent.putExtra("Time",time_interval);
                intent.putExtra("BookingId",bookingId);
                ((Activity)getContext()).startActivityForResult(intent,1);

            }
        });

        return row;
    }

    static class BookingsHolder{
        TextView TVTableID, TVBookedCustName, TVBookingDate, TVBookingTime;
    }
}
