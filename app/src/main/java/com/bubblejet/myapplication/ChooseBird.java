package com.bubblejet.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class ChooseBird extends AppCompatActivity {

    public ImageView background, bird, playButton, birdText, feather;
    public ImageButton leftArrow, rightArrow;
    public ArrayList<Integer> birdList, textList, featherList, featherCountList, birdAnimationList;
    public Integer indexNum;
    public TextView featherCountText;
    public static MediaPlayer backgroundMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_bird);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        backgroundMusic = MediaPlayer.create(this, R.raw.background_music_1);

        bird = (ImageView)findViewById(R.id.bird);
        playButton = (ImageView)findViewById(R.id.play_button);
        birdText = (ImageView)findViewById(R.id.bird_text);
        leftArrow = (ImageButton)findViewById(R.id.left_arrow);
        rightArrow = (ImageButton)findViewById(R.id.right_arrow);
        feather = (ImageView)findViewById(R.id.feather);

        featherCountText = (TextView)findViewById(R.id.feather_count_text);

        indexNum = 0;

        background = (ImageView)findViewById(R.id.background);
        background.setBackgroundColor(Color.parseColor("#8CFFFB"));

        SharedPreferences sharedPreferences = getSharedPreferences("featherScores", Context.MODE_PRIVATE);

        Integer sparrowFeathersTotal = sharedPreferences.getInt("sparrowFeathersTotal", 0);
        Integer bluebirdFeathersTotal = sharedPreferences.getInt("bluebirdFeathersTotal", 0);
        Integer parrotFeathersTotal = sharedPreferences.getInt("parrotFeathersTotal", 0);
        Integer doveFeathersTotal = sharedPreferences.getInt("doveFeathersTotal", 0);
        Integer owlFeathersTotal = sharedPreferences.getInt("owlFeathersTotal", 0);
        Integer flappyBirdFeathersTotal = sharedPreferences.getInt("flappyBirdFeathersTotal", 0);
        Integer trashDoveFeathersTotal = sharedPreferences.getInt("trashDoveFeathersTotal", 0);
        Integer derpyPonyFeathersTotal = sharedPreferences.getInt("derpyPonyFeathersTotal", 0);

        featherCountList = new ArrayList<>();
        featherCountList.add(sparrowFeathersTotal);
        featherCountList.add(bluebirdFeathersTotal);
        featherCountList.add(parrotFeathersTotal);
        featherCountList.add(doveFeathersTotal);
        featherCountList.add(owlFeathersTotal);
        featherCountList.add(flappyBirdFeathersTotal);
        featherCountList.add(trashDoveFeathersTotal);
        featherCountList.add(derpyPonyFeathersTotal);

        birdList = new ArrayList<>();
        birdList.add(R.drawable.sparrow_flying_animation_large);
        birdList.add(R.drawable.bluebird_flying_animation_large);
        birdList.add(R.drawable.parrot_flying_animation_large);
        birdList.add(R.drawable.dove_flying_animation_large);
        birdList.add(R.drawable.owl_flying_animation_large);
        birdList.add(R.drawable.flappy_bird_flying_animation_large);
        birdList.add(R.drawable.trash_dove_flying_animation_large);
        birdList.add(R.drawable.derpy_pony_flying_animation_large);

        textList = new ArrayList<>();
        textList.add(R.drawable.sparrow_text);
        textList.add(R.drawable.bluebird_text);
        textList.add(R.drawable.parrot_text);
        textList.add(R.drawable.dove_text);
        textList.add(R.drawable.owl_text);
        textList.add(R.drawable.flappy_bird_text);
        textList.add(R.drawable.trash_dove_text);
        textList.add(R.drawable.derpy_pony_text);

        featherList = new ArrayList<>();
        featherList.add(R.drawable.feather_sparrow);
        featherList.add(R.drawable.feather_bluebird);
        featherList.add(R.drawable.feather_parrot);
        featherList.add(R.drawable.feather_dove);
        featherList.add(R.drawable.feather_owl);
        featherList.add(R.drawable.feather_flappy_bird);
        featherList.add(R.drawable.feather_trash_dove);
        featherList.add(R.drawable.feather_derpy_pony);

        birdAnimationList = new ArrayList<>();
        birdAnimationList.add(R.drawable.sparrow_flying_animation);
        birdAnimationList.add(R.drawable.bluebird_flying_animation);
        birdAnimationList.add(R.drawable.parrot_flying_animation);
        birdAnimationList.add(R.drawable.dove_flying_animation);
        birdAnimationList.add(R.drawable.owl_flying_animation);
        birdAnimationList.add(R.drawable.flappy_bird_flying_animation);
        birdAnimationList.add(R.drawable.trash_dove_flying_animation);
        birdAnimationList.add(R.drawable.derpy_pony_flying_animation);

        birdText.setImageResource(R.drawable.sparrow_text);
        bird.setBackgroundResource(R.drawable.sparrow_flying_animation_large);

        featherCountText.setTextColor(Color.parseColor("#0ed145"));
        Integer newFeatherCount = featherCountList.get(indexNum);
        featherCountText.setText(String.valueOf(newFeatherCount) + " /---");

        AnimationDrawable birdAnimation = (AnimationDrawable) bird.getBackground();
        birdAnimation.start();

    }

    public void onClickPlayButton(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("featherScores", Context.MODE_PRIVATE);

        Integer chosenBird = birdAnimationList.get(indexNum);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("chosenBird", chosenBird);
        editor.apply();

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void onClickHomeButton(View view){
        Intent i = new Intent(this, HomeScreen.class);
        startActivity(i);
    }


    public void onClickLeftArrow(View view){
        if (!indexNum.equals(0)){
            indexNum --;
            changeBird();
        }
    }

    public void onClickRightArrow(View view){
        if (!indexNum.equals(7)){
            indexNum ++;
            changeBird();
        }
    }

    public void changeBird(){
        Integer newBird = birdList.get(indexNum);
        Integer newBirdText = textList.get(indexNum);
        Integer newFeather = featherList.get(indexNum);
        Integer newFeatherCount = featherCountList.get(indexNum);

        feather.setImageResource(newFeather);
        if (indexNum.equals(0)){
            featherCountText.setText(String.valueOf(newFeatherCount) + " /---");
        }else{
            featherCountText.setText(String.valueOf(newFeatherCount) + " /200");
        }

        if (indexNum.equals(0)){
            bird.setImageResource(R.drawable.owl_null);
            bird.setBackgroundResource(newBird);
            birdText.setImageResource(newBirdText);

            birdText.setImageResource(newBirdText);

            AnimationDrawable birdAnimation = (AnimationDrawable) bird.getBackground();
            birdAnimation.start();

            playButton.setImageResource(R.drawable.play_button);
            playButton.setEnabled(true);
            featherCountText.setTextColor(Color.parseColor("#0ed145"));
        }else if (newFeatherCount >= 200){
            bird.setImageResource(R.drawable.owl_null);
            bird.setBackgroundResource(newBird);
            birdText.setImageResource(newBirdText);

            birdText.setImageResource(newBirdText);

            AnimationDrawable birdAnimation = (AnimationDrawable) bird.getBackground();
            birdAnimation.start();

            playButton.setImageResource(R.drawable.play_button);
            playButton.setEnabled(true);
            featherCountText.setTextColor(Color.parseColor("#0ed145"));
        }else{
            bird.setImageResource(newBird);
            bird.setBackgroundResource(R.drawable.owl_null);
            bird.setColorFilter(Color.parseColor("#000000"));

            birdText.setImageResource(R.drawable.unknown_text);

            playButton.setImageResource(R.drawable.playbutton_grey);
            playButton.setEnabled(false);
            featherCountText.setTextColor(Color.RED);
        }

        /*if (!indexNum.equals(0)){
            bird.setImageResource(newBird);
            bird.setBackgroundResource(R.drawable.owl_null);
            bird.setColorFilter(Color.parseColor("#000000"));

            birdText.setImageResource(R.drawable.unknown_text);

            playButton.setVisibility(View.VISIBLE);
            feather.setVisibility(View.INVISIBLE);
            featherCountText.setVisibility(View.INVISIBLE);
        }else{
            bird.setImageResource(R.drawable.owl_null);
            bird.setBackgroundResource(newBird);
            birdText.setImageResource(newBirdText);

            birdText.setImageResource(newBirdText);

            AnimationDrawable birdAnimation = (AnimationDrawable) bird.getBackground();
            birdAnimation.start();
        } */
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
