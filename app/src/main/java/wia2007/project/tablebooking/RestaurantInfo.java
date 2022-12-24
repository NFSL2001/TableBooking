package wia2007.project.tablebooking;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class RestaurantInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_info);

        getSupportActionBar().setTitle("Restaurant Info");

        Spinner spinnerCuisineType = findViewById(R.id.SpinnerCuisineType);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.cuisine_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerCuisineType.setAdapter(adapter);
    }
}