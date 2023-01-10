package wia2007.project.tablebooking;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminBookingList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminBookingList extends Fragment {
    //    DatabaseHelper myDb;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdminBookingList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminBookingList.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminBookingList newInstance(String param1, String param2) {
        AdminBookingList fragment = new AdminBookingList();
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
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.NHFMain, AdminBookingList.class, null).commit();
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
        }
        System.out.println(bundle.getInt("Restaurant_Id"));

        BackGroundTaskBooking backGroundTaskBooking = new BackGroundTaskBooking(this.getContext());
        backGroundTaskBooking.execute("get_info", sortCondition, restaurant_id);

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().getSupportFragmentManager().popBackStackImmediate();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
