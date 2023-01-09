package wia2007.project.tablebooking;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
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
        ((TextView) holder.TVCategory).setText(Cuisine.getCuisineItem(item.cuisine_type-1).name);
        ImageView titleImage = holder.IVTitle;
        if (item.title_image_path != null) {
            titleImage.setVisibility(View.VISIBLE);
            holder.setImageView(titleImage, item.title_image_path);
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


class BaseImageHolder extends RecyclerView.ViewHolder {
    public BaseImageHolder(@NonNull View itemView) {
        super(itemView);
    }
    public boolean setImageView(ImageView imageView, String uriString){
        /** Input: uriString, may be web address or File path address
         *  Output: boolean, true for web address, false for File address
         * **/
        URI u = null;
        try {
            // convert path to URI
            u = new URI(uriString);
            boolean isWeb = "http".equalsIgnoreCase(u.getScheme())
                    || "https".equalsIgnoreCase(u.getScheme());
            if (isWeb) {
                //change thread policy then get image
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                imageView.setImageBitmap(BitmapFactory.decodeStream((InputStream)u.toURL().getContent()));
                return true;
            } else throw new MalformedURLException("Not web URI"); //share throw exception
        } catch (IOException | URISyntaxException e) {
            // set image using local File Uri
            File img = new File(uriString);
            imageView.setImageURI(Uri.fromFile(img));
            return false;
        }
    }
}

class MainMenuRestaurantHolder extends BaseImageHolder {
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
