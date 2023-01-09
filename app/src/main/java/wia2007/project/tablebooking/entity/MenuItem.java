package wia2007.project.tablebooking.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class MenuItem extends MenuBaseData {
    @PrimaryKey(autoGenerate = true)
    private Integer menu_id;
    private String menu_name;
    private Float price;
    private Integer restaurant;
    private String path;
    private String description;
    private String category;

    public MenuItem() {
    }

    @Ignore
    public MenuItem(String menu_name, Float price, String description, Integer restaurant, String category, String path) {
        this.menu_name = menu_name;
        this.price = price;
        this.description = description;
        this.restaurant = restaurant;
        this.category = category;
        this.path = path;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "menu_id=" + menu_id +
                ", menu_name='" + menu_name + '\'' +
                ", price=" + price +
                ", restaurant=" + restaurant +
                ", path='" + path + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                '}';
    }

    public Integer getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(Integer menu_id) {
        this.menu_id = menu_id;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Integer restaurant) {
        this.restaurant = restaurant;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() { return category; }

    public void setCategory(String category) {
        this.category = category;
    }
}