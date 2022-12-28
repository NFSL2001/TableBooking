package wia2007.project.tablebooking;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainMenu extends Fragment {

    private Button menuTypeAll;
    private Button menuTypeCategory;
    private ViewGroup viewGroupAll;
    private ViewGroup viewGroupCategory;

    public MainMenu() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);

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

        return view;
    }
}