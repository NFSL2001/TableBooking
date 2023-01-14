package wia2007.project.tablebooking;

import static java.util.Map.Entry.comparingByKey;
import static java.util.stream.Collectors.toMap;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import wia2007.project.tablebooking.dao.TableDAO;
import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.MenuItem;
import wia2007.project.tablebooking.entity.Table;

public class table_manage extends AppCompatActivity {

    TableAdapter tableAdapter;
    EditText addtableName, addtablePax;
    Button Add, Cancel;
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
        addtableName = findViewById(R.id.addTableName);
        addtablePax = findViewById(R.id.editTablePax);
        Add = findViewById(R.id.btnAdd);
        Cancel = findViewById(R.id.btnCancel);

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
        tableAdapter = new TableAdapter(this, tableList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(tableAdapter);

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tableName = addtableName.getText().toString();
                int tablePax = Integer.parseInt(addtablePax.getText().toString());
                if (tableName.equals("") || addtablePax.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Name and No. of Pax cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    TableDAO tableDAO = database.tableDAO();
                    tableDAO.insertTables(new Table(restaurant_id, tableName, tablePax));
                }
                Intent intent = new Intent(getApplicationContext(),table_manage.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("RestaurantID",restaurant_id);
                startActivity(intent);
            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
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
            list.add(new TableViewModel(tableArr.get(j), sizeArr.get(j)));
        }
        return list;
    }

}
