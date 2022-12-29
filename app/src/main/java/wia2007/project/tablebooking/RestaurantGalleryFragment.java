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

        // TODO: Template list change to dynamic list
        // prepare list for adapter to show
        List<RestaurantImage> restaurantImages = new ArrayList<>();
        restaurantImages.add(new RestaurantImage(R.drawable.restaurant_sample_1));
        restaurantImages.add(new RestaurantImage(R.drawable.restaurant_sample_2));
        restaurantImages.add(new RestaurantImage(R.drawable.restaurant_sample_3));
        restaurantImages.add(new RestaurantImage(R.drawable.restaurant_sample_4));
        restaurantImages.add(new RestaurantImage(R.drawable.restaurant_sample_5));

        // get recycler view and bind view holder
        RecyclerView recyclerView = view.findViewById(R.id.restMenu_RVGallery);
        // set layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        // set adapter
        recyclerView.setAdapter(new RestaurantImageGalleryAdapter(view.getContext(), restaurantImages));

        return view;
    }
}