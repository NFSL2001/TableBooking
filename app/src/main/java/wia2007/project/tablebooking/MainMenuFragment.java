package wia2007.project.tablebooking;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import wia2007.project.tablebooking.entity.Cuisine;

public class MainMenuFragment extends Fragment {

    private Button menuTypeAll;
    private Button menuTypeCategory;
    private ViewGroup viewGroupAll;
    private ViewGroup viewGroupCategory;

    public MainMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);

        // fill in by category section
        GridLayout categoryGridLayout = (GridLayout) view.findViewById(R.id.main_menuListCategoryLayout);
        for (int i=0; i < Cuisine.getSize(); i++){
            //get layout file and inflate it
            View temp = inflater.inflate(R.layout.individual_main_category_item, categoryGridLayout, false);
            //get each cuisine item
            Cuisine.CuisineItem item = Cuisine.getCuisineItem(i);
            //get text, set text
            TextView temp_cuisineName = temp.findViewById(R.id.main_category_name);
            temp_cuisineName.setText(item.name);
            //get image, set image
            ImageView temp_categoryIcon = temp.findViewById(R.id.main_category_picture);
            temp_categoryIcon.setImageResource(item.iconResource);
            //add item to grid
            categoryGridLayout.addView(temp);
            // launch search by cuisine type
            int finalI = i;
            temp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), SearchActivity.class);
                    intent.setAction(Intent.ACTION_ASSIST);
                    intent.putExtra("cuisineID", finalI +1);
                    startActivity(intent);
                }
            });
        }

        // get the buttons and views
        this.menuTypeAll = view.findViewById(R.id.main_BtnAllCuisine);
        this.menuTypeCategory = view.findViewById(R.id.main_BtnByCategory);
        this.viewGroupAll = view.findViewById(R.id.main_menuListAll);
        this.viewGroupCategory = view.findViewById(R.id.main_menuListCategory);

        //bind onclick to button
        menuTypeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewGroupAll.setVisibility(View.VISIBLE);
                viewGroupCategory.setVisibility(View.GONE);
            }
        });
        menuTypeCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewGroupCategory.setVisibility(View.VISIBLE);
                viewGroupAll.setVisibility(View.GONE);
            }
        });

        //get search bar
        View searchButton = view.findViewById(R.id.main_searchView);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}