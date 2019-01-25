package com.example.mp3wforegroundservice;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import static com.example.mp3wforegroundservice.NotificationApp.CHANNEL_ID;

public class ForegroundService extends Service {
    public static final String TAG = "MP3_ ForegroundService";

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       // return super.onStartCommand(intent, flags, startId);

        String input = intent.getStringExtra("inputExtra");

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0
                );

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Foreground Serviee")
                .setContentText(input)
                .setContentIntent(pendingIntent)
                .build();


        Log.d(TAG, "startForeground");
        //startService(notificationIntent);  // android os may kill it when in background over 1 min
        startForeground(1, notification);  //for long running service, even app is gone

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
