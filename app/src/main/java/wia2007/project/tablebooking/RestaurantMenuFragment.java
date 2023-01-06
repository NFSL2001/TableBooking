package wia2007.project.tablebooking;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import wia2007.project.tablebooking.dao.MenuDAO;
import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.MenuBaseData;
import wia2007.project.tablebooking.entity.MenuCategory;
import wia2007.project.tablebooking.entity.MenuItem;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantMenuFragment extends Fragment {

    ArrayList<MenuBaseData> fullMenuList = new ArrayList<>();

    public RestaurantMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_menu, container, false);

        // get current restaurant ID
//        RestaurantMainActivity parentActivity = (RestaurantMainActivity) getActivity();
//        Integer restaurantID = parentActivity.restaurantID;
        int restaurantID = 1;

        // get current restaurant menu
        TableBookingDatabase database = TableBookingDatabase.getDatabase(getActivity().getApplicationContext());
        MenuDAO dao = database.menuDAO();
        ArrayList<MenuItem> daoMenuList = new ArrayList<>(dao.getDefaultMenuByRestaurant(restaurantID));

        String lastSeenMenuCategory = "";
        // loop through menu and insert MenuType header
        for (MenuItem item: daoMenuList){
            //if new menu type category (not seen before)
            if (! item.getCategory().equals(lastSeenMenuCategory)){
                //add category header
                lastSeenMenuCategory = item.getCategory();
                fullMenuList.add(new MenuCategory(lastSeenMenuCategory));
            }
            fullMenuList.add(item);
        }

        // get recycler view and bind view holder
        RecyclerView recyclerView = view.findViewById(R.id.restMenu_RVMenu);
        // set layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        // set adapter
        recyclerView.setAdapter(new MenuAdapter(view.getContext(), fullMenuList,null));


        return view;
    }
}