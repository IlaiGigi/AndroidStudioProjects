package com.example.dontannoythesquare;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Random;

public class Square extends androidx.appcompat.widget.AppCompatImageView implements Runnable, View.OnClickListener{
    private Point pos;
    private Context context;
    private Handler handler;
    private Random random;
    private Thread thread;
    private boolean isMoving;
    private int screenHeight, screenWidth;
    private int score;
    public Square(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.setOnClickListener(this);
        Drawable background = getResources().getDrawable(R.drawable.whitesquare);
        this.setBackground(background);
        this.screenHeight = getScreenDimensions(context)[0];
        this.screenWidth = getScreenDimensions(context)[1];
        this.pos = new Point(screenWidth, screenHeight);
        this.handler = new Handler();
        this.random = new Random();
        this.thread = new Thread(this);
        this.isMoving = false;
        this.score = 0;
        this.thread.start();
    }

    @Override
    public void run() {
        while (true){
            int movX = random.nextInt(100);
            int movY = random.nextInt(300);
            if (this.pos.x + movX > screenWidth - 50){
                movX = screenWidth - 50;
            }
            if (this.pos.y + movY > screenHeight - 50){
                movY = screenHeight - 50;
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view == this){
            if (!this.isMoving){
                this.score++;
            }
            else{
                this.score--;
            }
            MainActivity.tvScore.setText(String.format("Score: %d", this.score));
        }
        if (view == MainActivity.tvScore){
            animateScore();
        }
    }
    private int[] getScreenDimensions(Context context)
    {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        return new int[]{height, width};
    }
    private void animateScore(){
        ValueAnimator animator = ValueAnimator.ofInt(0, this.score);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (int)valueAnimator.getAnimatedValue();
                MainActivity.tvScore.setText(String.format("Score: %d", val));
            }
        });
        animator.start();
    }
    public RelativeLayout.LayoutParams getParams(){
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = this.pos.x;
        params.topMargin = this.pos.y;
        return params;
    }
    private int dpToPx(int dps)
    {
        // Get the screen's density scale
        final float scale =
                this.context.getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        int pixels = (int) (dps * scale + 0.5f);
        return pixels;
    }
}
