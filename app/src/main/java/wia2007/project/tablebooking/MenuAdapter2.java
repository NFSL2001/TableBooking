package wia2007.project.tablebooking;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
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

import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.MenuBaseData;
import wia2007.project.tablebooking.entity.MenuCategory;
import wia2007.project.tablebooking.entity.MenuItem;

public class MenuAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<MenuBaseData> menuList, originalMenuList;
    RecyclerView recyclerView;
    static Map<Integer, Integer> map = new HashMap<>();
    private String[] mTextList;

    public MenuAdapter2(Context context, List<MenuBaseData> menuList) {
        this.context = context;
        this.menuList = menuList;
        this.originalMenuList = new ArrayList<>(menuList);
        mTextList = new String[getItemCount()];
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
                menuItemHolder.disableTextChangeListener();
                menuItemHolder.OrderQuantity.setText(mTextList[position]);
                menuItemHolder.addTextChangedListener();

                menuItemHolder.setIsRecyclable(false);
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

    public class MenuItemHolder extends BaseImageHolder implements TextWatcher {

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

            OrderQuantity.setFilters(new InputFilter[]{new InputFilterMinMax(0, 10)});
            OrderQuantity.addTextChangedListener(this);

        }

        public void addTextChangedListener() {
            // This will add the listener. So, it will start to listen to new events
            OrderQuantity.addTextChangedListener(this);
        }

        public void disableTextChangeListener() {
            // This will remove the listener. So, it will stop to listen to new events
            OrderQuantity.removeTextChangedListener(this);
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(final Editable s) {
            MenuItem menuItem = (MenuItem) menuList.get(getAdapterPosition());
            String qty = OrderQuantity.getText().toString();
            if (qty.equals("")) {
                qty = "0";
            }
            map.put(menuItem.getMenu_id(), Integer.parseInt(qty));
            List<Integer> key = new ArrayList<>(map.keySet());
            List<Integer> values = new ArrayList<>(map.values());
            double price = 0;
            for (int i = 0; i < map.size(); i++) {
                if (values.get(i) != 0) {
                    price += TableBookingDatabase.getDatabase(context).menuDAO().getMenuById(key.get(i)).get(0).getPrice() * values.get(i);
                }
            }
            PreOrderFoodActivity.Price.setText("RM" + String.format("%.2f", price));
            int index = getAdapterPosition();
            if (index >= 0) {
                mTextList[index] = OrderQuantity.getText().toString();
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

    public static Map<Integer, Integer> getMap() {
        return map;
    }

    public class InputFilterMinMax implements InputFilter {
        private int min;
        private int max;

        public InputFilterMinMax(int min, int max) {
            this.min = min;
            this.max = max;
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            //noinspection EmptyCatchBlock
            try {
                int input = Integer.parseInt(dest.subSequence(0, dstart).toString() + source + dest.subSequence(dend, dest.length()));
                if (isInRange(min, max, input))
                    return null;
            } catch (NumberFormatException nfe) {
            }
            return "";
        }

        private boolean isInRange(int a, int b, int c) {
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }
    }
}
