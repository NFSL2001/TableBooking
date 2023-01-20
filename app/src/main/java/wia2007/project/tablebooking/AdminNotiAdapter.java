//package wia2007.project.tablebooking;
//
//import android.content.Context;
//import android.text.Html;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.sql.Time;
//import java.text.SimpleDateFormat;
//import java.util.List;
//
//public class AdminNotiAdapter extends RecyclerView.Adapter<AdminNotiAdapter.AdminNotiViewHolder> {
//
//    private List<ShowBookingsList> bookingList;
//    private Context mContext;
//
//    public AdminNotiAdapter(Context context, List<ShowBookingsList> bookingList) {
//        this.bookingList = bookingList;
//        mContext = context;
//    }
//
//    @NonNull
//    @Override
//    public AdminNotiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.individual_notification_item, parent, false);
//        return new AdminNotiViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull AdminNotiViewHolder holder, int position) {
//        Time startTime = bookingList.get(position).getStart_time();
//        Time endTime = bookingList.get(position).getEnd_time();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//        String start_time = simpleDateFormat.format(startTime);
//        String end_time = simpleDateFormat.format(endTime);
//        String time_interval = start_time + "-" + end_time.substring(11);
//        String notification = "<b>"+bookingList.get(position).getCustName()+"</b>"+" make a booking on <b>"+time_interval+"</b><br>(Table: "+bookingList.get(position).getTable_name()+")";
//        holder.TVNotificationDesc.setText(Html.fromHtml(notification));
//    }
//
//    @Override
//    public int getItemCount() {
//        return bookingList.size();
//    }
//
//    public class AdminNotiViewHolder extends RecyclerView.ViewHolder {
//        private TextView TVNotificationDesc;
//
//        public AdminNotiViewHolder(View view) {
//            super(view);
//            TVNotificationDesc = view.findViewById(R.id.TVNotificationDesc);
//        }
//    }
//}
