package wia2007.project.tablebooking;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import java.sql.Date;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.Booking;

public class BookingAdapter extends ListAdapter<BookingRestaurant, BookingViewHolder> {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public BookingAdapter(@NonNull DiffUtil.ItemCallback<BookingRestaurant> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return BookingViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        BookingRestaurant booking = getItem(position);
        holder.bind(booking.getRestaurant().getRestaurant_name(), dateFormat.format(booking.getStart_time().getTime()));
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(view.getContext(), BookingDetails.class);
//                intent.putExtra("bookingId", booking.getBooking_id());
//                view.getContext().startActivity(intent);
//            }
//        });
    }

    public static class bookDiff extends DiffUtil.ItemCallback<BookingRestaurant> {
        @Override
        public boolean areItemsTheSame(@NonNull BookingRestaurant oldItem, @NonNull BookingRestaurant newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull BookingRestaurant oldItem, @NonNull BookingRestaurant newItem) {
            return oldItem.getRestaurant().getRestaurant_id().equals(newItem.getRestaurant().getRestaurant_id()) &&
                    oldItem.getStart_time().equals(oldItem.getStart_time());
        }
    }
}
