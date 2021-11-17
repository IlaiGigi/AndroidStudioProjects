package com.example.testsimulation;

import android.graphics.drawable.Drawable;

import androidx.appcompat.app.AppCompatActivity;

public class Tools extends AppCompatActivity{
    public String[] actionsList = {"move", "throwrod", "throwbait", "thrownet", "repair", "swim", "attack"};
    final int[] imgs = {R.drawable.paddles, R.drawable.fishing_rod, R.drawable.bait, R.drawable.net, R.drawable.repair, R.drawable.swim, R.drawable.attak};
    public int getImage(int type){
        return this.imgs[type];
    }
}
