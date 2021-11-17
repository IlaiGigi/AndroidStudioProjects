package com.example.testsimulation;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Action extends androidx.appcompat.widget.AppCompatImageView {
    protected int type;
    protected int maxDistance;
    protected int affectSize;
    protected boolean selected;
    protected int amount;
    public Action(@NonNull Context context, int type) {
        super(context);
        // The type will be an index in the actions array
        this.type = type;

        if (type == 0){
            this.maxDistance = 2;
            this.affectSize = 1; // There is no range of effect during the move
            this.amount = -1;
        }
        else if (type == 1){
            this.maxDistance = 2;
            this.amount = -1;
            this.affectSize =1;
        }
        else if (type == 2){
            this.maxDistance = 1;
            this.amount = 4;
            this.affectSize = 1;
        }
        else if (type == 3){
            this.maxDistance = 1;
            this.affectSize = 2;
            this.amount = 3;
        }
        else if (type == 4){
            this.maxDistance = 1;
            this.affectSize = 1;
            this.amount = -1;
        }
        else if (type == 5){
            this.maxDistance = -1; // Can go any distance
            this.amount = 3;
            this.affectSize = 1;
        }
        Tools t1 = new Tools();
        setBackground(getResources().getDrawable(R.drawable.blacksquare));
        setImageResource(t1.getImage(type));
    }
    public void select(){
        this.selected = true;
    }
    public void deselect(){
        this.selected = false;
    }
}
