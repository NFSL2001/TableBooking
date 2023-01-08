package wia2007.project.tablebooking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import java.util.List;

import wia2007.project.tablebooking.dao.BookingContainMenuDAO;
import wia2007.project.tablebooking.dao.MenuDAO;
import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.BookingContainMenu;
import wia2007.project.tablebooking.entity.Menu;


public class PreOrderFoodActivity extends AppCompatActivity {

    public static final String MENU_ID = "wia2007.project.tablebooking.MENU_ID";

    Button SkipButton, NextButton, BackButton, CancelButton;
    TextView Price;
    int sum, MenuID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pre_order_food);

        SkipButton = findViewById(R.id.pre_order_food_buttonSkipNow);
        NextButton = findViewById(R.id.pre_order_food_nextButton);
        BackButton = findViewById(R.id.pre_order_food_backButton);
        CancelButton = findViewById(R.id.pre_order_food_cancelButton);

        Price = findViewById(R.id.pre_order_food_fees);

        TableBookingDatabase db = TableBookingDatabase.getDatabase(getApplicationContext());
        MenuDAO MenuDAO = db.menuDAO();

        SkipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skipToActivity();
            }
        });

       NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNextActivity(MenuID);
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

            }
        });


    }



    public void openNextActivity(int mID) {
        getIntent().putExtra(MENU_ID, mID);

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
}