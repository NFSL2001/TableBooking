package wia2007.project.tablebooking;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import wia2007.project.tablebooking.dao.BookingDAO;
import wia2007.project.tablebooking.dao.TableDAO;
import wia2007.project.tablebooking.database.TableBookingDatabase;

public class TableAvailabilityList extends AppCompatActivity {

    TextView date, time, numOfTableBooked, numOfTableAvailable, totalTable;
    List<Table> tableList2, tableList4, tableList6, tableList8;
    TableAdapter tableAdapter1, tableAdapter2, tableAdapter3, tableAdapter4;

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
        String TIME = getIntent().getStringExtra("keyTime");
        String sTime = getIntent().getStringExtra("keystartdateNTime");
        String eTime = getIntent().getStringExtra("keyenddateNTime");
        Timestamp ts1= Timestamp.valueOf(sTime);
        Long val1 = ts1.getTime();
        Timestamp ts2= Timestamp.valueOf(eTime);
        Long val2 = ts2.getTime();

        Time startTime = new Time(val1);
        Time endTime = new Time(val2);

        TableBookingDatabase database = TableBookingDatabase.getDatabase(getApplicationContext());
        TableDAO tableDAO = database.tableDAO();
        List<Table> total = tableDAO.getTableByRestaurant(0);
        List<Table> availableTableList = tableDAO.getAvailableTable(0,startTime, endTime);

        date.setText(DATE);
        time.setText(TIME);
        numOfTableBooked.setText((total.size()-availableTableList.size()));
        numOfTableAvailable.setText(availableTableList.size());
        totalTable.setText(total.size());

        tableList2 = new ArrayList<Table>();
        tableList4 = new ArrayList<Table>();
        tableList6 = new ArrayList<Table>();
        tableList8 = new ArrayList<Table>();
        for(int i=0;i<availableTableList.size();i++){
            if(availableTableList.get(i).getSize() == 2){
                tableList2.add(availableTableList.get(i));
            }else if(availableTableList.get(i).getSize() == 4){
                tableList4.add(availableTableList.get(i));
            }else if(availableTableList.get(i).getSize() == 6){
                tableList6.add(availableTableList.get(i));
            }else if(availableTableList.get(i).getSize() == 8){
                tableList8.add(availableTableList.get(i));
            }else{
                throw new RuntimeException("No Table");
            }
        }

        RecyclerView recyclerView1 = findViewById(R.id.RCtwopeopleTable);
        tableAdapter1 = new TableAdapter(this,tableList2);
        recyclerView1.setAdapter(tableAdapter1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        RecyclerView recyclerView2 = findViewById(R.id.RCfourpeopleTable);
        tableAdapter2 = new TableAdapter(this,tableList4);
        recyclerView2.setAdapter(tableAdapter2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        RecyclerView recyclerView3 = findViewById(R.id.RCsixpeopleTable);
        tableAdapter3 = new TableAdapter(this,tableList6);
        recyclerView3.setAdapter(tableAdapter3);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        RecyclerView recyclerView4 = findViewById(R.id.RCeightpeopleTable);
        tableAdapter4 = new TableAdapter(this,tableList8);
        recyclerView4.setAdapter(tableAdapter4);
        recyclerView4.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
