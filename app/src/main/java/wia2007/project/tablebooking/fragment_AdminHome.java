package wia2007.project.tablebooking;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import wia2007.project.tablebooking.entity.UserStatus;

/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_AdminHome extends Fragment {

    int restaurant_id;

    public fragment_AdminHome() {
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
        View view = inflater.inflate(R.layout.fragment_admin_home, container, false);
        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("admin", Context.MODE_PRIVATE);
        restaurant_id = sharedPref.getInt("userID",-1);

        Button BtnRestaurantInfo,BtnRestaurantMenu,BtnTableArrangement,BtnTableAvailability,BtnBookingList,BtnNotification,BtnViewReport,BtnViewStatistic;
        BtnRestaurantMenu = view.findViewById(R.id.ResMenu);
        BtnBookingList = view.findViewById(R.id.BookList);
        BtnViewReport = view.findViewById(R.id.viewReport);
        BtnViewStatistic = view.findViewById(R.id.viewStats);
        BtnRestaurantInfo = view.findViewById(R.id.ResInfo);

        BtnRestaurantMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),MenuAdmin.class);
                i.putExtra("RestaurantID",restaurant_id);
                startActivity(i);
            }
        });
        BtnViewReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),DownloadPDF.class);
                i.putExtra("RestaurantID",restaurant_id);
                startActivity(i);
            }
        });
        BtnViewStatistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),Graph.class);
                i.putExtra("RestaurantID",restaurant_id);
                startActivity(i);
            }
        });
        BtnBookingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminBookingList adminBookingList = new AdminBookingList();
                Bundle bundle = new Bundle();
                bundle.putInt("Restaurant_Id",restaurant_id);
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.NHFMain,AdminBookingList.class,bundle).commit();
            }
        });
        BtnRestaurantInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),RestaurantInfo.class);
                intent.putExtra("RestaurantID",restaurant_id);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("admin", Context.MODE_PRIVATE);
        Button BtnLogOut = view.findViewById(R.id.ResMenu_Logout);
        BtnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor edit = sharedPref.edit();
                edit.remove("user");
                edit.putBoolean(UserStatus.IS_ADMIN, false);
                edit.apply();

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}