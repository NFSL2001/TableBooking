package wia2007.project.tablebooking;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import wia2007.project.tablebooking.database.TableBookingDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RejectBookingPopUp#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RejectBookingPopUp extends Fragment {
    int bookingId;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RejectBookingPopUp() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RejectBookingPopUp.
     */
    // TODO: Rename and change types and number of parameters
    public static RejectBookingPopUp newInstance(String param1, String param2) {
        RejectBookingPopUp fragment = new RejectBookingPopUp();
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
        View view =  inflater.inflate(R.layout.fragment_reject_booking_pop_up, container, false);

        Bundle bundle = getActivity().getIntent().getExtras();
        if(bundle != null){
            bookingId=bundle.getInt("BookingId");
            System.out.println("BookingID:"+bookingId);
        }

        Button BtnConfirmReject = view.findViewById(R.id.BtnConfirmReject);
        BtnConfirmReject.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                view.setVisibility(View.INVISIBLE);
                TableBookingDatabase.getDatabase(view.getContext()).bookingDAO().rejectBooking(bookingId);
                TableBookingDatabase.getDatabase(view.getContext()).bookingContainMenuDAO().rejectBooking(bookingId);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.NHFMain,BookingList.class,null).commit();
                getActivity().overridePendingTransition(0,0);
                getActivity().finish();
            }
        });

        Button closeFragment = view.findViewById(R.id.BtnCloseReject);
        closeFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setVisibility(View.INVISIBLE);
            }
        });

        return view;
    }
}