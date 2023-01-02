package wia2007.project.tablebooking;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

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

        // code to inflate viewPager (image gallery)

        // TODO: Template list change to dynamic list
        // prepare list for adapter to show
        List<RestaurantImage> restaurantImages = new ArrayList<>();
        restaurantImages.add(new RestaurantImage(R.drawable.restaurant_sample_1));
        restaurantImages.add(new RestaurantImage(R.drawable.restaurant_sample_2));
        restaurantImages.add(new RestaurantImage(R.drawable.restaurant_sample_3));
        restaurantImages.add(new RestaurantImage(R.drawable.restaurant_sample_4));
        restaurantImages.add(new RestaurantImage(R.drawable.restaurant_sample_5));

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

        return view;
    }
}