package com.example.imd4008_tutorial5;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {
    private static final String CHANNEL = "default";
    private static final int NOTIFICATION_DEFAULT = 1100;
    TextInputEditText notificationText;
    NotificationManager notificationManager;
    Button sendNotificationBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationText = findViewById(R.id.notifInput);
        sendNotificationBtn = findViewById(R.id.notifBtn);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationChannel chn = new NotificationChannel(CHANNEL,
                "CHANNEL",
                NotificationManager.IMPORTANCE_DEFAULT);

        notificationManager.createNotificationChannel(chn);

        sendNotificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public Notification buildNotification(String title, String body) {
        Notification.Builder nb = new Notification.Builder(getApplicationContext(), CHANNEL)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.notification_icon)
                ;
        Notification = nb.build;
        return noti;
    }
}