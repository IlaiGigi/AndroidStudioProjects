package com.example.fallball;

import android.content.Context;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

public class Smiley extends androidx.appcompat.widget.AppCompatImageView {

    public static int[] images = {R.drawable.regular_face, R.drawable.sick_face, R.drawable.row_bomb_face, R.drawable.up_face};
    private int type;
    private int indexInRow;

    public Smiley(@NonNull Context context, int type, int indexInRow) {
        super(context);
        this.type = type;
        this.indexInRow = indexInRow;
        if (type == 0 || type == 3) this.setBackgroundResource(R.drawable.yellowoval);
        else if (type == 1) this.setBackgroundResource(R.drawable.redoval);
        else if (type == 2) this.setBackgroundResource(R.drawable.blackoval);
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
