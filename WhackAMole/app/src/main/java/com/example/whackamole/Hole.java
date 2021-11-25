package com.example.whackamole;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.os.Handler;
import android.widget.TextView;

import java.util.Random;

public class Hole extends androidx.appcompat.widget.AppCompatImageView implements Runnable, View.OnClickListener {
    private boolean hasMole;
    private final Handler handler;
    public static Object key = new Object();
    public static Integer numOfActiveMoles = 0;
    public static boolean run = true;
    public static int score = 0;
    private static int lives = 3;
    private final Tools t1;
    private final Thread thread;
    public Hole(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.hasMole = false;
        Drawable background = getResources().getDrawable(R.drawable.hole_only);
        this.setBackground(background);
        this.handler = new Handler();
        this.thread = new Thread(this);
        this.setOnClickListener(this);
        this.t1 = new Tools(context);
        thread.start();
    }

    @Override
    public void run() {
        while (true){
            try {
                Random random = new Random();
                Thread.sleep(random.nextInt(1000) + 2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (key){
                while (numOfActiveMoles == 2){
                    try {
                        key.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (lives <= 0)
                    break;
                numOfActiveMoles++;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        showMole();
                    }
                });
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (lives <= 0)
                break;
            synchronized (key){
                if (hasMole){
                    numOfActiveMoles--;
                    if (lives > 0){
                        score-=50;
                        MainActivity.hearts[lives - 1].setVisibility(INVISIBLE);
                    }
                    lives--;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            hideMole();
                        }
                    });
                }
                else
                    score+=50;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            MainActivity.tvScore.setText(String.format("Score: %d", score));
                        }
                    });
                key.notify();
            }
        }
        // Adding 100 to the score due to the drag created by delayed threads
        handler.post(new Runnable() {
            @Override
            public void run() {
                t1.createAlertDialog("Game Over", String.format("You Lost! Score: %d", score));
            }
        });
    }

    public void showMole(){
        this.setImageResource(R.drawable.mole_only);
        this.hasMole = true;
    }

    public void hideMole(){
        this.setImageResource(0);
        this.hasMole = false;
    }

    @Override
    public void onClick(View view) {
        if (this.hasMole){
            numOfActiveMoles--;
            hideMole();
        }
    }
}
