package com.bubblejet.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class SettingsScreen extends AppCompatActivity {

    public ImageView background;
    public ImageButton clearDataButton;
    public TextView developerLink, musicLink, soundfxLink;
    public static MediaPlayer backgroundMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_screen);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        backgroundMusic = MediaPlayer.create(this, R.raw.background_music);

        clearDataButton = (ImageButton)findViewById(R.id.clear_data_button);

        developerLink = (TextView)findViewById(R.id.developer_link);
        musicLink = (TextView)findViewById(R.id.music_link);
        soundfxLink = (TextView)findViewById(R.id.soundfx_link);

        developerLink.setMovementMethod(LinkMovementMethod.getInstance());
        musicLink.setMovementMethod(LinkMovementMethod.getInstance());
        soundfxLink.setMovementMethod(LinkMovementMethod.getInstance());

        background = (ImageView)findViewById(R.id.background);
        background.setBackgroundColor(Color.parseColor("#8CFFFB"));

    }

    public void onClickHomeButton(View view){
        Intent i = new Intent(this, HomeScreen.class);
        startActivity(i);
    }

    public void onClickClearDataButton(View view){
        getSharedPreferences("featherScores", Context.MODE_PRIVATE).edit().clear().apply();
        clearDataButton.setEnabled(false);
        clearDataButton.setImageResource(R.drawable.cleared_text);


    }

    @Override
    protected void onStop() {
        backgroundMusic.release();
        super.onStop();
    }

    @Override
    protected void onPause() {
        backgroundMusic.release();
        super.onPause();
    }

    @Override
    protected void onResume() {
        backgroundMusic.start();
        backgroundMusic.setLooping(true);
        super.onResume();
    }
}
