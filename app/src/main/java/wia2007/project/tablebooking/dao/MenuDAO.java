package wia2007.project.tablebooking.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import wia2007.project.tablebooking.entity.MenuItem;

@Dao
public interface MenuDAO {
    @Update
    public void updateMenus(MenuItem...menus);

    @Insert
    public void insertMenus(MenuItem...menus);

    @Delete
    public void deleteMenus(MenuItem...menus);

    @Query("SELECT * FROM MenuItem WHERE menu_id = :id")
    public List<MenuItem> getMenuById(Integer id);

    @Query("SELECT * FROM MenuItem WHERE restaurant = :restaurantId")
    public List<MenuItem> getMenuByRestaurant(Integer restaurantId);

    @Query("SELECT * FROM MenuItem WHERE restaurant = :restaurantId ORDER BY category COLLATE NOCASE")
    public List<MenuItem> getDefaultMenuByRestaurant(Integer restaurantId);

    @Query("Update MenuItem Set menu_name = :menu_name, description = :menu_description,category = :type, price = :menu_price, path = :path WHERE menu_id = :menu_id ")
    void updateMenuItem(String menu_name, String menu_description,String type, float menu_price, String path, int menu_id);

    @Query("Delete From MenuItem Where menu_id = :menu_id")
    void deleteMenuItem(int menu_id);

    @Query("SELECT * FROM MenuItem WHERE restaurant =:restaurant_id ORDER BY " +
            "CASE WHEN :sortCondition = 0 THEN menu_id END COLLATE NOCASE ASC," +
            "CASE WHEN :sortCondition = 1 THEN menu_id END COLLATE NOCASE DESC," +
            "CASE WHEN :sortCondition = 2 THEN price END COLLATE NOCASE ASC," +
            "CASE WHEN :sortCondition = 3 THEN price END COLLATE NOCASE DESC")
    public List<MenuItem> getMenuSortedList(int restaurant_id, int sortCondition);
}
