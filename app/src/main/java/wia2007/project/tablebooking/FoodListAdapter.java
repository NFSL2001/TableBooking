package wia2007.project.tablebooking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.NameList;

import wia2007.project.tablebooking.entity.BookingContainMenu;
import wia2007.project.tablebooking.entity.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.FoodListViewHolder> {

    Map<MenuItem,Integer> food = new HashMap<>();
    List<MenuItem> key;
    List<Integer> values;
    List<ShowFoodOrderList> list = new ArrayList<>();

    public FoodListAdapter(Context context, Map<MenuItem,Integer> menuItems) {
        this.food = menuItems;
        key = new ArrayList<>(food.keySet());
        values = new ArrayList<>(food.values());
    }
    public FoodListAdapter(Context context, List<ShowFoodOrderList> menuItems) {
        this.list = menuItems;
    }

    @NonNull
    @Override
    public FoodListAdapter.FoodListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.foodlist_recyclerview, parent, false);
        FoodListViewHolder viewHolder = new FoodListViewHolder(itemView);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemView.setLayoutParams(lp);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodListAdapter.FoodListViewHolder holder, int position) {
        if(!food.isEmpty()){
            float total = key.get(position).getPrice()*values.get(position);
            holder.FoodName.setText(key.get(position).getMenu_name());
            holder.Price.setText("RM"+String.format("%.2f",total));
            holder.Quantity.setText(Integer.toString(values.get(position)));
        }else{
            holder.FoodName.setText(list.get(position).getMenuName());
            holder.Quantity.setText(Integer.toString(list.get(position).getQuantity()));
            double total = list.get(position).getQuantity() * list.get(position).getTotalcost();
            holder.Price.setText("RM"+String.format("%.2f",total));
        }
    }

    @Override
    public int getItemCount() {
        if(!food.isEmpty())
            return food.size();
        else
            return list.size();
    }

    public static class FoodListViewHolder extends RecyclerView.ViewHolder {

        TextView Quantity, FoodName, Price;

        public FoodListViewHolder(@NonNull View itemView) {
            super(itemView);
            Quantity = itemView.findViewById(R.id.foodList_quantity);
            FoodName = itemView.findViewById(R.id.foodList_foodName);
            Price = itemView.findViewById(R.id.foodList_price);
        }
    }

    String grandTotal() {
        double totalPrice = 0;
        for (int i = 0; i < list.size(); i++) {
            totalPrice += list.get(i).getQuantity() * list.get(i).getTotalcost();
        }
        return "RM"+String.format("%.2f",totalPrice);
    }

}
