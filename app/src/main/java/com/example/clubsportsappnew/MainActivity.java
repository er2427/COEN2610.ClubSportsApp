package com.example.clubsportsappnew;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.content.Intent;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.example.clubsportsappnew.ui.account_info.MyAccountActivity;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clubsportsappnew.databinding.ActivityMainBinding;
import com.example.clubsportsappnew.ui.account_info.MyAccountActivity;
import com.example.clubsportsappnew.data.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    TextView displayFirstName, displayLastName, displayEmail;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Define top-level destinations (those that don't have Up button in the app bar)
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setOpenableLayout(drawer)
                .build();

        // Set up navigation controller
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        View headerView = navigationView.getHeaderView(0);



        // Set up the navigation item click listener
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.directoryFragment) {
                    // Navigate to DirectoryFragment when "Club Sports Directory" item is clicked
                    navController.navigate(R.id.directoryFragment);
                    drawer.closeDrawers(); // Close the drawer after navigation
                    return true;
                }
                else if (id == R.id.nav_favorites) {
                    navController.navigate(R.id.nav_favorites);
                    drawer.closeDrawers(); // Close the drawer after navigation
                    return true;
                }
                else if (id == R.id.nav_calendar) {
                    navController.navigate(R.id.nav_calendar);
                    drawer.closeDrawers();
                    return true;
                }
                else if (id == R.id.nav_logout) {
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("DataPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isLoggedIn", false);
                    editor.apply();

                    // Start LoginPage activity
                    Intent intent = new Intent(MainActivity.this, com.example.clubsportsappnew.ui.account_info.LoginActivity.class);
                    startActivity(intent);

                    // Finish current activity (MainActivity)
                    finish();

                    return true;
                }
                return false;
            }
        });

        //making it show the users first and last name and email
        displayFirstName = headerView.findViewById(R.id.displayFirstName);
        displayLastName = headerView.findViewById(R.id.displayLastName);
        displayEmail = headerView.findViewById(R.id.displayEmail);

        databaseHelper = new DatabaseHelper(this);
        String username = getIntent().getStringExtra("username");
        displayUserData(username);


    }
    //makes sure that even if the navigation bar isn't clicked first the data still loads to it
    @Override
    protected void onResume() {
        super.onResume();

        // Retrieve user data from SharedPreferences
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("UserData", MODE_PRIVATE);
        String firstName = sharedPreferences.getString("firstName", "");
        String lastName = sharedPreferences.getString("lastName", "");
        String email = sharedPreferences.getString("email", "");

        // Display user data
        displayFirstName.setText(firstName);
        displayLastName.setText(lastName);
        displayEmail.setText(email);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // If action_settings was clicked
        if (id == R.id.action_settings) {
            // Create a new Intent to start MyAccountActivity
            Intent intent = new Intent(MainActivity.this, MyAccountActivity.class);
            intent.putExtra("username", getIntent().getStringExtra("username"));
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void displayUserData(String username) {
        if (username != null) {
            Cursor cursor = databaseHelper.getUserData(username);
            if (cursor.getCount() == 1) { //assuming username is unique
                cursor.moveToFirst();

                @SuppressLint("Range") String firstName = cursor.getString(cursor.getColumnIndex("firstName"));
                @SuppressLint("Range") String lastName = cursor.getString(cursor.getColumnIndex("lastName"));
                @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex("username"));

                displayFirstName.setText(firstName);
                displayLastName.setText(lastName);
                displayEmail.setText(email);
            }
        }
    }
}

