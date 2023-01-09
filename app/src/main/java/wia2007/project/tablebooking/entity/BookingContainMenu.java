package wia2007.project.tablebooking.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(primaryKeys = {"booking_id", "menu_id"}, tableName = "Contain")
public class BookingContainMenu {
    @NonNull
    private Integer booking_id;
    @NonNull
    private Integer menu_id;
    private Integer quantity;

    public BookingContainMenu() {
    }

    @Ignore
    public BookingContainMenu(Integer booking_id, Integer menu_id, Integer quantity) {
        this.booking_id = booking_id;
        this.menu_id = menu_id;
        this.quantity = quantity;
    }

    public Integer getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(Integer booking_id) {
        this.booking_id = booking_id;
    }

    public Integer getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(Integer menu_id) {
        this.menu_id = menu_id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "BookingContainMenu{" +
                "booking_id=" + booking_id +
                ", menu_id=" + menu_id +
                ", quantity=" + quantity +
                '}';
    }
}
