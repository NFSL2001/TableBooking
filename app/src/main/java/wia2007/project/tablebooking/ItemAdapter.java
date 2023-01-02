package wia2007.project.tablebooking;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

import wia2007.project.tablebooking.entity.Menu;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<Menu> itemList;
    private final RecycleViewInterface recycleViewInterface;
    public ItemAdapter(List<Menu> itemList, RecycleViewInterface recycleViewInterface){
        this.itemList = itemList;
        this.recycleViewInterface = recycleViewInterface;
    }

    @NonNull
    @Override
    public ItemAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_menu_row,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ItemViewHolder holder, int position) {
        holder.menuName.setText(itemList.get(position).getMenu_name());
        holder.menuDescription.setText(itemList.get(position).getDescription());
        float price = itemList.get(position).getPrice();
        if(price != -1){
            holder.menuPrice.setText("RM "+String.format("%.02f", price));
        }else{
            holder.menuPrice.setText("");
        }
        String path = itemList.get(position).getPath();
        if(path != null){
            File img = new File(path);
            holder.menuImage.setImageURI(Uri.fromFile(img));
        }else{
            holder.menuImage.setVisibility(View.GONE);
        }
        holder.menuType.setText(itemList.get(position).getType());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView menuName,menuPrice, menuDescription, menuType;
        ImageView menuImage;
        public ItemViewHolder(@NonNull View itemView) {
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
}
