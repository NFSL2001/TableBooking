package wia2007.project.tablebooking.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity
public class Customer {
    public static final int GENDER_MALE = 0;
    public static final int GENDER_FEMALE = 1;
    public static final int GENDER_OTHER = 2;

    @PrimaryKey(autoGenerate = true)
    private Integer customer_id;
    private String user_name;
    private String password;
    private String mobile_number;
    private String email;
    private Integer gender;
    private Date birth_date;

    public Customer() {
    }

    public Customer(Integer customer_id, String user_name, String password, String mobile_number, String email, Integer gender, Date birth_date) {
        this.customer_id = customer_id;
        this.user_name = user_name;
        this.password = password;
        this.mobile_number = mobile_number;
        this.email = email;
        this.gender = gender;
        this.birth_date = birth_date;
    }

    public Integer getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Integer customer_id) {
        this.customer_id = customer_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customer_id=" + customer_id +
                ", user_name='" + user_name + '\'' +
                ", mobile_number='" + mobile_number + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + ((gender == GENDER_MALE)?"male": ((gender == GENDER_FEMALE)?"female":"other")) + '\'' +
                ", birth_date=" + birth_date +
                '}';
    }
}
