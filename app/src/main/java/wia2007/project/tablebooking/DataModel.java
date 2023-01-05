package wia2007.project.tablebooking;

import java.util.List;

import wia2007.project.tablebooking.entity.MenuItem;

public class DataModel {

    private List<MenuItem> nestedMenuList;
    private String menuCategory;
    private boolean isExpandable;

    public DataModel(List<MenuItem> nestedMenuList, String menuType) {
        this.nestedMenuList = nestedMenuList;
        this.menuCategory = menuType;
        this.isExpandable = false;
    }

    public List<MenuItem> getNestedMenuList() {
        return nestedMenuList;
    }

    public void setNestedMenuList(List<MenuItem> nestedMenuList) {
        this.nestedMenuList = nestedMenuList;
    }

    public String getMenuCategory() {
        return menuCategory;
    }

    public void setMenuCategory(String menuCategory) {
        this.menuCategory = menuCategory;
    }

    public boolean isExpandable() {
        return isExpandable;
    }

    public void setExpandable(boolean expandable) {
        isExpandable = expandable;
    }
}