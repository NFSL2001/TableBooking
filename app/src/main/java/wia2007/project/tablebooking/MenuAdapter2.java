package wia2007.project.tablebooking;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import wia2007.project.tablebooking.entity.MenuBaseData;
import wia2007.project.tablebooking.entity.MenuCategory;
import wia2007.project.tablebooking.entity.MenuItem;

public class MenuAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<MenuBaseData> menuList, originalMenuList;
    RecyclerView recyclerView;
    static Map<Integer,Integer> map = new HashMap<>();
    public MenuAdapter2(Context context, List<MenuBaseData> menuList) {
        this.context = context;
        this.menuList = menuList;
        this.originalMenuList = new ArrayList<MenuBaseData>(menuList);
    }

    public void notifyNewData(List<MenuBaseData> menu) {
        this.menuList.clear();
        this.originalMenuList.clear();
        menuList = menu;
        originalMenuList = new ArrayList<>(menu);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        switch (viewType) {
            case R.layout.preorder_foodselect_recyclerview: // menu item
                v = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.preorder_foodselect_recyclerview,
                        parent,
                        false
                );
                return new MenuAdapter2.MenuItemHolder(v);

            case R.layout.individual_restaurant_menu_section: // category header
                v = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.individual_restaurant_menu_section,
                        parent,
                        false
                );
                return new MenuAdapter2.MenuCategoryHolder(v);
            default:
                throw new RuntimeException("Not menu object");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case R.layout.preorder_foodselect_recyclerview:
                // get the associate object and cast to class
                MenuItem item = (MenuItem) menuList.get(position);
                // get the associate holder
                MenuAdapter2.MenuItemHolder menuItemHolder = (MenuAdapter2.MenuItemHolder) holder;
                //set holder display info
                menuItemHolder.setViewData(item);

                break;
            case R.layout.individual_restaurant_menu_section:
                // get the associate object and cast to class
                MenuCategory category = (MenuCategory) menuList.get(position);
                // get the associate holder
                MenuAdapter2.MenuCategoryHolder menuCategoryHolder = (MenuAdapter2.MenuCategoryHolder) holder;
                //set holder display info
                menuCategoryHolder.setViewData(category);
                //change folded icon
                if (category.isFolded) {
                    menuCategoryHolder.menuCategoryTitle.setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.restaurant_menu_expand, 0, 0, 0);
                } else {
                    menuCategoryHolder.menuCategoryTitle.setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.restaurant_menu_collapse, 0, 0, 0);
                }
                break;
            default:
                throw new RuntimeException("Not menu object");
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    @Override
    public int getItemViewType(int position) {
        MenuBaseData object = menuList.get(position);
        if (object instanceof MenuItem) return R.layout.preorder_foodselect_recyclerview;
        else if (object instanceof MenuCategory) return R.layout.individual_restaurant_menu_section;
        else return 0;
    }



    public class MenuCategoryHolder extends RecyclerView.ViewHolder {

        TextView menuCategoryTitle;
        MenuCategory internalMenuCategory;

        public MenuCategoryHolder(@NonNull View itemView) {
            super(itemView);
            this.menuCategoryTitle = itemView.findViewById(R.id.restMenu_sectionHeader);
        }

        public void setViewData(MenuCategory category) {
            //set holder display info
            this.menuCategoryTitle.setText(category.name);
            this.internalMenuCategory = category;
        }


    }

    public class MenuItemHolder extends BaseImageHolder {

        TextView menuName, menuPrice, menuDescription;
        ImageView menuImage;
        String internalMenuCategory;
        EditText OrderQuantity;

        public MenuItemHolder(@NonNull View itemView) {
            super(itemView);
            this.menuName = itemView.findViewById(R.id.TVMenuName2);
            this.menuPrice = itemView.findViewById(R.id.TVMenuPriceAdmin2);
            this.menuDescription = itemView.findViewById(R.id.TVDescriptionMenuAdmin2);
            this.menuImage = itemView.findViewById(R.id.MenuImage2);
            this.OrderQuantity = itemView.findViewById(R.id.OrderQuantity);

            OrderQuantity.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(!OrderQuantity.getText().toString().equals("")){
                        MenuItem menuItem = (MenuItem) menuList.get(getAdapterPosition());
                        map.put(menuItem.getMenu_id(),Integer.parseInt(OrderQuantity.getText().toString()));
                    }
                }
            });

        }

        public void setViewData(MenuItem item) {
            //set holder display info
            this.menuName.setText(item.getMenu_name());
            if (item.getDescription() == null || item.getDescription().equals("")) {
                this.menuDescription.setVisibility(View.GONE);
            } else {
                this.menuDescription.setVisibility(View.VISIBLE);
                this.menuDescription.setText(item.getDescription());
            }
            float price = item.getPrice();
            if (price != -1) {
                this.menuPrice.setVisibility(View.VISIBLE);
                this.menuPrice.setText("RM " + String.format("%.02f", price));
            } else {
                this.menuPrice.setVisibility(View.GONE);
            }
            String path = item.getPath();
            if (path != null) {
                //File img = new File(path);
                this.menuImage.setVisibility(View.VISIBLE);
                this.setImageView(this.menuImage, path);
                //this.menuImage.setImageURI(Uri.fromFile(img));
            } else {
                this.menuImage.setVisibility(View.GONE);
            }
            this.internalMenuCategory = item.getCategory();
            this.OrderQuantity.setText("0");
        }
    }

    public static Map<Integer,Integer> getMap(){
        return map;
    }
}
