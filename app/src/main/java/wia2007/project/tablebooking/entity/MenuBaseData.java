package wia2007.project.tablebooking.entity;

import androidx.annotation.NonNull;

/*
* Class to hold info for restaurant menu view
* */
// base class for implementing List<MenuBaseData>
public class MenuBaseData {
    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }

    // add buttons for admin side
    public class MenuAddItemButton extends MenuBaseData{
        public MenuItem menuItem;
        public Integer restaurantID;

        public MenuAddItemButton(MenuItem menuItem, Integer restaurantID) {
            this.menuItem = menuItem;
            this.restaurantID = restaurantID;
        }
    }

    public class MenuAddCategoryButton extends MenuBaseData{}
}