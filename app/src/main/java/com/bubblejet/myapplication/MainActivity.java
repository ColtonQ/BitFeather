package com.bubblejet.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.transition.Transition;

import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public ImageButton upButton, downButton, spaceTopLeft, spaceBottomLeft, spaceCenter, powerUpTop, powerUpBottom,
        powerUpCenter, spaceTopLeftPowerUp, spaceBottomLeftPowerUp, playButton, powerUpTop1,
        powerUpTop2, powerUpCenter1, powerUpCenter2, powerUpBottom1, powerUpBottom2, homeButton;
    public Boolean moveUp, moveDown, inTransition, inUseTop0, top0Ready, top1Ready, top2Ready,
    center0Ready, center1Ready, center2Ready, bottom0Ready, bottom1Ready, bottom2Ready, gameOver,
        gameOverHome, gameStart;
    public String currentPlace, chooseRow;
    public ViewGroup relativeLayoutOwl, relativeLayoutPowerUp, relativeLayoutButtons,
        relativeLayoutBackground;
    public TextView testText, testText1, testText2;
    public Integer result0, result1, powerUpId, powerUpTopId, powerUpCenterId, powerUpBottomId,
        powerUpTop1Id, powerUpTop2Id, powerUpCenter1Id, powerUpCenter2Id, powerUpBottom1Id,
        powerUpBottom2Id, feathersCollected, feathersReleased, powerUpProb, sparrowFeather,
        bluebirdFeather, parrotFeather, doveFeather, owlFeather, flappyBirdFeather,
        trashDoveFeather, derpyPonyFeather;
    public View powerUpView;
    public Long owlSpeed, powerUpSpeed, featherDelay;
    public AnimationDrawable frameAnimationBird;
    public ImageView background, owl, gameOverText;
    public ArrayList<Integer> featherList;
    public static MediaPlayer backgroundMusic;
    //public Transition movePowerUpTrans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        backgroundMusic = MediaPlayer.create(this, R.raw.background_music);

        upButton = (ImageButton) findViewById(R.id.upbutton);
        downButton = (ImageButton) findViewById(R.id.downbutton);
        owl = (ImageView) findViewById(R.id.owl);
        spaceTopLeft = (ImageButton) findViewById(R.id.space_topleft);
        spaceCenter = (ImageButton) findViewById(R.id.space_center);
        spaceBottomLeft = (ImageButton) findViewById(R.id.space_bottomleft);
        powerUpTop = (ImageButton) findViewById(R.id.power_up_top);
        powerUpCenter = (ImageButton) findViewById(R.id.power_up_center);
        powerUpBottom = (ImageButton) findViewById(R.id.power_up_bottom);
        powerUpTop1 = (ImageButton) findViewById(R.id.power_up_top_1);
        powerUpTop2 = (ImageButton) findViewById(R.id.power_up_top_2);
        powerUpCenter1 = (ImageButton) findViewById(R.id.power_up_center_1);
        powerUpCenter2 = (ImageButton) findViewById(R.id.power_up_center_2);
        powerUpBottom1 = (ImageButton) findViewById(R.id.power_up_bottom_1);
        powerUpBottom2 = (ImageButton) findViewById(R.id.power_up_bottom_2);
        spaceTopLeftPowerUp = (ImageButton) findViewById(R.id.space_topleft_power_up);
        spaceBottomLeftPowerUp = (ImageButton) findViewById(R.id.space_bottomleft_power_up);
        playButton = (ImageButton) findViewById(R.id.play_button);
        homeButton = (ImageButton) findViewById(R.id.home_button);
        background = (ImageView) findViewById(R.id.background);
        gameOverText = (ImageView) findViewById(R.id.game_over_text);

        relativeLayoutOwl = (ViewGroup) findViewById(R.id.relative_layout_owl);
        relativeLayoutPowerUp = (ViewGroup) findViewById(R.id.relative_layout_power_up);
        relativeLayoutButtons = (ViewGroup) findViewById(R.id.relative_layout_buttons);
        relativeLayoutBackground = (ViewGroup) findViewById(R.id.relative_layout_background);

        testText = (TextView) findViewById(R.id.test_text);
        testText1 = (TextView) findViewById(R.id.test_text_1);
        testText2 = (TextView) findViewById(R.id.test_text_2);

        moveUp = false;
        moveDown = false;
        inTransition = false;

        top0Ready = true;
        top1Ready = true;
        top2Ready = true;
        center0Ready = true;
        center1Ready = true;
        center2Ready = true;
        bottom0Ready = true;
        bottom1Ready = true;
        bottom2Ready = true;

        gameOver = false;
        gameOverHome = false;
        gameStart = false;

        feathersCollected = 0;
        feathersReleased = 0;
        powerUpProb = 1; //-----------------------------------------------------------------------Should be 3. Lower powerUpProb to increase probability of releasing power ups
        owlSpeed = 50L;
        powerUpSpeed = 2500L;
        featherDelay = 600L;

        sparrowFeather = 0;
        bluebirdFeather = 0;
        parrotFeather = 0;
        doveFeather = 0;
        owlFeather = 0;
        flappyBirdFeather = 0;
        trashDoveFeather = 0;
        derpyPonyFeather = 0;

        currentPlace = "center";
        chooseRow = "bottom";

        featherList = new ArrayList<>();
        for (int n = 0; n < 25; n++){
            featherList.add(0);
            featherList.add(1);
        }
        for (int n = 0; n < 20; n++){
            featherList.add(2);
        }
        for (int n = 0; n < 15; n++){
            featherList.add(3);
        }
        for (int n = 0; n < 9; n++){
            featherList.add(4);
        }
        for (int n = 0; n < 3; n++){
            featherList.add(5);
        }
        for (int n = 0; n < 2; n++){
            featherList.add(6);
        }
        for (int n = 0; n < 1; n++){
            featherList.add(7);
        }

        SharedPreferences sharedPreferences = getSharedPreferences("featherScores", Context.MODE_PRIVATE);
        Integer chosenBird = sharedPreferences.getInt("chosenBird", R.drawable.sparrow_flying_animation);

        owl.setBackgroundResource(chosenBird);
        upButton.setEnabled(false);
        downButton.setEnabled(false);

        powerUpTopId = powerUpTop.getId();
        powerUpCenterId = powerUpCenter.getId();
        powerUpBottomId = powerUpBottom.getId();
        powerUpTop1Id = powerUpTop1.getId();
        powerUpTop2Id = powerUpTop2.getId();
        powerUpCenter1Id = powerUpCenter1.getId();
        powerUpCenter2Id = powerUpCenter2.getId();
        powerUpBottom1Id = powerUpBottom1.getId();
        powerUpBottom2Id = powerUpBottom2.getId();

        background.setBackgroundColor(Color.parseColor("#8CFFFB"));

        testText2.setText(String.valueOf(powerUpTopId) + " " + String.valueOf(powerUpCenterId) + " " + String.valueOf(powerUpBottomId));

        //background.setImageResource(R.drawable.night_sky_background);
        //background.setBackgroundColor(Color.parseColor("#8CFFFB"));
        //background.setBackgroundResource(R.drawable.night_sky_background_animation);
        //AnimationDrawable frameAnimationBackground = (AnimationDrawable) background.getBackground();
        //frameAnimationBackground.start();
        //background.setAdjustViewBounds(true);


        //----------------------------------------------------------------------------------------------------------------------------------------------------------MAKING BIRD ANIMATIONS

       /*owl.setBackgroundResource(R.drawable.owl_flying_animation);
       AnimationDrawable frameAnimation = (AnimationDrawable) owl.getBackground();
       frameAnimation.start(); */

    }

    public void onClickPlayButton(View view) {
        //owl.setBackgroundResource(R.drawable.trash_dove_flying_animation);
        //frameAnimationBird = (AnimationDrawable) owl.getBackground();
        //frameAnimationBird.start();

        /*/-------FUN RANDOM CHARACTER GENERATOR

        int low2 = 0;
        int high2 = 7;
        int chosenFeather = low2 + (int) (Math.random() * ((high2 - low2) + 1));

        if (chosenFeather == 0){
            owl.setBackgroundResource(R.drawable.owl_flying_animation);
        }else if (chosenFeather == 1){
            owl.setBackgroundResource(R.drawable.bluebird_flying_animation);
        }else if (chosenFeather == 2){
            owl.setBackgroundResource(R.drawable.dove_flying_animation);
        }else if (chosenFeather == 3){
            owl.setBackgroundResource(R.drawable.sparrow_flying_animation);
        }else if (chosenFeather == 4){
            owl.setBackgroundResource(R.drawable.parrot_flying_animation);
        }else if (chosenFeather == 5){
            owl.setBackgroundResource(R.drawable.trash_dove_flying_animation);
        }else if (chosenFeather == 6){
            owl.setBackgroundResource(R.drawable.derpy_pony_flying_animation);
        }else if (chosenFeather == 7){
            owl.setBackgroundResource(R.drawable.flappy_bird_flying_animation);
        } */

        frameAnimationBird = (AnimationDrawable) owl.getBackground();
        frameAnimationBird.start();

        //--------------------------------------- */

        upButton.setEnabled(true);
        downButton.setEnabled(true);
        playButton.setVisibility(View.INVISIBLE);

        gameStart = true;

        /* powerUpTop.setBackgroundResource(R.drawable.feather_owl_animation);
        AnimationDrawable frameAnimationPowerUpTop = (AnimationDrawable) powerUpTop.getBackground();
        frameAnimationPowerUpTop.start();
        powerUpTop1.setBackgroundResource(R.drawable.feather_owl_animation);
        AnimationDrawable frameAnimationPowerUpTop1 = (AnimationDrawable) powerUpTop1.getBackground();
        frameAnimationPowerUpTop1.start();
        powerUpTop2.setBackgroundResource(R.drawable.feather_owl_animation);
        AnimationDrawable frameAnimationPowerUpTop2 = (AnimationDrawable) powerUpTop2.getBackground();
        frameAnimationPowerUpTop2.start();

        powerUpCenter.setBackgroundResource(R.drawable.feather_owl_animation);
        AnimationDrawable frameAnimationPowerUpCenter = (AnimationDrawable) powerUpCenter.getBackground();
        frameAnimationPowerUpCenter.start();
        powerUpCenter1.setBackgroundResource(R.drawable.feather_owl_animation);
        AnimationDrawable frameAnimationPowerUpCenter1 = (AnimationDrawable) powerUpCenter1.getBackground();
        frameAnimationPowerUpCenter1.start();
        powerUpCenter2.setBackgroundResource(R.drawable.feather_owl_animation);
        AnimationDrawable frameAnimationPowerUpCenter2 = (AnimationDrawable) powerUpCenter2.getBackground();
        frameAnimationPowerUpCenter2.start();

        powerUpBottom.setBackgroundResource(R.drawable.feather_owl_animation);
        AnimationDrawable frameAnimationPowerUpBottom = (AnimationDrawable) powerUpBottom.getBackground();
        frameAnimationPowerUpBottom.start();
        powerUpBottom1.setBackgroundResource(R.drawable.feather_owl_animation);
        AnimationDrawable frameAnimationPowerUpBottom1 = (AnimationDrawable) powerUpBottom1.getBackground();
        frameAnimationPowerUpBottom1.start();
        powerUpBottom2.setBackgroundResource(R.drawable.feather_owl_animation);
        AnimationDrawable frameAnimationPowerUpBottom2 = (AnimationDrawable) powerUpBottom2.getBackground();
        frameAnimationPowerUpBottom2.start(); */

        moveBackground();

        randomizer();
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------MOVING BACKGROUND

    public void moveBackground(){
        View backgroundView = findViewById(R.id.background);
        Transition backgroundTrans = new ChangeBounds();
        backgroundTrans.setDuration(20000L);
        //TransitionManager.beginDelayedTransition(relative);
    }


    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------MOVING THE OWL

    public void onClickUpButton(View view) {
        if (!currentPlace.equals("top")) {
            moveUp = true;

            new CountDownTimer(200L, 100){

                public void onTick(long millisUntilFinished){
                    upButton.setImageResource(R.drawable.up_arrow_pressed);
                }

                public void onFinish(){
                    upButton.setImageResource(R.drawable.up_arrow_unpressed);
                }

            }.start();

            moveOwl();
        }

    }

    public void onClickDownButton(View view) {
        if (!currentPlace.equals("bottom")) {
            moveDown = true;

            new CountDownTimer(200L, 100){

                public void onTick(long millisUntilFinished){
                    downButton.setImageResource(R.drawable.down_arrow_pressed);
                }

                public void onFinish(){
                    downButton.setImageResource(R.drawable.down_arrow_unpressed);
                }

            }.start();

            moveOwl();
        }

    }

    public void moveOwl() {
        upButton.setEnabled(false);
        downButton.setEnabled(false);

        View owlView = findViewById(R.id.owl);

        final Transition owlTrans = new ChangeBounds();
        owlTrans.setDuration(owlSpeed);

        TransitionManager.beginDelayedTransition(relativeLayoutOwl, owlTrans);

        final float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        int pixelsWidth = (int) (60 * scale + 0.5f);
        int pixelsHeight = (int) (46 * scale + 0.5f);

        //RelativeLayout.LayoutParams owlPosition = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        RelativeLayout.LayoutParams owlPosition = new RelativeLayout.LayoutParams(pixelsWidth, pixelsHeight);

        if (moveUp) {
            if (currentPlace.equals("center")) {
                owlPosition.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                owlPosition.addRule(RelativeLayout.RIGHT_OF, R.id.space_topleft);
                currentPlace = "top";
            } else if (currentPlace.equals("bottom")) {
                owlPosition.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                owlPosition.addRule(RelativeLayout.RIGHT_OF, R.id.space_center);
                currentPlace = "center";
            }
        } else if (moveDown) {
            if (currentPlace.equals("top")) {
                owlPosition.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                owlPosition.addRule(RelativeLayout.RIGHT_OF, R.id.space_center);
                currentPlace = "center";
            } else if (currentPlace.equals("center")) {
                owlPosition.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
                owlPosition.addRule(RelativeLayout.RIGHT_OF, R.id.space_bottomleft);
                currentPlace = "bottom";
            }
        }

        owlView.setLayoutParams(owlPosition);
        moveDown = false;
        moveUp = false;

        new CountDownTimer(owlSpeed - 50L, 100) {

            public void onTick(long millisUntilFinished) {
                inTransition = true;
            }

            public void onFinish() {
                inTransition = false;
                upButton.setEnabled(true);
                downButton.setEnabled(true);
            }

        }.start();

        //-----------------------------------------------------------------------------------------------------------------------------------------------------------------CHOOSING POWER UPS

    }

    public void randomizer() {
        int low0 = 0;
        int high0 = powerUpProb;
        result0 = low0 + (int) (Math.random() * ((high0 - low0) + 1));

        if (result0 == 0) {
            int low1 = 0; //-------------------------------------------------------------------Should be 0 and 2. 0 for Top, 1 for Center, 2 for Bottom
            int high1 = 2;
            result1 = low1 + (int) (Math.random() * ((high1 - low1) + 1));

            if (result1 == 0) {
                if (top0Ready || top1Ready || top2Ready) {
                    chooseRow = "top";
                    createPowerUp();
            } else {
                    new CountDownTimer(featherDelay, 100) {

                        public void onTick(long millisUntilFinished) {

                        }

                        public void onFinish() {
                            randomizer();
                        }

                    }.start();
                }
            } else if (result1 == 1) {
                if (center0Ready || center1Ready || center2Ready){
                    chooseRow = "center";
                    createPowerUp();
                }else{
                    new CountDownTimer(featherDelay, 100) {

                        public void onTick(long millisUntilFinished) {

                        }

                        public void onFinish() {
                            randomizer();
                        }

                    }.start();
                }
            } else if (result1 == 2) {
                if (bottom0Ready || bottom1Ready || bottom2Ready){
                    chooseRow = "bottom";
                    createPowerUp();
                }else{
                    new CountDownTimer(featherDelay, 100) {

                        public void onTick(long millisUntilFinished) {

                        }

                        public void onFinish() {
                            randomizer();
                        }

                    }.start();
                }

            }

            //createPowerUp();

        } else {
            new CountDownTimer(featherDelay, 100) {

                public void onTick(long millisUntilFinished) {

                }

                public void onFinish() {
                    randomizer();
                }

            }.start();

        }

        testText.setText(String.valueOf(result0) + " " + String.valueOf(result1) + " " + chooseRow);


    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------CREATING POWER UPS

    public void createPowerUp() {
        final Transition movePowerUpTrans = new ChangeBounds();
        movePowerUpTrans.setDuration(powerUpSpeed);

        TransitionManager.beginDelayedTransition(relativeLayoutPowerUp, movePowerUpTrans);

        RelativeLayout.LayoutParams powerUpPositionTop0 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams powerUpPositionTop1 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams powerUpPositionTop2 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams powerUpPositionCenter0 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams powerUpPositionCenter1 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams powerUpPositionCenter2 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams powerUpPositionBottom0 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams powerUpPositionBottom1 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams powerUpPositionBottom2 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        //powerUpTopPosition.addRule(RelativeLayout.RIGHT_OF, R.id.space_topleft_power_up);
        //powerUpTopPosition.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

        //powerUpTopView.setLayoutParams(powerUpTopPosition);

        if (chooseRow.equals("top")) {
            if (top0Ready){
                powerUpView = findViewById(R.id.power_up_top);
                powerUpPositionTop0.addRule(RelativeLayout.RIGHT_OF, R.id.space_topleft_power_up);
                powerUpPositionTop0.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                powerUpView.setVisibility(View.VISIBLE);
                powerUpView.setLayoutParams(powerUpPositionTop0);
                top0Ready = false;
            }else if (top1Ready){
                powerUpView = findViewById(R.id.power_up_top_1);
                powerUpPositionTop1.addRule(RelativeLayout.RIGHT_OF, R.id.space_topleft_power_up);
                powerUpPositionTop1.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                powerUpView.setVisibility(View.VISIBLE);
                powerUpView.setLayoutParams(powerUpPositionTop1);
                top1Ready = false;
            }else if (top2Ready){
                powerUpView = findViewById(R.id.power_up_top_2);
                powerUpPositionTop2.addRule(RelativeLayout.RIGHT_OF, R.id.space_topleft_power_up);
                powerUpPositionTop2.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                powerUpView.setVisibility(View.VISIBLE);
                powerUpView.setLayoutParams(powerUpPositionTop2);
                top2Ready = false;
            }
        } else if (chooseRow.equals("center")) {
            if (center0Ready){
                powerUpView = findViewById(R.id.power_up_center);
                powerUpPositionCenter0.addRule(RelativeLayout.RIGHT_OF, R.id.space_center_power_up);
                powerUpPositionCenter0.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                powerUpView.setVisibility(View.VISIBLE);
                powerUpView.setLayoutParams(powerUpPositionCenter0);
                center0Ready = false;
            }else if(center1Ready){
                powerUpView = findViewById(R.id.power_up_center_1);
                powerUpPositionCenter1.addRule(RelativeLayout.RIGHT_OF, R.id.space_center_power_up);
                powerUpPositionCenter1.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                powerUpView.setVisibility(View.VISIBLE);
                powerUpView.setLayoutParams(powerUpPositionCenter1);
                center1Ready = false;
            }else if (center2Ready){
                powerUpView = findViewById(R.id.power_up_center_2);
                powerUpPositionCenter2.addRule(RelativeLayout.RIGHT_OF, R.id.space_center_power_up);
                powerUpPositionCenter2.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                powerUpView.setVisibility(View.VISIBLE);
                powerUpView.setLayoutParams(powerUpPositionCenter2);
                center2Ready = false;
            }
        } else if (chooseRow.equals("bottom")) {
            if (bottom0Ready){
                powerUpView = findViewById(R.id.power_up_bottom);
                powerUpPositionBottom0.addRule(RelativeLayout.RIGHT_OF, R.id.space_bottomleft_power_up);
                powerUpPositionBottom0.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
                powerUpView.setVisibility(View.VISIBLE);
                powerUpView.setLayoutParams(powerUpPositionBottom0);
                bottom0Ready = false;
            }else if (bottom1Ready){
                powerUpView = findViewById(R.id.power_up_bottom_1);
                powerUpPositionBottom1.addRule(RelativeLayout.RIGHT_OF, R.id.space_bottomleft_power_up);
                powerUpPositionBottom1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
                powerUpView.setVisibility(View.VISIBLE);
                powerUpView.setLayoutParams(powerUpPositionBottom1);
                bottom1Ready = false;
            }else if (bottom2Ready){
                powerUpView = findViewById(R.id.power_up_bottom_2);
                powerUpPositionBottom2.addRule(RelativeLayout.RIGHT_OF, R.id.space_bottomleft_power_up);
                powerUpPositionBottom2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
                powerUpView.setVisibility(View.VISIBLE);
                powerUpView.setLayoutParams(powerUpPositionBottom2);
                bottom2Ready = false;
            }

        }

        feathersReleased += 1;
        testText2.setText(String.valueOf(feathersReleased));

        if (!gameOver){
            waitForPowerUp(powerUpView);

            new CountDownTimer(featherDelay, 100) {

                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    randomizer();
                    //checkPowerUp(powerUpView);
                }

            }.start();
        }

        //powerUpView.setLayoutParams(powerUpPosition);



    }

    public void waitForPowerUp(final View powerUpView) { //----------------------------------------------------------------------------------------------CREATING FEATHER ANIMATIONS
        /*int low2 = 0;
        int high2 = 7;
        final int chosenFeather = low2 + (int) (Math.random() * ((high2 - low2) + 1)); */

        int len = featherList.size();
        Random random = new Random();
        int randomFeatherIndex = random.nextInt(len);
        final int chosenFeather = featherList.get(randomFeatherIndex);

        if (chosenFeather == 0){
            powerUpView.setBackgroundResource(R.drawable.feather_sparrow_animation);
        }else if (chosenFeather == 1){
            powerUpView.setBackgroundResource(R.drawable.feather_bluebird_animation);
        }else if (chosenFeather == 2){
            powerUpView.setBackgroundResource(R.drawable.feather_parrot_animation);
        }else if (chosenFeather == 3){
            powerUpView.setBackgroundResource(R.drawable.feather_dove_animation);
        }else if (chosenFeather == 4){
            powerUpView.setBackgroundResource(R.drawable.feather_owl_animation);
        }else if (chosenFeather == 5){
            powerUpView.setBackgroundResource(R.drawable.feather_flappy_bird_animation);
        }else if (chosenFeather == 6){
            powerUpView.setBackgroundResource(R.drawable.feather_trash_dove_animation);
        }else if (chosenFeather == 7){
            powerUpView.setBackgroundResource(R.drawable.feather_derpy_pony_animation);
        }

        AnimationDrawable frameAnimationFeather = (AnimationDrawable) powerUpView.getBackground();
        frameAnimationFeather.start();

        new CountDownTimer(powerUpSpeed - 350L, 100) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                powerUpId = powerUpView.getId();

                if (!gameOver){
                    if (powerUpId.equals(powerUpTopId) || powerUpId.equals(powerUpTop1Id) || powerUpId.equals(powerUpTop2Id)) {
                        checkPowerUp(powerUpView, "top", chosenFeather);
                    } else if (powerUpId.equals(powerUpCenterId) || powerUpId.equals(powerUpCenter1Id) || powerUpId.equals(powerUpCenter2Id)) {
                        checkPowerUp(powerUpView, "center", chosenFeather);
                    } else if (powerUpId.equals(powerUpBottomId) || powerUpId.equals(powerUpBottom1Id) || powerUpId.equals(powerUpBottom2Id)) {
                        checkPowerUp(powerUpView, "bottom", chosenFeather);
                    }
                }
            }
        }.start();
    }

    public void checkPowerUp(final View powerUpView, String powerUpPlace, Integer chosenFeather) { //---------------------------------------------------------------------------CHECKING POWER UPS
        if (powerUpPlace.equals(currentPlace)) {
            //powerUpView.setVisibility(View.INVISIBLE);

            if (!gameOver){
                final MediaPlayer featherSoundEffect = MediaPlayer.create(this, R.raw.feather_sound_effect);
                featherSoundEffect.start();
                featherSoundEffect.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        featherSoundEffect.release();
                    }
                });

                feathersCollected += 1;

                saveFeather(chosenFeather);

                testText1.setText(String.valueOf(feathersCollected));
                powerUpView.setVisibility(View.INVISIBLE);

                increasePowerUpProb();

                returnPowerUp(powerUpView);
            }

        } else {
            frameAnimationBird.stop();
            gameOver = true;
            //owl.setBackgroundResource(R.drawable.owl_center);
            upButton.setEnabled(false);
            downButton.setEnabled(false);
            testText2.setText("game over");
            homeButton.setEnabled(false);
            gameOverText.setVisibility(View.VISIBLE);

            if (!gameOverHome && gameStart){

                new CountDownTimer(1500, 100) {

                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        switchActivity();
                        //checkPowerUp(powerUpView);
                    }

                }.start();

            }

        }

    }

    public void saveFeather(Integer chosenFeather){
        if (chosenFeather == 0){
            sparrowFeather ++;
        }else if (chosenFeather == 1){
            bluebirdFeather ++;
        }else if (chosenFeather == 2){
            parrotFeather ++;
        }else if (chosenFeather == 3){
            doveFeather ++;
        }else if (chosenFeather == 4){
            owlFeather ++;
        }else if (chosenFeather == 5){
            flappyBirdFeather ++;
        }else if (chosenFeather == 6){
            trashDoveFeather ++;
        }else if (chosenFeather == 7){
            derpyPonyFeather ++;
        }
        //testText2.setText(String.valueOf(sparrowFeather) + String.valueOf(bluebirdFeather) + String.valueOf(parrotFeather) + String.valueOf(doveFeather) + String.valueOf(owlFeather) +
                //String.valueOf(flappyBirdFeather) + String.valueOf(trashDoveFeather) + String.valueOf(derpyPonyFeather));
    }

    public void increasePowerUpProb() { //-----------------------------------------------------------------------------------------------------INCREASING POWER UP PROBABILITY
        if  (feathersCollected.equals(50)){
            featherDelay = 500L;
        }else if (feathersCollected.equals(40)){
            powerUpProb = 0;
        }else if (feathersCollected.equals(30)){
            featherDelay = 550L;
        }else if (feathersCollected.equals(20)){
            powerUpProb = 1;
        }else if (feathersCollected.equals(10)){
            featherDelay = 575L;
        }
    }

    public void returnPowerUp(View powerUpView) { //------------------------------------------------------------------------------------------RETURNING POWER UPS TO ORIGINAL POSITION

        final Transition movePowerUpTrans = new ChangeBounds();
        movePowerUpTrans.setDuration(0L);

        TransitionManager.beginDelayedTransition(relativeLayoutPowerUp, movePowerUpTrans);

        RelativeLayout.LayoutParams powerUpPosition = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        Integer powerUpId = powerUpView.getId();

        powerUpPosition.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
        if (powerUpId.equals(powerUpTopId) || powerUpId.equals(powerUpTop1Id) || powerUpId.equals(powerUpTop2Id)) {
            powerUpPosition.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        } else if (powerUpId.equals(powerUpCenterId) || powerUpId.equals(powerUpCenter1Id) || powerUpId.equals(powerUpCenter2Id)) {
            powerUpPosition.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        } else if (powerUpId.equals(powerUpBottomId) || powerUpId.equals(powerUpBottom1Id) || powerUpId.equals(powerUpBottom2Id)) {
            powerUpPosition.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        }

        if (powerUpId.equals(powerUpTopId)){
            top0Ready = true;
        }else if (powerUpId.equals(powerUpTop1Id)){
            top1Ready = true;
        }else if (powerUpId.equals(powerUpTop2Id)){
            top2Ready = true;
        }else if (powerUpId.equals(powerUpCenterId)){
            center0Ready = true;
        }else if (powerUpId.equals(powerUpCenter1Id)){
            center1Ready = true;
        }else if (powerUpId.equals(powerUpCenter2Id)){
            center2Ready = true;
        }else if (powerUpId.equals(powerUpBottomId)){
            bottom0Ready = true;
        }else if (powerUpId.equals(powerUpBottom1Id)){
            bottom1Ready = true;
        }else if (powerUpId.equals(powerUpBottom2Id)){
            bottom2Ready = true;
        }

        powerUpView.setLayoutParams(powerUpPosition);

    }

    public void switchActivity(){
        Intent i = new Intent(this, EndScreen.class);
        i.putExtra("sparrowFeathers", sparrowFeather);
        i.putExtra("bluebirdFeathers", bluebirdFeather);
        i.putExtra("parrotFeathers", parrotFeather);
        i.putExtra("doveFeathers", doveFeather);
        i.putExtra("owlFeathers", owlFeather);
        i.putExtra("flappyBirdFeathers", flappyBirdFeather);
        i.putExtra("trashDoveFeathers", trashDoveFeather);
        i.putExtra("derpyPonyFeathers", derpyPonyFeather);
        i.putExtra("totalFeathers", feathersCollected);

        startActivity(i);
    }

    public void onClickHomeButton(View view){
        gameOverHome = true;
        Intent i = new Intent(this, HomeScreen.class);
        startActivity(i);
    }

    public void onClickTestButton(View view){
        Intent i = new Intent (this, ChooseBird.class);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        backgroundMusic.start();
        backgroundMusic.setLooping(true);
        gameStart = false;
        super.onResume();
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
}
