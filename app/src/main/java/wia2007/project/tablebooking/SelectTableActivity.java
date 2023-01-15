package wia2007.project.tablebooking;

import static java.util.Map.Entry.comparingByKey;
import static java.util.stream.Collectors.toMap;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import wia2007.project.tablebooking.dao.TableDAO;
import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.Restaurant;
import wia2007.project.tablebooking.entity.Table;


public class SelectTableActivity extends AppCompatActivity {


    static TextView TableSelected;
    Button NextButton, CancelButton, BackButton;
    RecyclerView recyclerView;
    Map<Integer, List<Table>> tableMap = new HashMap<>();
    Map<Integer, List<Table>> tableBySize = new HashMap();
    List<TableViewModel> fullList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_table);
        String name = getIntent().getStringExtra("name");
        int customerID = getIntent().getIntExtra("cusID", 0);
        int restaurantID = getIntent().getIntExtra("resID", 0);
        int tableSize = getIntent().getIntExtra("tSize", 0);
        String startString = getIntent().getStringExtra("startString");
        String endString = getIntent().getStringExtra("endString");
        TableSelected = findViewById(R.id.select_table_tableSelected);

        recyclerView = findViewById(R.id.RVSelectTable);
        NextButton = findViewById(R.id.select_table_nextButton);
        CancelButton = findViewById(R.id.select_table_cancelButton);
        BackButton = findViewById(R.id.select_table_backButton);

        TableBookingDatabase db = TableBookingDatabase.getDatabase(getApplicationContext());
        TableDAO tableDAO = db.tableDAO();
        List<Table> tableList = tableDAO.getAvailableTableAndSize(restaurantID,startString,endString,tableSize);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tableMap = tableList.stream().collect(Collectors.groupingBy(m -> m.getSize() == -1 ? -1 : m.getSize()));
            tableBySize = tableMap
                    .entrySet()
                    .stream()
                    .sorted(comparingByKey())
                    .collect(toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));
        }
        fullList = addToTableBaseData(tableBySize);
        TableAdapter tableAdapter = new TableAdapter(this, fullList,getClass());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(tableAdapter);

        NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Table> selectedTable = new ArrayList<>(ItemAdapter.selectedTable);
                int table = selectedTable.get(0).getTable_id();
                int size = selectedTable.get(0).getSize();

                if(selectedTable.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Table Selection Cannot be Empty",Toast.LENGTH_SHORT).show();
                }else{
                    openNextActivity(customerID, restaurantID, size,table,startString,endString,name);
                }
            }
        });

        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelActivity();
            }
        });

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPreviousActivity();
            }
        });
    }


    public void openPreviousActivity() {
        ItemAdapter.selectedTable.clear();
        finish();
    }

    public void openNextActivity(int customerID, int restaurantID, int tableSize, int tID, String startString, String endString,String name) {
        Intent intent = new Intent(this, PreOrderFoodActivity.class);
        intent.putExtra("cusID", customerID);
        intent.putExtra("resID", restaurantID);
        intent.putExtra("tSize", tableSize);
        intent.putExtra("name",name);
        intent.putExtra("tableID",tID);
        intent.putExtra("startString", startString);
        intent.putExtra("endString", endString);
        startActivity(intent);
    }

    public void cancelActivity() {
        ItemAdapter.selectedTable.clear();
        Intent backIntent = new Intent(this, RestaurantMainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        backIntent.putExtra("ID",getIntent().getIntExtra("resID",0));
        backIntent.putExtra("name",getIntent().getStringExtra("name"));
        startActivity(backIntent);
    }

    public List<TableViewModel> addToTableBaseData(Map<Integer, List<Table>> tableBySize) {
        ArrayList<TableViewModel> list = new ArrayList<>();
        List<Integer> sizeArr = new ArrayList<>(tableBySize.keySet());
        List<List<Table>> tableArr = new ArrayList<>(tableBySize.values());
        for (int j = 0; j < sizeArr.size(); j++) {
            list.add(new TableViewModel(tableArr.get(j), sizeArr.get(j)));
        }
        return list;
    }
}