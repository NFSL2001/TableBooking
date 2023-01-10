package wia2007.project.tablebooking;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import wia2007.project.tablebooking.dao.TableDAO;
import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.Table;


public class SelectTableActivity extends AppCompatActivity {


    TextView TableSelected;
    Button NextButton, CancelButton, BackButton;
    Spinner TableSize, TableChoice;
    int tableID = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_table);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        int customerID = getIntent().getIntExtra("cusID", 0);
        int restaurantID = getIntent().getIntExtra("resID", 0);
        int tableSize = getIntent().getIntExtra("tSize", 0);
        long startTime = getIntent().getLongExtra("sTime", 0);
        long endTime = getIntent().getLongExtra("eTime", 0);
        String startString = getIntent().getStringExtra("startString");
        String endString = getIntent().getStringExtra("endString");
        int numPeople = getIntent().getIntExtra("numPeople",1);
        TableSelected = findViewById(R.id.select_table_tableSelected);

        NextButton = findViewById(R.id.select_table_nextButton);
        CancelButton = findViewById(R.id.select_table_cancelButton);
        BackButton = findViewById(R.id.select_table_backButton);

        TableSize = findViewById(R.id.select_table_sizeSpinner);
        TableChoice = findViewById(R.id.select_table_choice);

        Time startT = new Time(startTime);
        Time endT = new Time(endTime);

        TableBookingDatabase db = TableBookingDatabase.getDatabase(getApplicationContext());
        TableDAO tableDAO = db.tableDAO();
        List<Table> tableList = tableDAO.getTableById(restaurantID);
        Set<Integer> size = new HashSet<>();
        for (int i = 0; i < tableList.size(); i++) {
            size.add(tableList.get(i).getSize());
        }
        int n = size.size();
        Integer arr[] = new Integer[n];
        int i = 0;
        for (Integer x : size)
            arr[i++] = x;
        ArrayAdapter<Integer> spinnerAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, arr);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        TableSize.setAdapter(spinnerAdapter);
        TableSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int size = Integer.parseInt(TableSize.getSelectedItem().toString());
                List<Table> tables = db.tableDAO().getAvailableTable(restaurantID, startT, endT);
                List<String> tableName = new ArrayList<>();
                for (int j = 0; j < tables.size(); j++) {
                    if (tables.get(i).getSize() == size) {
                        tableName.add(tables.get(i).getName());
                    }
                }
                String[] name = new String[tableName.size()];
                int k = 0;
                for (String s : tableName) {
                    name[k++] = s;
                }
                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, name);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                TableChoice.setAdapter(spinnerAdapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        TableChoice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TableSelected.setText(TableChoice.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNextActivity(customerID, restaurantID, tableSize, startTime, endTime, tableID,startString,endString,numPeople);
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
        Intent backIntent = new Intent(this, SelectTimeActivity.class);
        startActivity(backIntent);
    }

    public void openNextActivity(int customerID, int restaurantID, int tableSize, long startTime, long endTime, int tID, String startString, String endString,int numPeople) {


        Intent intent = new Intent(this, PreOrderFoodActivity.class);
        intent.putExtra("cusID", customerID);
        intent.putExtra("resID", restaurantID);
        intent.putExtra("numPeople",numPeople);
        intent.putExtra("tSize", tableSize);
        intent.putExtra("sTime", startTime);
        intent.putExtra("eTime", endTime);
        intent.putExtra("startString", startString);
        intent.putExtra("endString", endString);
        startActivity(intent);
    }

    public void cancelActivity() {
        Intent backIntent = new Intent(this, MainMenuFragment.class);
        startActivity(backIntent);
    }
}