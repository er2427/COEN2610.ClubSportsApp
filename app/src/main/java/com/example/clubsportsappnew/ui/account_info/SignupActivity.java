package com.example.clubsportsappnew.ui.account_info;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.clubsportsappnew.data.DatabaseHelper;
import com.example.clubsportsappnew.databinding.SignupPageBinding;

public class SignupActivity extends AppCompatActivity {

    SignupPageBinding binding;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SignupPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        binding.createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = binding.signUpUsername.getText().toString();
                String password = binding.signUpPassword.getText().toString();
                String confirmPassword = binding.confirmPassword.getText().toString();
                String firstName = binding.firstName.getText().toString();
                String lastName = binding.lastName.getText().toString();

                if (username.equals("") || password.equals("") || confirmPassword.equals("") ||
                    firstName.equals("") || lastName.equals("")) {
                    Toast.makeText(SignupActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                } else {
                    if (password.equals(confirmPassword)){
                        Boolean checkUserUsername = databaseHelper.checkUsername(username);

                        if (!checkUserUsername){
                            Boolean insert = databaseHelper.insertData(username, password, firstName, lastName);

                            if (insert){
                                Toast.makeText(SignupActivity.this, "Signup Successful", Toast.LENGTH_SHORT).show();
                                Intent intent =  new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                            } else{
                                Toast.makeText(SignupActivity.this, "Signup Failed", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SignupActivity.this, "User already Exists", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignupActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.BackToLoginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}