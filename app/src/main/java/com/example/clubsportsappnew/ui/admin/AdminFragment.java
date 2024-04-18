package com.example.clubsportsappnew.ui.admin;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.clubsportsappnew.ui.home.DatabaseHelper;

public class AdminFragment extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_admin)
        databaseHelper = new DatabaseHelper(this);

        String username;
        boolean isAdmin = databaseHelper.isAdminUser(username);

        if (isAdmin) {
            Toast.makeText(this, "Admin user detected", Toast.LENGTH_SHORT).show();
            //Handle add event
        } else {
            Toast.makeText(this, "Regular user", Toast.LENGTH_SHORT).show();
            //Handle Regular user
        }
    }

    @Override
    protected void onDestry() {
        super.onDestroy();
        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }
}



