package wia2007.project.tablebooking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

import wia2007.project.tablebooking.dao.RestaurantDAO;
import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.Restaurant;

public class RestaurantInfo extends AppCompatActivity {

    EditText name, address, contact, averagePrice, description;
    Spinner cuisineType;
    Button save, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_info);

        Toolbar toolbar = findViewById(R.id.toolbarResInfo);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Restaurant Info");

        name = findViewById(R.id.FillName);
        address = findViewById(R.id.fillAdd);
        contact = findViewById(R.id.fillContact);
        averagePrice = findViewById(R.id.FillAverageP);
        cuisineType = findViewById(R.id.fillCuisineT);
        description = findViewById(R.id.fillDes);
        save = findViewById(R.id.saveButton);
        cancel = findViewById(R.id.cancelButton);

        //dropdown menu for cuisine type
        ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(this, R.array.cuisine, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        cuisineType.setAdapter(adapter);

        //save data
        //SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        //String resname = sharedPref.getString("user", null);

        TableBookingDatabase database = TableBookingDatabase.getDatabase(getApplicationContext());
        RestaurantDAO restaurantDAO = database.restaurantDAO();
        //List<Restaurant> restaurantList = restaurantDAO.getRestaurantByRestaurantUserName(resname);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String NAME = name.getText().toString();
                String ADDRESS = address.getText().toString();
                String  CONTACT = contact.getText().toString();
                float  PRICE = Float.parseFloat(averagePrice.getText().toString());
                String DESCRIPTION = description.getText().toString();
                int CUISINETYPE=0;
                switch(cuisineType.getAdapter().toString()){
                    case "MALAYSIAN":
                        CUISINETYPE = 1;
                        break;
                    case "Dinner Buffet":
                        CUISINETYPE = 2;
                        break;
                    case "Japanese":
                        CUISINETYPE = 3;
                        break;
                    case "Indian":
                        CUISINETYPE = 4;
                        break;
                    case "High Tea Buffet":
                        CUISINETYPE = 5;
                        break;
                    case "Chinese":
                        CUISINETYPE = 6;
                        break;
                    case "Western":
                        CUISINETYPE = 7;
                        break;
                    case "Thai":
                        CUISINETYPE = 8;
                        break;
                }

                Restaurant restaurant = null;

                /*if(restaurantList.size() == 0){
                    throw new RuntimeException("No Restaurant Register");
                }
                int size = restaurantList.size();
                while(size != 0) {
                    if (restaurantList.get(size).getRestaurant_name().equals(resname)) {
                        restaurant = restaurantList.get(size);
                    }
                    size--;
                }*/

                restaurant.setRestaurant_name(NAME);
                restaurant.setAddress(ADDRESS);
                restaurant.setContact_number(CONTACT);
                restaurant.setAverage_price(PRICE);
                restaurant.setCuisine_type(CUISINETYPE);
                restaurant.setDescription(DESCRIPTION);
                restaurantDAO.updateRestaurants(restaurant);

                Intent intent = new Intent(RestaurantInfo.this, MainActivity.class);
                startActivity(intent);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}