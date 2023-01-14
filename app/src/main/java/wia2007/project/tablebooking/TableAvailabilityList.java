package wia2007.project.tablebooking;

import static java.util.Map.Entry.comparingByKey;
import static java.util.stream.Collectors.toMap;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import wia2007.project.tablebooking.dao.TableDAO;
import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.Table;

public class TableAvailabilityList extends AppCompatActivity{

    TextView date, time, numOfTableBooked, numOfTableAvailable, totalTable;
    TableAdapter tableAdapter;
    Map<Integer, List<Table>> tableMap = new HashMap<>();
    Map<Integer, List<Table>> tableBySize = new HashMap();
    List<TableViewModel> tableList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_availability_list);

        Toolbar toolbar = findViewById(R.id.toolbarViewAbilityList);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Check Table Availability");

        date = findViewById(R.id.showDate);
        time = findViewById(R.id.showTime2);
        numOfTableBooked = findViewById(R.id.showTableBooked2);
        numOfTableAvailable = findViewById(R.id.showTableAvailable);
        totalTable = findViewById(R.id.showTotalTable);

        int restaurant_id = getIntent().getExtras().getInt("RestaurantID");
        String DATE = getIntent().getStringExtra("keyDate");
        String startTime = getIntent().getStringExtra("startTime");
        String endTime = getIntent().getStringExtra("endTime");

        TableBookingDatabase database = TableBookingDatabase.getDatabase(getApplicationContext());
        TableDAO tableDAO = database.tableDAO();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat dfOri = new SimpleDateFormat("yyyy-MM-dd");

        try {
            date.setText(df.format(dfOri.parse(DATE)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Table> total = tableDAO.getTableByRestaurant(restaurant_id);
        List<Table> availableTableList = tableDAO.getAvailableTable(restaurant_id,DATE+" "+startTime,DATE+" "+endTime);

        time.setText(startTime+" - "+endTime);
        numOfTableBooked.setText(Integer.toString(total.size() - availableTableList.size()));
        numOfTableAvailable.setText(Integer.toString(availableTableList.size()));
        totalTable.setText(Integer.toString(total.size()));

        RecyclerView recyclerView = findViewById(R.id.RVTableAvailability);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tableMap = total.stream().collect(Collectors.groupingBy(m -> m.getSize() == -1 ? -1 : m.getSize()));
            tableBySize = tableMap
                    .entrySet()
                    .stream()
                    .sorted(Collections.reverseOrder(comparingByKey()))
                    .collect(toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));
        }
        tableList = addToTableBaseData(tableBySize);
        List<Table> NotAvailable = new ArrayList<>(total);
        NotAvailable.removeAll(availableTableList);
        tableAdapter = new TableAdapter(this, tableList,NotAvailable);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(tableAdapter);
//        tableAdapter.notifyNewData(tableList);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public List<TableViewModel> addToTableBaseData(Map<Integer, List<Table>> tableBySize) {
        ArrayList<TableViewModel> list = new ArrayList<>();
        List<Integer> sizeArr = new ArrayList<>(tableBySize.keySet());
        List<List<Table>> tableArr = new ArrayList<>(tableBySize.values());
        for (int j = 0; j < sizeArr.size(); j++) {
            list.add(new TableViewModel(tableArr.get(j),sizeArr.get(j)));
        }
        return list;
    }
}