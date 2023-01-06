package wia2007.project.tablebooking;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import wia2007.project.tablebooking.dao.RestaurantDAO;
import wia2007.project.tablebooking.entity.Cuisine;

public class MainMenuRestaurantAdapter extends RecyclerView.Adapter<MainMenuRestaurantHolder> {

    private Context context;
    private ArrayList<RestaurantDAO.RestaurantNameInfo> allList;

    public MainMenuRestaurantAdapter(Context context, List<RestaurantDAO.RestaurantNameInfo> allList){
        this.context = context;
        this.allList = new ArrayList<>(allList);
    }
    @NonNull
    @Override
    public MainMenuRestaurantHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(
                R.layout.individual_main_restaurant_item,
                parent,
                false
        );
        MainMenuRestaurantHolder holder = new MainMenuRestaurantHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainMenuRestaurantHolder holder, int position) {
        RestaurantDAO.RestaurantNameInfo item = allList.get(position);
        ((TextView) holder.TVName).setText(item.name);
        ((TextView) holder.TVCategory).setText(Cuisine.getCuisineItem(item.cuisine_type).name);
        ImageView titleImage = holder.IVTitle;
        if (item.title_image_path != null) {
            File img = new File(item.title_image_path);
            titleImage.setVisibility(View.VISIBLE);
            titleImage.setImageURI(Uri.fromFile(img));
        } else {
            titleImage.setVisibility(View.GONE);
        }
        holder.restaurantID = item.id;
        holder.name = item.name;
    }

    @Override
    public int getItemCount() {
        return allList.size();
    }
}

class MainMenuRestaurantHolder extends RecyclerView.ViewHolder {
    Integer restaurantID;
    String name;
    TextView TVName, TVCategory;
    ImageView IVTitle;
    Button BtnView;

    public MainMenuRestaurantHolder(@NonNull View itemView) {
        super(itemView);
        TVName = itemView.findViewById(R.id.main_all_storeName);
        TVCategory = itemView.findViewById(R.id.main_all_storeType);
        IVTitle = itemView.findViewById(R.id.main_all_picture);
        BtnView = itemView.findViewById(R.id.main_all_BtnBook);

        BtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = BtnView.getContext();
                Intent restaurantMenuIntent = new Intent(context, RestaurantMainActivity.class);
                restaurantMenuIntent.putExtra("ID", restaurantID);
                restaurantMenuIntent.putExtra("name", name);
                context.startActivity(restaurantMenuIntent);
            }
        });
    }
}
