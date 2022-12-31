package wia2007.project.tablebooking;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.BookingContainMenu;

public class BackGroundTaskFood extends AsyncTask<String, ShowFoodOrderList, String> {
    Context context;
    FoodOrderAdapter foodOrderAdapter;
    Activity activity;
    ListView listView;

    BackGroundTaskFood(Context context) {
        this.context = context;
        activity = (Activity) context;
    }

    @SuppressLint("Range")
    @Override
    protected String doInBackground(String... strings) {
        String method = strings[0];
        if (method.equals("get_food_order")) {
            listView = (ListView) activity.findViewById(R.id.FoodOrderListView);
            TextView tv = activity.findViewById(R.id.TVID);
            int BNum = Integer.parseInt(tv.getText().toString());
            Cursor cursor = TableBookingDatabase.getDatabase(context).bookingContainMenuDAO().getFoodOrder(BNum);
            foodOrderAdapter = new FoodOrderAdapter(context, R.layout.display_food_order_row);
            String menuName;
            int booking_id, quantity;
            double totalPrice;
            while (cursor.moveToNext()) {
                booking_id = cursor.getInt(cursor.getColumnIndex("booking_id"));
                quantity = cursor.getInt(cursor.getColumnIndex("quantity"));
                totalPrice = cursor.getDouble(cursor.getColumnIndex("Total_cost"));
                menuName = cursor.getString(cursor.getColumnIndex("menu_name"));

                ShowFoodOrderList showFoodOrderList = new ShowFoodOrderList(booking_id, menuName, quantity, totalPrice);

                publishProgress(showFoodOrderList);
            }
            return "get_food_order";
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(ShowFoodOrderList... values) {
        foodOrderAdapter.add(values[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        if (s.equals("get_food_order")) {
            listView = (ListView) activity.findViewById(R.id.FoodOrderListView);
            listView.setAdapter(foodOrderAdapter);
        } else {
            Toast.makeText(context, s, Toast.LENGTH_LONG).show();
        }
    }
}
