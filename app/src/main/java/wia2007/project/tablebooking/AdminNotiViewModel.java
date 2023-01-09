package wia2007.project.tablebooking;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import wia2007.project.tablebooking.dao.BookingDAO;
import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.Booking;

import java.util.List;

public class AdminNotiViewModel extends AndroidViewModel {

    private List<Booking> notificationList;
    private TableBookingDatabase tableBookingDatabase;
    private BookingDAO bookingDAO;

    public AdminNotiViewModel(@NonNull Application application) {
        super(application);

        tableBookingDatabase = TableBookingDatabase.getDatabase(application);
        bookingDAO = tableBookingDatabase.bookingDAO();
        notificationList = bookingDAO.getBookingByRestaurant(0);
    }

        public void insert(Booking noti){
            new AdminNotiViewModel.InsertAsyncTask(bookingDAO).execute(noti);
        }


        List<Booking> getAllBooking(){
            return notificationList;
        }

        private class OperationsAsyncTask extends AsyncTask<Booking, Void, Void> {

            BookingDAO mAsyncTaskDao;

            OperationsAsyncTask(BookingDAO dao) {
                this.mAsyncTaskDao = dao;
            }

            @Override
            protected Void doInBackground(Booking... bookings) {
                return null;
            }
        }

        private class InsertAsyncTask extends AdminNotiViewModel.OperationsAsyncTask {
            InsertAsyncTask(BookingDAO mbookingDAO) {
                super(mbookingDAO);
            }

            @Override
            protected Void doInBackground(Booking...noti) {
                mAsyncTaskDao.insertBookings(noti[0]);
                return null;
            }
        }


}


