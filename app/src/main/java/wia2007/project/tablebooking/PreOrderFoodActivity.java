package wia2007.project.tablebooking;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import java.util.List;

import wia2007.project.tablebooking.dao.MenuDAO;
import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.MenuItem;


public class PreOrderFoodActivity extends AppCompatActivity {


    Button SkipButton, NextButton, BackButton, CancelButton;
    TextView Price;
    int menuID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_order_food);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        int customerID = getIntent().getIntExtra("cusID", 0);
        int restaurantID = getIntent().getIntExtra("resID", 0);
        int tableSize = getIntent().getIntExtra("tSize", 0);
        long startTime = getIntent().getIntExtra("sTime", 0);
        long endTime = getIntent().getIntExtra("eTime", 0);
        int tID = getIntent().getIntExtra("tableID", 0);

        SkipButton = findViewById(R.id.pre_order_food_buttonSkipNow);
        NextButton = findViewById(R.id.pre_order_food_nextButton);
        BackButton = findViewById(R.id.pre_order_food_backButton);
        CancelButton = findViewById(R.id.pre_order_food_cancelButton);

        Price = findViewById(R.id.pre_order_food_fees);

        TableBookingDatabase db = TableBookingDatabase.getDatabase(getApplicationContext());
        MenuDAO MenuDAO = db.menuDAO();
        List<MenuItem> menuList = MenuDAO.getMenuByRestaurant(restaurantID);

        SkipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skipToActivity();
            }
        });

        NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNextActivity(customerID, restaurantID, tableSize, startTime, endTime, tID, menuID);
            }
        });

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPreviousActivity();
            }
        });

        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelActivity();
            }
        });


    }

    public void openNextActivity(int customerID, int restaurantID, int tableSize, long startTime, long endTime, int tID, int mID) {
        getIntent().putExtra("cusID", customerID);
        getIntent().putExtra("resID", restaurantID);
        getIntent().putExtra("tSize", tableSize);
        getIntent().putExtra("sTime", startTime);
        getIntent().putExtra("eTime", endTime);
        getIntent().putExtra("tableID", tID);
        getIntent().putExtra("menuID", mID);

        Intent intent = new Intent(this, CheckBookingActivity.class);
        startActivity(intent);
    }

    public void skipToActivity() {
        Intent intent = new Intent(this, CheckBookingActivity.class);
        startActivity(intent);
    }

    public void openPreviousActivity() {
        Intent backIntent = new Intent(this, SelectTableActivity.class);
        startActivity(backIntent);
    }

    public void cancelActivity() {
        Intent backIntent = new Intent(this, MainMenuFragment.class);
        startActivity(backIntent);
    }
}