package wia2007.project.tablebooking.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import wia2007.project.tablebooking.entity.Customer;

@Dao
public interface CustomerDAO {
    @Update
    public void updateCustomers(Customer ...customers);

    @Insert
    public List<Long> insertCustomers(Customer ...customers);

    @Delete
    public void deleteCustomers(Customer ...customers);

    @Query("SELECT * FROM Customer WHERE customer_id = :id")
    public List<Customer> getCustomerById(Integer id);

    @Query("SELECT * FROM Customer WHERE user_name = :username")
    public List<Customer> getCustomerByUsername(String username);

    @Query("DELETE FROM Customer")
    public void deleteAll();
}
