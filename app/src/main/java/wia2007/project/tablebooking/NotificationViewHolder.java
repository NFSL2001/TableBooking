package wia2007.project.tablebooking;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotificationViewHolder extends RecyclerView.ViewHolder {
    private final TextView notification;
    private NotificationViewHolder(@NonNull View itemView) {
        super(itemView);
        notification = itemView.findViewById(R.id.TVNotificationDesc);
    }

    public void bind(String description) {
        this.notification.setText(Html.fromHtml(description));
    }

    public static NotificationViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.individual_notification_item, parent, false);
        return new NotificationViewHolder(view);
    }
}
