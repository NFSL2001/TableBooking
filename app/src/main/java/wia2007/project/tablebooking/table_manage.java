package wia2007.project.tablebooking;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import wia2007.project.tablebooking.dao.TableDAO;
import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.Table;

import java.util.List;

public class table_manage extends AppCompatActivity {

    TableAdapter tableAdapter, tableAdapter1, tableAdapter2, tableAdapter3, tableAdapter4;
    TableViewModel tableViewModel, tableViewModel1, tableViewModel2, tableViewModel3, tableViewModel4;
    EditText addtableNo, addtablePax, deletetableNo;
    Button save, cancel;
    List<Table> tableList, tableList2, tableList4, tableList6, tableList8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_manage);

        TableBookingDatabase database = TableBookingDatabase.getDatabase(getApplicationContext());
        TableDAO tableDAO = database.tableDAO();

        addtableNo = findViewById(R.id.addTableNo);
        addtablePax = findViewById(R.id.editTablePax);
        deletetableNo = findViewById(R.id.deleteTableNo);
        save = findViewById(R.id.btnSave);
        cancel = findViewById(R.id.btnCancel);

        save.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                TableDAO tableDAO = database.tableDAO();
                Table table = new Table();

                if(addtableNo != null && deletetableNo == null){
                    String  tableno = addtableNo.toString();
                    int tablepax = Integer.parseInt(addtablePax.toString());
                    String[] split =tableno.split("T");
                    int tableid = Integer.parseInt(split[1]);

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
                    int tableid = Integer.parseInt(split[1]);

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

        //to separate into table category
        tableList = tableDAO.getTableByRestaurant(0);
        tableList2 = new ArrayList<Table>();
        tableList4 = new ArrayList<Table>();
        tableList6 = new ArrayList<Table>();
        tableList8 = new ArrayList<Table>();

        for(int i=0;i<tableList.size();i++){
            switch(tableList.get(i).getSize()) {
                case 2:
                    tableList2.add(tableList.get(i));
                    break;
                case 4:
                    tableList4.add(tableList.get(i));
                    break;
                case 6:
                    tableList6.add(tableList.get(i));
                    break;
                case 8:
                    tableList8.add(tableList.get(i));
                    break;
                default:
                    new RuntimeException("No Table");
            }
        }



        RecyclerView recyclerView1 = findViewById(R.id.RC2Table);
        tableAdapter1 = new TableAdapter(this,tableList2);
        recyclerView1.setAdapter(tableAdapter1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        RecyclerView recyclerView2 = findViewById(R.id.RC4Table);
        tableAdapter2 = new TableAdapter(this,tableList4);
        recyclerView2.setAdapter(tableAdapter2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        RecyclerView recyclerView3 = findViewById(R.id.RC6Table);
        tableAdapter3 = new TableAdapter(this,tableList6);
        recyclerView3.setAdapter(tableAdapter3);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        RecyclerView recyclerView4 = findViewById(R.id.RC8Table);
        tableAdapter4 = new TableAdapter(this,tableList8);
        recyclerView4.setAdapter(tableAdapter4);
        recyclerView4.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

//        tableViewModel = new ViewModelProvider(this).get(TableViewModel.class);
//        tableViewModel.getAllTable().observe(this, new Observer<List<Table>>() {
//            @Override
//            public void onChanged(List<Table> tables) {
//                tableAdapter.setTable(tables);
//            }
//        });

    }


}
