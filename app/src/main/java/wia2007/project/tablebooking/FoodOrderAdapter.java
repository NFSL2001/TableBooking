package wia2007.project.tablebooking;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class FoodOrderAdapter extends ArrayAdapter {
    List list = new ArrayList();

    public FoodOrderAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public void add(ShowFoodOrderList showFoodOrderList) {
        list.add(showFoodOrderList);
        super.add(showFoodOrderList);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        FoodOrderHolder foodOrderHolder;
        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.display_food_order_row, parent, false);
            foodOrderHolder = new FoodOrderHolder();
            foodOrderHolder.TVQtyName = row.findViewById(R.id.TVMenuNameQty);
            foodOrderHolder.TVPrice = row.findViewById(R.id.TVMenuPrice);
            row.setTag(foodOrderHolder);
        } else {
            foodOrderHolder = (FoodOrderHolder) row.getTag();
        }

        ShowFoodOrderList showFoodOrderList = (ShowFoodOrderList) getItem(position);
        int booking_id = showFoodOrderList.getBooking_id();


        String qty = Integer.toString(showFoodOrderList.getQuantity());
        String menuName = showFoodOrderList.getMenuName();
        double totalPrice = showFoodOrderList.getTotalcost();

        foodOrderHolder.TVQtyName.setText(qty + "x " + menuName);

        foodOrderHolder.TVPrice.setText("RM"+String.format("%,.2f", totalPrice));

        return row;
    }

    static class FoodOrderHolder {
        TextView TVQtyName, TVPrice;
    }
}
