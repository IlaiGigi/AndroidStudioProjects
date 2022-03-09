package com.example.fallball;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;

public class SmileyRow extends RelativeLayout implements View.OnClickListener {

    private final Smiley[] smileysArray;
    private int smileysNum;
    private ValueAnimator animator;
    private final Random random;
    private final ArrayList<Integer> takenIndexes;

    public SmileyRow(Context context) {
        super(context);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT); // The row cannot be longer than the screen itself
        this.setLayoutParams(params);
        this.setX((getScreenSizePx(context).getWidth() - 8 * dpToPx(40)) / 2); // Center the row in the screen

        this.random = new Random();
        this.smileysNum = random.nextInt(9); // Generate the number of smileys in the row
        Log.d("yosi", String.valueOf(smileysNum));
        this.smileysArray = new Smiley[8];
        this.takenIndexes = new ArrayList<>();

        this.animator = ValueAnimator.ofInt(50, getScreenSizeDp(context).getHeight() - 100); // Leave space at the top, iterate to the bottom of the screen minus the size of the menu bar
        this.animator.setInterpolator(new LinearInterpolator()); // Set the of the animation to be constant
        this.animator.setDuration(1000L * (getScreenSizeDp(context).getHeight() / 52)); // Way (OUM - dps) / speed (OUM - 52 dps/s) = time (UOM - sec), multiply by 1000 to cast to milliseconds

        // Generate smileys and insert them into the row's array
        for (int i=0; i<this.smileysNum; i++){
            Smiley smiley = generateSmiley(context);
            smiley.setX(smiley.getIndexInRow() * dpToPx(40));
            smiley.setOnClickListener(this);
            Log.d("yosi", String.valueOf(smiley.getIndexInRow()));
            this.smileysArray[smiley.getIndexInRow()] = smiley;
            this.addView(smiley);
        }

        move(); // Call the animation function
    }

    @Override
    public void onClick(View view) {
        // No other views other than smileys - we can infer view will always be of type Smiley
        Smiley smiley = (Smiley) view;
        smiley.smileyClicked();
        removeSmiley(smiley.getIndexInRow());
    }

    public Smiley generateSmiley(Context context){ // Generate smiley at an unoccupied index
        int possIndex = random.nextInt(8); // Search until an empty index is found
        while (takenIndexes.contains(possIndex)){
            possIndex = random.nextInt(8);
        }
        takenIndexes.add(possIndex);
        // Determine type
        int type = 4; // (Change from constant later)
        return new Smiley(context, type, possIndex);
    }

    public @Nullable Smiley addSmiley(Context context, int index){
        // If the given index is taken, return null
        if (takenIndexes.size() == 8 || takenIndexes.contains(index))
            return null;
        int type = random.nextInt(4);
        Smiley smiley = new Smiley(context, type, index);
        this.addView(smiley);
        return smiley;
    }

    public void removeSmiley(int index){
        if (this.smileysArray[index] == null) // If there's no smiley at the requested index, return null
            return;
        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long l) {
                // Redundant
            }
            @Override
            public void onFinish() {
                deallocateIndex(index);
            }
        }.start();
    }

    public void deallocateIndex(int index){ // Clear a given index of it's smiley
        this.removeView(smileysArray[index]);
        this.smileysArray[index] = null;
        this.takenIndexes.remove((Integer) index);
        this.smileysNum--;
    }

    public void move(){
        this.animator.addUpdateListener(valueAnimator -> {
            int val = (int)valueAnimator.getAnimatedValue();
            this.setY(dpToPx(val));
        });
        animator.start();
    }

    public void pauseAnimation(){
        this.animator.pause();
    }

    public void resumeAnimation(){
        this.animator.resume();
    }

    public int dpToPx(int dp) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (dp * scale + 0.5f);
    }

    private Size getScreenSizePx(Context context) { // Get the screen's dimensions (UOM - pixels)
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        return new Size(width, height);
    }

    private Size getScreenSizeDp(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return new Size((int)dpWidth, (int)dpHeight);
    }

    public boolean hasSmileyes() {return this.takenIndexes.size() != 0;}
}
