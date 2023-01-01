package wia2007.project.tablebooking;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.Menu;

public class MenuAdmin extends AppCompatActivity implements RecycleViewInterface {
    RecycleViewInterface r;
    ExtendedFloatingActionButton BtnAddItem;
//    FloatingActionButton FAB;
//    ExtendedFloatingActionButton FABSort,FABSortTypeAsc,FABSortTypeDesc, FABSortPriceAsc, FABSortPriceDesc;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    AlertDialog alertDialog;
    Boolean isAllFabVisible = false;
    Boolean isAllSortFABVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);
        Toolbar toolbar = findViewById(R.id.TVRestaurantMenuAct);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Restaurant Menu");
        Spinner SpinnerMenuSortCondition = findViewById(R.id.SpinnerMenuSortCondition);
        List<Menu> menuItem = TableBookingDatabase.getDatabase(getApplicationContext()).menuDAO().getMenuSortedList(1,SpinnerMenuSortCondition.getSelectedItemPosition());
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.SpinnerForMenuAdmin, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        SpinnerMenuSortCondition.setAdapter(spinnerAdapter);

        recyclerView = findViewById(R.id.RVMenu);

        adapter = new MenuAdapter(this, menuItem, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
//        FAB = findViewById(R.id.FAB);
//        FABSort = findViewById(R.id.FABSortItem);
//        FABSortTypeAsc = findViewById(R.id.FABSortTypeAsc);
//        FABSortPriceAsc = findViewById(R.id.FABSortPriceAsc);
//        FABSortTypeDesc = findViewById(R.id.FABSortTypeDesc);
//        FABSortPriceDesc = findViewById(R.id.FABSortPriceDesc);

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
                List<Menu> sorted = TableBookingDatabase.getDatabase(getApplicationContext()).menuDAO().getMenuSortedList(1,SpinnerMenuSortCondition.getSelectedItemPosition());
                ((MenuAdapter)adapter).notifyNewData(sorted);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//
//        FABSortTypeDesc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                List<Menu> sorted = TableBookingDatabase.getDatabase(getApplicationContext()).menuDAO().getMenuSortedList(1,3);
//                ((MenuAdapter)adapter).notifyNewData(sorted);
//            }
//        });
//        FABSortTypeAsc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                List<Menu> sorted = TableBookingDatabase.getDatabase(getApplicationContext()).menuDAO().getMenuSortedList(1,1);
//                ((MenuAdapter)adapter).notifyNewData(sorted);
//            }
//        });
//        FABSortPriceDesc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                List<Menu> sorted = TableBookingDatabase.getDatabase(getApplicationContext()).menuDAO().getMenuSortedList(1,2);
//                ((MenuAdapter)adapter).notifyNewData(sorted);
//            }
//        });
//        FABSortPriceAsc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                List<Menu> sorted = TableBookingDatabase.getDatabase(getApplicationContext()).menuDAO().getMenuSortedList(1,0);
//                ((MenuAdapter)adapter).notifyNewData(sorted);
//            }
//        });

    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MenuAdmin.this, ItemDetails.class);
        MenuAdapter menuAdapter = (MenuAdapter) adapter;
        intent.putExtra("ItemId", Integer.toString(menuAdapter.getMenuItem().get(position).getMenu_id()));
        intent.putExtra("ItemName", menuAdapter.getMenuItem().get(position).getMenu_name());
        intent.putExtra("ItemDescription", menuAdapter.getMenuItem().get(position).getDescription());
        String itemPrice = String.format("%.02f", menuAdapter.getMenuItem().get(position).getPrice());
        intent.putExtra("ItemPrice", itemPrice);
        intent.putExtra("ItemImage", menuAdapter.getMenuItem().get(position).getPath());
        intent.putExtra("ItemType",menuAdapter.getMenuItem().get(position).getType());
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
                        startActivity(new Intent(MenuAdmin.this,MenuAdmin.class));
                    }
                });

        alertDialogBuilder.setNegativeButton("No", null);

        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void deleteItem(int position){
        MenuAdapter menuAdapter = (MenuAdapter) adapter;
        TableBookingDatabase.getDatabase(getApplicationContext()).menuDAO().deleteMenuItem(menuAdapter.getMenuItem().get(position).getMenu_id());
        String toast = ((MenuAdapter) adapter).getMenuItem().get(position).getMenu_name() + " is successfully deleted";
        Toast.makeText(getApplicationContext(),toast,Toast.LENGTH_LONG).show();
    }

}