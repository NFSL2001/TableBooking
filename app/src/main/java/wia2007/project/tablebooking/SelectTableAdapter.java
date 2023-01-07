package wia2007.project.tablebooking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.invitable.R;

import wia2007.project.tablebooking.entity.Table;

import java.util.List;

public class SelectTableAdapter extends RecyclerView.Adapter<SelectTableAdapter.selectTableViewHolder> {

    private final LayoutInflater layoutInflater;
    private List<Table> tables;
    private ItemClickListenerInterface clickListener;

    SelectTableAdapter(Context context, List<Table> tables, ItemClickListenerInterface clickListener) {
        layoutInflater = LayoutInflater.from(context);
        this.tables = tables;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public SelectTableAdapter.selectTableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.select_table_button_recyclerview, parent, false);
        SelectTableAdapter.selectTableViewHolder viewHolder = new SelectTableAdapter.selectTableViewHolder(itemView, clickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectTableAdapter.selectTableViewHolder holder, int position) {
        holder.tableButton.setText(tables.get(position).getTable_id());
    }

    @Override
    public int getItemCount() {
        return tables.size();
    }

    public static class selectTableViewHolder extends RecyclerView.ViewHolder {

        Button tableButton;

        public selectTableViewHolder(@NonNull View itemView, ItemClickListenerInterface clickListener) {
            super(itemView);

            tableButton = itemView.findViewById(R.id.select_table_tButton_recyclerview);

            tableButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener != null) {
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION) {
                            clickListener.onClick(pos);
                        }

                    }
                }
            });
        }
    }
}
