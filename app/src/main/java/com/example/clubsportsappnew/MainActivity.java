package com.example.clubsportsappnew;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.content.Intent;

import com.example.clubsportsappnew.ui.home.MyAccountActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clubsportsappnew.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

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
//                else if (id == R.id.nav_logout) {
////                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("DataPref", MODE_PRIVATE);
////                    SharedPreferences.Editor editor = sharedPreferences.edit();
////                    editor.putBoolean("isLoggedIn", false);
////                    editor.apply();
////
////                    // Start LoginPage activity
////                    Intent intent = new Intent(MainActivity.this, com.example.clubsportsappnew.ui.home.LoginActivity.class);
////                    startActivity(intent);
////
////                    // Finish current activity (MainActivity)
////                    finish();
//
//                    return true;
//                }
                return false;
            }
        });
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
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

