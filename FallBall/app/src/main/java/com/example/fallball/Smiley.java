package com.example.fallball;

import android.content.Context;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

public class Smiley extends androidx.appcompat.widget.AppCompatImageView {

    public static int[] images = {R.drawable.regular_face, R.drawable.empty, R.drawable.frozen_face, R.drawable.guard_face, R.drawable.happy_face, R.drawable.joker_face, R.drawable.king_face, R.drawable.lock_face, R.drawable.plus_bomb_face, R.drawable.plus_bomb_face, R.drawable.princess_face, R.drawable.down_face, R.drawable.row_bomb_face, R.drawable.sad_face, R.drawable.shock_face, R.drawable.sick_face, R.drawable.up_face};
    private int type;
    private int indexInRow;

    public Smiley(@NonNull Context context, int type, int indexInRow) {
        super(context);
        this.type = type;
        this.indexInRow = indexInRow;
        this.setBackgroundResource(R.drawable.yellowoval); // Set the smiley's background
        this.setImageResource(images[type]); // Set the smiley's face

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(Utils.dpToPx(context,40), Utils.dpToPx(context,40));
        setLayoutParams(params);
    }

    public int getType() {return this.type;}

    public int getIndexInRow() {return this.indexInRow;}

    public void setType(int aType) {this.type = aType;}

    public void setIndexInRow(int aIndex) {this.indexInRow = aIndex;}

    public void changeToHappy() {this.setImageResource(R.drawable.happy_face);}

    public void changeToSad() {this.setImageResource(R.drawable.sad_face);}

    public void smileyClicked(){
        if (images[this.type] == R.drawable.regular_face)
            this.setImageResource(R.drawable.shock_face);
    }
}
