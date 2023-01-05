package wia2007.project.tablebooking;

import static java.util.Map.Entry.comparingByKey;
import static java.util.stream.Collectors.toMap;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.MenuItem;

public class MenuAdmin extends AppCompatActivity implements RecycleViewInterface {
    ExtendedFloatingActionButton BtnAddItem;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    AlertDialog alertDialog;
    Map<String, List<MenuItem>> menuMap = null;
    Map<String, List<MenuItem>> menuByType = null;
    Spinner SpinnerItemSortCondition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);
        Toolbar toolbar = findViewById(R.id.TVRestaurantMenuAct);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Restaurant MenuItem");
        Spinner SpinnerMenuSortCondition = findViewById(R.id.SpinnerMenuSortCondition);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.SpinnerForMenuAdmin, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        SpinnerMenuSortCondition.setAdapter(spinnerAdapter);

        SpinnerItemSortCondition = findViewById(R.id.SpinnerItemSortCondition);
        ArrayAdapter<CharSequence> spinnerItemAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.SpinnerForMenuItemAdmin, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        SpinnerItemSortCondition.setAdapter(spinnerItemAdapter);

        List<DataModel> menuTypeList = new ArrayList<>();
        List<MenuItem> menuItem = TableBookingDatabase.getDatabase(getApplicationContext()).menuDAO().getMenuSortedList(1, SpinnerItemSortCondition.getSelectedItemPosition());
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            menuMap = menuItem.stream().collect(Collectors.groupingBy(m -> m.getCategory() == null ? "Not defined" : m.getCategory()));
        }

        recyclerView = findViewById(R.id.RVMenu);
        adapter = new MenuAdapter(this, menuTypeList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        BtnAddItem = findViewById(R.id.FABAddItem);
        BtnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuAdmin.this, ItemDetails.class));
            }
        });

        SpinnerMenuSortCondition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 1) {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//                        menuMap = menuItem.stream().collect(Collectors.groupingBy(MenuItem::getType));
                        menuByType = menuMap
                                .entrySet()
                                .stream()
                                .sorted(Collections.reverseOrder(Map.Entry.comparingByKey()))
                                .collect(toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));
                    }
                } else {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        menuByType = menuMap
                                .entrySet()
                                .stream()
                                .sorted(comparingByKey())
                                .collect(toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));
                    }
                }
                List<DataModel> sorted = new ArrayList<>();
                List<String> typeArr = new ArrayList<>(menuByType.keySet());
                List<List<MenuItem>> menuArr = new ArrayList<>(menuByType.values());
                for (int k = 0; k < typeArr.size(); k++) {
                    sorted.add(new DataModel(menuArr.get(k), typeArr.get(k)));
                }
                ((MenuAdapter) adapter).notifyNewData(sorted);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        SpinnerItemSortCondition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                List<DataModel> sorted = new ArrayList<>();
                List<MenuItem> sortedItem = TableBookingDatabase.getDatabase(getApplicationContext()).menuDAO().getMenuSortedList(1, SpinnerItemSortCondition.getSelectedItemPosition());
                int pos = SpinnerMenuSortCondition.getSelectedItemPosition();
                if (pos == 1) {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        menuMap = sortedItem.stream().collect(Collectors.groupingBy(m -> m.getCategory() == null ? "Not defined" : m.getCategory()));
                        menuByType = menuMap
                                .entrySet()
                                .stream()
                                .sorted(Collections.reverseOrder(Map.Entry.comparingByKey()))
                                .collect(toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));
                    }
                } else {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        menuMap = sortedItem.stream().collect(Collectors.groupingBy(m -> m.getCategory() == null ? "Not Defined" : m.getCategory()));
                        menuByType = menuMap
                                .entrySet()
                                .stream()
                                .sorted(comparingByKey())
                                .collect(toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));
                    }
                }
                List<String> typeArr = new ArrayList<>(menuByType.keySet());
                List<List<MenuItem>> menuArr = new ArrayList<>(menuByType.values());
                for (int k = 0; k < typeArr.size(); k++) {
                    sorted.add(new DataModel(menuArr.get(k), typeArr.get(k)));
                }
                ((MenuAdapter) adapter).notifyNewData(sorted);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MenuAdmin.this, ItemDetails.class);
        ItemAdapter menuAdapter = ((MenuAdapter)adapter).getItemAdapter();
        intent.putExtra("ItemId", Integer.toString(menuAdapter.getMenuItem().get(position).getMenu_id()));
        intent.putExtra("ItemName", menuAdapter.getMenuItem().get(position).getMenu_name());
        intent.putExtra("ItemDescription", menuAdapter.getMenuItem().get(position).getDescription());
        String itemPrice = String.format("%.02f", menuAdapter.getMenuItem().get(position).getPrice());
        intent.putExtra("ItemPrice", itemPrice);
        intent.putExtra("ItemImage", menuAdapter.getMenuItem().get(position).getPath());
        intent.putExtra("ItemType", menuAdapter.getMenuItem().get(position).getCategory());
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
        ItemAdapter menuAdapter = ((MenuAdapter)adapter).getItemAdapter();
        TableBookingDatabase.getDatabase(getApplicationContext()).menuDAO().deleteMenuItem(menuAdapter.getMenuItem().get(position).getMenu_id());
        String toast = menuAdapter.getMenuItem().get(position).getMenu_name() + " is successfully deleted";
        Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_LONG).show();
    }

}