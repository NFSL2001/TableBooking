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
import java.util.List;
import java.util.Map;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.FoodListViewHolder> {

    Map<MenuItem,Integer> food;
    List<MenuItem> key;
    List<Integer> values;
    List<BookingContainMenu> order;

    public FoodListAdapter(Context context, Map<MenuItem,Integer> menuItems) {
        this.food = menuItems;
        key = new ArrayList<>(food.keySet());
        values = new ArrayList<>(food.values());
    }

    public FoodListAdapter(Context context, List<BookingContainMenu> order) {
        this.order = order;
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
        float total = key.get(position).getPrice()*values.get(position);
        System.out.println(key.get(position).toString());
        holder.FoodName.setText(key.get(position).getMenu_name());
        holder.Price.setText("RM"+String.format("%.2f",total));
        holder.Quantity.setText(Integer.toString(values.get(position)));
    }

    @Override
    public int getItemCount() { return food.size(); }

    public static class FoodListViewHolder extends RecyclerView.ViewHolder {

        TextView Quantity, FoodName, Price;

        public FoodListViewHolder(@NonNull View itemView) {
            super(itemView);
            Quantity = itemView.findViewById(R.id.foodList_quantity);
            FoodName = itemView.findViewById(R.id.foodList_foodName);
            Price = itemView.findViewById(R.id.foodList_price);
        }
    }
}
