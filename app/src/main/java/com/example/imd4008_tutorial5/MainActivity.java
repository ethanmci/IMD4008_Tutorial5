package com.example.imd4008_tutorial5;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {
    private static final String CHANNEL = "default";
    private static final int NOTIFICATION_DEFAULT = 1100;
    TextInputEditText notificationText;
    TextInputEditText notificationTimer;
    NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationText = findViewById(R.id.notifInput);
        notificationTimer = findViewById(R.id.notifTimerInput);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationChannel chn = new NotificationChannel(CHANNEL,
                "CHANNEL",
                NotificationManager.IMPORTANCE_DEFAULT);

        notificationManager.createNotificationChannel(chn);
    }

    public void notificationButton(View view) {
        Toast.makeText(MainActivity.this, "Notification sent!", Toast.LENGTH_SHORT).show();

        sendNotification(NOTIFICATION_DEFAULT, "Notification");
    }

    public void scheduleNotificationButton(View view) {
        Toast.makeText(MainActivity.this, "Notification scheduled!", Toast.LENGTH_SHORT).show();

        int delay;
        if(notificationTimer.getText().toString().length() <= 0) delay = 5000;
        else delay = Integer.parseInt(notificationTimer.getText().toString());
        Log.d("NOTIF:", notificationTimer.getText().toString());

        scheduleNotification(NOTIFICATION_DEFAULT, "Scheduled notification", delay);
    }

    public Notification buildNotification(String title, String body) {
        Notification.Builder nb = new Notification.Builder(getApplicationContext(), CHANNEL)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.notification_icon)
                ;
        Notification noti = nb.build();
        return noti;
    }

    public void sendNotification(int id, String title) {
        Notification noti = buildNotification(title, notificationText.getText().toString());
        if (noti != null) {
            notificationManager.notify(id, noti);
        }
    }

    public void scheduleNotification(int id, String title, int delay) {
        Notification noti = buildNotification(title, notificationText.getText().toString());

        Intent notificationIntent = new Intent(this, NotificationBroadcaster.class);
        notificationIntent.putExtra(NotificationBroadcaster.NOTIFICATION_ID, id);
        notificationIntent.putExtra(NotificationBroadcaster.NOTIFICATION, noti);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
                notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        long alarmTime = System.currentTimeMillis();
        AlarmManager al = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        al.set(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);
    }
}