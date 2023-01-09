package wia2007.project.tablebooking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import wia2007.project.tablebooking.entity.Table;

import java.util.List;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.TableViewHolder> {
   private LayoutInflater layoutInflater;
    private Context mcontext;
    private List<Table> mTable;

    public TableAdapter(Context context, List<Table> tableList ) {
        layoutInflater = LayoutInflater.from(context);
        mcontext = context;
        mTable = tableList;
    }

    @NonNull
    @Override
    public TableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.from(parent.getContext()).inflate(R.layout.table_list,parent,false);
        TableViewHolder viewHolder = new TableViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TableViewHolder holder, int position) {
        if (mTable != null){
            Table table = mTable.get(position);
            holder.setData(table.getName());

        }else{
            //cover the data that is not ready yet
            holder.tableView.setText(R.string.no_table);
        }
    }

    @Override
    public int getItemCount() {
        if(mTable != null){
            return mTable.size();
        }else{
            return 0;
        }
    }

    public void setTable(List<Table>table){
        mTable = table;
        notifyDataSetChanged();
    }

    public class TableViewHolder extends RecyclerView.ViewHolder {
        private TextView tableView;
        public TableViewHolder(@NonNull View itemView) {
            super(itemView);
            tableView = itemView.findViewById(R.id.N1);
        }

        public void setData(String tablename) {
            tableView.setText(tablename);
        }
    }
}
