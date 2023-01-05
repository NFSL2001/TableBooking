package wia2007.project.tablebooking;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import wia2007.project.tablebooking.entity.MenuItem;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuHolder> {
    Context context;
    List<DataModel> menuItem;
    List<MenuItem> itemList = new ArrayList<>();
    private final RecycleViewInterface recycleViewInterface;
    ItemAdapter itemAdapter;
    public MenuAdapter(Context context, List<DataModel> menuItem, RecycleViewInterface recycleViewInterface) {
        this.context = context;
        this.menuItem = menuItem;
        this.recycleViewInterface = recycleViewInterface;
    }

    public void notifyNewData(List<DataModel> menu){
        this.menuItem.clear();
        menuItem = menu;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MenuAdapter.MenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_each_type_card_view, parent, false);
        return new MenuHolder(view, recycleViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuHolder holder, int position) {
        int pos = position;
        DataModel dataModel = menuItem.get(position);
        holder.menu_type_title.setText(dataModel.getMenuCategory());

        boolean isExpandable = dataModel.isExpandable();
        holder.expandableLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);

        if(isExpandable){
            holder.arrowIV.setImageResource(R.drawable.restaurant_menu_collapse);
            holder.TypeBackground.setBackgroundColor(Color.parseColor("#33000000"));
        }else {
            holder.arrowIV.setImageResource(R.drawable.restaurant_menu_expand);
            holder.TypeBackground.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }

        itemAdapter = new ItemAdapter(itemList,recycleViewInterface);
        holder.nestedRV.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.nestedRV.setHasFixedSize(true);
        holder.nestedRV.setAdapter(itemAdapter);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < menuItem.size(); i++) {
                    if (i == pos) {
                        if (menuItem.get(pos).isExpandable()) {
                            menuItem.get(pos).setExpandable(false);
                        } else {
                            menuItem.get(pos).setExpandable(true);
                        }
                    } else {
                        menuItem.get(i).setExpandable(false);
                    }
                    itemList = dataModel.getNestedMenuList();
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuItem.size();
    }

    public class MenuHolder extends RecyclerView.ViewHolder {

        private LinearLayout linearLayout;
        private RelativeLayout expandableLayout;
        private TextView menu_type_title;
        private ImageView arrowIV;
        private RecyclerView nestedRV;
        private ConstraintLayout TypeBackground;

        public MenuHolder(@NonNull View itemView, RecycleViewInterface recycleViewInterface) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.menuLinearlayout);
            expandableLayout = itemView.findViewById(R.id.expandable_layout);
            menu_type_title = itemView.findViewById(R.id.menuTypeTv);
            arrowIV = itemView.findViewById(R.id.arrow_imageview);
            nestedRV = itemView.findViewById(R.id.child_rv);
            TypeBackground = itemView.findViewById(R.id.TypeBackground);
        }

    }

    public ItemAdapter getItemAdapter() {
        return itemAdapter;
    }
}
