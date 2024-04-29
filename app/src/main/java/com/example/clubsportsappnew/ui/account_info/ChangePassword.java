package com.example.clubsportsappnew.ui.account_info;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clubsportsappnew.R;
import com.example.clubsportsappnew.data.DatabaseHelper;

public class ChangePassword extends AppCompatActivity {

    private EditText oldPasswordEditText;
    private EditText newPasswordEditText;
    private DatabaseHelper databaseHelper;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        oldPasswordEditText = findViewById(R.id.oldPassword);
        newPasswordEditText = findViewById(R.id.newPassword);
        Button changePasswordButton = findViewById(R.id.changePasswordButton);
        Button backToLoginButton = findViewById(R.id.backToLoginButton);


        databaseHelper = new DatabaseHelper(this);

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        username = getIntent().getStringExtra("username");

        changePasswordButton.setOnClickListener(v -> {
            String oldPassword = oldPasswordEditText.getText().toString();
            String newPassword = newPasswordEditText.getText().toString();

            if (databaseHelper.checkUsernamePassword(username, oldPassword)) {
                databaseHelper.updatePassword(username, newPassword);
                Toast.makeText(ChangePassword.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ChangePassword.this, "Old password is incorrect", Toast.LENGTH_SHORT).show();
            }
        });

        backToLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangePassword.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}