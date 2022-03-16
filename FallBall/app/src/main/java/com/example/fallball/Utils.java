package com.example.fallball;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Size;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

abstract class Utils {

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

    public static void createGameOverDialog(Context context){
        Intent restartIntent = new Intent(context, MainActivity.class);
        new AlertDialog.Builder(context)
                .setTitle("Game Over")
                .setMessage("Start Over?")
                .setCancelable(false)
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, (dialog, which) -> context.startActivity(restartIntent))
                .setNegativeButton(android.R.string.no, (dialog, which) -> dialog.cancel())
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setIcon(android.R.drawable.ic_dialog_alert).show();
    }
}
