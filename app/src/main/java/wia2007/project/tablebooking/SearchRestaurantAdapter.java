package wia2007.project.tablebooking;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import wia2007.project.tablebooking.dao.RestaurantDAO;

public class SearchRestaurantAdapter extends RecyclerView.Adapter<SearchRestaurantHolder> {

    private Context context;
    private ArrayList<RestaurantDAO.RestaurantNameInfo> pairList;
    private final RecyclerView recyclerView;

    public SearchRestaurantAdapter(Context context, RecyclerView recyclerView, List<RestaurantDAO.RestaurantNameInfo> pairList){
        this.context = context;
        this.recyclerView = recyclerView;
        this.pairList = new ArrayList<>(pairList);
    }

    @NonNull
    @Override
    public SearchRestaurantHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(
                android.R.layout.simple_list_item_1,
                parent,
                false
        );
        SearchRestaurantHolder holder = new SearchRestaurantHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = recyclerView.getChildAdapterPosition(v);
                Intent restaurantMenuIntent = new Intent(context, RestaurantMainActivity.class);
                restaurantMenuIntent.putExtra("ID", pairList.get(position).id);
                restaurantMenuIntent.putExtra("name", pairList.get(position).name);
                context.startActivity(restaurantMenuIntent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchRestaurantHolder holder, int position) {
        TextView TVitem = (TextView) holder.itemView;
        String restaurantName = pairList.get(position).name;
        TVitem.setText(restaurantName);
        // add background ripple
        TypedValue outValue = new TypedValue();
        // get ?android/selectableItemBackground
        context.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
        TVitem.setBackgroundResource(outValue.resourceId);
    }

    @Override
    public int getItemCount() {
        return pairList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<RestaurantDAO.RestaurantNameInfo> newList){
        this.pairList = new ArrayList<>(newList);
        this.notifyDataSetChanged();
    }

}

// View Holder
class SearchRestaurantHolder extends RecyclerView.ViewHolder {
    public SearchRestaurantHolder(@NonNull View itemView) {
        super(itemView);
    }
}