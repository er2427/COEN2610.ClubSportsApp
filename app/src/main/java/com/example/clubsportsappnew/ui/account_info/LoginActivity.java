package com.example.clubsportsappnew.ui.account_info;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.clubsportsappnew.data.DatabaseHelper;
import com.example.clubsportsappnew.databinding.LoginPageBinding;

public class LoginActivity extends AppCompatActivity {

    LoginPageBinding binding;
    DatabaseHelper databaseHelper;

    private static final String PREF_NAME = "UserPrefs";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_LOGGED_IN = "isLoggedIn";
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LoginPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        if (isLoggedIn()) {
            String username = sharedPreferences.getString(KEY_USERNAME, "");
            navigateToMainPage(username);
            return;
        }
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = binding.username.getText().toString();
                String password = binding.password.getText().toString();

                if (username.equals("") || password.equals("")){
                    Toast.makeText(LoginActivity.this, "All fields are mandatory!", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkCredentials = databaseHelper.checkUsernamePassword(username, password);

                    if (checkCredentials){
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        saveLoginState(username);
                        navigateToMainPage(username);
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

    }

    private void saveLoginState(String username) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_LOGGED_IN, true);
        editor.putString(KEY_USERNAME, username);
        editor.apply();
    }
    private boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_LOGGED_IN, false);
    }

    private void navigateToMainPage(String username) {
        Intent intent = new Intent(getApplicationContext(), com.example.clubsportsappnew.MainActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
        finish(); // Finish the LoginActivity so the user cannot navigate back to it.
    }
}