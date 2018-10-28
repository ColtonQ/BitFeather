package com.bubblejet.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class EndScreen extends AppCompatActivity {

    public ImageView background;
    public TextView sparrowScore, bluebirdScore, parrotScore, doveScore, owlScore, flappyBirdScore,
        trashDoveScore, derpyPonyScore, totalScore, highScoreNum;
    public static MediaPlayer backgroundMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_screen);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        backgroundMusic = MediaPlayer.create(this, R.raw.background_music_1);

        sparrowScore = (TextView)findViewById(R.id.sparrow_score);
        bluebirdScore = (TextView)findViewById(R.id.bluebird_score);
        parrotScore = (TextView)findViewById(R.id.parrot_score);
        doveScore = (TextView)findViewById(R.id.dove_score);
        owlScore = (TextView)findViewById(R.id.owl_score);
        flappyBirdScore = (TextView)findViewById(R.id.flappy_bird_score);
        trashDoveScore = (TextView)findViewById(R.id.trash_dove_score);
        derpyPonyScore = (TextView)findViewById(R.id.derpy_pony_score);
        totalScore = (TextView)findViewById(R.id.total_score);

        highScoreNum = (TextView)findViewById(R.id.high_score);

        background = (ImageView)findViewById(R.id.background);
        background.setBackgroundColor(Color.parseColor("#8CFFFB"));

        setScores();
    }

    public void setScores(){
        Bundle featherData = getIntent().getExtras();
        Integer sparrowFeathers = featherData.getInt("sparrowFeathers");
        Integer bluebirdFeathers = featherData.getInt("bluebirdFeathers");
        Integer parrotFeathers = featherData.getInt("parrotFeathers");
        Integer doveFeathers = featherData.getInt("doveFeathers");
        Integer owlFeathers = featherData.getInt("owlFeathers");
        Integer flappyBirdFeathers = featherData.getInt("flappyBirdFeathers");
        Integer trashDoveFeathers = featherData.getInt("trashDoveFeathers");
        Integer derpyPonyFeathers = featherData.getInt("derpyPonyFeathers");
        Integer totalFeathers = featherData.getInt("totalFeathers");

        sparrowScore.setText(String.valueOf(sparrowFeathers));
        bluebirdScore.setText(String.valueOf(bluebirdFeathers));
        parrotScore.setText(String.valueOf(parrotFeathers));
        doveScore.setText(String.valueOf(doveFeathers));
        owlScore.setText(String.valueOf(owlFeathers));
        flappyBirdScore.setText(String.valueOf(flappyBirdFeathers));
        trashDoveScore.setText(String.valueOf(trashDoveFeathers));
        derpyPonyScore.setText(String.valueOf(derpyPonyFeathers));

        totalScore.setText(String.valueOf(totalFeathers));

        SharedPreferences sharedPreferences = getSharedPreferences("featherScores", Context.MODE_PRIVATE);

        Integer sparrowFeathersTotal = sharedPreferences.getInt("sparrowFeathersTotal", 0);
        Integer bluebirdFeathersTotal = sharedPreferences.getInt("bluebirdFeathersTotal", 0);
        Integer parrotFeathersTotal = sharedPreferences.getInt("parrotFeathersTotal", 0);
        Integer doveFeathersTotal = sharedPreferences.getInt("doveFeathersTotal", 0);
        Integer owlFeathersTotal = sharedPreferences.getInt("owlFeathersTotal", 0);
        Integer flappyBirdFeathersTotal = sharedPreferences.getInt("flappyBirdFeathersTotal", 0);
        Integer trashDoveFeathersTotal = sharedPreferences.getInt("trashDoveFeathersTotal", 0);
        Integer derpyPonyFeathersTotal = sharedPreferences.getInt("derpyPonyFeathersTotal", 0);
        Integer highScore = sharedPreferences.getInt("highScore", 0);

        sparrowFeathersTotal = sparrowFeathersTotal + sparrowFeathers;
        bluebirdFeathersTotal = bluebirdFeathersTotal + bluebirdFeathers;
        parrotFeathersTotal = parrotFeathersTotal + parrotFeathers;
        doveFeathersTotal = doveFeathersTotal + doveFeathers;
        owlFeathersTotal = owlFeathersTotal + owlFeathers;
        flappyBirdFeathersTotal = flappyBirdFeathersTotal + flappyBirdFeathers;
        trashDoveFeathersTotal = trashDoveFeathersTotal + trashDoveFeathers;
        derpyPonyFeathersTotal = derpyPonyFeathersTotal + derpyPonyFeathers;

        if (totalFeathers > highScore){
            highScore = totalFeathers;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("sparrowFeathersTotal", sparrowFeathersTotal);
        editor.putInt("bluebirdFeathersTotal", bluebirdFeathersTotal);
        editor.putInt("parrotFeathersTotal", parrotFeathersTotal);
        editor.putInt("doveFeathersTotal", doveFeathersTotal);
        editor.putInt("owlFeathersTotal", owlFeathersTotal);
        editor.putInt("flappyBirdFeathersTotal", flappyBirdFeathersTotal);
        editor.putInt("trashDoveFeathersTotal", trashDoveFeathersTotal);
        editor.putInt("derpyPonyFeathersTotal", derpyPonyFeathersTotal);
        editor.putInt("highScore", highScore);
        editor.apply();

        highScoreNum.setText(String.valueOf(highScore));
    }

    public void onClickPlayButton(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void onClickBirdButton(View view){
        Intent i = new Intent(this, ChooseBird.class);
        startActivity(i);
    }

    public void onClickHomeButton(View view){
        Intent i = new Intent(this, HomeScreen.class);
        startActivity(i);
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
