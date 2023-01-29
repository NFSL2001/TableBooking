package wia2007.project.tablebooking;

import static java.util.Map.Entry.comparingByKey;
import static java.util.stream.Collectors.toMap;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.MenuBaseData;
import wia2007.project.tablebooking.entity.MenuCategory;
import wia2007.project.tablebooking.entity.MenuItem;

public class MenuAdmin extends AppCompatActivity implements RecycleViewInterface {
    ExtendedFloatingActionButton BtnAddItem;
    RecyclerView recyclerView;
    MenuAdapter adapter;
    AlertDialog alertDialog;
    Map<String, List<MenuItem>> menuMap = null;
    Map<String, List<MenuItem>> menuByType = null;
    Spinner SpinnerItemSortCondition;
    ArrayList<MenuBaseData> fullMenuList = new ArrayList<>();
    TableBookingDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);
        Toolbar toolbar = findViewById(R.id.TVRestaurantMenuAct);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Restaurant Menu");
        Spinner SpinnerMenuSortCondition = findViewById(R.id.SpinnerMenuSortCondition);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.SpinnerForMenuAdmin, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        SpinnerMenuSortCondition.setAdapter(spinnerAdapter);

        SpinnerItemSortCondition = findViewById(R.id.SpinnerItemSortCondition);
        ArrayAdapter<CharSequence> spinnerItemAdapter = ArrayAdapter.createFromResource(this, R.array.SpinnerForMenuItemAdmin, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        SpinnerItemSortCondition.setAdapter(spinnerItemAdapter);

        db = TableBookingDatabase.getDatabase(getApplicationContext());
        List<MenuItem> menuItem = db.menuDAO().getMenuSortedList(getIntent().getExtras().getInt("RestaurantID"), SpinnerItemSortCondition.getSelectedItemPosition());
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            menuMap = menuItem.stream().collect(Collectors.groupingBy(m -> m.getCategory() == null ? "Not defined" : m.getCategory()));
        }
        recyclerView = findViewById(R.id.RVMenu);
        adapter = new MenuAdapter(this, fullMenuList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        BtnAddItem = findViewById(R.id.FABAddItem);
        BtnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuAdmin.this, ItemDetails.class);
                intent.putExtra("RestaurantID",getIntent().getExtras().getInt("RestaurantID"));
                startActivity(intent);
            }
        });

        SpinnerMenuSortCondition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 1) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        menuByType = menuMap
                                .entrySet()
                                .stream()
                                .sorted(Collections.reverseOrder(comparingByKey()))
                                .collect(toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        menuByType = menuMap
                                .entrySet()
                                .stream()
                                .sorted(comparingByKey())
                                .collect(toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));
                    }
                }
                List<MenuBaseData> sorted = addToMenuBaseData(menuByType);
                adapter.notifyNewData(sorted);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        SpinnerItemSortCondition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                db = TableBookingDatabase.getDatabase(getApplicationContext());
                List<MenuItem> sortedItem = db.menuDAO().getMenuSortedList(getIntent().getExtras().getInt("RestaurantID"), SpinnerItemSortCondition.getSelectedItemPosition());
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    menuMap = sortedItem.stream().collect(Collectors.groupingBy(m -> m.getCategory() == null ? "Not Defined" : m.getCategory()));
                    int pos = SpinnerMenuSortCondition.getSelectedItemPosition();
                    if (pos == 1) {
                        menuByType = menuMap
                                .entrySet()
                                .stream()
                                .sorted(Collections.reverseOrder(Map.Entry.comparingByKey()))
                                .collect(toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));

                    } else {
                        menuByType = menuMap
                                .entrySet()
                                .stream()
                                .sorted(comparingByKey())
                                .collect(toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));
                    }
                    List<MenuBaseData> sorted = addToMenuBaseData(menuByType);
                    adapter.notifyNewData(sorted);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    public List<MenuBaseData> addToMenuBaseData(Map<String, List<MenuItem>> menuByType) {
        ArrayList<MenuBaseData> sorted = new ArrayList<>();
        List<String> typeArr = new ArrayList<>(menuByType.keySet());
        List<List<MenuItem>> menuArr = new ArrayList<>(menuByType.values());
        for (int j = 0; j < typeArr.size(); j++) {
            sorted.add(new MenuCategory(typeArr.get(j)));
            List<MenuItem> menuItems = menuArr.get(j);
            for (int k = 0; k < menuItems.size(); k++) {
                sorted.add(menuItems.get(k));
            }
            sorted.add(new MenuBaseData.MenuAddItemButton(typeArr.get(j)));
        }
        sorted.add(new MenuBaseData.MenuAddCategoryButton());
        return sorted;
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MenuAdmin.this, ItemDetails.class);
        MenuItem menuItemList = (MenuItem) adapter.menuList.get(position);
        intent.putExtra("ItemId", Integer.toString(menuItemList.getMenu_id()));
        intent.putExtra("ItemName", menuItemList.getMenu_name());
        intent.putExtra("ItemDescription", menuItemList.getDescription());
        String itemPrice = String.format("%.02f", menuItemList.getPrice());
        intent.putExtra("ItemPrice", itemPrice);
        intent.putExtra("ItemImage", menuItemList.getPath());
        intent.putExtra("ItemType", menuItemList.getCategory());
        intent.putExtra("RestaurantID",getIntent().getExtras().getInt("RestaurantID"));
        startActivity(intent);
    }

    @Override
    public void onLongClick(int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you wanted to delete?");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteItem(position);
                        startActivity(new Intent(MenuAdmin.this, MenuAdmin.class));
                    }
                });

        alertDialogBuilder.setNegativeButton("No", null);

        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void deleteItem(int position) {
        MenuItem menuItemList = (MenuItem) adapter.menuList.get(position);
        db = TableBookingDatabase.getDatabase(getApplicationContext());
        db.menuDAO().deleteMenuItem(menuItemList.getMenu_id());
        String toast = menuItemList.getMenu_name() + " is successfully deleted";
        Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}