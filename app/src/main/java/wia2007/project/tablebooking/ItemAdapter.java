package wia2007.project.tablebooking;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import wia2007.project.tablebooking.entity.Table;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<Table> itemList;
    private List<Table> NotAvailable;
    private final RecycleViewInterface recycleViewInterface;
    public ItemAdapter(List<Table> itemList, List<Table> differences,RecycleViewInterface recycleViewInterface){
        this.itemList = itemList;
        NotAvailable = differences;
        this.recycleViewInterface = recycleViewInterface;
    }

    @NonNull
    @Override
    public ItemAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_list,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ItemViewHolder holder, int position) {
        holder.tableName.setText(itemList.get(position).getName());
        if(NotAvailable.contains(itemList.get(position))) {
            holder.tableName.setTextColor(Color.parseColor("#FFFFFF"));
            holder.CVN1.setCardBackgroundColor(Color.parseColor("#C1121F"));
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView tableName;
        CardView CVN1;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tableName = itemView.findViewById(R.id.N1);
            CVN1 = itemView.findViewById(R.id.CVN1);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recycleViewInterface != null){
                        int pos = getAdapterPosition();

                        if(pos!= RecyclerView.NO_POSITION){
                            recycleViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }

    }
    public List<Table> getItemList() {
        return itemList;
    }
}
