package wia2007.project.tablebooking.dao;

import androidx.room.Dao;
import androidx.room.Delete;
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

    @Query("SELECT * FROM Restaurant WHERE restaurant_name LIKE '%' || :phrase || '%'")
    public List<Restaurant> searchRestaurant(String phrase);

    @Query("SELECT * FROM Restaurant WHERE cuisine_type = :type")
    public List<Restaurant> getRestaurantByCuisine(Integer type);
}
