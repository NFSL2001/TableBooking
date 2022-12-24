package wia2007.project.tablebooking;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BookingsAdapter extends ArrayAdapter {
    List list = new ArrayList();
    public BookingsAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public void add(Bookings bookings) {
        list.add(bookings);
        super.add(bookings);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    static class BookingsHolder{

    }
}
