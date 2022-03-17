package com.example.fallball;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GameThread extends Thread {

    public static final ArrayList<SmileyRow> smileyRows = new ArrayList<>();
    private final int SLEEP_INTERVAL = 2000;
    private final TextView tvAuthorizedSmileys;
    private final RelativeLayout rowsLayout;
    private final ImageView borderView;
    private final ImageView[] hearts;
    private final TextView tvPoints;
    private final Context context;
    private final Handler handler;
    private final Random random;
    private int currentAuthorizedNumber;
    private int timesToChangeNumber;
    private boolean createMoreRows;
    public static int remainingHearts = 3;
    public static int points = 0;
    public static boolean run = true;

    public GameThread(RelativeLayout rowsLayout, Context context, ImageView borderView, TextView tvAuthorizedSmileys, TextView tvPoints, ImageView[] hearts){
        this.rowsLayout = rowsLayout;
        this.handler = new Handler();
        this.createMoreRows = true;
        this.context = context;
        this.timesToChangeNumber = 3;
        this.borderView = borderView;
        this.tvAuthorizedSmileys = tvAuthorizedSmileys;
        this.tvPoints = tvPoints;
        this.hearts = hearts;
        this.random = new Random();
        this.currentAuthorizedNumber = this.random.nextInt(8) + 1;
        this.tvAuthorizedSmileys.setText(String.valueOf(this.currentAuthorizedNumber));
        this.initializeHeartAnimation(R.drawable.heart_beat_animation, -1);


        this.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void run() {
        super.run();
        while (run){
            if (this.createMoreRows){
                handler.post(() -> {
                    SmileyRow smileyRow = new SmileyRow(this.context, this.hearts);
                    this.rowsLayout.addView(smileyRow);
                    smileyRows.add(smileyRow);
                    checkRowPass();
                });
            }
            try {
                Thread.sleep(SLEEP_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void pauseGame(){
        for (SmileyRow row: smileyRows){
            row.pauseAnimation();
        }
        this.createMoreRows = false;
    }

    public void resumeGame(){
        for (SmileyRow row: smileyRows){
            row.resumeAnimation();
        }
        this.createMoreRows = true;
    }

    public int getNewRandomNumber(){
        if (this.timesToChangeNumber <= 0){
            Toast.makeText(this.context, "No saves left", Toast.LENGTH_SHORT).show();
            return this.currentAuthorizedNumber;
        }
        this.timesToChangeNumber--;
        int num = this.random.nextInt(8) + 1;
        this.currentAuthorizedNumber = num;
        return num;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void checkRowPass(){
        if (smileyRows.get(0).getY() >= this.borderView.getY() - Utils.dpToPx(context, 50) && smileyRows.size() != 1){
            assert remainingHearts != 0;
            boolean valid = smileyRows.get(0).checkSmileyNumber(this.currentAuthorizedNumber);
            this.timesToChangeNumber = 3;
            this.tvAuthorizedSmileys.setText(String.valueOf(this.random.nextInt(8) + 1));
            if (valid){
                points += smileyRows.get(0).getSmileysNum();
                this.tvPoints.setText(String.valueOf(points));
            }
            else{
                points--;
                this.tvPoints.setText(String.valueOf(points));
                this.initializeHeartAnimation(R.drawable.heart_explode_animation, remainingHearts -1);
                this.hearts[remainingHearts -1] = null;
                remainingHearts--;
            }
            smileyRows.remove(0);
        }
        if (remainingHearts == 0){
            this.pauseGame();
            run = false;
            Utils.createGameOverDialog(context);
        }
    }

    private void initializeHeartAnimation(int id, int index){
        // If the index is valid (>=0) then run the animation on a specific heart, else, run it on all hearts
        if (index != -1){
            this.hearts[index].setBackgroundResource(id);
            AnimationDrawable frameAnimation = (AnimationDrawable) this.hearts[index].getBackground();
            frameAnimation.start();
            return;
        }
        for (ImageView heart: this.hearts){
            if (heart != null) {
                heart.setBackgroundResource(id);
                AnimationDrawable frameAnimation = (AnimationDrawable) heart.getBackground();
                frameAnimation.start();
            }
        }
    }
}
