package wia2007.project.tablebooking.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.sql.Time;
import java.util.List;

import wia2007.project.tablebooking.entity.Table;

@Dao
public interface TableDAO {
    @Update
    public void updateTables(Table...tables);

    @Insert
    public void insertTables(Table...tables);

    @Delete
    public void deleteTables(Table...tables);

    @Query("SELECT * FROM `Table` WHERE table_id = :id")
    public List<Table> getTableById(Integer id);

    @Query("SELECT * FROM `Table` WHERE restaurant_id = :restaurantId")
    public List<Table> getTableByRestaurant(Integer restaurantId);

    @Query("SELECT * FROM `Table` " +
            "WHERE restaurant_id = :restaurantId " +
            "AND table_id NOT IN (SELECT table_id FROM Booking WHERE start_time < :endTime AND :startTime < end_time)")
    public List<Table> getAvailableTable(Integer restaurantId, Time startTime, Time endTime);
}