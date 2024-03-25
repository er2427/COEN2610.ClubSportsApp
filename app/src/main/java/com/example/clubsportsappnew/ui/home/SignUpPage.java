package com.example.clubsportsappnew.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.clubsportsappnew.R;
import com.google.android.material.textfield.TextInputEditText;

public class SignUpPage extends AppCompatActivity {

    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText username;
    private TextInputEditText passwordInput;
    private TextInputEditText checkPasswordInput;
    private Button createAccount;
    private TextView backToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the layout for this activity to be signup_page.xml
        setContentView(R.layout.signup_page);
        passwordInput = findViewById(R.id.signUpPassword);
        checkPasswordInput = findViewById(R.id.confirmPassword);
        createAccount = findViewById(R.id.createAccount);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        username = findViewById(R.id.signUpUsername);
        backToLogin = findViewById(R.id.BackToLoginText);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean accountMade = submitForm();
                if(accountMade){ //if an account is made, bring them to the login page
                    Intent loginIntent = new Intent(SignUpPage.this, LoginPage.class);
                    startActivity(loginIntent);
                }
            }
        });

        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent3 = new Intent(SignUpPage.this, LoginPage.class);
                startActivity(intent3);
            }
        });
    }

    //Getter methods for the input fields
    private String getPassword(){
        if(passwordInput.getText() != null){
            return passwordInput.getText().toString();
        }
        else{
            return null;
        }
    }

    private String getCheckPassword(){
        if(checkPasswordInput.getText() != null){
            return checkPasswordInput.getText().toString();
        }
        else{
            return null;
        }
    }

    private String getFirst(){
        if(firstName.getText() != null){
            return firstName.getText().toString();
        }
        else{
            return null;
        }
    }

    private String getLast(){
        if(lastName.getText() != null){
            return lastName.getText().toString();
        }
        else{
            return null;
        }
    }

    private String getEmail(){
        if(email.getText() != null){
            return email.getText().toString();
        }
        else{
            return null;
        }
    }

    private String getUsername(){
        if(username.getText() != null){
            return username.getText().toString();
        }
        else{
            return null;
        }
    }

    private boolean submitForm(){
        //get the information from the fields
        String password = getPassword();
        String checkPassword = getCheckPassword();
        String firstName = getFirst();
        String lastName = getLast();
        String email = getEmail();
        String username = getUsername();
        boolean valid = false; //initialize valid login as false


        //if any of the fields are null, then the form is not valid
        if(password.isEmpty() || checkPassword.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || username.isEmpty()){
            Toast.makeText(SignUpPage.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
        }
        //if the email is not valid, alert the user
        else if(!(email.contains("@") && email.contains("."))){
            Toast.makeText(SignUpPage.this, "Invalid Email", Toast.LENGTH_SHORT).show();
        }
        //password length and number requirements
        else if(!password.matches(".*\\d.*")){
            Toast.makeText(SignUpPage.this, "Password must contain a number", Toast.LENGTH_SHORT).show();
        }
        else if(password.length()<8){
            Toast.makeText(SignUpPage.this, "Password must be at least 8 characters", Toast.LENGTH_SHORT).show();
        }
        //if the password equals the checkPassword field, then the form is valid
        else if(password.equals(checkPassword)) {
            Toast.makeText(SignUpPage.this, "Account Created", Toast.LENGTH_SHORT).show();
            valid = true;
        }
        //if they don't match, alert the user
        else{
            Toast.makeText(SignUpPage.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
        }
        return valid;
    }
}