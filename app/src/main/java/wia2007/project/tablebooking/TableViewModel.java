package wia2007.project.tablebooking;

import java.util.List;

import wia2007.project.tablebooking.entity.Table;

public class TableViewModel {

    private List<Table> mTable;
    private int size;

    public TableViewModel(List<Table> mTable,int size) {
        this.mTable = mTable;
        this.size = size;
    }

    public List<Table> getTable() {
        return mTable;
    }

    public void setTable(List<Table> mTable) {
        this.mTable = mTable;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
