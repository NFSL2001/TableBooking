package wia2007.project.tablebooking;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;

import java.util.List;

import wia2007.project.tablebooking.dao.TableDAO;
import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.Table;

public class TableViewModel extends AndroidViewModel {

    private TableDAO tableDAO;
    private TableBookingDatabase tableBookingDatabase;
    private List<Table> mallTable;

    public TableViewModel(@NonNull Application application) {
        super(application);

        tableBookingDatabase = TableBookingDatabase.getDatabase(application);
        tableDAO = tableBookingDatabase.tableDAO();
        mallTable = tableDAO.getTableByRestaurant(0);
    }

    public void insert(Table table){
        new InsertAsyncTask(tableDAO).execute(table);
    }

    public void delete(Table table) {
        new DeleteAsyncTask(tableDAO).execute(table);
    }

    List<Table> getAllTable(){
        return mallTable;
    }

    private class OperationsAsyncTask extends AsyncTask<Table, Void, Void> {

        TableDAO mAsyncTaskDao;

        OperationsAsyncTask(TableDAO dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Table... tables) {
            return null;
        }
    }

    private class InsertAsyncTask extends OperationsAsyncTask {
        InsertAsyncTask(TableDAO mtableDAO) {
            super(mtableDAO);
        }

        @Override
        protected Void doInBackground(Table...table) {
            mAsyncTaskDao.insertTables(table[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends OperationsAsyncTask {

        public DeleteAsyncTask(TableDAO tableDao) {
            super(tableDao);
        }

        @Override
        protected Void doInBackground(Table... table) {
            mAsyncTaskDao.deleteTables(table[0]);
            return null;
        }
    }
}
