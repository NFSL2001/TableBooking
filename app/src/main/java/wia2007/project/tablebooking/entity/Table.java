package wia2007.project.tablebooking.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.lang.reflect.Member;

@Entity
public class Table {
    @PrimaryKey(autoGenerate = true)
    private Integer table_id;
    private Integer restaurant_id;
    private String name;
    private Integer size;

    public Table() {
    }

    @Ignore
    public Table(Integer restaurant_id, String name, Integer size) {
        this.restaurant_id = restaurant_id;
        this.name = name;
        this.size = size;
    }

    public Integer getTable_id() {
        return table_id;
    }

    public void setTable_id(Integer table_id) {
        this.table_id = table_id;
    }

    public Integer getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(Integer restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Table{" +
                "table_id=" + table_id +
                ", restaurant_id=" + restaurant_id +
                ", name='" + name + '\'' +
                ", size=" + size +
                '}';
    }

    @Override
    public boolean equals(Object anObject) {
        if (!(anObject instanceof Table)) {
            return false;
        }
        Table otherMember = (Table) anObject;
        return otherMember.getTable_id().equals(getTable_id());
    }
}