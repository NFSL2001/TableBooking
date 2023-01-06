package wia2007.project.tablebooking.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Restaurant {
    @PrimaryKey(autoGenerate = true)
    private Integer restaurant_id;
    private String restaurant_user_name;
    private String password;
    private String restaurant_name;
    private String contact_number;
    private Float average_price;
    private String address;
    private Integer cuisine_type;

    public Restaurant() {
    }

    @Ignore
    public Restaurant(Integer restaurant_id, String restaurant_user_name, String password, String restaurant_name, String contact_number, Float average_price, String address, Integer cuisine_type) {
        this.restaurant_id = restaurant_id;
        this.restaurant_user_name = restaurant_user_name;
        this.password = password;
        this.restaurant_name = restaurant_name;
        this.contact_number = contact_number;
        this.average_price = average_price;
        this.address = address;
        this.cuisine_type = cuisine_type;
    }

    public Integer getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(Integer restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getRestaurant_user_name() {
        return restaurant_user_name;
    }

    public void setRestaurant_user_name(String restaurant_user_name) {
        this.restaurant_user_name = restaurant_user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public Float getAverage_price() {
        return average_price;
    }

    public void setAverage_price(Float average_price) {
        this.average_price = average_price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getCuisine_type() {
        return cuisine_type;
    }

    public void setCuisine_type(Integer cuisine_type) {
        this.cuisine_type = cuisine_type;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "restaurant_id=" + restaurant_id +
                ", restaurant_user_name='" + restaurant_user_name + '\'' +
                ", restaurant_name='" + restaurant_name + '\'' +
                ", contact_number='" + contact_number + '\'' +
                ", average_price=" + average_price +
                ", address='" + address + '\'' +
                ", cuisine_type=" + cuisine_type +
                '}';
    }
}
