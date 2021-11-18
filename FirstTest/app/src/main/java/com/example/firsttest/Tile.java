package com.example.firsttest;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class Tile extends androidx.appcompat.widget.AppCompatImageView {
    private Soldier soldier;
    private Point position;
    private boolean isSelected;
    public Tile(Context context, Point position) {
        super(context);
        this.position = position;
        this.soldier = null;
        this.isSelected = false;
        Drawable background = getResources().getDrawable(R.drawable.tile);
        this.setBackground(background);
    }
    public void setSoldier(Soldier soldier){
        this.soldier = soldier;
        soldier.setPosition(this.position);
        if (soldier.getType() == 'A'){
            this.setImageResource(R.drawable.airelement);
        }
        else if (soldier.getType() == 'E'){
            this.setImageResource(R.drawable.earthelement);
        }
        else if (soldier.getType() == 'F'){
            this.setImageResource(R.drawable.fireelement);
        }
        else if (soldier.getType() == 'W'){
            this.setImageResource(R.drawable.waterelement);
        }
    }
    public void removeSolider(){
        this.soldier = null;
        this.setImageResource(0);
    }
    public boolean hasSoldier(){
        if (this.soldier != null)
            return true;
        return false;
    }
    public void select(){
        Drawable background = getResources().getDrawable(R.drawable.selectedtile);
        this.setBackground(background);
        this.isSelected = true;
    }
    public void deselect(){
        Drawable background = getResources().getDrawable(R.drawable.tile);
        this.setBackground(background);
        this.isSelected = false;
    }
    // public void select
    // public void deselect
    public Soldier getSoldier(){return this.soldier;}
    public Point getPosition(){return this.position;}
}
