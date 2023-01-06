package wia2007.project.tablebooking;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import wia2007.project.tablebooking.dao.BookingDAO;
import wia2007.project.tablebooking.dao.TableDAO;
import wia2007.project.tablebooking.database.TableBookingDatabase;

public class TableAvailabilityList extends AppCompatActivity {

    TextView date, time, numOfTableBooked, numOfTableAvailable, totalTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_availability_list);

        date = findViewById(R.id.showDate);
        time = findViewById(R.id.showTime2);
        numOfTableBooked = findViewById(R.id.showTableBooked2);
        numOfTableAvailable = findViewById(R.id.showTableAvaialable);
        totalTable = findViewById(R.id.showTotalTable);

        String DATE = getIntent().getStringExtra("keydate");
        String TIME = getIntent().getStringExtra("keytime");

        TableBookingDatabase database = TableBookingDatabase.getDatabase(getApplicationContext());
        TableDAO tableDAO = database.tableDAO();
        BookingDAO bookingDAO = database.bookingDAO();
        //List<Table> tableList = tableDAO.getAvailableTable(TIME, TIME);

        date.setText(DATE);
        time.setText(TIME);
        //numOfTableAvailable.setText(tableList.toString());
        totalTable.setText("20");



    }
}