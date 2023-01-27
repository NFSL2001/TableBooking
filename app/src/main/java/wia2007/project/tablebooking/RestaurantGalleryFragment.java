package wia2007.project.tablebooking;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import wia2007.project.tablebooking.dao.RestaurantDAO;
import wia2007.project.tablebooking.database.TableBookingDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantGalleryFragment extends Fragment {

    public RestaurantGalleryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_gallery, container, false);

        // get current restaurant ID
        RestaurantMainActivity parentActivity = (RestaurantMainActivity) getActivity();
        Integer restaurantID = parentActivity.restaurantID;

        // get current restaurant gallery
        TableBookingDatabase database = TableBookingDatabase.getDatabase(getActivity());
        RestaurantDAO dao = database.restaurantDAO();
        List<String> restaurantImages = dao.getRestaurantById(restaurantID).get(0).getImages();

        // get recycler view and bind view holder
        RecyclerView recyclerView = view.findViewById(R.id.restMenu_RVGallery);
        // set layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        // set adapter
        recyclerView.setAdapter(new RestaurantImageGalleryAdapter(view.getContext(), restaurantImages));

        return view;
    }
}