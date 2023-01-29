package wia2007.project.tablebooking.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import wia2007.project.tablebooking.EditProfileActivity;
import wia2007.project.tablebooking.LoginActivity;
import wia2007.project.tablebooking.MainActivity;
import wia2007.project.tablebooking.R;
import wia2007.project.tablebooking.SignUpActivity;
import wia2007.project.tablebooking.dao.CustomerDAO;
import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.Customer;

public class ProfileFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = getActivity().findViewById(R.id.TVMainAct);
        toolbar.setVisibility(View.GONE);

        TableBookingDatabase database = TableBookingDatabase.getDatabase(getActivity().getApplicationContext());
        CustomerDAO customerDAO = database.customerDAO();


        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        String username = sharedPref.getString("user", null);
        if(username != null) {
            List<Customer> customerList = customerDAO.getCustomerByUsername(username);
            if (customerList.size() != 1)
                throw new RuntimeException("More than one user with the same username found");

            Customer customer = customerList.get(0);
            TextView TVLogInTitle = view.findViewById(R.id.TVLogInTitle);
            TVLogInTitle.setText(customer.getUser_name());

            TextView TVEditProfile = view.findViewById(R.id.TVLogIn);
            TVEditProfile.setText("Edit Profile");
            TVEditProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                    startActivity(intent);
                }
            });
            TextView TVLogOut = view.findViewById(R.id.TVSignUp);
            TVLogOut.setText("Log out");
            TVLogOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sharedPref.edit().clear().commit();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            view.findViewById(R.id.TVLogInTitle).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            });

            view.findViewById(R.id.TVLogIn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent,1);
                }
            });
            view.findViewById(R.id.TVSignUp).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), SignUpActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            startActivity(new Intent(getContext(),MainActivity.class));
        }
    }
}