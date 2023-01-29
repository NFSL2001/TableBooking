package wia2007.project.tablebooking;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.sqlite.db.SimpleSQLiteQuery;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import wia2007.project.tablebooking.database.TableBookingDatabase;

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
            bookingsHolder.TVShowStatus = row.findViewById(R.id.TVShowStatus);
            row.setTag(bookingsHolder);
        }else{
            bookingsHolder = (BookingsHolder) row.getTag();
        }

        ShowBookingsList showBookingsList = (ShowBookingsList) getItem(position);
        String tableName= showBookingsList.getTable_name();
        String customerName = showBookingsList.getCustName();
        Integer bookingId = showBookingsList.getBooking_id();
        Time startTime = showBookingsList.getStart_time();
        Time endTime = showBookingsList.getEnd_time();
        String status = showBookingsList.getStatus();

        //Time to String
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String start_time = simpleDateFormat.format(startTime);
        String end_time = simpleDateFormat.format(endTime);
        String time_interval = start_time.substring(11)+" - "+end_time.substring(11);
        String date = start_time.substring(0,10);

        boolean bookingOver = false;
        Date compareDate = null;
        try {
            Calendar calendar = Calendar.getInstance();
            compareDate = simpleDateFormat.parse(start_time);
            if (compareDate.before(calendar.getTime()) && !"Cancelled".equalsIgnoreCase(status)) {
                bookingOver = true;
                bookingsHolder.TVShowStatus.setText("Completed");
                TableBookingDatabase.getDatabase(getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("UPDATE Booking SET status = 'Completed' WHERE booking_id = "+bookingId));
                bookingsHolder.TVShowStatus.setVisibility(View.VISIBLE);
            } else {
                bookingOver = false;
                bookingsHolder.TVShowStatus.setVisibility(View.INVISIBLE);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if("Cancelled".equalsIgnoreCase(status)){
            bookingOver=true;
            bookingsHolder.TVShowStatus.setText("Cancelled");
            bookingsHolder.TVShowStatus.setVisibility(View.VISIBLE);
        }
        bookingsHolder.TVTableID.setText(tableName);
        bookingsHolder.TVBookedCustName.setText(customerName);
        bookingsHolder.TVBookingDate.setText(date);
        bookingsHolder.TVBookingTime.setText(time_interval);

        Button BtnViewBooking = row.findViewById(R.id.BtnViewBooking);
        boolean finalBookingOver = bookingOver;
        BtnViewBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String customerPhone = showBookingsList.getMobile_number();
                String customerEmail = showBookingsList.getEmail();
                String remark = showBookingsList.getRemark();

                intent.putExtra("CustomerName",customerName);
                intent.putExtra("CustomerPhone",customerPhone);
                intent.putExtra("CustomerEmail",customerEmail);
                intent.putExtra("Remark",remark);
                intent.putExtra("TableName",tableName);
                intent.putExtra("Date",date);
                intent.putExtra("Time",time_interval);
                intent.putExtra("bookingID",bookingId);
                intent.putExtra("BookingOver", finalBookingOver);
                intent.putExtra("status",status);
                ((Activity)getContext()).startActivityForResult(intent,1);
            }
        });

        return row;
    }

    static class BookingsHolder{
        TextView TVTableID, TVBookedCustName, TVBookingDate, TVBookingTime, TVShowStatus;
    }
}
