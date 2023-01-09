package wia2007.project.tablebooking.entity;

// class for category header view holder
public class MenuCategory extends MenuBaseData {
    public String name;
    public boolean isFolded = false;

    public MenuCategory(){}

    public MenuCategory(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
