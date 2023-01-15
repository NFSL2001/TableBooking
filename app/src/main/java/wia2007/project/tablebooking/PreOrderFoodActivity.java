package wia2007.project.tablebooking;

import static java.util.Map.Entry.comparingByKey;
import static java.util.stream.Collectors.toMap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import wia2007.project.tablebooking.dao.MenuDAO;
import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.MenuBaseData;
import wia2007.project.tablebooking.entity.MenuCategory;
import wia2007.project.tablebooking.entity.MenuItem;


public class PreOrderFoodActivity extends AppCompatActivity {


    Button SkipButton, NextButton, BackButton, CancelButton;
    static TextView Price;
    MenuAdapter2 adapter;
    Map<String, List<MenuItem>> menuMap = null;
    Map<String, List<MenuItem>> menuByType = null;
    List<MenuBaseData> fullMenuList = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_order_food);

        String name = getIntent().getStringExtra("name");
        int customerID = getIntent().getIntExtra("cusID", 0);
        int restaurantID = getIntent().getIntExtra("resID", 1);
        int tableSize = getIntent().getIntExtra("tSize", 0);
        int tID = getIntent().getIntExtra("tableID", 0);
        String startString = getIntent().getStringExtra("startString");
        String endString = getIntent().getStringExtra("endString");
        int numPeople = getIntent().getIntExtra("numPeople",1);
        SkipButton = findViewById(R.id.pre_order_food_buttonSkipNow);
        NextButton = findViewById(R.id.pre_order_food_nextButton);
        BackButton = findViewById(R.id.pre_order_food_backButton);
        CancelButton = findViewById(R.id.pre_order_food_cancelButton);
        recyclerView = findViewById(R.id.pre_order_food_listMenu);

        List<MenuItem> menuItem = TableBookingDatabase.getDatabase(getApplicationContext()).menuDAO().getMenuByRestaurant(restaurantID);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            menuMap = menuItem.stream().collect(Collectors.groupingBy(m -> m.getCategory() == null ? "Not defined" : m.getCategory()));
            menuByType = menuMap
                    .entrySet()
                    .stream()
                    .sorted(Collections.reverseOrder(comparingByKey()))
                    .collect(toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));
            List<String> typeArr = new ArrayList<>(menuByType.keySet());
            List<List<MenuItem>> menuArr = new ArrayList<>(menuByType.values());
            for (int j = 0; j < typeArr.size(); j++) {
                fullMenuList.add(new MenuCategory(typeArr.get(j)));
                List<MenuItem> menuItems = menuArr.get(j);
                for (int k = 0; k < menuItems.size(); k++) {
                    fullMenuList.add(menuItems.get(k));
                }
            }
        }
        adapter = new MenuAdapter2(this, fullMenuList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        double price = 0;

        Price = findViewById(R.id.pre_order_food_fees);
        Price.setText(String.format("%.2f",price));
        SkipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skipToActivity(customerID, restaurantID, tableSize, tID,startString,endString,numPeople,name);
            }
        });

        NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNextActivity(customerID, restaurantID, tableSize, tID,startString,endString,numPeople,name);
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

    public void openNextActivity(int customerID, int restaurantID, int tableSize, int tID, String startString, String endString,int numPeople,String name) {
        Intent intent = new Intent(this, CheckBookingActivity.class);
        intent.putExtra("cusID", customerID);
        intent.putExtra("resID", restaurantID);
        intent.putExtra("numPeople",numPeople);
        intent.putExtra("tSize", tableSize);
        intent.putExtra("tableID", tID);
        intent.putExtra("startString",startString);
        intent.putExtra("endString",endString);
        intent.putExtra("name",name);
        intent.putExtra("Skip","No");
        intent.putExtra("Price",Price.getText().toString());
        startActivity(intent);
    }

    public void skipToActivity(int customerID, int restaurantID, int tableSize, int tID, String startString, String endString,int numPeople,String name) {
        Intent intent = new Intent(this, CheckBookingActivity.class);
        intent.putExtra("cusID", customerID);
        intent.putExtra("resID", restaurantID);
        intent.putExtra("numPeople",numPeople);
        intent.putExtra("tSize", tableSize);
        intent.putExtra("name",name);
        intent.putExtra("tableID", tID);
        intent.putExtra("startString",startString);
        intent.putExtra("endString",endString);
        intent.putExtra("Skip","Yes");
        startActivity(intent);
    }

    public void openPreviousActivity() {
        finish();
    }

    public void cancelActivity() {
        ItemAdapter.selectedTable.clear();
        Intent backIntent = new Intent(this, RestaurantMainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        backIntent.putExtra("ID",getIntent().getIntExtra("resID",0));
        backIntent.putExtra("name",getIntent().getStringExtra("name"));
        startActivity(backIntent);
    }
}