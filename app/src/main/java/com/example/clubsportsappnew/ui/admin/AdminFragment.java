package com.example.clubsportsappnew.ui.admin;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.clubsportsappnew.R;
import com.example.clubsportsappnew.ui.calendar.EventAdapter;

import com.example.clubsportsappnew.ui.home.DatabaseHelper;

public class AdminFragment extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase getReadableDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_admin);
        databaseHelper = new DatabaseHelper(this);

        //To be finalized when I have the context from database admin column
        //String username = ;
        //boolean isAdmin = databaseHelper.finalize(username);
        public boolean isAdminUser(String username) {
            SQLiteDatabase db = this.getReadableDatabase;
            boolean isAdmin = false;

            try{
                Cursor cursor = db.rawQuery(query, new String[]{username});
            }

        }
        if (isAdmin) {
            Toast.makeText(this, "Admin user detected", Toast.LENGTH_SHORT).show();
            //Handle add event
        } else {
            Toast.makeText(this, "Regular user", Toast.LENGTH_SHORT).show();
            //Handle Regular user
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }
}



