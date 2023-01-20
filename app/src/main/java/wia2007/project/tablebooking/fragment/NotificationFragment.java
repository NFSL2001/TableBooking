package wia2007.project.tablebooking.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

import wia2007.project.tablebooking.NotificationAdapter;
import wia2007.project.tablebooking.R;
import wia2007.project.tablebooking.dao.CustomerDAO;
import wia2007.project.tablebooking.dao.NotificationDAO;
import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.Customer;

public class NotificationFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public NotificationFragment() {
        // Required empty public constructor
    }

    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = getActivity().findViewById(R.id.TVMainAct);
        toolbar.setVisibility(View.VISIBLE);
        toolbar.setTitle("Notification");

        RecyclerView recyclerView = getActivity().findViewById(R.id.RVNotification);
        final NotificationAdapter adapter = new NotificationAdapter(new NotificationAdapter.notificationDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        TableBookingDatabase database = TableBookingDatabase.getDatabase(getActivity().getApplicationContext());
        NotificationDAO notificationDAO = database.notificationDAO();
        CustomerDAO customerDAO = database.customerDAO();

        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        String username = sharedPref.getString("user", null);
        if (username == null)
            return;
        List<Customer> customerList = customerDAO.getCustomerByUsername(username);
        if (customerList.size() != 1)
            throw new RuntimeException("More than one user with the same username found");

        notificationDAO.getNotification(customerList.get(0).getCustomer_id()).observe(getViewLifecycleOwner(), notifications -> {adapter.submitList(notifications);});
    }
}