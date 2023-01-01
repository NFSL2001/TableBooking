package wia2007.project.tablebooking.dao;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import wia2007.project.tablebooking.entity.Menu;

@Dao
public interface MenuDAO {
    @Update
    public void updateMenus(Menu ...menus);

    @Insert
    public void insertMenus(Menu ...menus);

    @Delete
    public void deleteMenus(Menu ...menus);

    @Query("SELECT * FROM Menu WHERE menu_id = :id")
    public List<Menu> getMenuById(Integer id);

    @Query("SELECT * FROM Menu WHERE restaurant = :restaurantId")
    public List<Menu> getMenuByRestaurant(Integer restaurantId);


    @Query("Update Menu Set menu_name = :menu_name, description = :menu_description,type = :type, price = :menu_price, path = :path WHERE menu_id = :menu_id ")
    void updateMenuItem(String menu_name, String menu_description,String type, float menu_price, String path, int menu_id);

    @Query("Delete From Menu Where menu_id = :menu_id")
    void deleteMenuItem(int menu_id);

    @Query("SELECT * FROM Menu WHERE restaurant =:restaurant_id ORDER BY " +
            "CASE WHEN :sortCondition = 0 THEN menu_id END COLLATE NOCASE ASC," +
            "CASE WHEN :sortCondition = 1 THEN menu_id END COLLATE NOCASE DESC," +
            "CASE WHEN :sortCondition = 2 THEN price END COLLATE NOCASE ASC," +
            "CASE WHEN :sortCondition = 3 THEN price END COLLATE NOCASE DESC," +
            "CASE WHEN :sortCondition = 4 THEN type END COLLATE NOCASE ASC," +
            "CASE WHEN :sortCondition = 5 THEN type END COLLATE NOCASE DESC")
    public List<Menu> getMenuSortedList(int restaurant_id, int sortCondition);
}
