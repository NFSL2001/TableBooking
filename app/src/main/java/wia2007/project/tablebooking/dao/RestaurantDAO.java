package wia2007.project.tablebooking.dao;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
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
    public long[] insertRestaurants(Restaurant ...restaurants);

    @Delete
    public void deleteRestaurants(Restaurant ...restaurants);

    @Query("SELECT * FROM Restaurant WHERE restaurant_id = :id")
    public List<Restaurant> getRestaurantById(Integer id);

    @Query("SELECT * FROM Restaurant WHERE restaurant_user_name = :restaurantUsername")
    public List<Restaurant> getRestaurantByRestaurantUserName(String restaurantUsername);

    @Query("SELECT restaurant_id, restaurant_name, cuisine_type, title_image_path FROM Restaurant WHERE cuisine_type = :type")
    public List<RestaurantNameInfo> getRestaurantByCuisine(Integer type);

    @Query("SELECT restaurant_id, restaurant_name, cuisine_type, title_image_path FROM Restaurant")
    public List<RestaurantNameInfo> listAllRestaurantInfo();

    @Query("SELECT restaurant_name,address,contact_number,title_image_path FROM Restaurant WHERE restaurant_id = :id")
    public List<Restaurant> getRestaurantInfoById(Integer id);

    @Query("UPDATE Restaurant SET restaurant_name = :restaurant_name, contact_number = :contact_number,average_price = :average_price, address = :address, working_hour = :working_hour, payment = :payment, parking = :parking, dresscode = :dresscode, accessibility = :accessibility, website = :website, cuisine_type = :cuisine_type, description = :description, title_image_path = :title_image_path, image_path_1 = :Img1, image_path_2 = :Img2, image_path_3 = :Img3, image_path_4 = :Img4, image_path_5 = :Img5 WHERE restaurant_id = :restaurant_id")
    public void updateRestaurantInfo(int restaurant_id,String restaurant_name, String contact_number, Float average_price, String address, String working_hour, String payment, String parking, String dresscode, String accessibility, String website, Integer cuisine_type, String description, String title_image_path, String Img1, String Img2, String Img3, String Img4, String Img5);

    class RestaurantNameInfo {
        @ColumnInfo(name = "restaurant_id")
        @NonNull
        public int id;

        @ColumnInfo(name = "restaurant_name")
        public String name;

        @ColumnInfo(name = "cuisine_type")
        public int cuisine_type;

        @ColumnInfo(name = "title_image_path")
        public String title_image_path;

        public RestaurantNameInfo(int id, String name, int cuisine_type, String title_image_path) {
            this.id = id;
            this.name = name;
            this.cuisine_type = cuisine_type;
            this.title_image_path = title_image_path;
        }
    }
}
