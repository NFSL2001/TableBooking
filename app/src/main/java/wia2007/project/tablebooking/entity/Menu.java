package wia2007.project.tablebooking.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity
public class Menu {
    @PrimaryKey
    private Integer menu_id;
    private String menu_name;
    private Float price;
    private Integer restaurant;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private String path;

    public Menu() {
    }

    public Menu(Integer menu_id, String menu_name, Float price, Integer restaurant, String path) {
        this.menu_id = menu_id;
        this.menu_name = menu_name;
        this.price = price;
        this.restaurant = restaurant;
        this.path = path;
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
}
