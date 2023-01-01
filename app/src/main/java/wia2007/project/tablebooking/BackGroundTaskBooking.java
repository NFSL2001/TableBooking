package wia2007.project.tablebooking;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import wia2007.project.tablebooking.database.TableBookingDatabase;

public class BackGroundTaskBooking extends AsyncTask<String, ShowBookingsList, String> {
    Context context;
    BookingsAdapter bookingsAdapter;
    Activity activity;
    ListView listView;

    BackGroundTaskBooking(Context context) {
        this.context = context;
        activity = (Activity) context;
    }

    @SuppressLint("Range")
    @Override
    protected String doInBackground(String... strings) {
        String method = strings[0];
//        DatabaseHelper dbHelper = new DatabaseHelper(context);
        if (method.equals("get_info")) {
            listView = (ListView) activity.findViewById(R.id.BookingListView);
            bookingsAdapter = new BookingsAdapter(context, R.layout.display_booking_list_row);
            String sortCondition = strings[1];
            int sortInt = 0;
            sortInt = Integer.parseInt(sortCondition);
            Cursor cursor = TableBookingDatabase.getDatabase(this.context).bookingDAO().getBookingsList(1,sortInt);

            Integer booking_id;
            String startTime, endTime;
            String remark, custName, custEmail, custMobile,tableName;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");

            while (cursor.moveToNext()) {
                booking_id = cursor.getInt(cursor.getColumnIndex("booking_id"));
                tableName = cursor.getString(cursor.getColumnIndex("name"));
                startTime = cursor.getString(cursor.getColumnIndex("start_time"));
                endTime = cursor.getString(cursor.getColumnIndex("end_time"));
                custName = cursor.getString(cursor.getColumnIndex("user_name"));
                remark = cursor.getString(cursor.getColumnIndex("remark"));
                custEmail = cursor.getString(cursor.getColumnIndex("email"));
                custMobile = cursor.getString(cursor.getColumnIndex("mobile_number"));

                Time start_time = new Time(0);
                Time end_time = new Time(0);
                try {
                    start_time = new Time(simpleDateFormat.parse(startTime).getTime());
                    end_time = new Time(simpleDateFormat.parse(endTime).getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                ShowBookingsList showBookingsList = new ShowBookingsList(booking_id, start_time,end_time, tableName, custName, remark, custEmail, custMobile);

                publishProgress(showBookingsList);
            }
            return "get_info";
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(ShowBookingsList... values) {
        bookingsAdapter.add(values[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        if (s.equals("get_info")) {
            listView = (ListView) activity.findViewById(R.id.BookingListView);
            listView.setAdapter(bookingsAdapter);
        } else {
            Toast.makeText(context, s, Toast.LENGTH_LONG).show();
        }
    }
}
