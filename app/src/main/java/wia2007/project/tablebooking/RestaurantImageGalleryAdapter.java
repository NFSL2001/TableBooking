package wia2007.project.tablebooking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Adapter for View Holder (below) use in Gallery (vertical)
public class RestaurantImageGalleryAdapter extends RecyclerView.Adapter<RestaurantImageViewHolder> {

    private Context context;
    private List<RestaurantImage> imageList;

    public RestaurantImageGalleryAdapter(Context context, List<RestaurantImage> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public RestaurantImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RestaurantImageViewHolder(LayoutInflater.from(context).inflate(
                R.layout.individual_restaurant_image,
                parent,
                false)
        );
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


/*
    ====================
    supporting class
    ====================
*/


// View Holder
class RestaurantImageViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;

    public RestaurantImageViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.restInfo_IVImage);
    }

}

// Image object class
class RestaurantImage {
    Integer image;

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public RestaurantImage(Integer image) {
        this.image = image;
    }
}