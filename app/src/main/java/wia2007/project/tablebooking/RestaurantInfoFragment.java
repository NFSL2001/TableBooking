package wia2007.project.tablebooking;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import wia2007.project.tablebooking.dao.RestaurantDAO;
import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.Cuisine;
import wia2007.project.tablebooking.entity.Restaurant;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantInfoFragment extends Fragment {

    private TextView pagerIndicator;

    public RestaurantInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_info, container, false);

        // get current restaurant ID
        RestaurantMainActivity parentActivity = (RestaurantMainActivity) getActivity();
        Integer restaurantID = parentActivity.restaurantID;

        // get current restaurant info
        TableBookingDatabase database = TableBookingDatabase.getDatabase(getActivity());
        RestaurantDAO dao = database.restaurantDAO();
        Restaurant restaurant = dao.getRestaurantById(restaurantID).get(0);

        /** populate data **/
        populateViewData(view, restaurant);

        /** inflate viewPager (image gallery) **/
        // prepare list for adapter to show
        List<String> restaurantImages = restaurant.getImages();
        // get recycler view and bind view holder
        ViewPager2 viewPager = view.findViewById(R.id.restInfo_topGallery);
        // set adapter
        viewPager.setAdapter(new RestaurantImagePagerAdapter(view.getContext(), restaurantImages));
        // set swipe event
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                //change page number
                pagerIndicator.setText(Integer.toString(position + 1) + "/" + Integer.toString(viewPager.getAdapter().getItemCount()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        // get indicator
        pagerIndicator = view.findViewById(R.id.restInfo_topGalleryPageIndicator);
        // initialize start page 1/5
        pagerIndicator.setText(Integer.toString(1) + "/" + Integer.toString(viewPager.getAdapter().getItemCount()));

        /** set buttons **/
        // set onclick navigation
        ImageButton btnViewGallery = view.findViewById(R.id.restInfo_btnShowGallery);
        btnViewGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_restInfo2restGallery);
            }
        });
        Button btnMenu = view.findViewById(R.id.restInfo_btnViewMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_restInfo2restMenu);
            }
        });
        Button btnNewBooking = view.findViewById(R.id.restInfo_btnNewBooking);
        btnNewBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Add navigation to new booking
                Context context = v.getContext();
                Intent intent = new Intent(context, SelectTimeActivity.class);
                intent.putExtra("resID", restaurantID);
                context.startActivity(intent);
            }
        });

        // TODO: add booking list by user

        return view;
    }

    void populateViewData(View view, Restaurant restaurant) {
        // required info
        {
            ((TextView) view.findViewById(R.id.restInfo_Name)).setText(restaurant.getRestaurant_name());

            ((TextView) view.findViewById(R.id.restInfo_topAddress)).setText(restaurant.getAddress());
            ((TextView) view.findViewById(R.id.menuInfo_dataAddress)).setText(restaurant.getAddress());
            String cuisineType = Cuisine.getCuisineItem(restaurant.getCuisine_type()-1).name;
            ((TextView) view.findViewById(R.id.restInfo_topType)).setText(cuisineType);
            ((TextView) view.findViewById(R.id.menuInfo_dataCuisine)).setText(cuisineType);
            String price = "RM " + String.format("%.02f", restaurant.getAverage_price());
            ((TextView) view.findViewById(R.id.restInfo_topPrice)).setText(price);
            ((TextView) view.findViewById(R.id.menuInfo_dataAveragePrice)).setText(price);
        }
        // info might not present
        if (restaurant.getContact_number() == null || restaurant.getContact_number().trim().isEmpty())
            view.findViewById(R.id.menuInfo_parentPhone).setVisibility(View.GONE);
        else
            ((TextView) view.findViewById(R.id.menuInfo_dataPhone)).setText(restaurant.getContact_number());
        if (restaurant.getWorking_hour() == null || restaurant.getWorking_hour().trim().isEmpty())
            view.findViewById(R.id.menuInfo_parentTime).setVisibility(View.GONE);
        else
            ((TextView) view.findViewById(R.id.menuInfo_dataTime)).setText(restaurant.getWorking_hour());
        if (restaurant.getPayment() == null || restaurant.getPayment().trim().isEmpty())
            view.findViewById(R.id.menuInfo_parentPayment).setVisibility(View.GONE);
        else
            ((TextView) view.findViewById(R.id.menuInfo_dataPayment)).setText(restaurant.getPayment());
        if (restaurant.getParking() == null || restaurant.getParking().trim().isEmpty())
            view.findViewById(R.id.menuInfo_parentParking).setVisibility(View.GONE);
        else
            ((TextView) view.findViewById(R.id.menuInfo_dataParking)).setText(restaurant.getParking());
        if (restaurant.getDresscode() == null || restaurant.getDresscode().trim().isEmpty())
            view.findViewById(R.id.menuInfo_parentDressCode).setVisibility(View.GONE);
        else
            ((TextView) view.findViewById(R.id.menuInfo_dataDressCode)).setText(restaurant.getDresscode());
        if (restaurant.getAccessibility() == null || restaurant.getAccessibility().trim().isEmpty())
            view.findViewById(R.id.menuInfo_parentAccessibility).setVisibility(View.GONE);
        else
            ((TextView) view.findViewById(R.id.menuInfo_dataAccessibility)).setText(restaurant.getAccessibility());
        if (restaurant.getWebsite() == null || restaurant.getWebsite().trim().isEmpty())
            view.findViewById(R.id.menuInfo_parentWebsite).setVisibility(View.GONE);
        else
            ((TextView) view.findViewById(R.id.menuInfo_dataWebsite)).setText(restaurant.getWebsite());
        if (restaurant.getDescription() != null && !restaurant.getDescription().trim().isEmpty()){
            String outString = restaurant.getDescription().replace("\\n", System.getProperty("line.separator"));
            ((TextView) view.findViewById(R.id.menuInfo_dataDescription)).setText(outString);
        }
    }
}