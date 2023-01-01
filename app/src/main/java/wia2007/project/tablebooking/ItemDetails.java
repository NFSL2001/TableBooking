package wia2007.project.tablebooking;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.Menu;

public class ItemDetails extends AppCompatActivity {
    int SELECT_PICTURE = 200;
    ImageView menuPic;
    EditText menuName;
    EditText menuDescription;
    EditText menuPrice;
    String itemId;
    Button BtnInsertItemPic;
    Button BtnDeleteItemPic;

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
        BtnInsertItemPic = findViewById(R.id.btnInsertItemPic);
        BtnDeleteItemPic = findViewById(R.id.btnDeleteItemPic);
        menuPic = findViewById(R.id.IVItemPic);

        Spinner SpinnerMenuType = findViewById(R.id.SpinnerMenuType);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.SpinnerForItemDetail, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        SpinnerMenuType.setAdapter(adapter);

        String itemName = getIntent().getStringExtra("ItemName");
        if(itemName != null)
            getSupportActionBar().setTitle(itemName);
        String itemDescription = getIntent().getStringExtra("ItemDescription");
        String itemPrice = getIntent().getStringExtra("ItemPrice");
        itemId = getIntent().getStringExtra("ItemId");
        String path = getIntent().getStringExtra("ItemImage");
        String type = getIntent().getStringExtra("ItemType");
        if(path != null){
            File img = new File(path);
            BtnInsertItemPic.setText("Change Image");
            menuPic.setImageURI(Uri.fromFile(img));
        }else{
            menuPic.setVisibility(View.INVISIBLE);
        }

        menuName.setText(itemName);
        menuDescription.setText(itemDescription);
        menuPrice.setText(itemPrice);
        int selectionPosition= adapter.getPosition(type);
        SpinnerMenuType.setSelection(selectionPosition);
        BtnInsertItemPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });

        BtnDeleteItemPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuPic.setImageResource(0);
                BtnInsertItemPic.setText("Upload Image");
            }
        });

        BtnSaveItemDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = menuName.getText().toString();
                String description = menuDescription.getText().toString();
                String priceStr = menuPrice.getText().toString();
                float price = -1;
                if(!priceStr.isEmpty()){
                    price = Float.parseFloat(priceStr);
                }
                String itemType = SpinnerMenuType.getSelectedItem().toString();
                if(name.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Name cannot be empty",Toast.LENGTH_LONG).show();
                    return;
                }

                if (menuPic.getDrawable() != null) {
                    String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
                    String imageName = itemName + "_" + timeStamp + ".jpg";

                    Bitmap fileContents = ((BitmapDrawable) menuPic.getDrawable()).getBitmap();

                    ContextWrapper wrapper = new ContextWrapper(getApplicationContext());

                    File mediaFile = wrapper.getDir("Images",MODE_PRIVATE);

                    mediaFile = new File(mediaFile + File.pathSeparator + imageName);

                    try{
                        OutputStream stream = null;
                        stream = new FileOutputStream(mediaFile);
                        fileContents.compress(Bitmap.CompressFormat.JPEG,100,stream);
                        stream.flush();
                        stream.close();
                    }catch (IOException e)
                    {
                        e.printStackTrace();
                    }

                    Uri mImageUri = Uri.parse(mediaFile.getAbsolutePath());

                    if (itemId != null) {
                        TableBookingDatabase.getDatabase(getApplicationContext()).menuDAO().updateMenuItem(name, description,itemType,price,mImageUri.toString(),Integer.parseInt(itemId));
                    } else {
                        TableBookingDatabase.getDatabase(getApplicationContext()).menuDAO().insertMenus(new Menu(name, price, description, 1, mImageUri.toString()));

                    }
                } else {
                    if (itemId != null) {
                        TableBookingDatabase.getDatabase(getApplicationContext()).menuDAO().updateMenuItem(name, description,itemType,price,null,Integer.parseInt(itemId));
                    } else {
                        TableBookingDatabase.getDatabase(getApplicationContext()).menuDAO().insertMenus(new Menu(name, price, description, 1));

                    }
                }


                startActivity(new Intent(ItemDetails.this, MenuAdmin.class));
            }
        });

        BtnCancelItemDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    BtnInsertItemPic.setText("Change Image");
                    menuPic.setVisibility(View.VISIBLE);
                    menuPic.setImageURI(selectedImageUri);
                }
            }
        }
    }
}