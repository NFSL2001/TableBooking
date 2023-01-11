package wia2007.project.tablebooking;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class BookingAdapter extends CursorAdapter {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public BookingAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        System.out.println(c.getColumnIndex("booking_id"));
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.individual_booking_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView restaurantName = view.findViewById(R.id.TVBookingListRestaurantName);
        restaurantName.setText(cursor.getString(cursor.getColumnIndexOrThrow("restaurant_name")));

        TextView date = view.findViewById(R.id.TVBookingListBookingDate);
        date.setText(cursor.getString(cursor.getColumnIndexOrThrow("start_time")));
    }
}
