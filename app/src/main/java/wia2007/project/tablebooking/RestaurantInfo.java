package wia2007.project.tablebooking;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import wia2007.project.tablebooking.dao.RestaurantDAO;
import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.Cuisine;
import wia2007.project.tablebooking.entity.Restaurant;

public class RestaurantInfo extends AppCompatActivity {

    EditText name, address, contact, averagePrice, description, parking, dressCode, Accessibility, payment, operation_Hour, website;
    EditText ETTitleImage, ETImg1, ETImg2, ETImg3, ETImg4, ETImg5;
    ImageView IVTitleImage, IVImg1, IVImg2, IVImg3, IVImg4, IVImg5;
    Spinner cuisineType;
    Button save, cancel;
    boolean titleValid = false, Img1Valid = false, Img2Valid = false, Img3Valid = false, Img4Valid = false, Img5Valid = false;
    boolean temp;
    LinearLayout LLImg2, LLImg3, LLImg4, LLImg5;
    List<Boolean> validity = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_info);

        Toolbar toolbar = findViewById(R.id.toolbarResInfo);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Restaurant Info");

        int restaurant_id = getIntent().getExtras().getInt("RestaurantID");

        name = findViewById(R.id.FillName);
        address = findViewById(R.id.fillAdd);
        contact = findViewById(R.id.fillContact);
        averagePrice = findViewById(R.id.FillAverageP);
        cuisineType = findViewById(R.id.fillCuisineT);
        description = findViewById(R.id.fillDes);
        save = findViewById(R.id.saveButton);
        cancel = findViewById(R.id.cancelButton);
        parking = findViewById(R.id.ETParking);
        dressCode = findViewById(R.id.ETDressCode);
        Accessibility = findViewById(R.id.ETAccessibility);
        payment = findViewById(R.id.ETPayment);
        website = findViewById(R.id.fillWebsite);
        operation_Hour = findViewById(R.id.FillOperationHour);
        ETTitleImage = findViewById(R.id.ETURLTitle);
        ETImg1 = findViewById(R.id.ETURLImg1);
        ETImg2 = findViewById(R.id.ETURLImg2);
        ETImg3 = findViewById(R.id.ETURLImg3);
        ETImg4 = findViewById(R.id.ETURLImg4);
        ETImg5 = findViewById(R.id.ETURLImg5);
        IVTitleImage = findViewById(R.id.IVTitleImg);
        IVImg1 = findViewById(R.id.IVImg1);
        IVImg2 = findViewById(R.id.IVImg2);
        IVImg3 = findViewById(R.id.IVImg3);
        IVImg4 = findViewById(R.id.IVImg4);
        IVImg5 = findViewById(R.id.IVImg5);
        LLImg2 = findViewById(R.id.LLImg2);
        LLImg3 = findViewById(R.id.LLImg3);
        LLImg4 = findViewById(R.id.LLImg4);
        LLImg5 = findViewById(R.id.LLImg5);

        //dropdown menu for cuisine type
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cuisine, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        cuisineType.setAdapter(adapter);

        TableBookingDatabase database = TableBookingDatabase.getDatabase(getApplicationContext());
        RestaurantDAO restaurantDAO = database.restaurantDAO();
        List<Restaurant> info = restaurantDAO.getRestaurantById(restaurant_id);

        String type = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            checkForNull(name, Optional.ofNullable(info.get(0).getRestaurant_name()));
            checkForNull(address, Optional.ofNullable(info.get(0).getAddress()));
            checkForNull(contact, Optional.ofNullable(info.get(0).getContact_number()));
            checkForNull(averagePrice, Optional.of(Optional.ofNullable(info.get(0).getAverage_price()).toString()));
            if (averagePrice.getText().toString().contains("Optional"))
                averagePrice.setText(averagePrice.getText().toString().substring(9, averagePrice.getText().toString().length() - 1));
            checkForNull(description, Optional.ofNullable(info.get(0).getDescription()));
            checkForNull(operation_Hour, Optional.ofNullable(info.get(0).getWorking_hour()));
            checkForNull(website, Optional.ofNullable(info.get(0).getWebsite()));
            checkForNull(payment, Optional.ofNullable(info.get(0).getPayment()));
            checkForNull(Accessibility, Optional.ofNullable(info.get(0).getAccessibility()));
            checkForNull(dressCode, Optional.ofNullable(info.get(0).getDresscode()));
            if (!Optional.ofNullable(info.get(0).getCuisine_type()).equals("Optional.empty")) {
                type = Optional.ofNullable(Cuisine.getCuisineNameList().get(info.get(0).getCuisine_type())).get();
            }
            if (!type.equalsIgnoreCase("Optional.empty")) {
                cuisineType.setSelection(info.get(0).getCuisine_type() - 1);
            }

            String titleImagePath = checkForNullString(Optional.ofNullable(info.get(0).getTitle_image_path()));
            String imagePath1 = checkForNullString(Optional.ofNullable(info.get(0).getImage_path_1()));
            String imagePath2 = checkForNullString(Optional.ofNullable(info.get(0).getImage_path_2()));
            String imagePath3 = checkForNullString(Optional.ofNullable(info.get(0).getImage_path_3()));
            String imagePath4 = checkForNullString(Optional.ofNullable(info.get(0).getImage_path_4()));
            String imagePath5 = checkForNullString(Optional.ofNullable(info.get(0).getImage_path_5()));
            if (!titleImagePath.equals("")) {
                IVTitleImage.setVisibility(View.VISIBLE);
                ETTitleImage.setText(titleImagePath);
                Picasso.get().load(titleImagePath)
                        .placeholder(android.R.drawable.ic_menu_gallery)
                        .into(IVTitleImage);
                titleValid = true;
            } else {
                IVTitleImage.setVisibility(View.GONE);
                ETTitleImage.setText(titleImagePath);
            }
            if (!imagePath1.equals("")) {
                IVImg1.setVisibility(View.VISIBLE);
                ETImg1.setText(imagePath1);
                Img1Valid = true;
                Picasso.get().load(imagePath1)
                        .placeholder(android.R.drawable.ic_menu_gallery)
                        .into(IVImg1);
                if (!imagePath2.equals("")) {
                    IVImg2.setVisibility(View.VISIBLE);
                    ETImg2.setText(imagePath2);
                    Img2Valid = true;
                    Picasso.get().load(imagePath2)
                            .placeholder(android.R.drawable.ic_menu_gallery)
                            .into(IVImg2);
                    if (!imagePath3.equals("")) {
                        IVImg3.setVisibility(View.VISIBLE);
                        ETImg3.setText(imagePath3);
                        Picasso.get().load(imagePath3)
                                .placeholder(android.R.drawable.ic_menu_gallery)
                                .into(IVImg3);
                        Img3Valid = true;
                        if (!imagePath4.equals("")) {
                            IVImg4.setVisibility(View.VISIBLE);
                            ETImg4.setText(imagePath4);
                            Picasso.get().load(imagePath4)
                                    .placeholder(android.R.drawable.ic_menu_gallery)
                                    .into(IVImg4);
                            Img4Valid = true;
                            if (!imagePath5.equals("")) {
                                IVImg5.setVisibility(View.VISIBLE);
                                ETImg5.setText(imagePath5);
                                Picasso.get().load(imagePath5)
                                        .placeholder(android.R.drawable.ic_menu_gallery)
                                        .into(IVImg5);
                                Img5Valid = true;
                            } else {
                                IVImg5.setVisibility(View.GONE);
                                ETImg5.setText(imagePath5);
                            }
                        } else {
                            LLImg5.setVisibility(View.GONE);
                            IVImg4.setVisibility(View.GONE);
                            ETImg4.setText(imagePath4);
                        }
                    } else {
                        LLImg4.setVisibility(View.GONE);
                        LLImg5.setVisibility(View.GONE);
                        IVImg3.setVisibility(View.GONE);
                        ETImg3.setText(imagePath3);
                    }
                } else {
                    LLImg3.setVisibility(View.GONE);
                    LLImg4.setVisibility(View.GONE);
                    LLImg5.setVisibility(View.GONE);
                    IVImg2.setVisibility(View.GONE);
                    ETImg2.setText(imagePath2);
                }
            } else {
                LLImg2.setVisibility(View.GONE);
                LLImg3.setVisibility(View.GONE);
                LLImg4.setVisibility(View.GONE);
                LLImg5.setVisibility(View.GONE);
                IVImg1.setVisibility(View.GONE);
                ETImg1.setText(imagePath1);
            }
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String NAME = name.getText().toString();
                String ADDRESS = address.getText().toString();
                String CONTACT = contact.getText().toString();
                float PRICE = Float.parseFloat(averagePrice.getText().toString());
                String DESCRIPTION = description.getText().toString();
                int CUISINE_TYPE = cuisineType.getSelectedItemPosition() + 1;
                String WORKING_HOUR = operation_Hour.getText().toString();
                String PAYMENT = payment.getText().toString();
                String PARKING = parking.getText().toString();
                String DRESS_CODE = dressCode.getText().toString();
                String ACCESSIBILITY = Accessibility.getText().toString();
                String WEBSITE = website.getText().toString();
                validity.add(Img1Valid);
                validity.add(Img2Valid);
                validity.add(Img3Valid);
                validity.add(Img4Valid);
                validity.add(Img5Valid);
                boolean wrong = false;
                for (int i = 0; i < validity.size() - 1; i++) {
                    boolean v1 = validity.get(i);
                    boolean v2 = validity.get(i + 1);
                    if (!v1 && v2) {
                        wrong = true;
                        break;
                    }
                }

                String Img1 = "", Img2 = "", Img3 = "", Img4 = "", Img5 = "";
                if (Img1Valid) {
                    Img1 = ETImg1.getText().toString();
                    if (Img2Valid) {
                        Img2 = ETImg2.getText().toString();
                        if (Img3Valid) {
                            Img3 = ETImg3.getText().toString();
                            if (Img4Valid) {
                                Img4 = ETImg4.getText().toString();
                                if (Img5Valid)
                                    Img5 = ETImg5.getText().toString();
                            }
                        }
                    }
                }

                if (NAME.isEmpty())
                    Toast.makeText(getApplicationContext(), "Name cannot be Empty", Toast.LENGTH_SHORT).show();
                else if (ADDRESS.isEmpty())
                    Toast.makeText(getApplicationContext(), "Address cannot be Empty", Toast.LENGTH_SHORT).show();
                else if (wrong)
                    Toast.makeText(getApplicationContext(), "Valid Image Path Not Following Order", Toast.LENGTH_SHORT).show();
                else if (!titleValid)
                    Toast.makeText(getApplicationContext(), "Title Image is Empty/Not Valid", Toast.LENGTH_SHORT).show();
                else {
                    String titlePath = ETTitleImage.getText().toString();
                    TableBookingDatabase.getDatabase(getApplicationContext()).restaurantDAO().updateRestaurantInfo(restaurant_id, NAME, CONTACT, PRICE, ADDRESS, WORKING_HOUR, PAYMENT, PARKING, DRESS_CODE, ACCESSIBILITY, WEBSITE, CUISINE_TYPE, DESCRIPTION, titlePath, Img1, Img2, Img3, Img4, Img5);
                    finish();
                }
                validity.clear();

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ETTitleImage.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    titleValid = validImage(s, IVTitleImage);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (titleValid) {
                    Picasso.get().load(s.toString())
                            .placeholder(android.R.drawable.ic_menu_gallery)
                            .into(IVTitleImage);
                    IVTitleImage.setVisibility(View.VISIBLE);
                } else
                    IVTitleImage.setVisibility(View.GONE);
            }
        });

        ETImg1.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    Img1Valid = validImage(s, IVImg1);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (Img1Valid) {
                    Picasso.get().load(s.toString())
                            .placeholder(android.R.drawable.ic_menu_gallery)
                            .into(IVImg1);
                    IVImg1.setVisibility(View.VISIBLE);
                } else
                    IVImg1.setVisibility(View.GONE);
            }
        });

        ETImg2.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    Img2Valid = validImage(s, IVImg2);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (Img2Valid) {
                    Picasso.get().load(s.toString())
                            .placeholder(android.R.drawable.ic_menu_gallery)
                            .into(IVImg2);
                    IVImg2.setVisibility(View.VISIBLE);
                    LLImg3.setVisibility(View.VISIBLE);
                } else
                    IVImg2.setVisibility(View.GONE);
            }
        });

        ETImg3.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    Img3Valid = validImage(s, IVImg3);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (Img3Valid) {
                    Picasso.get().load(s.toString())
                            .placeholder(android.R.drawable.ic_menu_gallery)
                            .into(IVImg3);
                    IVImg3.setVisibility(View.VISIBLE);
                    LLImg4.setVisibility(View.VISIBLE);
                } else
                    IVImg3.setVisibility(View.GONE);
            }
        });

        ETImg4.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    Img4Valid = validImage(s, IVImg4);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (Img4Valid) {
                    Picasso.get().load(s.toString())
                            .placeholder(android.R.drawable.ic_menu_gallery)
                            .into(IVImg4);
                    IVImg4.setVisibility(View.VISIBLE);
                    LLImg5.setVisibility(View.VISIBLE);
                } else
                    IVImg4.setVisibility(View.GONE);
            }
        });

        ETImg5.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    Img5Valid = validImage(s, IVImg5);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (Img5Valid) {
                    Picasso.get().load(s.toString())
                            .placeholder(android.R.drawable.ic_menu_gallery)
                            .into(IVImg5);
                    IVImg5.setVisibility(View.VISIBLE);
                } else
                    IVImg5.setVisibility(View.GONE);
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

    private void checkForNull(EditText view, Optional<String> optional) {
        if (!optional.toString().equalsIgnoreCase("Optional.empty"))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                view.setText(optional.get());
            }
    }

    private String checkForNullString(Optional<String> optional) {
        if (!optional.toString().equalsIgnoreCase("Optional.empty"))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return optional.get();
            }
        return "";
    }

    private boolean validImage(Editable s, ImageView imageView) throws ExecutionException, InterruptedException {
        String p = s.toString();
        temp = false;
        if (!p.equals("")) {
            Picasso.get().load(p)
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .into(imageView);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();
                new Thread(new Runnable() { // if already doing the checking on network thread then no need to add this thread
                    @Override
                    public void run() {
                        try {
                            URLConnection connection = new URL(p).openConnection();
                            String contentType = connection.getHeaderField("Content-Type");
                            if (contentType == null)
                                temp = false;
                            else {
                                temp = contentType.startsWith("image/"); //true if image
                            }
                            completableFuture.complete(temp);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                temp = completableFuture.get();
            }
        }
        return temp;
    }
}