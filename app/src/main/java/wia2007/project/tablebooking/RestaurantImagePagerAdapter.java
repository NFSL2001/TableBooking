package wia2007.project.tablebooking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Adapter for View Holder (below) use in Gallery (vertical)
public class RestaurantImagePagerAdapter extends RecyclerView.Adapter<RestaurantImageViewHolder> {

    private Context context;
    private List<RestaurantImage> imageList;

    public RestaurantImagePagerAdapter(Context context, List<RestaurantImage> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public RestaurantImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.individual_restaurant_image,
                parent,
                false);
        //  Pager cannot detect match_parent, have to manually override for now
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return new RestaurantImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantImageViewHolder holder, int position) {
        holder.imageView.setImageResource(
                imageList.get(position).getImage()
        );
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }
}