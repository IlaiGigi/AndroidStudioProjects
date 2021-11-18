package com.example.go_fish_game;
import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
public class Action extends AppCompatImageView {
    int type;
    int maxDistance;
    int affectSize;
    boolean selected;
    int amount;
    Context context;

    public Action(@NonNull Context context, int type) {
        super(context);
        this.type = type;
        this.context = context;
    }

    public void select() {
        String str = "fisherman" + String.valueOf(type);
        int resourceViewID = getResources().getIdentifier(str, "id", context.getPackageName());
        ((Activity) context).findViewById(resourceViewID).setBackgroundResource(R.drawable.green_square);

    }

    public void deselect() {
        String str = "fisherman" + String.valueOf(type);
        int resourceViewID = getResources().getIdentifier(str, "id", context.getPackageName());
        ((Activity) context).findViewById(resourceViewID).setBackgroundResource(R.drawable.black_square);
            }
public int GetId()
{
    return  getResources().getIdentifier("fisherman" + String.valueOf(type), "id", context.getPackageName());
}


    }

