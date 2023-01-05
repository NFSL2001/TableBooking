package com.example.invitable;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;

import com.example.invitable.dao.TableDAO;
import com.example.invitable.database.TableBookingDatabase;
import com.example.invitable.entity.Table;

import java.util.List;

public class TableViewModel extends AndroidViewModel {

    private TableDAO tableDAO;
    private TableBookingDatabase tableBookingDatabase;
    private LiveData<List<Table>> mallTable;

    public TableViewModel(@NonNull Application application) {
        super(application);

        tableBookingDatabase = TableBookingDatabase.getDatabase(application);
        tableDAO = tableBookingDatabase.tableDAO();
        mallTable = tableDAO.getTableByRestaurant(0);
    }

    public void insert(Table table){
        TableBookingDatabase.databaseExecutor.execute(() ->{
            tableDAO.insertTables(table);
        });
    }

    LiveData<List<Table>> getAllTable(){
        return mallTable;
    }

    private class InsertAsyncTask {
        public InsertAsyncTask(TableDAO tableDAO) {
        }

        public void execute(Table table) {
        }
    }
}
