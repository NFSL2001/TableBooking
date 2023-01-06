package wia2007.project.tablebooking.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import wia2007.project.tablebooking.entity.Notification;

@Dao
public interface NotificationDAO {
    @Insert
    public void insertNotification(Notification... notification);


    @Query("SELECT * FROM Notification WHERE customer_id = :customer_id")
    public LiveData<List<Notification>> getNotification(Integer customer_id);
}
