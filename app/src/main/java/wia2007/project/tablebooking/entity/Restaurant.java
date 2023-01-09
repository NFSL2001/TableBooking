package wia2007.project.tablebooking.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Restaurant {
    @PrimaryKey(autoGenerate = true)
    private Integer restaurant_id;
    private String restaurant_user_name;
    private String password;
    private String restaurant_name;
    private String contact_number;
    private Float average_price;
    private String address;
    private String working_hour;
    private String payment;
    private String parking;
    private String dresscode;
    private String accessibility;
    private String website;
    private Integer cuisine_type;
    private String description;
    @NonNull
    private String title_image_path;
    @Nullable
    private String image_path_1;
    @Nullable
    private String image_path_2;
    @Nullable
    private String image_path_3;
    @Nullable
    private String image_path_4;
    @Nullable
    private String image_path_5;

    public Restaurant() {
    }

    @Ignore
    public Restaurant(String restaurant_user_name, String password, String restaurant_name, String contact_number, Float average_price, String address, String working_hour, String payment, String parking, String dresscode, String accessibility, String website, Integer cuisine_type, String description, @NonNull String title_image_path) {
        this.restaurant_user_name = restaurant_user_name;
        this.password = password;
        this.restaurant_name = restaurant_name;
        this.contact_number = contact_number;
        this.average_price = average_price;
        this.address = address;
        this.working_hour = working_hour;
        this.payment = payment;
        this.parking = parking;
        this.dresscode = dresscode;
        this.accessibility = accessibility;
        this.website = website;
        this.cuisine_type = cuisine_type;
        this.description = description;
        this.title_image_path = title_image_path;
    }

    public Integer getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(Integer restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getRestaurant_user_name() {
        return restaurant_user_name;
    }

    public void setRestaurant_user_name(String restaurant_user_name) {
        this.restaurant_user_name = restaurant_user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {this.restaurant_name = restaurant_name;}

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public Float getAverage_price() {
        return average_price;
    }

    public void setAverage_price(Float average_price) {
        this.average_price = average_price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getCuisine_type() {
        return cuisine_type;
    }

    public void setCuisine_type(Integer cuisine_type) {
        this.cuisine_type = cuisine_type;
    }

    @NonNull
    public String getTitle_image_path() {
        return title_image_path;
    }

    public void setTitle_image_path(@NonNull String title_image_path) {
        this.title_image_path = title_image_path;
    }

    @Nullable
    public String getImage_path_1() {
        return image_path_1;
    }

    public void setImage_path_1(@Nullable String image_path_1) {
        this.image_path_1 = image_path_1;
    }

    @Nullable
    public String getImage_path_2() {
        return image_path_2;
    }

    public void setImage_path_2(@Nullable String image_path_2) {
        this.image_path_2 = image_path_2;
    }

    @Nullable
    public String getImage_path_3() {
        return image_path_3;
    }

    public void setImage_path_3(@Nullable String image_path_3) {
        this.image_path_3 = image_path_3;
    }

    @Nullable
    public String getImage_path_4() {
        return image_path_4;
    }

    public void setImage_path_4(@Nullable String image_path_4) {
        this.image_path_4 = image_path_4;
    }

    @Nullable
    public String getImage_path_5() {
        return image_path_5;
    }

    public void setImage_path_5(@Nullable String image_path_5) {
        this.image_path_5 = image_path_5;
    }

    public List<String> getImages(){
        List<String> returnURI = new ArrayList<>();
        returnURI.add(this.title_image_path);
        if(this.image_path_1 == null) return returnURI;
        returnURI.add(this.image_path_1);
        if(this.image_path_2 == null) return returnURI;
        returnURI.add(this.image_path_2);
        if(this.image_path_3 == null) return returnURI;
        returnURI.add(this.image_path_3);
        if(this.image_path_4 == null) return returnURI;
        returnURI.add(this.image_path_4);
        if(this.image_path_5 == null) return returnURI;
        returnURI.add(this.image_path_5);
        return returnURI;
    }

    public void setImages(String path1){
        //add images in order, remove old images
        this.image_path_1 = path1; this.image_path_2 = null; this.image_path_3 = null; this.image_path_4 = null; this.image_path_5 = null;
    }
    public void setImages(String path1, String path2){
        //add images in order, remove old images
        this.image_path_1 = path1; this.image_path_2 = path2; this.image_path_3 = null; this.image_path_4 = null; this.image_path_5 = null;
    }
    public void setImages(String path1, String path2, String path3){
        //add images in order, remove old images
        this.image_path_1 = path1; this.image_path_2 = path2; this.image_path_3 = path3; this.image_path_4 = null; this.image_path_5 = null;
    }
    public void setImages(String path1, String path2, String path3, String path4){
        //add images in order, remove old images
        this.image_path_1 = path1; this.image_path_2 = path2; this.image_path_3 = path3; this.image_path_4 = path4; this.image_path_5 = null;
    }
    public void setImages(String path1, String path2, String path3, String path4, String path5){
        //add images in order, remove old images
        this.image_path_1 = path1; this.image_path_2 = path2; this.image_path_3 = path3; this.image_path_4 = path4; this.image_path_5 = path5;
    }

    public String getWorking_hour() {
        return working_hour;
    }

    public void setWorking_hour(String working_hour) {
        this.working_hour = working_hour;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getParking() {
        return parking;
    }

    public void setParking(String parking) {
        this.parking = parking;
    }

    public String getDresscode() {
        return dresscode;
    }

    public void setDresscode(String dresscode) {this.dresscode = dresscode;}

    public String getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(String accessibility) {
        this.accessibility = accessibility;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    @Override
    public String toString() {
        return "Restaurant{" +
                "restaurant_id=" + restaurant_id +
                ", restaurant_user_name='" + restaurant_user_name + '\'' +
                ", restaurant_name='" + restaurant_name + '\'' +
                ", contact_number='" + contact_number + '\'' +
                ", average_price=" + average_price +
                ", address='" + address + '\'' +
                ", cuisine_type=" + cuisine_type +
                '}';
    }
}
