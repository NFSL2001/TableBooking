package wia2007.project.tablebooking;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import androidx.sqlite.db.SimpleSQLiteQuery;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import wia2007.project.tablebooking.database.TableBookingDatabase;

public class BookingAdapter extends CursorAdapter{
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public BookingAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.individual_booking_item, viewGroup, false);
    }
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView restaurantName = view.findViewById(R.id.TVBookingListRestaurantName);
        String name = cursor.getString(cursor.getColumnIndexOrThrow("restaurant_name"));
        restaurantName.setText(name);
        TextView date = view.findViewById(R.id.TVBookingListBookingDate);
        try {
            date.setText(dateFormat2.format(dateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow("start_time")))).substring(0,10));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String status = cursor.getString(cursor.getColumnIndexOrThrow("status"));
        boolean bookingOver = false;
        int booking = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
        TextView TVShowStatus = view.findViewById(R.id.TVStatus);
        Date compareDate = null;
        if ("Cancelled".equalsIgnoreCase(status)) {
            bookingOver = true;
            TVShowStatus.setVisibility(View.VISIBLE);
            TVShowStatus.setText("Cancelled");
        } else {
            bookingOver = false;
            TVShowStatus.setVisibility(View.GONE);
        }
        try {
            Calendar calendar = Calendar.getInstance();
            compareDate = dateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow("start_time")));
            if(compareDate.before(calendar.getTime()) && !"Cancelled".equalsIgnoreCase(status)) {
                bookingOver = true;
                TableBookingDatabase.getDatabase(context).bookingDAO().rawQuery(new SimpleSQLiteQuery("UPDATE Booking SET status = 'Completed' WHERE booking_id = "+booking));
                TVShowStatus.setVisibility(View.VISIBLE);
                TVShowStatus.setText("Completed");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        boolean finalBookingOver = bookingOver;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ManageBookingActivity.class);
                intent.putExtra("restName",name);
                intent.putExtra("bookingID",booking);
                System.out.println(name+":"+ finalBookingOver);
                intent.putExtra("bookingOver", finalBookingOver);
                intent.putExtra("status",status);
                context.startActivity(intent);
            }
        });
    }
}
