package wia2007.project.tablebooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.dao.RestaurantDAO;

public class SearchActivity extends AppCompatActivity {

    List<RestaurantDAO.RestaurantNameInfo> allList;
    SearchRestaurantAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.search_TVMainAct);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // add back button
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //initiate list of restaurants
        TableBookingDatabase database = TableBookingDatabase.getDatabase(this);
        RestaurantDAO dao = database.restaurantDAO();

        //
        /*ArrayList<RestaurantDAO.RestaurantNameInfo> tempList = new ArrayList<>();
        tempList.add(new RestaurantDAO.RestaurantNameInfo(1,"Atmosphere 360", 1, ""));
        tempList.add(new RestaurantDAO.RestaurantNameInfo(2,"Cons Transphere", 2, ""));
        tempList.add(new RestaurantDAO.RestaurantNameInfo(3,"KFC Malaysia", 1, ""));
        tempList.add(new RestaurantDAO.RestaurantNameInfo(4,"Malaysia Cuisine", 3, ""));
        tempList.add(new RestaurantDAO.RestaurantNameInfo(5,"Domino's 360", 7, ""));
*/
        //change list if cuisine type
        if (Intent.ACTION_ASSIST.equals(getIntent().getAction())){
            Integer cuisineID = getIntent().getIntExtra("cuisineType", 1);
            //
            /*allList = new ArrayList<RestaurantDAO.RestaurantNameInfo>();
            for(RestaurantDAO.RestaurantNameInfo item: tempList){
                if (item.cuisine_type == cuisineID)
                    allList.add(item);
            }*/
            allList = dao.getRestaurantByCuisine(cuisineID);
        } else {
            allList = dao.listAllRestaurantInfo();
        }

        // get recycler view and bind view holder
        RecyclerView recyclerView = findViewById(R.id.search_RVResult);
        // set layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // set adapter
        // context use `this` so theming can pass through, don't use getApplicationContext() please!
        // see https://stackoverflow.com/a/3001546/12919373
        arrayAdapter = new SearchRestaurantAdapter(this, recyclerView, allList);
        recyclerView.setAdapter(arrayAdapter);

        //setup new incoming intent
        handleIntent(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search_activity, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        // Expand search icon into toolbar
        MenuItem searchMenuItem = menu.findItem(R.id.mainSearchItem);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        // Auto focus search bar
        searchView.setFocusable(true);
        searchView.requestFocusFromTouch();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                List<RestaurantDAO.RestaurantNameInfo> newList = getFilteredRestaurantList(query);
                arrayAdapter.setData(newList);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.length() == 0) {
                    arrayAdapter.setData(allList);
                    return true;
                }
                List<RestaurantDAO.RestaurantNameInfo> newList = getFilteredRestaurantList(newText);
                arrayAdapter.setData(newList);
                return false;
            }
        });

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            // if press back button, return activity back to main menu
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        //if search intent
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
            List<RestaurantDAO.RestaurantNameInfo> newList = getFilteredRestaurantList(query);
            arrayAdapter.setData(newList);
        }
    }

    public List<RestaurantDAO.RestaurantNameInfo> getFilteredRestaurantList(String text) {
        List<RestaurantDAO.RestaurantNameInfo> newList = new ArrayList<>();
        for(RestaurantDAO.RestaurantNameInfo item : this.allList) {
            if(containsIgnoreCase(item.name, text)) {
                newList.add(item);
            }
        }
        return (List<RestaurantDAO.RestaurantNameInfo>) newList;
    }

    public static boolean containsIgnoreCase(String src, String what) {
        final int length = what.length();
        if (length == 0)
            return true; // Empty string is contained

        final char firstLo = Character.toLowerCase(what.charAt(0));
        final char firstUp = Character.toUpperCase(what.charAt(0));

        for (int i = src.length() - length; i >= 0; i--) {
            // Quick check before calling the more expensive regionMatches() method:
            final char ch = src.charAt(i);
            if (ch != firstLo && ch != firstUp)
                continue;

            if (src.regionMatches(true, i, what, 0, length))
                return true;
        }

        return false;
    }
}