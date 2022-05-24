package com.example.finalproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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

    public static SharedPreferences defineSharedPreferences(Context context, String name){
        return context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public static String getDataFromSharedPreferences(SharedPreferences sp, String key, String defaultValue){
        return sp.getString(key, defaultValue); // To switch between output types, change all instances of 'String' to desired type
    }

    public static void insertDataToSharedPreferences(SharedPreferences sp, String key, String data){
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, data);
        editor.commit();
        // To switch between input types, change all instances of 'String' to desired type (excluding 'key')
    }

    public static void changeNotificationBarColor(Activity activity, int color){
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(color);
    }

    public static File createFile(String fileName){
        return new File(fileName);
    }

    public static void writeToFile(File file, String text){
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write((text + System.lineSeparator()).getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFromFile(File file, int readLength){
        byte[] fileContent;
        if (readLength == -1){
            // -1 means no specified length, return all the content in the file
            fileContent = new byte[(int) file.length()];
        }
        else
            fileContent = new byte[readLength];
        try {
            FileInputStream fis = new FileInputStream(file);
            fis.read(fileContent);
            fis.close();
            return new String(fileContent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // In the case of an error, return null
    }

    public static ByteArrayOutputStream readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo;
        } catch (IOException e) {
            return null;
        }
    }
}