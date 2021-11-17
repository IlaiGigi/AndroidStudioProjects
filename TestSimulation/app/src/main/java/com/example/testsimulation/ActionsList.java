package com.example.testsimulation;

import android.content.Context;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class ActionsList extends LinearLayout {
    private ArrayList<Action> actions;
    public ActionsList(Context context, int type) {
        super(context);
        Tools t1 = new Tools();
        if (type == 0){
            //Type fisherman
            for (int i=0; i<5; i++){
                this.actions.add(new Action(context, i));
            }
        }
        else{
            //Type fish
            for (int i=5; i<7; i++){
                this.actions.add(new Action(context, i));
            }
        }
    }
    public ArrayList<Action> getActions(){return this.actions;}
}
