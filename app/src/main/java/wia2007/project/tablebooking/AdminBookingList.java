package wia2007.project.tablebooking;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminBookingList extends Fragment {

    int iCurrentSelection;
    String sortCondition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booking_list, container, false);
        Toolbar toolbar = view.findViewById(R.id.TVBookingListAct);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).setTitle("Booking List");

        Spinner SpinnerSortCondition = (Spinner) view.findViewById(R.id.SpinnerSortCondition);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.sorting_condition, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        SpinnerSortCondition.setAdapter(adapter);


        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("FileName", MODE_PRIVATE);
        int spinnerValue = sharedPref.getInt("userChoiceSpinner", -1);
        if (spinnerValue != -1) {
            // set the selected value of the spinner
            SpinnerSortCondition.setSelection(spinnerValue);
        }
        sortCondition = Integer.toString(SpinnerSortCondition.getSelectedItemPosition());
        iCurrentSelection = SpinnerSortCondition.getSelectedItemPosition();
        SpinnerSortCondition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (iCurrentSelection != i) {
                    int userChoice = SpinnerSortCondition.getSelectedItemPosition();
                    SharedPreferences sharedPref = getActivity().getSharedPreferences("FileName", 0);
                    SharedPreferences.Editor prefEditor = sharedPref.edit();
                    prefEditor.putInt("userChoiceSpinner", userChoice);
                    prefEditor.commit();
                    SpinnerSortCondition.setSelection(spinnerValue);
                    sortCondition = Integer.toString(SpinnerSortCondition.getSelectedItemPosition());
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.NHFMain, AdminBookingList.class, getArguments()).commit();
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        String restaurant_id = null;
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            restaurant_id = Integer.toString(bundle.getInt("Restaurant_Id"));
        }else{
            sharedPref = getActivity().getSharedPreferences("admin", Context.MODE_PRIVATE);
            restaurant_id = Integer.toString(sharedPref.getInt("userID",-1));
        }

        BackGroundTaskBooking backGroundTaskBooking = new BackGroundTaskBooking(this.getContext());
        backGroundTaskBooking.execute("get_info", sortCondition, restaurant_id);

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
