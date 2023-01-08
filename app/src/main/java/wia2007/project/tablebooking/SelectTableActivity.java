package wia2007.project.tablebooking;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import wia2007.project.tablebooking.dao.TableDAO;
import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.Table;

import java.util.List;


public class SelectTableActivity extends AppCompatActivity {

    public static final String TABLE_ID = "wia2007.project.tablebooking.TABLE_ID";

    TextView TableSelected;
    Button NextButton, CancelButton, BackButton;
    int tableID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_select_table);

        TableSelected = findViewById(R.id.select_table_tableSelected);

        NextButton = findViewById(R.id.select_table_nextButton);
        CancelButton = findViewById(R.id.select_table_cancelButton);
        BackButton = findViewById(R.id.select_table_backButton);

        TableBookingDatabase db = TableBookingDatabase.getDatabase(getApplicationContext());
        TableDAO tableDAO = db.tableDAO();

        NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNextActivity(tableID, 0);
            }
        });

        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

    public void openNextActivity(int tID, int back) {
            getIntent().putExtra(TABLE_ID, tID);

        Intent intent = new Intent(this, PreOrderFoodActivity.class);
        startActivity(intent);
    }
}