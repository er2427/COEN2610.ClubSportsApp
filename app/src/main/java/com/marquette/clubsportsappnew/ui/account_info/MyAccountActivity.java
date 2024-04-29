package com.marquette.clubsportsappnew.ui.account_info;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.marquette.clubsportsappnew.R;
import com.marquette.clubsportsappnew.data.DatabaseHelper;
import com.marquette.clubsportsappnew.databinding.MyAccountBinding;

public class MyAccountActivity extends AppCompatActivity {
    private MyAccountBinding binding;
    TextView textViewDisplayFirstName, textViewDisplayLastName, textViewDisplayEmail;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MyAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        textViewDisplayFirstName = findViewById(R.id.textViewDisplayFirstName);
        textViewDisplayLastName = findViewById(R.id.textViewDisplayLastName);
        textViewDisplayEmail = findViewById(R.id.textViewDisplayEmail);

        databaseHelper = new DatabaseHelper(this);

        String username = getIntent().getStringExtra("username");

        displayUserData(username);

        binding.buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyAccountActivity.this, ChangePassword.class);
                intent.putExtra("username", username); // Pass the username to ChangePassword activity
                startActivity(intent);
            }
        });
    }

    public void displayUserData(String username) {
        if (username != null) {
            Cursor cursor = databaseHelper.getUserData(username);
            if (cursor.getCount() == 1) { //assuming username is unique
                cursor.moveToFirst();

                @SuppressLint("Range") String firstName = cursor.getString(cursor.getColumnIndex("firstName"));
                @SuppressLint("Range") String lastName = cursor.getString(cursor.getColumnIndex("lastName"));
                @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex("username"));

                textViewDisplayFirstName.setText(firstName);
                textViewDisplayLastName.setText(lastName);
                textViewDisplayEmail.setText(email);

                // Store user data in SharedPreferences
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("firstName", firstName);
                editor.putString("lastName", lastName);
                editor.putString("email", email);
                editor.apply();
            }
        }
    }
}