package wia2007.project.tablebooking.entity;

import androidx.room.Entity;

@Entity(primaryKeys = {"booking_id", "menu_id"}, tableName = "Contain")
public class BookingContainMenu {
    private Integer booking_id;
    private Integer menu_id;
}
