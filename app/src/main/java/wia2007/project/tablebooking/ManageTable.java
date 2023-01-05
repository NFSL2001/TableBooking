package com.example.invitable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.invitable.database.TableBookingDatabase;
import com.example.invitable.entity.Table;
import com.example.invitable.dao.TableDAO;

import java.util.List;

public class ManageTable extends AppCompatActivity {

    EditText addtableNo, addtablePax, deletetableNo;
    Button save, cancel, btnTable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_table);

        addtableNo = findViewById(R.id.addTableNo);
        addtablePax = findViewById(R.id.editTablePax);
        deletetableNo = findViewById(R.id.deleteTableNo);
        save = findViewById(R.id.saveButton);
        cancel = findViewById(R.id.cancelButton);

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
    }
}