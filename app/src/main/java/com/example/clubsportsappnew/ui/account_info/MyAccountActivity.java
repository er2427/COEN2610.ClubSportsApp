package com.example.clubsportsappnew.ui.account_info;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clubsportsappnew.MainActivity;
import com.example.clubsportsappnew.R;
import com.example.clubsportsappnew.data.DatabaseHelper;
import com.example.clubsportsappnew.databinding.MyAccountBinding;
import com.example.clubsportsappnew.notifications.AlarmReceiver;

public class MyAccountActivity extends AppCompatActivity {
    private MyAccountBinding binding;
    TextView textViewDisplayFirstName, textViewDisplayLastName, textViewDisplayEmail;
    DatabaseHelper databaseHelper;
    Switch switchNotifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MyAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        switchNotifications = findViewById(R.id.switchNotifications);

        binding.backText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyAccountActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        textViewDisplayFirstName = findViewById(R.id.textViewDisplayFirstName);
        textViewDisplayLastName = findViewById(R.id.textViewDisplayLastName);
        textViewDisplayEmail = findViewById(R.id.textViewDisplayEmail);

        databaseHelper = new DatabaseHelper(this);

        String username = getIntent().getStringExtra("username");

        displayUserData(username);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("UserData", MODE_PRIVATE);
        boolean isNotificationsEnabled = sharedPreferences.getBoolean("isNotificationsEnabled", true);
        switchNotifications.setChecked(isNotificationsEnabled);

        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isNotificationsEnabled", isChecked);
            editor.apply();
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
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("UserData", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("firstName", firstName);
                editor.putString("lastName", lastName);
                editor.putString("email", email);
                editor.apply();
            }
        }
    }
}