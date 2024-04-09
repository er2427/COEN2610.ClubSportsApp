package com.example.clubsportsappnew;

import android.app.PendingIntent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.content.Intent;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

//New imports for notifications
import android.app.Notification;
import android.content.Context;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.Manifest;

import com.example.clubsportsappnew.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private static final String CHANNEL_ID = "channel_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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

        // Notifications
        createNotificationChannel();
        showNotification();

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


    //Notification Section (Pop up when you physically open the app)
    private void createNotificationChannel() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = "Channel Name";  //Name of the channel
                String description = "Channel Description";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
                channel.setDescription(description);

                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);
            }
        }

        private void showNotification() {
        int notificationId= 101;  //this is an arbitrary number that can be used to identify the notification

        //Permission Check for Notifications
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
                return; //Exit the function if permission is not granted
            }


            //Create an intent that will be fired when the user clicks the exit button
            Intent exitIntent = new Intent(this, MainActivity.class);
            exitIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent exitPendingIntent = PendingIntent.getActivity(this, 0, exitIntent, PendingIntent.FLAG_CANCEL_CURRENT|PendingIntent.FLAG_IMMUTABLE);

            //Create the exit action
            NotificationCompat.Action exitAction = new NotificationCompat.Action.Builder(R.drawable.ic_launcher_background, "Exit", exitPendingIntent).build();


            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle("Club Sports App")
                    .setContentText("Welcome to Club Sports App")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .addAction(exitAction);  //adding the exit action to the notification

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            try {
                notificationManager.notify(notificationId, builder.build());
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super. onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }




