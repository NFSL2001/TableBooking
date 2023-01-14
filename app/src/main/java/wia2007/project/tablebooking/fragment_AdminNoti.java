package wia2007.project.tablebooking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class fragment_AdminNoti extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private AdminNotiAdapter adminNotiAdapter;

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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_noti, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.RCTableView);
//        adminNotiAdapter = new AdminNotiAdapter(getContext());
//        recyclerView.setAdapter(adminNotiAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        return view;
    }
}