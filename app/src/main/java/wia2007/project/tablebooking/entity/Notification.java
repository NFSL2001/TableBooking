package wia2007.project.tablebooking.entity;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

import wia2007.project.tablebooking.entity.Customer;

@Entity
public class Notification {
    @PrimaryKey(autoGenerate = true)
    private int noti_id;
    @NonNull
    private String notification;
    private Integer customer_id;
    private Integer restaurant_id;

    public Notification(){}

    public Notification(@NonNull String notification, Integer customer_id, Integer restaurant_id) {
        this.notification = notification;
        this.customer_id = customer_id;
        this.restaurant_id = restaurant_id;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public Integer getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Integer customer_id) {
        this.customer_id = customer_id;
    }

    public Integer getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(Integer restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public int getNoti_id() {
        return noti_id;
    }

    public void setNoti_id(int noti_id) {
        this.noti_id = noti_id;
    }
}
