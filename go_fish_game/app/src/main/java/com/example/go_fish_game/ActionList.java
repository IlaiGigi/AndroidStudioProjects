package com.example.go_fish_game;

import android.content.Context;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class ActionList extends LinearLayout {
    ArrayList<Action> actionList=new ArrayList<Action>();
    public ActionList(Context context,int player) {
        super(context);
        if(player==2)
        {
            actionList.add(new Action(context,6));
            actionList.add(new Action(context,7));
        }
        else if(player==1)
        {
            actionList.add(new Action(context,1));
            actionList.add(new Action(context,2));
            actionList.add(new Action(context,3));
            actionList.add(new Action(context,4));
            actionList.add(new Action(context,5));

        }
    }

    public ArrayList<Action> getActionList() {
        return actionList;
    }
}