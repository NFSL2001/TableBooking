package wia2007.project.tablebooking;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BookingViewHolder extends RecyclerView.ViewHolder {
    private final TextView restaurantName;
    private final TextView bookingDate;

    private BookingViewHolder(@NonNull View itemView) {
        super(itemView);
        restaurantName = itemView.findViewById(R.id.TVBookingListRestaurantName);
        bookingDate = itemView.findViewById(R.id.TVBookingListBookingDate);
    }

    public void bind(String restaurantName, String bookingDate) {
        this.restaurantName.setText(restaurantName);
        this.bookingDate.setText(bookingDate);
    }

    public static BookingViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.individual_booking_item, parent, false);
        return new BookingViewHolder(view);
    }
}
