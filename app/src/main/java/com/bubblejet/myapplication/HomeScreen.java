package com.bubblejet.myapplication;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class HomeScreen extends AppCompatActivity {

    public ImageView background;
    public static MediaPlayer backgroundMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        backgroundMusic = MediaPlayer.create(this, R.raw.background_music);

        background = (ImageView)findViewById(R.id.background);
        background.setBackgroundColor(Color.parseColor("#8CFFFB"));
    }

    public void onClickPlayButton(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void onClickBirdButton(View view){
        Intent i = new Intent(this, ChooseBird.class);
        startActivity(i);
    }

    public void onClickSettingsButton(View view){
        Intent i = new Intent(this, SettingsScreen.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        backgroundMusic.release();
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
        super.onBackPressed();
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
