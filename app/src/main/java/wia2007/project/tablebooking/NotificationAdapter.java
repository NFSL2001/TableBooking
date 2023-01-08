package wia2007.project.tablebooking;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import wia2007.project.tablebooking.entity.Notification;

public class NotificationAdapter extends ListAdapter<Notification, NotificationViewHolder> {
    public NotificationAdapter(@NonNull DiffUtil.ItemCallback<Notification> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return NotificationViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification notification = getItem(position);
        holder.bind(notification.getNotification());
    }

    public static class notificationDiff extends DiffUtil.ItemCallback<Notification> {
        @Override
        public boolean areItemsTheSame(@NonNull Notification oldItem, @NonNull Notification newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Notification oldItem, @NonNull Notification newItem) {
            return oldItem.getCustomer_id().equals(newItem.getCustomer_id()) &&
                    oldItem.getNotification().equals(newItem.getNotification());
        }
    }
}
