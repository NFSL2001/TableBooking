package wia2007.project.tablebooking;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.Booking;

public class fragment_AdminNoti extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private NotificationAdapter adminNotiAdapter;

    private String mParam1;
    private String mParam2;

    public fragment_AdminNoti() {
        // Required empty public constructor
    }

    public static fragment_AdminNoti newInstance(String param1, String param2) {
        fragment_AdminNoti fragment = new fragment_AdminNoti();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("Range")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_noti, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.RCTableView);
        Toolbar toolbar = view.findViewById(R.id.TVNotificationAct);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).setTitle("Notification");

        Bundle bundle = this.getArguments();
        int restaurant_id = 0;
        if (bundle != null) {
            restaurant_id = bundle.getInt("Restaurant_Id");
        }
        adminNotiAdapter = new NotificationAdapter(new NotificationAdapter.notificationDiff());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adminNotiAdapter);
        TableBookingDatabase.getDatabase(getContext()).notificationDAO().getRestaurantNotification(restaurant_id).observe(getViewLifecycleOwner(), notifications -> {adminNotiAdapter.submitList(notifications);});
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().getSupportFragmentManager().popBackStack();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}