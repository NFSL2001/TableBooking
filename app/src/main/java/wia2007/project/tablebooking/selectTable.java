package wia2007.project.tablebooking;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.TextView;

import com.example.invitable.R;

import wia2007.project.tablebooking.dao.TableDAO;
import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.Table;

import java.util.List;


public class selectTable extends AppCompatActivity implements ItemClickListenerTable {

    public static final String TABLE_ID = "com.example.invitable.TABLE_ID";

    TextView TableSelected;
    List<Table> tableList2, tableList4, tableList6, tableList8;
    selectTableAdapter tableAdapter1, tableAdapter2, tableAdapter3, tableAdapter4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_select_table);

        TableBookingDatabase db = TableBookingDatabase.getDatabase(getApplicationContext());
        TableDAO tableDAO = db.tableDAO();
//        List<Table> availableTableList = tableDAO.getAvailableTable(0,startTime, endTime);
//
//        for(int i=0;i<availableTableList.size();i++){
//            if(availableTableList.get(i).getSize() == 2){
//                tableList2.add(availableTableList.get(i));
//            }else if(availableTableList.get(i).getSize() == 4){
//                tableList4.add(availableTableList.get(i));
//            }else if(availableTableList.get(i).getSize() == 6){
//                tableList6.add(availableTableList.get(i));
//            }else if(availableTableList.get(i).getSize() == 8){
//                tableList8.add(availableTableList.get(i));
//            }else{
//                throw new RuntimeException("No Table");
//            }
//        }
//
//        RecyclerView recyclerView1 = findViewById(R.id.select_table_2people_recylerview);
//        tableAdapter1 = new selectTableAdapter(this,tableList2, this);
//        recyclerView1.setAdapter(tableAdapter1);
//
//        RecyclerView recyclerView2 = findViewById(R.id.select_table_4people_recylerview);
//        tableAdapter2 = new selectTableAdapter(this,tableList4, this);
//        recyclerView2.setAdapter(tableAdapter2);
//
//        RecyclerView recyclerView3 = findViewById(R.id.select_table_6people_recylerview);
//        tableAdapter3 = new selectTableAdapter(this,tableList6 , this);
//        recyclerView3.setAdapter(tableAdapter3);
//
//        RecyclerView recyclerView4 = findViewById(R.id.select_table_8people_recylerview);
//        tableAdapter4 = new selectTableAdapter(this,tableList8, this);
//        recyclerView4.setAdapter(tableAdapter4);
    }

    @Override
    public void onClick(int position) {
        TableSelected.setText(tableList2.get(position).getTable_id().toString());
    }
}