package wia2007.project.tablebooking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import wia2007.project.tablebooking.entity.Booking;

import java.util.List;

public class AdminNotiAdapter extends RecyclerView.Adapter<AdminNotiAdapter.AdminNotiViewHolder> {

    private LayoutInflater layoutInflater;
    private List<Booking> bookingList;
    private Context mContext;

    public AdminNotiAdapter(Context context,List<Booking> bookingList) {
        layoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @NonNull
    @Override
    public AdminNotiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_notification_list,parent,false);
        return new AdminNotiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminNotiViewHolder holder, int position) {
        holder.bID.setText(bookingList.get(position).getBooking_id());
        holder.cust.setText(bookingList.get(position).getCustomer_id());
        holder.table.setText(bookingList.get(position).getTable_id());
        holder.time.setText(bookingList.get(position).getStart_time().toString());
        holder.remark.setText(bookingList.get(position).getRemark());

    }

    public void setNoti(List<Booking>bookingList){
        bookingList = bookingList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public class AdminNotiViewHolder extends RecyclerView.ViewHolder {
        private TextView bID, cust, time, table, remark;

        public AdminNotiViewHolder(View view) {
            super(view);
            bID = view.findViewById(R.id.tvBookingid);
            cust = view.findViewById(R.id.tvCustName);
            time = view.findViewById(R.id.tvBookedTime);
            table = view.findViewById(R.id.tvBookedTable);
            remark = view.findViewById(R.id.remark);
        }
    }
}
