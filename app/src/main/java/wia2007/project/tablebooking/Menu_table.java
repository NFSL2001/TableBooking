//package wia2007.project.tablebooking;
//
//import android.graphics.Bitmap;
//
//import androidx.room.ColumnInfo;
//import androidx.room.Entity;
//import androidx.room.ForeignKey;
//import androidx.room.PrimaryKey;
//
//import java.io.Serializable;
//
//@Entity(foreignKeys = {@ForeignKey(entity = Restaurant_table.class,parentColumns = "Restaurant_id",childColumns = "Restaurant_id",onDelete = ForeignKey.CASCADE)})
//public class Menu_table implements Serializable {
//    @PrimaryKey(autoGenerate = true)
//    public int Menu_id;
//    @ColumnInfo
//    public String Menu_name;
//    @ColumnInfo
//    public int Price;
//    @ColumnInfo
//    public int Restaurant_id;
//    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
//    private byte[] Image;
//
//
//    public Menu_table() {
//    }
//
//    public int getMenu_id() {
//        return Menu_id;
//    }
//
//    public void setMenu_id(int menu_id) {
//        Menu_id = menu_id;
//    }
//
//    public String getMenu_name() {
//        return Menu_name;
//    }
//
//    public void setMenu_name(String menu_name) {
//        Menu_name = menu_name;
//    }
//
//    public int getPrice() {
//        return Price;
//    }
//
//    public void setPrice(int price) {
//        Price = price;
//    }
//
//    public int getRestaurant_id() {
//        return Restaurant_id;
//    }
//
//    public void setRestaurant_id(int restaurant_id) {
//        Restaurant_id = restaurant_id;
//    }
//
//    public byte[] getImage() {
//        return Image;
//    }
//
//    public void setImage(byte[] image) {
//        Image = image;
//    }
//}
