package wia2007.project.tablebooking;

import java.util.List;

import wia2007.project.tablebooking.entity.Menu;

public class DataModel {

    private List<Menu> nestedMenuList;
    private String menuType;
    private boolean isExpandable;

    public DataModel(List<Menu> nestedMenuList, String menuType) {
        this.nestedMenuList = nestedMenuList;
        this.menuType = menuType;
        this.isExpandable = false;
    }

    public DataModel(List<DataModel> list) {
        this.nestedMenuList = (List<Menu>) nestedMenuList.get(1);
        this.menuType = String.valueOf(list.get(0));
        this.isExpandable = false;
    }

    public List<Menu> getNestedMenuList() {
        return nestedMenuList;
    }

    public void setNestedMenuList(List<Menu> nestedMenuList) {
        this.nestedMenuList = nestedMenuList;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public boolean isExpandable() {
        return isExpandable;
    }

    public void setExpandable(boolean expandable) {
        isExpandable = expandable;
    }
}