package wia2007.project.tablebooking;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantMenuFragment extends Fragment {
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

        RestaurantMainActivity parentActivity = (RestaurantMainActivity) getActivity();
        Integer restaurantID = parentActivity.restaurantID;
        Log.d(null, "Loaded menu");
Log.d(null, Integer.toString(restaurantID));
        return view;
    }
}