package wia2007.project.tablebooking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.invitable.R;

import wia2007.project.tablebooking.entity.Booking;

import java.util.List;


public class SelectTimeButtonAdapter extends RecyclerView.Adapter<SelectTimeButtonAdapter.selectTimeButtonViewHolder>{

    private final LayoutInflater layoutInflater;
    private List<Booking> bookingList;

    public SelectTimeButtonAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public SelectTimeButtonAdapter.selectTimeButtonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.select_time_button_recyclerview, parent, false);
        selectTimeButtonViewHolder viewHolder = new selectTimeButtonViewHolder(itemView);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectTimeButtonAdapter.selectTimeButtonViewHolder holder, int position) {
        holder.Time.setText(0);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class selectTimeButtonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Button Time;

        public selectTimeButtonViewHolder(@NonNull View itemView) {
            super(itemView);

            Time = itemView.findViewById(R.id.select_time_tButton);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
