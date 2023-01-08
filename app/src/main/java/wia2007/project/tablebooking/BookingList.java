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

import androidx.fragment.app.Fragment;
import androidx.sqlite.db.SimpleSQLiteQuery;

import wia2007.project.tablebooking.database.TableBookingDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookingList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookingList extends Fragment {
    //    DatabaseHelper myDb;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BookingList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookingList.
     */
    // TODO: Rename and change types and number of parameters
    public static BookingList newInstance(String param1, String param2) {
        BookingList fragment = new BookingList();
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

    int iCurrentSelection;
    String sortCondition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booking_list, container, false);
        getActivity().setTitle("Booking List");
//        addData();

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
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.NHFMain, BookingList.class, null).commit();
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        String restaurant_id = "1";
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            restaurant_id = bundle.getString("Restaurant_Id");
        }

        BackGroundTaskBooking backGroundTaskBooking = new BackGroundTaskBooking(this.getContext());
        backGroundTaskBooking.execute("get_info", sortCondition, restaurant_id);

        return view;
    }

    public void addData() {
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Customer(User_name, Password, Mobile_number, Email, Gender)VALUES ('Bob', 'Password2', '0129876543', 'bob@gmail.com', '0');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Customer(User_name, Password, Mobile_number, Email, Gender)VALUES ('Christine', 'Password3', '01125648963', 'christine@hotmail.com', '1');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Customer(User_name, Password, Mobile_number, Email, Gender)VALUES ('Daniel', 'Password4', '0196543211', 'daniel@gmail.com', '2');"));


        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Customer(User_name, Password, Mobile_number, Email, Gender)VALUES ('Bob', 'Password2', '0129876543', 'bob@gmail.com', '0');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Customer(User_name, Password, Mobile_number, Email, Gender)VALUES ('Christine', 'Password3', '01125648963', 'christine@hotmail.com', '1');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Customer(User_name, Password, Mobile_number, Email, Gender)VALUES ('Daniel', 'Password4', '0196543211', 'daniel@gmail.com', '2');"));

        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Restaurant(Restaurant_name, Password, Contact_number, Average_price, address, Cuisine_type,title_image_path)VALUES('Restaurant ABC', 'Password','046543652', '20', 'LG01-2B,123,Jalan ABC, 12345, Selangor', '1','');"));
//        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Restaurant(Restaurant_user_name, Password, Contact_number, Average_price, Address, Cuisine_type)VALUES('Restaurant BCD', 'Password2','035951284', '30', '1F-25A,456,Jalan BCD, 23451, Pulau Pinang', '2');"));
//        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Restaurant(Restaurant_user_name, Password, Contact_number, Average_price, Address, Cuisine_type)VALUES('Restaurant CDE', 'Password3','082293246', '50', '3F-34C,789,Jalan CDE, 34512, Kuala Lumpur', '3');"));
//        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Restaurant(Restaurant_user_name, Password, Contact_number, Average_price, Address, Cuisine_type)VALUES('Restaurant DEF', 'Password4','016845384', '30', '2F-2A,987,Jalan DEF, 45123, Pulau Pinang', '4');"));
//        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Restaurant(Restaurant_user_name, Password, Contact_number, Average_price, Address, Cuisine_type)VALUES('Restaurant EFG', 'Password5','041651323', '100', 'G02-3B,654,Jalan EFG, 51234, Kuala Lumpur', '4');"));
//        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Restaurant(Restaurant_user_name, Password, Contact_number, Average_price, Address, Cuisine_type)VALUES('Restaurant FGH', 'Password6','082381294', '25', '1F-T04,321,Jalan FGH, 54321, Selangor', '3');"));
//        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Restaurant(Restaurant_user_name, Password, Contact_number, Average_price, Address, Cuisine_type)VALUES('Restaurant GHI', 'Password7','062464125', '30', '2F-5D,159,Jalan GHI, 43215, Selangor', '2');"));
//        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Restaurant(Restaurant_user_name, Password, Contact_number, Average_price, Address, Cuisine_type)VALUES('Restaurant HIJ', 'Password8','051656515', '12', '12F-3,753,Jalan HIJ, 32154, Pulau Pinang', '1');"));

        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO 'table'(Restaurant_id,Name,Size)VALUES('1','N1','2');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO 'table'(Restaurant_id,Name,Size)VALUES('1','N2','1');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO 'table'(Restaurant_id,Name,Size)VALUES('1','N3','2');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO 'table'(Restaurant_id,Name,Size)VALUES('1','N4','2');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO 'table'(Restaurant_id,Name,Size)VALUES('1','N5','3');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO 'table'(Restaurant_id,Name,Size)VALUES('1','N6','5');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO 'table'(Restaurant_id,Name,Size)VALUES('1','N7','3');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO 'table'(Restaurant_id,Name,Size)VALUES('1','N8','4');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO 'table'(Restaurant_id,Name,Size)VALUES('1','N9','2');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO 'table'(Restaurant_id,Name,Size)VALUES('1','W1','4');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO 'table'(Restaurant_id,Name,Size)VALUES('1','W2','2');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO 'table'(Restaurant_id,Name,Size)VALUES('1','W3','3');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO 'table'(Restaurant_id,Name,Size)VALUES('1','W4','5');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO 'table'(Restaurant_id,Name,Size)VALUES('1','W5','4');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO 'table'(Restaurant_id,Name,Size)VALUES('2','T1','2');"));

        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Booking (Table_id,Customer_id,start_time,end_time,Remark)VALUES ('1', '2', '2023/01/25 10:00','2023/01/25 11:00', 'Allegry to fish');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Booking (Table_id,Customer_id,start_time,end_time,Remark)VALUES ('2', '1', '2023/01/24 17:00','2023/01/24 18:00', '');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Booking (Table_id,Customer_id,start_time,end_time,Remark)VALUES ('3', '3', '2022/12/23 15:00','2022/12/23 16:00', '');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Booking (Table_id,Customer_id,start_time,end_time,Remark)VALUES ('5', '4', '2022/11/23 19:00', '2022/11/23 20:00', 'More chilli');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Booking (Table_id,Customer_id,start_time,end_time,Remark)VALUES ('7', '2', '2022/12/25 13:00', '2022/12/25 13:30','More drumstick');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Booking (Table_id,Customer_id,start_time,end_time,Remark)VALUES ('11', '3', '2023/01/01 19:00','2023/01/01 20:00' ,'');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Booking (Table_id,Customer_id,start_time,end_time,Remark)VALUES ('15', '1', '2022/12/23 21:00','2022/12/23 21:30', '');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Booking (Table_id,Customer_id,start_time,end_time,Remark)VALUES ('1', '2', '2023/01/25 08:00','2023/01/25 08:30', 'Allegry to fish');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Booking (Table_id,Customer_id,start_time,end_time,Remark)VALUES ('2', '1', '2023/01/24 17:00','2023/01/24 18:00', '');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Booking (Table_id,Customer_id,start_time,end_time,Remark)VALUES ('3', '3', '2022/12/23 15:00', '2022/12/23 16:00', 'Need Wheelchair');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Booking (Table_id,Customer_id,start_time,end_time,Remark)VALUES ('5', '4', '2023/02/25 7:00','2023/02/25 08:00', 'Come around 7.05');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Booking (Table_id,Customer_id,start_time,end_time,Remark)VALUES ('7', '2', '2022/12/25 16:00', '2022/12/25 17:00','More tomato sauce');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Booking (Table_id,Customer_id,start_time,end_time,Remark)VALUES ('11', '3', '2022/01/15 17:30','15/01/2022 18:30','');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Booking (Table_id,Customer_id,start_time,end_time,Remark)VALUES ('15', '1', '2022/01/02 09:00','2022/01/02 10:00', '');"));


        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO MenuItem (Menu_name,Price,Restaurant)VALUES ('Chicken Chop', '25.5','1');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO MenuItem (Menu_name,Price,Restaurant)VALUES ('Ice Lemon Tea', '8.3','1');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO MenuItem (Menu_name,Price,Restaurant)VALUES ('Fish and Chips', '20.45','1');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO MenuItem (Menu_name,Price,Restaurant)VALUES ('Chicken Marylb', '19.9','1');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO MenuItem (Menu_name,Price,Restaurant)VALUES ('Drumsticks', '15.50','1');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO MenuItem (Menu_name,Price,Restaurant)VALUES ('Lb Chop', '26.5','1');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO MenuItem (Menu_name,Price,Restaurant)VALUES ('Latte Hot', '7.5','1');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO MenuItem (Menu_name,Price,Restaurant)VALUES ('Milo Ice', '8.0','1');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO MenuItem (Menu_name,Price,Restaurant)VALUES ('Milo Hot', '7.8','1');"));

        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Contain (Booking_id,Menu_id,Quantity)VALUES ('1', '1','1');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Contain (Booking_id,Menu_id,Quantity)VALUES ('1', '2','1');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Contain (Booking_id,Menu_id,Quantity)VALUES ('1', '3','1');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Contain (Booking_id,Menu_id,Quantity)VALUES ('1', '4','1');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Contain (Booking_id,Menu_id,Quantity)VALUES ('1', '5','1');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Contain (Booking_id,Menu_id,Quantity)VALUES ('1', '6','1');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Contain (Booking_id,Menu_id,Quantity)VALUES ('1', '7','1');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Contain (Booking_id,Menu_id,Quantity)VALUES ('1', '8','1');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Contain (Booking_id,Menu_id,Quantity)VALUES ('1', '9','1');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Contain (Booking_id,Menu_id,Quantity)VALUES ('2', '2','2');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Contain (Booking_id,Menu_id,Quantity)VALUES ('2', '3','1');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Contain (Booking_id,Menu_id,Quantity)VALUES ('2', '4','2');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Contain (Booking_id,Menu_id,Quantity)VALUES ('3', '1','1');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Contain (Booking_id,Menu_id,Quantity)VALUES ('3', '4','3');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Contain (Booking_id,Menu_id,Quantity)VALUES ('4', '4','2');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Contain (Booking_id,Menu_id,Quantity)VALUES ('4', '2','1');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Contain (Booking_id,Menu_id,Quantity)VALUES ('5', '3','3');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Contain (Booking_id,Menu_id,Quantity)VALUES ('6', '1','1');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Contain (Booking_id,Menu_id,Quantity)VALUES ('6', '2','1');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Contain (Booking_id,Menu_id,Quantity)VALUES ('7', '2','2');"));
        TableBookingDatabase.getDatabase(this.getContext()).bookingDAO().rawQuery(new SimpleSQLiteQuery("INSERT INTO Contain (Booking_id,Menu_id,Quantity)VALUES ('7', '3','1');"));


    }
}