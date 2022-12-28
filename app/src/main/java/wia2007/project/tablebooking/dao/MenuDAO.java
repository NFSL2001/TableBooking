package wia2007.project.tablebooking.dao;

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
}
