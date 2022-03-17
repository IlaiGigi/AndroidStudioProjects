package com.example.fallball;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

abstract class Utils {

    public static Context context;

    public static int dpToPx(Context context,int dp) {
        // Get the screen's density scale
        final float scale = context.getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (dp * scale + 0.5f);
    }

    public static Size getScreenSizeDp(Context context){ // Get the screen's dimensions (OUM - dps)
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return new Size((int)dpWidth, (int)dpHeight);
    }

    public static Size getScreenSizePx(Context context) { // Get the screen's dimensions (UOM - pixels)
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        return new Size(width, height);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void createGameOverDialog(Context context){
        Intent restartIntent = new Intent(context, MainActivity.class);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptView = layoutInflater.inflate(R.layout.game_over_dialog, null);
        final AlertDialog alertD = new AlertDialog.Builder(context).create();
        alertD.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageButton ibRestart = promptView.findViewById(R.id.ibRestart);
        ibRestart.setOnClickListener(view -> {
            context.startActivity(restartIntent);
            GameThread.remainingHearts = 3;
            GameThread.points = 0;
        });
        TextView tvPoints = promptView.findViewById(R.id.tvPoints);
        tvPoints.setText("Points: "+GameThread.points);
        alertD.setView(promptView);
        alertD.setCancelable(false);
        alertD.show();
    }
}
