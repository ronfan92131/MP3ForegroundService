package com.example.mp3wforegroundservice;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MP3_ MainActivity";
    private static final  int REQUEST_CODE = 43;
    MediaPlayer player;
    Uri uri;
    boolean serviceFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void selectFile(View v){
        Log.d(TAG, "selectFile");
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("audio/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult:" + requestCode + " " + resultCode);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            if (data != null){
                uri = data.getData();
                Log.d(TAG, "onActivityResult: " + uri);
                Toast.makeText(this, "Uri: " + uri, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void startService(View v){
        Log.d(TAG, "startService: " + serviceFlag);
        if (serviceFlag) { return;}

        Intent serviceIntent = new Intent(this, ForegroundService.class);
        startService(serviceIntent);
        startMP3Player();
        serviceFlag = true;
    }

    public void stopService(View v){
        Log.d(TAG, "stopService");
        Intent serviceIntent = new Intent(this, ForegroundService.class);
        stopService(serviceIntent);
        stopMP3Player();
        serviceFlag = false;
    }

    private void startMP3Player() {
        Log.d(TAG, "startMP3Player");
        if (player == null){
            if (uri != null){
                Log.d(TAG, "startMP3Player uri");
                player = MediaPlayer.create(this, uri);
            }else {
                Log.d(TAG, "startMP3Player raw");
                player = MediaPlayer.create(this, R.raw.c192);
            }
        }
        player.setLooping(true);
        player.start();
    }

    private void stopMP3Player() {
        Log.d(TAG, "stopMP3Player");
        if (player != null){
            player.release();
            player = null;
            Toast.makeText(this, "MP3 player released", Toast.LENGTH_SHORT).show();
        }
    }
}
