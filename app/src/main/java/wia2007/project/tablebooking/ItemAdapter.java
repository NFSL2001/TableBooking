package wia2007.project.tablebooking;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.Html;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.Table;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    Context context;
    private List<Table> itemList;
    private List<Table> NotAvailable;


    public ItemAdapter(Context context, List<Table> itemList, List<Table> differences) {
        this.context = context;
        this.itemList = itemList;
        NotAvailable = differences;
    }

    @NonNull
    @Override
    public ItemAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_list, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ItemViewHolder holder, int position) {
        holder.tableName.setText(itemList.get(position).getName());
        if (!NotAvailable.isEmpty()) {
            if (NotAvailable.contains(itemList.get(position))) {
                holder.tableName.setTextColor(Color.parseColor("#FFFFFF"));
                holder.CVN1.setCardBackgroundColor(Color.parseColor("#C1121F"));
            }
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView tableName;
        CardView CVN1;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tableName = itemView.findViewById(R.id.N1);
            CVN1 = itemView.findViewById(R.id.CVN1);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            LinearLayout layout = new LinearLayout(context);

            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
            final TextView TName = new TextView(context);
            final TextView TPax = new TextView(context);
            final EditText ETName = new EditText(context);
            final EditText ETPax = new EditText(context);

            Table table = itemList.get(getAdapterPosition());
            TName.setText("Table Name: ");
            TPax.setText("Table Pax: ");
            ETName.setHint("Table Name");
            ETPax.setHint("Table Pax");
            ETName.setText(table.getName());
            ETPax.setText(Integer.toString(table.getSize()));
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.leftMargin = 50; params.rightMargin = 50;
            ETName.setLayoutParams(params);
            ETPax.setLayoutParams(params);

            TName.setTextSize(20f);
            TPax.setTextSize(20f);
            TName.setGravity(1);
            TPax.setGravity(1);
            ETName.setGravity(1);
            ETPax.setGravity(1);
            ETPax.setInputType(InputType.TYPE_CLASS_NUMBER);
            TName.setTextColor(Color.BLACK);
            TPax.setTextColor(Color.BLACK);

            layout.addView(TName);
            layout.addView(ETName);
            layout.addView(TPax);
            layout.addView(ETPax);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle(Html.fromHtml("<font color='#C1121F'>Update Table</font>"));
            alertDialogBuilder.setPositiveButton("yes",
                    new DialogInterface.OnClickListener() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            String name = ETName.getText().toString();
                            String pax = ETPax.getText().toString();


                            if(name != null && pax != null){
                                SharedPreferences sharedPreferences = context.getSharedPreferences("admin", Context.MODE_PRIVATE);
                                int restaurant_id = sharedPreferences.getInt("userID",-1);
                                TableBookingDatabase.getDatabase(context).tableDAO().updateByID(name,Integer.parseInt(pax),table.getTable_id());
                                Intent intent = new Intent(context,table_manage.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                intent.putExtra("RestaurantID",restaurant_id);
                                context.startActivity(intent);
                            }
                        }
                    });

            alertDialogBuilder.setNegativeButton("No", null);
            AlertDialog alertDialog;
            alertDialog = alertDialogBuilder.create();
            alertDialog.setView(layout);
            alertDialog.show();
        }

        @SuppressLint("SetTextI18n")
        @Override
        public boolean onLongClick(View view) {
            LinearLayout layout = new LinearLayout(context);

            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
            final TextView TName = new TextView(context);
            final TextView TPax = new TextView(context);
            Table table = itemList.get(getAdapterPosition());
            TName.setText("Table Name: " + table.getName());
            TPax.setText("Table Pax: " + table.getSize());

            TName.setTextSize(20f);
            TPax.setTextSize(20f);
            TName.setGravity(1);
            TPax.setGravity(1);
            TName.setTextColor(Color.BLACK);
            TPax.setTextColor(Color.BLACK);

            layout.addView(TName);
            layout.addView(TPax);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle(Html.fromHtml("<font color='#C1121F'>Are you sure to delete?</font>"));
            alertDialogBuilder.setPositiveButton("yes",
                    new DialogInterface.OnClickListener() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            SharedPreferences sharedPreferences = context.getSharedPreferences("admin", Context.MODE_PRIVATE);
                            int restaurant_id = sharedPreferences.getInt("userID",-1);
                            TableBookingDatabase.getDatabase(context).tableDAO().deleteById(table.getTable_id());
                            Intent intent = new Intent(context,table_manage.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            intent.putExtra("RestaurantID",restaurant_id);
                            context.startActivity(intent);
                        }
                    });

            alertDialogBuilder.setNegativeButton("No", null);
            AlertDialog alertDialog;
            alertDialog = alertDialogBuilder.create();
            alertDialog.setView(layout);
            alertDialog.show();
            return true;
        }
    }
}
