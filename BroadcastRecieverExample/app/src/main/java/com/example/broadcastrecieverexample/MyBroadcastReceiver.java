package com.example.broadcastrecieverexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("com.example.broadcastrecieveexample.WEATHERCHANGE")){
            String message = intent.getStringExtra("textData");
            Drawable image = bytesArrayToDrawable(context, intent.getByteArrayExtra("imageData"));
            MainActivity.tvCurrentWeather.setText(message);
            MainActivity.ivCurrentWeather.setBackground(image);
        }
    }

    private Drawable bytesArrayToDrawable(Context context, byte[] imageBytes){
        Bitmap bitmap =
                BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        return new BitmapDrawable(context.getResources(), bitmap);
    }
}
