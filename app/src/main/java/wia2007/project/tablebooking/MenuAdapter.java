package wia2007.project.tablebooking;

import android.content.Context;
import android.graphics.Movie;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

import wia2007.project.tablebooking.entity.Menu;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuHolder> {
    Context context;
    List<Menu> menuItem;
    private final RecycleViewInterface recycleViewInterface;
    public MenuAdapter(Context context, List<Menu> menuItem, RecycleViewInterface recycleViewInterface) {
        this.context = context;
        this.menuItem = menuItem;
        this.recycleViewInterface = recycleViewInterface;
    }

    public void notifyNewData(List<Menu> menu){
        this.menuItem.clear();
        menuItem = menu;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MenuAdapter.MenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_menu_row, parent, false);
        return new MenuHolder(view, recycleViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuHolder holder, int position) {
        holder.menuName.setText(menuItem.get(position).getMenu_name());
        holder.menuDescription.setText(menuItem.get(position).getDescription());
        float price = menuItem.get(position).getPrice();
        if(price != -1){
            holder.menuPrice.setText("RM "+String.format("%.02f", price));
        }else{
            holder.menuPrice.setText("");
        }
        String path = menuItem.get(position).getPath();
        if(path != null){
            File img = new File(path);
            holder.menuImage.setImageURI(Uri.fromFile(img));
        }else{
            holder.menuImage.setVisibility(View.GONE);
        }
        holder.menuType.setText(menuItem.get(position).getType());
    }

    @Override
    public int getItemCount() {
        return menuItem.size();
    }

    public class MenuHolder extends RecyclerView.ViewHolder {

        TextView menuName,menuPrice,menuDescription,menuType;
        ImageView menuImage;

        public MenuHolder(@NonNull View itemView, RecycleViewInterface recycleViewInterface) {
            super(itemView);
            menuName = itemView.findViewById(R.id.TVMenuName);
            menuPrice = itemView.findViewById(R.id.TVMenuPriceAdmin);
            menuDescription = itemView.findViewById(R.id.TVDescriptionMenuAdmin);
            menuImage = itemView.findViewById(R.id.MenuImage);
            menuType = itemView.findViewById(R.id.TVDishType);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recycleViewInterface != null){
                        int pos = getAdapterPosition();

                        if(pos!=RecyclerView.NO_POSITION){
                            recycleViewInterface.onItemClick(pos);
                        }
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int pos = getAdapterPosition();

                    if(pos!=RecyclerView.NO_POSITION){
                        recycleViewInterface.onLongClick(pos);
                        return true;
                    }
                    return false;
                }
            });
        }

    }

    public List<Menu> getMenuItem() {
        return menuItem;
    }

}
