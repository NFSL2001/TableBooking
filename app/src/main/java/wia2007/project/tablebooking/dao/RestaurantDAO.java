package wia2007.project.tablebooking.dao;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import wia2007.project.tablebooking.entity.Restaurant;

@Dao
public interface RestaurantDAO {
    @Update
    public void updateRestaurants(Restaurant ...restaurants);

    @Insert
    public void insertRestaurants(Restaurant ...restaurants);

    @Delete
    public void deleteRestaurants(Restaurant ...restaurants);

    @Query("SELECT * FROM Restaurant WHERE restaurant_id = :id")
    public List<Restaurant> getRestaurantById(Integer id);

    @Query("SELECT * FROM Restaurant WHERE restaurant_user_name = :restaurantUsername")
    public List<Restaurant> getRestaurantByRestaurantUserName(String restaurantUsername);

    @Query("SELECT restaurant_id, restaurant_name, cuisine_type FROM Restaurant WHERE cuisine_type = :type")
    public List<RestaurantNameInfoPair> getRestaurantByCuisine(Integer type);

    @Query("SELECT restaurant_id, restaurant_name, cuisine_type FROM Restaurant")
    public List<RestaurantNameInfoPair> listAllRestaurantName();

    class RestaurantNameInfoPair {
        @ColumnInfo(name = "restaurant_id")
        @NonNull
        public int id;

        @ColumnInfo(name = "restaurant_name")
        public String name;

        @ColumnInfo(name = "cuisine_type")
        public int cuisine_type;

        public RestaurantNameInfoPair(int id, String name, int cuisine_type) {
            this.id = id;
            this.name = name;
            this.cuisine_type = cuisine_type;
        }
    }
}
