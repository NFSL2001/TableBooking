package wia2007.project.tablebooking.entity;

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
}
