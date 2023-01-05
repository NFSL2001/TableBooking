package com.example.invitable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.invitable.dao.TableDAO;
import com.example.invitable.database.TableBookingDatabase;
import com.example.invitable.entity.Table;

import java.util.List;

public class table_manage extends AppCompatActivity {

    private TableAdapter tableAdapter1, tableAdapter2, tableAdapter3, tableAdapter4;
    EditText addtableNo, addtablePax, deletetableNo;
    Button save, cancel, btnTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_manage);

        TableBookingDatabase tableBookingDatabase = TableBookingDatabase.getDatabase(getApplicationContext());
        addtableNo = findViewById(R.id.addTableNo);
        addtablePax = findViewById(R.id.editTablePax);
        deletetableNo = findViewById(R.id.deleteTableNo);
        save = findViewById(R.id.btnSave);
        cancel = findViewById(R.id.btnCancel);

        save.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                TableBookingDatabase database = TableBookingDatabase.getDatabase(getApplicationContext());
                TableDAO tableDAO = database.tableDAO();
                Table table = new Table();

                if(addtableNo != null && deletetableNo == null){
                    String  tableno = addtableNo.toString();
                    int tablepax = Integer.parseInt(addtablePax.toString());
                    String[] split =tableno.split("T");
                    int tableid = Integer.parseInt(split[0]);

                    //update table in database
                    table.setTable_id(tableid);
                    table.setName(tableno);
                    table.setSize(tablepax);
                    List<Table> tableList = tableDAO.getTableById(tableid);
                    table.setRestaurant_id(0);
                    tableDAO.insertTables(table);

                }else if(addtableNo == null && deletetableNo != null){
                    String deleteTable = deletetableNo.toString();
                    String[] split =deleteTable.split("T");
                    int tableid = Integer.parseInt(split[0]);

                    //update table in database
                    List<Table> tableList = tableDAO.getTableById(tableid);
                    table = tableList.get(0);
                    tableDAO.deleteTables(table);

                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        RecyclerView recyclerView1 = findViewById(R.id.RC2Table);
        tableAdapter1 = new TableAdapter(this);
        recyclerView1.setAdapter(tableAdapter1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        RecyclerView recyclerView2 = findViewById(R.id.RC4Table);
        tableAdapter2 = new TableAdapter(this);
        recyclerView2.setAdapter(tableAdapter2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        RecyclerView recyclerView3 = findViewById(R.id.RC6Table);
        tableAdapter3 = new TableAdapter(this);
        recyclerView3.setAdapter(tableAdapter3);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        RecyclerView recyclerView4 = findViewById(R.id.RC8Table);
        tableAdapter4 = new TableAdapter(this);
        recyclerView4.setAdapter(tableAdapter4);
        recyclerView4.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }
}