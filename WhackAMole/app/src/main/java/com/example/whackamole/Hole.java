package com.example.whackamole;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Hole extends androidx.appcompat.widget.AppCompatImageView implements Runnable, View.OnClickListener {
    private boolean hasMole;
    public Hole(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.hasMole = false;
        Drawable background = getResources().getDrawable(R.drawable.hole_only);
        this.setBackground(background);
    }

    @Override
    public void run() {

    }
    public boolean doesHaveMole(){return this.hasMole;}
    public void showMole(){
        this.setBackgroundResource(R.drawable.mole_only);
        this.hasMole = true;
    }
    public void hideMole(){
        this.setBackgroundResource(0);
        this.hasMole = false;
    }

    @Override
    public void onClick(View view) {
        if (this.hasMole)
            hideMole();
    }
}
