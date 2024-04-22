package com.example.clubsportsappnew.notifications;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.clubsportsappnew.R;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String EventReminderChannel = "EventReminderChannel";

    @SuppressLint("MissingPermission")
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        boolean isNotificationsEnabled = sharedPreferences.getBoolean("isNotificationsEnabled", true);

        if(!isNotificationsEnabled) {
            Log.d("AlarmReceiver", "Notifications are disabled");
            return;
        }

        String sportName = intent.getStringExtra("sportName");
        Log.d("AlarmReceiver", "Received alarm for " + sportName);

        // Create a notification channel for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Event Reminder";
            String description = "Channel for event reminder notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(EventReminderChannel, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, EventReminderChannel)
                .setSmallIcon(R.drawable.mu_logo)
                .setContentTitle("Event Reminder")
                .setContentText("Don't forget about your " + sportName + " event today!")
                .setPriority(NotificationCompat.PRIORITY_MAX);

        // Show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        int notificationId = (int) System.currentTimeMillis();
        notificationManager.notify(notificationId, builder.build());

        Log.d("AlarmReceiver", "Notification sent for " + sportName);
    }
}
