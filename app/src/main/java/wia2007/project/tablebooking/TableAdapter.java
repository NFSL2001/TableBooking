package wia2007.project.tablebooking;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import wia2007.project.tablebooking.entity.Table;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.TableHolder> {
    Context context;
    List<TableViewModel> tableItem;
    List<Table> itemList = new ArrayList<>();
    List<Table> NotAvailable = new ArrayList<>();
    RecycleViewInterface recycleViewInterface;
    ItemAdapter itemAdapter;
    Class pClass;

    public TableAdapter(Context context, List<TableViewModel> menuItem,List<Table> NotAvailable,Class pClass) {
        this.context = context;
        this.tableItem = menuItem;
        this.NotAvailable = NotAvailable;
        this.pClass = pClass;
    }

    public TableAdapter(Context context, List<TableViewModel> menuItem,Class pClass) {
        this.context = context;
        this.tableItem = menuItem;
        this.pClass = pClass;
    }

    @NonNull
    @Override
    public TableAdapter.TableHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_list_section, parent, false);
        return new TableHolder(view, recycleViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull TableHolder holder, int position) {
        int pos = position;
        TableViewModel dataModel = tableItem.get(position);
        holder.table_title.setText(Integer.toString(dataModel.getSize())+" People Table");
        itemList = dataModel.getTable();
        itemAdapter = new ItemAdapter(context,itemList,NotAvailable,pClass);
        holder.nestedRV.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(),RecyclerView.HORIZONTAL,false));
        holder.nestedRV.setHasFixedSize(true);
        holder.nestedRV.setAdapter(itemAdapter);
    }

    @Override
    public int getItemCount() {
        return tableItem.size();
    }

    public class TableHolder extends RecyclerView.ViewHolder {

        private TextView table_title;
        private RecyclerView nestedRV;

        public TableHolder(@NonNull View itemView, RecycleViewInterface recycleViewInterface) {
            super(itemView);
            table_title = itemView.findViewById(R.id.TableSize);
            nestedRV = itemView.findViewById(R.id.child_rv);
        }

    }

}
