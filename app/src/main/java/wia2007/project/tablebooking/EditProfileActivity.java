package wia2007.project.tablebooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.sql.Date;
import java.util.List;

import wia2007.project.tablebooking.dao.CustomerDAO;
import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.Customer;
import wia2007.project.tablebooking.entity.PasswordChecker;

public class EditProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.TBEditProfileAct);
        setSupportActionBar(toolbar);
        // add back button
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Edit Profile");

        SharedPreferences sharedPref = getSharedPreferences("user", Context.MODE_PRIVATE);
        String username = sharedPref.getString("user", null);
        if(username == null){
            throw new RuntimeException("User don't login");
        }

        TableBookingDatabase database = TableBookingDatabase.getDatabase(getApplicationContext());
        CustomerDAO customerDAO = database.customerDAO();
        List<Customer> customerList = customerDAO.getCustomerByUsername(username);
        if(customerList.size() != 1) {
            throw new RuntimeException("Incorrect username being store");
        }
        Customer customer = customerList.get(0);

        ((EditText) findViewById(R.id.ETEditProfileName)).setText(customer.getName());
        ((EditText) findViewById(R.id.ETEditProfilePhone)).setText(customer.getMobile_number());
        ((EditText) findViewById(R.id.ETEditProfileEmail)).setText(customer.getEmail());
        ((EditText) findViewById(R.id.ETEditProfileBirthDate)).setText(customer.getBirth_date().toString());
        if(customer.getGender() == Customer.GENDER_MALE)
            ((RadioButton) findViewById(R.id.RBtnEditProfileMale)).setChecked(true);
        else
            ((RadioButton) findViewById(R.id.RBtnEditProfileFemale)).setChecked(true);


        findViewById(R.id.BtnEditProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ((EditText) findViewById(R.id.ETEditProfileName)).getText().toString();
                String phone = ((EditText) findViewById(R.id.ETEditProfilePhone)).getText().toString();
                String email = ((EditText) findViewById(R.id.ETEditProfileEmail)).getText().toString();
                int gender = (((RadioButton) findViewById(R.id.RBtnEditProfileMale)).isChecked())? Customer.GENDER_MALE:(((RadioButton) findViewById(R.id.RBtnEditProfileFemale)).isChecked())?Customer.GENDER_FEMALE:-1;
                String birthDate = ((EditText) findViewById(R.id.ETEditProfileBirthDate)).getText().toString();
                String password = ((EditText) findViewById(R.id.ETEditProfilePassword)).getText().toString();


                if (email.equals("") || name.equals("") || phone.equals("") || birthDate.equals("") || gender == -1) {
                    return;
                }
                if (!customer.getPassword().equals(password)) {
                    findViewById(R.id.TVEditProfileIncorrectPassword).setVisibility(View.VISIBLE);
                    return;
                }

                try{
                    customer.setName(name);
                    customer.setMobile_number(phone);
                    customer.setEmail(email);
                    customer.setGender(gender);
                    customer.setBirth_date(Date.valueOf(birthDate));

                    customerDAO.updateCustomers(customer);

                    Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
                    startActivity(intent);
                } catch (IllegalArgumentException e) {
                    TypedValue typedValue = new TypedValue();
                    getTheme().resolveAttribute(androidx.appcompat.R.attr.colorPrimary, typedValue, true);
                    int color = typedValue.data;
                    ((TextView) findViewById(R.id.TVEditProfileDateFormatExplain)).setTextColor(color);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            // map toolbar back button same as system back button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}