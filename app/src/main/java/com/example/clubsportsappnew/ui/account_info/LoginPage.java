package com.example.clubsportsappnew.ui.account_info;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;


import com.example.clubsportsappnew.MainActivity;
import com.example.clubsportsappnew.R;
import com.google.android.material.textfield.TextInputEditText;

public class LoginPage extends AppCompatActivity {
    EditText username;
    TextInputEditText password;
    Button loginButton;
    TextView registerButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.signupText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if (username.getText().toString().equals("user") && password.getText().toString().equals("123456")) {
                    Toast.makeText(LoginPage.this, "Login Sucessful!", Toast.LENGTH_SHORT).show();

                    // Set "isLoggedIn" flag to true
                    setLoggedIn(true);

                    // Start MainActivity
                    Intent intent = new Intent(LoginPage.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Close the login activity
                } else {
                    Toast.makeText(LoginPage.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent2 = new Intent(LoginPage.this, com.example.clubsportsappnew.ui.account_info.SignUpPage.class);
                startActivity(intent2);
            }
        });
    }

    private void setLoggedIn(boolean isLoggedIn) {
        // Store the login state in SharedPreferences
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("DataPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", isLoggedIn);
        editor.apply();
    }
}
