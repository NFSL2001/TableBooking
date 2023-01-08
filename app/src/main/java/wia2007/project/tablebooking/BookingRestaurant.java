package wia2007.project.tablebooking;

import androidx.room.Embedded;

import java.sql.Time;

import wia2007.project.tablebooking.entity.Booking;
import wia2007.project.tablebooking.entity.Restaurant;

public class BookingRestaurant {
    private int booking_id;
    private Time start_time;
    @Embedded
    private Restaurant restaurant;

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Time getStart_time() {
        return start_time;
    }

    public void setStart_time(Time start_time) {
        this.start_time = start_time;
    }

    public int getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }
}
