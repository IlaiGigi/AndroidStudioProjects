package com.example.fallball;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;

public class SmileyRow extends RelativeLayout implements View.OnClickListener {

    private final ArrayList<Integer> takenIndexes;
    private final ValueAnimator animator;
    private final Smiley[] smileysArray;
    private final Random random;
    private final Context context;
    private int smileysNum;
    private ImageView[] hearts;

    @SuppressLint("ResourceType")
    public SmileyRow(Context context, ImageView[] hearts) {
        super(context);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT); // The row cannot be longer than the screen itself
        this.setLayoutParams(params);
        this.setX((Utils.getScreenSizePx(context).getWidth() - 8 * Utils.dpToPx(context,40)) / 2); // Center the row in the screen

        this.random = new Random();
        this.context = context;
        this.smileysNum = random.nextInt(9); // Generate the number of smileys in the row
        this.smileysArray = new Smiley[8];
        this.takenIndexes = new ArrayList<>();
        this.hearts = hearts;

        this.animator = ValueAnimator.ofInt(50, Utils.getScreenSizeDp(context).getHeight() - 100); // Leave space at the top, iterate to the bottom of the screen minus the size of the menu bar
        this.animator.setInterpolator(new LinearInterpolator()); // Set the of the animation to be constant
        this.animator.setDuration(1000L * (Utils.getScreenSizeDp(context).getHeight() / 52)); // Way (OUM - dps) / speed (OUM - 52 dps/s) = time (UOM - sec), multiply by 1000 to cast to milliseconds

        // Generate smileys and insert them into the row's array
        for (int i=0; i<this.smileysNum; i++){
            Smiley smiley = generateSmiley(context);
            smiley.setX(smiley.getIndexInRow() * Utils.dpToPx(context,40));
            smiley.setOnClickListener(this);
            this.smileysArray[smiley.getIndexInRow()] = smiley;
            this.addView(smiley);
        }

        move(); // Call the animation function
    }

    @Override
    public void onClick(View view) {
        // No other views other than smileys - we can infer view will always be of type Smiley
        Smiley smiley = (Smiley) view;
        if (smiley.getType() == 0){
            removeSmiley(smiley.getIndexInRow());
        }
        else if (smiley.getType() == 1){
            removeHeart();
            removeSmiley(smiley.getIndexInRow());
        }
        else if (smiley.getType() == 2){
            for (Smiley smiley1: this.smileysArray){
                if (smiley1 != null) deallocateIndex(smiley1.getIndexInRow());
            }
        }
        else if (smiley.getType() == 3){
            ArrayList<SmileyRow> smileyRows = GameThread.smileyRows;
            int rowIndex = smileyRows.indexOf(this);
            if (rowIndex == smileyRows.size() -1) return;
            if (smileyRows.get(rowIndex+1).getSmileysNum() == 8) return;
            deallocateIndex(smiley.getIndexInRow());
            smileyRows.get(rowIndex+1).addSmiley(smiley);
        }
    }

    public Smiley generateSmiley(Context context){ // Generate smiley at an unoccupied index
        int possIndex = random.nextInt(8); // Search until an empty index is found
        while (takenIndexes.contains(possIndex)){
            possIndex = random.nextInt(8);
        }
        takenIndexes.add(possIndex);
        int type = this.random.nextInt(4); // Determine type
        return new Smiley(context, type, possIndex);
    }

    public void addSmiley(Smiley smiley){
        // If the given index is taken, return null
        int possIndex = random.nextInt(8); // Search until an empty index is found
        while (takenIndexes.contains(possIndex)){
            possIndex = random.nextInt(8);
        }
        takenIndexes.add(possIndex);
        smiley.setIndexInRow(possIndex);
        smiley.setX(smiley.getIndexInRow() * Utils.dpToPx(context,40));
        smiley.setOnClickListener(this);
        this.smileysArray[smiley.getIndexInRow()] = smiley;
        this.addView(smiley);
    }

    private void removeSmiley(int index){
        if (this.smileysArray[index] == null) return; // If there's no smiley at the requested index, return null
        new CountDownTimer(300, 1000) {
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

    private void deallocateIndex(int index){ // Clear a given index of it's smiley
        this.removeView(smileysArray[index]);
        this.smileysArray[index] = null;
        this.takenIndexes.remove((Integer) index);
        this.smileysNum--;
    }

    private void move(){
        animator.addUpdateListener(valueAnimator -> {
            int val = (int)valueAnimator.getAnimatedValue();
            setY(Utils.dpToPx(this.context, val));
        });
        animator.start();
    }

    public void pauseAnimation(){
        this.animator.pause();
    }

    public void resumeAnimation(){
        this.animator.resume();
    }

    public boolean hasSmileys() {return this.takenIndexes.size() != 0;}

    public SmileyRow getSmileyRow() {return this;}

    public int getSmileysNum() {return this.smileysNum;}

    public boolean checkSmileyNumber(int authorizedNumber) {
        for (Smiley smiley: this.smileysArray){
            if (smiley != null) smiley.setClickable(false);
        }
        if (this.smileysNum == 0) return true;
        boolean cond = false;
        if (this.smileysNum == authorizedNumber) cond = true;
        new CountDownTimer(300, 1000) {
            @Override
            public void onTick(long l) {
            }
            @Override
            public void onFinish() {
                ((ViewGroup)getParent()).removeView(getSmileyRow());
            }
        }.start();
        return cond;
    }

    private void removeHeart(){
        this.hearts[GameThread.remainingHearts -1].setBackgroundResource(R.drawable.heart_explode_animation);
        AnimationDrawable frameAnimation = (AnimationDrawable) this.hearts[GameThread.remainingHearts -1].getBackground();
        frameAnimation.start();
    }
}
