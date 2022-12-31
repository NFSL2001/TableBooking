package wia2007.project.tablebooking;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import wia2007.project.tablebooking.entity.Menu;

public class MenuAdapter extends ListAdapter<Menu,MenuHolder> {
    protected MenuAdapter(@NonNull DiffUtil.ItemCallback<Menu> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public MenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return MenuHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuHolder holder, int position) {
        Menu current = getItem(position);
        holder.bind(current.getMenu_name(),current.getPrice(),current.getPath());//current.getDescription()
    }

    static class NoteDiff extends DiffUtil.ItemCallback<Menu> {
        @Override
        public boolean areItemsTheSame(@NonNull Menu oldItem, @NonNull Menu newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Menu oldItem, @NonNull
                Menu newItem) {
            return oldItem.getMenu_name().equals(newItem.getMenu_name());
        }
    }
}
