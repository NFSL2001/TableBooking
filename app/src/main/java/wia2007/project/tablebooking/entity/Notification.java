package wia2007.project.tablebooking.entity;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

import wia2007.project.tablebooking.entity.Customer;

@Entity
public class Notification {
    @PrimaryKey
    @NonNull
    private String notification;
    private Integer customer_id;

    public Notification(){}

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
}
