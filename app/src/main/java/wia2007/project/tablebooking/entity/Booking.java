package wia2007.project.tablebooking.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.sql.Time;

@Entity
public class Booking {
    @PrimaryKey(autoGenerate = true)
    private Integer booking_id;
    private Integer table_id;
    private Integer customer_id;
    private Time start_time;
    private Time end_time;
    private String remark;

    public Booking() {
    }

    @Ignore
    public Booking(Integer booking_id, Integer table_id, Integer customer_id, Time start_time, Time end_time, String remark) {
        this.booking_id = booking_id;
        this.table_id = table_id;
        this.customer_id = customer_id;
        this.start_time = start_time;
        this.end_time = end_time;
        this.remark = remark;
    }

    public Booking(Integer table_id, Integer customer_id, Time start_time, Time end_time, String remark) {
        this.table_id = table_id;
        this.customer_id = customer_id;
        this.start_time = start_time;
        this.end_time = end_time;
        this.remark = remark;
    }

    public Integer getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(Integer booking_id) {
        this.booking_id = booking_id;
    }

    public Integer getTable_id() {
        return table_id;
    }

    public void setTable_id(Integer table_id) {
        this.table_id = table_id;
    }

    public Integer getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Integer customer_id) {
        this.customer_id = customer_id;
    }

    public Time getStart_time() {
        return start_time;
    }

    public void setStart_time(Time start_time) {
        this.start_time = start_time;
    }

    public Time getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Time end_time) {
        this.end_time = end_time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "booking_id=" + booking_id +
                ", table_id=" + table_id +
                ", customer_id=" + customer_id +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", remark='" + remark + '\'' +
                '}';
    }
}
