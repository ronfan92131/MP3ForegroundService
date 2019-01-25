package com.example.mp3wforegroundservice;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MP3_ MainActivity";

    private EditText editTextInput;
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextInput = findViewById(R.id.edit_text_input);
    }

    public void startService(View v){
        Log.d(TAG, "startService");
        String input = editTextInput.getText().toString();

        Intent serviceIntent = new Intent(this, ForegroundService.class);
        serviceIntent.putExtra("inputExtra", input);

        startService(serviceIntent);
        startMP3Player();
    }

    public void stopService(View v){
        Log.d(TAG, "stopService");
        Intent serviceIntent = new Intent(this, ForegroundService.class);
        stopService(serviceIntent);

        stopMP3Player();
    }

    private void startMP3Player() {
        Log.d(TAG, "startMP3Player");
        if (player == null){
            player = MediaPlayer.create(this, R.raw.c192);

            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    Log.d(TAG, "startMP3Player onCompletion, repeat");
                    player.start();  //repeat
                }
            });

        }
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
