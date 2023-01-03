package wia2007.project.tablebooking;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.Date;
import java.util.List;

import wia2007.project.tablebooking.dao.CustomerDAO;
import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.Customer;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        TableBookingDatabase database = TableBookingDatabase.getDatabase(appContext);
        CustomerDAO customerDAO = database.customerDAO();
        customerDAO.deleteAll();

        Customer customer = new Customer("root", "1234", "01110687471", "root@gmail.com", Customer.GENDER_MALE, new Date(System.currentTimeMillis()));
        List<Long> list = customerDAO.insertCustomers(customer);
        int id = list.get(0).intValue();
        System.out.println(customerDAO.getCustomerById(id));
    }
}
