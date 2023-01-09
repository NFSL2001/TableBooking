package wia2007.project.tablebooking;

import static java.lang.String.valueOf;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import wia2007.project.tablebooking.dao.TableDAO;
import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.Table;


public class SelectTableActivity extends AppCompatActivity {


    TextView TableSelected;
    Button NextButton, CancelButton, BackButton;
    Spinner TwoPeopleSpinner, FourPeopleSpinner, SixPeopleSpinner, EightPeopleSpinner;
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

        TableSelected = findViewById(R.id.select_table_tableSelected);

        NextButton = findViewById(R.id.select_table_nextButton);
        CancelButton = findViewById(R.id.select_table_cancelButton);
        BackButton = findViewById(R.id.select_table_backButton);

        TwoPeopleSpinner = findViewById(R.id.select_table_2peopleSpinner);
        FourPeopleSpinner = findViewById(R.id.select_table_4peopleSpinner);
        SixPeopleSpinner = findViewById(R.id.select_table_6peopleSpinner);
        EightPeopleSpinner = findViewById(R.id.select_table_8peopleSpinner);

        Time startT = new Time(startTime);
        Time endT = new Time(endTime);

        TableBookingDatabase db = TableBookingDatabase.getDatabase(getApplicationContext());
        TableDAO tableDAO = db.tableDAO();
        List<Table> tableList = tableDAO.getAvailableTable(restaurantID, startT, endT);

        List<String> table2 = new ArrayList<String>();
        List<String> table4 = new ArrayList<String>();
        List<String> table6 = new ArrayList<String>();
        List<String> table8 = new ArrayList<String>();

        List<Integer> table2id = new ArrayList<Integer>();
        List<Integer> table4id = new ArrayList<Integer>();
        List<Integer> table6id = new ArrayList<Integer>();
        List<Integer> table8id = new ArrayList<Integer>();

        String chooseOne = "Choose Only One";

        table2.add(chooseOne);
        table4.add(chooseOne);
        table6.add(chooseOne);
        table8.add(chooseOne);

        table2id.add(0);
        table4id.add(0);
        table6id.add(0);
        table8id.add(0);

        for(int i=0;i< tableList.size();i++){
            if(tableList.get(i).getSize() == 2){
                table2.add(tableList.get(i).getName());
                table2id.add(tableList.get(i).getTable_id());
            }else if(tableList.get(i).getSize() == 4){
                table4.add(tableList.get(i).getName());
                table4id.add(tableList.get(i).getTable_id());
            }else if(tableList.get(i).getSize() == 6){
                table6.add(tableList.get(i).getName());
                table6id.add(tableList.get(i).getTable_id());
            }else if(tableList.get(i).getSize() == 8){
                table8.add(tableList.get(i).getName());
                table8id.add(tableList.get(i).getTable_id());
            }else{
                throw new RuntimeException("No Table");
            }
        }

        ArrayAdapter<String> spinner2Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, table2);
        ArrayAdapter<String> spinner4Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, table4);
        ArrayAdapter<String> spinner6Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, table6);
        ArrayAdapter<String> spinner8Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, table8);

        spinner2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner6Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner8Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        TwoPeopleSpinner.setAdapter(spinner2Adapter);
        FourPeopleSpinner.setAdapter(spinner4Adapter);
        SixPeopleSpinner.setAdapter(spinner6Adapter);
        EightPeopleSpinner.setAdapter(spinner8Adapter);


        TwoPeopleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                FourPeopleSpinner.setSelection(0);
                SixPeopleSpinner.setSelection(0);
                EightPeopleSpinner.setSelection(0);
                TableSelected.setText(adapterView.getItemAtPosition(i).toString());
                tableID = table2id.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        FourPeopleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TwoPeopleSpinner.setSelection(0);
                SixPeopleSpinner.setSelection(0);
                EightPeopleSpinner.setSelection(0);
                TableSelected.setText(adapterView.getItemAtPosition(i).toString());
                tableID = table4id.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        SixPeopleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TwoPeopleSpinner.setSelection(0);
                FourPeopleSpinner.setSelection(0);
                EightPeopleSpinner.setSelection(0);
                TableSelected.setText(adapterView.getItemAtPosition(i).toString());
                tableID = table6id.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        EightPeopleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TwoPeopleSpinner.setSelection(0);
                FourPeopleSpinner.setSelection(0);
                SixPeopleSpinner.setSelection(0);
                TableSelected.setText(adapterView.getItemAtPosition(i).toString());
                tableID = table8id.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });



        NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNextActivity(customerID, restaurantID, tableSize, startTime, endTime, tableID);
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

    public void openNextActivity(int customerID, int restaurantID, int tableSize, long startTime, long endTime, int tID) {
        getIntent().putExtra("cusID", customerID);
        getIntent().putExtra("resID", restaurantID);
        getIntent().putExtra("tSize", tableSize);
        getIntent().putExtra("sTime", startTime);
        getIntent().putExtra("eTime", endTime);

        Intent intent = new Intent(this, PreOrderFoodActivity.class);
        startActivity(intent);
    }

    public void cancelActivity() {
        Intent backIntent = new Intent(this, MainMenuFragment.class);
        startActivity(backIntent);
    }
}