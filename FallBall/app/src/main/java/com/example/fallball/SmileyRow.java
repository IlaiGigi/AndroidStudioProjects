package com.example.fallball;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

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

    public SmileyRow(Context context) {
        super(context);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT); // The row cannot be longer than the screen itself
        this.setLayoutParams(params);
        this.setX((Utils.getScreenSizePx(context).getWidth() - 8 * Utils.dpToPx(context,40)) / 2); // Center the row in the screen

        this.random = new Random();
        this.context = context;
        this.smileysNum = random.nextInt(9); // Generate the number of smileys in the row
        Log.d("yosi", String.valueOf(smileysNum));
        this.smileysArray = new Smiley[8];
        this.takenIndexes = new ArrayList<>();

        this.animator = ValueAnimator.ofInt(50, Utils.getScreenSizeDp(context).getHeight() - 100); // Leave space at the top, iterate to the bottom of the screen minus the size of the menu bar
        this.animator.setInterpolator(new LinearInterpolator()); // Set the of the animation to be constant
        this.animator.setDuration(1000L * (Utils.getScreenSizeDp(context).getHeight() / 52)); // Way (OUM - dps) / speed (OUM - 52 dps/s) = time (UOM - sec), multiply by 1000 to cast to milliseconds

        // Generate smileys and insert them into the row's array
        for (int i=0; i<this.smileysNum; i++){
            Smiley smiley = generateSmiley(context);
            smiley.setX(smiley.getIndexInRow() * Utils.dpToPx(context,40));
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
        int type = 0; // (Change from constant later)
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

    private void removeSmiley(int index){
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

    public boolean checkSmileyNumber(int authorizedNumber) {
        if (!this.hasSmileys()){
            return authorizedNumber == 0;
        }
        if (this.smileysNum == authorizedNumber){
            for (int i=0; i<8; i++){
                if (this.smileysArray[i] == null)
                    continue;
                this.smileysArray[i].changeToHappy();
                this.smileysArray[i].setClickable(false);
            }
        }
        else{
            for (int i=0; i<8; i++){
                if (this.smileysArray[i] == null)
                    continue;
                this.smileysArray[i].changeToSad();
                this.smileysArray[i].setClickable(false);
            }
        }
        new CountDownTimer(300, 1000) {
            @Override
            public void onTick(long l) {
            }
            @Override
            public void onFinish() {
                ((ViewGroup)getParent()).removeView(getSmileyRow());
            }
        }.start();
        return this.smileysNum == authorizedNumber;
    }
}
