package com.example.go_fish_game;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;

public class SeaTile extends AppCompatImageView  {
    int tileNumber;
    boolean hasRipple;
    public SeaTile(@NonNull Context context,int tileNumber) {
        super(context);
        this.tileNumber=tileNumber;
        this.hasRipple=false;
        if(tileNumber%2==0)
            this.setBackgroundResource(R.drawable.sea_tile);
        else
            this.setBackgroundResource(R.drawable.sea_tile2);

    }
    public void addRipple()
    {
        this.hasRipple=true;
        this.setImageResource(R.drawable.ripple);
    }
    public void removeRipple()
    {
        this.hasRipple=false;
        if(tileNumber%2==0)
        this.setImageResource(R.drawable.sea_tile);
        else
            this.setImageResource(R.drawable.sea_tile2);
    }

    public int getTileNumber() {
        return tileNumber;
    }

    public ImageView getImageView() {
        return this;
    }

    public void setImageView(ImageView imageView) {

    }


}
