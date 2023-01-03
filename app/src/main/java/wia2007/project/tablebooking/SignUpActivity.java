package wia2007.project.tablebooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import java.sql.Date;
import java.util.List;

import wia2007.project.tablebooking.dao.CustomerDAO;
import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.Customer;
import wia2007.project.tablebooking.entity.PasswordChecker;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Button btnSignUp = findViewById(R.id.BtnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = ((EditText) findViewById(R.id.ETSignUpUsername)).getText().toString();
                String email = ((EditText) findViewById(R.id.ETSignUpEmail)).getText().toString();
                String name = ((EditText) findViewById(R.id.ETSignUpName)).getText().toString();
                String phone = ((EditText) findViewById(R.id.ETSignUpPhone)).getText().toString();
                String birthDate = ((EditText) findViewById(R.id.ETSignUpBirthDate)).getText().toString();
                String password = ((EditText) findViewById(R.id.ETSignUpPassword)).getText().toString();
                String confirmPassword = ((EditText) findViewById(R.id.ETSignUpConfirmPassword)).getText().toString();
                int gender = (((RadioButton) findViewById(R.id.RBtnMale)).isChecked())?Customer.GENDER_MALE:(((RadioButton) findViewById(R.id.RBtnFemale)).isChecked())?Customer.GENDER_FEMALE:-1;
                if (!password.equals(confirmPassword) || !PasswordChecker.validPassword(password) ||
                        username.equals("") || email.equals("") || name.equals("") ||
                        phone.equals("") || birthDate.equals("") || gender == -1
                ) {
                    return;
                }

                TableBookingDatabase database = TableBookingDatabase.getDatabase(getApplicationContext());
                CustomerDAO customerDAO = database.customerDAO();
                List<Customer> customerList = customerDAO.getCustomerByUsername(username);
                if (customerList.size() != 0){
                    findViewById(R.id.TVSignUpUsernameExist).setVisibility(View.VISIBLE);
                    return;
                }
                customerList = customerDAO.getCustomerByEmail(email);
                if (customerList.size() != 0){
                    findViewById(R.id.TVSignUpEmailExist).setVisibility(View.VISIBLE);
                    return;
                }

                customerDAO.insertCustomers(new Customer(username, password, name, phone, email, gender, Date.valueOf(birthDate)));

                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}