package wia2007.project.tablebooking;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import wia2007.project.tablebooking.entity.MenuBaseData;
import wia2007.project.tablebooking.entity.MenuCategory;
import wia2007.project.tablebooking.entity.MenuItem;

public class MenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<MenuBaseData> menuList, originalMenuList;
    RecyclerView recyclerView;
    RecycleViewInterface recycleViewInterface;

    public MenuAdapter(Context context, List<MenuBaseData> menuList, RecycleViewInterface recycleViewInterface) {
        this.context = context;
        this.menuList = menuList;
        this.originalMenuList = new ArrayList<MenuBaseData>(menuList);
        this.recycleViewInterface = recycleViewInterface;
    }

    public MenuAdapter(Context context, List<MenuBaseData> menuList) {
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
            case R.layout.display_menu_row: // menu item
                v = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.display_menu_row,
                        parent,
                        false
                );
                return new MenuItemHolder(v);
            case R.layout.individual_menu_add_item_button: // item button
                v = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.individual_menu_add_item_button,
                        parent,
                        false
                );
                return new MenuItemAddHolder(v);
            case R.layout.individual_restaurant_menu_section: // category header
                v = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.individual_restaurant_menu_section,
                        parent,
                        false
                );
                return new MenuCategoryHolder(v);
            case R.layout.individual_menu_add_category_button: // category button
                v = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.individual_menu_add_category_button,
                        parent,
                        false
                );
                return new MenuCategoryAddHolder(v);
            default:
                throw new RuntimeException("Not menu object");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case R.layout.display_menu_row:
                // get the associate object and cast to class
                MenuItem item = (MenuItem) menuList.get(position);
                // get the associate holder
                MenuItemHolder menuItemHolder = (MenuItemHolder) holder;
                //set holder display info
                menuItemHolder.setViewData(item);

                break;
            case R.layout.individual_restaurant_menu_section:
                // get the associate object and cast to class
                MenuCategory category = (MenuCategory) menuList.get(position);
                // get the associate holder
                MenuCategoryHolder menuCategoryHolder = (MenuCategoryHolder) holder;
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
            case R.layout.individual_menu_add_category_button:
            case R.layout.individual_menu_add_item_button:
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
        if (object instanceof MenuItem) return R.layout.display_menu_row;
        else if (object instanceof MenuCategory) return R.layout.individual_restaurant_menu_section;
        else if (object instanceof MenuBaseData.MenuAddItemButton)
            return R.layout.individual_menu_add_item_button;
        else if (object instanceof MenuBaseData.MenuAddCategoryButton)
            return R.layout.individual_menu_add_category_button;
        else return 0;
    }

    public void refreshView() {
        this.menuList.clear();

        /** Folding category code **/
        MenuCategory lastSeenMenuCategory = new MenuCategory();
        //loop through original list
        for (MenuBaseData item : originalMenuList) {
            //get the leading MenuCategory
            if (item instanceof MenuCategory) {
                lastSeenMenuCategory = (MenuCategory) item;
                menuList.add(item);
            } else if (item instanceof MenuItem || item instanceof MenuBaseData.MenuAddItemButton) {
                //if leading MenuCategory is not hidden/folded
                if (!lastSeenMenuCategory.isFolded) {
                    //add item into viewing list
                    menuList.add(item);
                }
            }else if(item instanceof MenuBaseData.MenuAddCategoryButton){
                menuList.add(item);
            }
        }
        notifyDataSetChanged();
    }

    public class MenuItemHolder extends BaseImageHolder {

        TextView menuName, menuPrice, menuDescription;
        ImageView menuImage;
        String internalMenuCategory;

        public MenuItemHolder(@NonNull View itemView) {
            super(itemView);
            this.menuName = itemView.findViewById(R.id.TVMenuName);
            this.menuPrice = itemView.findViewById(R.id.TVMenuPriceAdmin);
            this.menuDescription = itemView.findViewById(R.id.TVDescriptionMenuAdmin);
            this.menuImage = itemView.findViewById(R.id.MenuImage);

            if(recycleViewInterface!=null){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (recycleViewInterface != null) {
                            int pos = getAdapterPosition();

                            if (pos != RecyclerView.NO_POSITION) {
                                recycleViewInterface.onItemClick(pos);
                            }
                        }
                    }
                });

                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION) {
                            recycleViewInterface.onLongClick(pos);
                            return true;
                        }
                        return false;
                    }
                });
            }
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
            if (path == null) {
                this.menuImage.setVisibility(View.GONE);
            }
            boolean isSet = this.setImageView(this.menuImage, path);
            if (!isSet)
                this.menuImage.setVisibility(View.GONE);
            else
                this.menuImage.setVisibility(View.VISIBLE);

            this.internalMenuCategory = item.getCategory();
        }
    }

    public class MenuCategoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView menuCategoryTitle;
        MenuCategory internalMenuCategory;

        public MenuCategoryHolder(@NonNull View itemView) {
            super(itemView);
            this.menuCategoryTitle = itemView.findViewById(R.id.restMenu_sectionHeader);
            itemView.setOnClickListener(this);
        }

        public void setViewData(MenuCategory category) {
            //set holder display info
            this.menuCategoryTitle.setText(category.name);
            this.internalMenuCategory = category;
        }

        @Override
        public void onClick(View v) {
            if (!Objects.isNull(this.internalMenuCategory)) {
                // flip isFolded state
                this.internalMenuCategory.isFolded = !this.internalMenuCategory.isFolded;
                refreshView();
            }
        }
    }

    public class MenuCategoryAddHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public MenuCategoryAddHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }


        @SuppressLint("RestrictedApi")
        @Override
        public void onClick(View v) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setMessage("Add New Category");
            EditText edittext;
            edittext = new EditText(context);
            alertDialogBuilder.setView(edittext,50,0,50,0);
            alertDialogBuilder.setPositiveButton("yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            String category = edittext.getText().toString();
                            menuList.add(menuList.size()-1, new MenuCategory(category));
                            menuList.add(menuList.size()-1,new MenuBaseData.MenuAddItemButton(category));
                        }
                    });
            alertDialogBuilder.setNegativeButton("No", null);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    public class MenuItemAddHolder extends RecyclerView.ViewHolder {

        public MenuItemAddHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ItemDetails.class);
                    MenuBaseData.MenuAddItemButton menuAddItem = (MenuBaseData.MenuAddItemButton) menuList.get(getAbsoluteAdapterPosition());
                    intent.putExtra("ItemType",menuAddItem.type);
                    ((Activity) itemView.getContext()).startActivity(intent);
                }
            });
        }

    }
}