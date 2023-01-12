package wia2007.project.tablebooking;

import static java.util.Map.Entry.comparingByKey;
import static java.util.stream.Collectors.toMap;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import wia2007.project.tablebooking.dao.TableDAO;
import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.Table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class table_manage extends AppCompatActivity implements RecycleViewInterface {

    TableAdapter tableAdapter;
    EditText addtableNo, addtablePax, deletetableNo;
    Button save, cancel;
    List<TableViewModel> tableList;
    Map<Integer, List<Table>> tableMap = new HashMap<>();
    Map<Integer, List<Table>> tableBySize = new HashMap();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_manage);

        Toolbar toolbar = findViewById(R.id.TVTableManageAct);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Manage Table");

        TableBookingDatabase database = TableBookingDatabase.getDatabase(this);
        TableDAO tableDAO = database.tableDAO();
        int restaurant_id = getIntent().getExtras().getInt("RestaurantID");
        addtableNo = findViewById(R.id.addTableNo);
        addtablePax = findViewById(R.id.editTablePax);
        deletetableNo = findViewById(R.id.deleteTableNo);
        save = findViewById(R.id.btnSave);
        cancel = findViewById(R.id.btnCancel);

        List<Table> total = tableDAO.getTableByRestaurant(restaurant_id);
        RecyclerView recyclerView = findViewById(R.id.RVTable);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tableMap = total.stream().collect(Collectors.groupingBy(m -> m.getSize() == -1 ? -1 : m.getSize()));
            tableBySize = tableMap
                    .entrySet()
                    .stream()
                    .sorted(Collections.reverseOrder(comparingByKey()))
                    .collect(toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));
        }
        tableList = addToTableBaseData(tableBySize);
        tableAdapter = new TableAdapter(this, tableList,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(tableAdapter);

        save.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                TableDAO tableDAO = database.tableDAO();
                    String  tableno = addtableNo.getText().toString();
                    int tablepax = Integer.parseInt(addtablePax.getText().toString());
                    String[] split =tableno.split("T");

                    //update table in database
//                    table.setTable_id(tableid);
//                    table.setName(tableno);
//                    table.setSize(tablepax);
                    tableDAO.insertTables(new Table(restaurant_id,tableno,tablepax));


//                    String deleteTable = deletetableNo.toString();
//                    String[] split =deleteTable.split("T");
//                    int tableid = Integer.parseInt(split[0]);
//
//                    //update table in database
//                    List<Table> tableList = tableDAO.getTableById(tableid);
//                    table = tableList.get(0);
//                    tableDAO.deleteTables(table);


            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });






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

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onLongClick(int position) {

    }
}
