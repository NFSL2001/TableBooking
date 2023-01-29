package wia2007.project.tablebooking;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.MenuItem;

public class ItemDetails extends AppCompatActivity {
    int SELECT_PICTURE = 200;
    ImageView menuPic;
    EditText menuName;
    EditText menuDescription;
    EditText menuPrice;
    EditText ETURL;
    String itemId;
    Button BtnDeleteItemPic;
    AlertDialog alertDialog;
    EditText edittext;
    boolean image=false;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        Toolbar toolbar = findViewById(R.id.TVItemDetailsAct);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Menu Item Details");

        menuName = findViewById(R.id.ETItemName);
        menuDescription = findViewById(R.id.ETItemDescription);
        menuPrice = findViewById(R.id.ETItemPrice);
        Button BtnSaveItemDetails = findViewById(R.id.BtnSaveItemDetails);
        Button BtnCancelItemDetails = findViewById(R.id.BtnCancelItemDetails);
        BtnDeleteItemPic = findViewById(R.id.btnDeleteItemPic);
        menuPic = findViewById(R.id.IVItemPic);
        ETURL = findViewById(R.id.ETURL);

        Spinner SpinnerMenuType = findViewById(R.id.SpinnerMenuType);
        String[] arr = getResources().getStringArray(R.array.SpinnerForItemDetail);
        List<String> arrList = Arrays.asList(arr);
        List<String> adapterList = new ArrayList<>(arrList);
        arr = adapterList.toArray(new String[0]);
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        SpinnerMenuType.setAdapter(adapter);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ItemDetails.this);
        alertDialogBuilder.setMessage("Add new type of dishes");
        edittext = new EditText(getApplicationContext());
        edittext.setHint("Other Category");
        int nightModeFlag = this.getResources().getConfiguration().uiMode &
                Configuration.UI_MODE_NIGHT_MASK;
        if(nightModeFlag == Configuration.UI_MODE_NIGHT_YES){
            edittext.setTextColor(Color.WHITE);
            edittext.setHintTextColor(Color.parseColor("#50FFFFFF"));
        }
        alertDialogBuilder.setView(edittext, 50, 0, 50, 0);
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        adapterList.add(edittext.getText().toString());
                        adapter.notifyDataSetChanged();
                        String[] array = adapterList.toArray(new String[0]);
                        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, array);
                        SpinnerMenuType.setAdapter(adapter);
                        SpinnerMenuType.setSelection(array.length - 1);
                    }
                });
        alertDialogBuilder.setNegativeButton("No", null);
        alertDialog = alertDialogBuilder.create();

        String itemName = getIntent().getStringExtra("ItemName");
        if (itemName != null)
            getSupportActionBar().setTitle(itemName);
        String itemDescription = getIntent().getStringExtra("ItemDescription");
        String itemPrice = getIntent().getStringExtra("ItemPrice");
        itemId = getIntent().getStringExtra("ItemId");
        String path = getIntent().getStringExtra("ItemImage");
        String type = getIntent().getStringExtra("ItemType");
        int restaurant_id = getIntent().getIntExtra("RestaurantID",-1);

        if (path != null) {
            ETURL.setText(path);
            if(!path.isEmpty()){
                Picasso.get().load(path)
                        .placeholder(android.R.drawable.ic_menu_gallery)
                        .into(menuPic);
            }
//            menuPic.setImageURI(Uri.fromFile(img));
        } else {
            menuPic.setVisibility(View.INVISIBLE);
        }

        menuName.setText(itemName);
        menuDescription.setText(itemDescription);
        if ("0.0".equals(itemPrice)) {
            menuPrice.setText("");
        } else {
            menuPrice.setText(itemPrice);
        }
        int selectionPosition = adapter.getPosition(type);
        SpinnerMenuType.setSelection(selectionPosition);
        if (selectionPosition == -1) {
            if (itemId != null || type != null) {
                edittext.setText(type);
                SpinnerMenuType.setSelection(adapterList.size() - 1);
                alertDialog.show();
                alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.parseColor("#C1121F"));
                alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#C1121F"));
                alertDialog.dismiss();
                alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).performClick();
            }
        }

        ETURL.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void afterTextChanged(Editable s) {
                String p = s.toString();
                if(!p.equals("")){
                    Picasso.get().load(p)
                            .placeholder(android.R.drawable.ic_menu_gallery)
                            .into(menuPic);
                    new Thread(new Runnable() { // if already doing the checking on network thread then no need to add this thread
                        @Override
                        public void run() {
                            try {
                                URLConnection connection = new URL(p).openConnection();
                                String contentType = connection.getHeaderField("Content-Type");
                                if(contentType == null)
                                    image = false;
                                else
                                    image = contentType.startsWith("image/"); //true if image
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        });

        SpinnerMenuType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(nightModeFlag == Configuration.UI_MODE_NIGHT_YES){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(0xFFFFFFFF);
                }
                if ("Others".equals(SpinnerMenuType.getSelectedItem().toString())) {
                    alertDialog.show();
                    alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.parseColor("#C1121F"));
                    alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#C1121F"));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        BtnInsertItemPic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                imageChooser();
//            }
//        });

        BtnDeleteItemPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuPic.setImageResource(0);
                ETURL.setText("");
            }
        });

        BtnSaveItemDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = menuName.getText().toString();
                String description = menuDescription.getText().toString();
                String priceStr = menuPrice.getText().toString();
                float price = 0f;
                if (!priceStr.isEmpty()) {
                    price = Float.parseFloat(priceStr);
                }
                String itemType = SpinnerMenuType.getSelectedItem().toString();
                if (name.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Name cannot be empty", Toast.LENGTH_LONG).show();
                    return;
                }
                String path = ETURL.getText().toString();
                if (image) {
//                    String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
//                    String imageName = itemName + "_" + timeStamp + ".jpg";
//
//                    Bitmap fileContents = ((BitmapDrawable) menuPic.getDrawable()).getBitmap();
//
//                    ContextWrapper wrapper = new ContextWrapper(getApplicationContext());
//
//                    File mediaFile = wrapper.getDir("Images", MODE_PRIVATE);
//
//                    mediaFile = new File(mediaFile + File.pathSeparator + imageName);
//
//                    try {
//                        OutputStream stream = null;
//                        stream = new FileOutputStream(mediaFile);
//                        fileContents.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//                        stream.flush();
//                        stream.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                    Uri mImageUri = Uri.parse(mediaFile.getAbsolutePath());

                    if (itemId != null) {
                        TableBookingDatabase.getDatabase(getApplicationContext()).menuDAO().updateMenuItem(name, description, itemType, price, path, Integer.parseInt(itemId));
                    } else {
                        TableBookingDatabase.getDatabase(getApplicationContext()).menuDAO().insertMenus(new MenuItem(name, price, description, restaurant_id, itemType, path));

                    }
                } else {
                    if (itemId != null) {
                        TableBookingDatabase.getDatabase(getApplicationContext()).menuDAO().updateMenuItem(name, description, itemType, price, "", Integer.parseInt(itemId));
                    } else {
                        TableBookingDatabase.getDatabase(getApplicationContext()).menuDAO().insertMenus(new MenuItem(name, price, description, restaurant_id, itemType, ""));

                    }
                }

                Intent intent = new Intent(ItemDetails.this, MenuAdmin.class);
                intent.putExtra("RestaurantID", getIntent().getExtras().getInt("RestaurantID"));
                startActivity(intent);
            }
        });

        BtnCancelItemDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

//    void imageChooser() {
//        Intent i = new Intent();
//        i.setType("image/*");
//        i.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
//    }

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            if (requestCode == SELECT_PICTURE) {
//                Uri selectedImageUri = data.getData();
//                if (null != selectedImageUri) {
//                    BtnInsertItemPic.setText("Change Image");
//                    menuPic.setVisibility(View.VISIBLE);
//                    menuPic.setImageURI(selectedImageUri);
//                }
//            }
//        }
//    }

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