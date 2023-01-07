package wia2007.project.tablebooking;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.invitable.R;

import java.util.List;

import wia2007.project.tablebooking.dao.BookingContainMenuDAO;
import wia2007.project.tablebooking.dao.MenuDAO;
import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.BookingContainMenu;
import wia2007.project.tablebooking.entity.Menu;


public class PreOrderFoodActivity extends AppCompatActivity {

    Button SkipButton, NextButton, BackButton, CancelButton;
    TextView Price;
    int sum;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pre_order_food);

        SkipButton = findViewById(R.id.pre_order_food_buttonSkipNow);
        NextButton = findViewById(R.id.pre_order_food_nextButton);
        BackButton = findViewById(R.id.pre_order_food_backButton);
        CancelButton = findViewById(R.id.pre_order_food_cancelButton);

        Price = findViewById(R.id.pre_order_food_fees);

        TableBookingDatabase db = TableBookingDatabase.getDatabase(getApplicationContext());
        MenuDAO MenuDAO = db.menuDAO();
        BookingContainMenuDAO BCMDAO = db.bookingContainMenuDAO();
        List<BookingContainMenu> BCMList = BCMDAO.getContainsByBookingId(0);
        List<Menu> menuList = MenuDAO.getMenuById(BCMList.get(0).getMenu_id());

        for(int i = 0; i < menuList.size(); i++) {
            sum += menuList.get(0).getPrice();
        }


    }
}