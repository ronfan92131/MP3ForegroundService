package com.example.mp3wforegroundservice;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

//this module is to create a notification chanel
public class NotificationApp extends Application {
    public static final String TAG = "MP3_ NotificationApp";
    public static final String CHANNEL_ID = "MP3_ NotificationApp";

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        super.onCreate();
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel serviceChannel = new NotificationChannel(
              CHANNEL_ID,
              "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}
