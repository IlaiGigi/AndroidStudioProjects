package com.example.broadcastrecieverexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("com.example.broadcastrecieveexample.WEATHERCHANGE")){
            String message = intent.getStringExtra("data");
            MainActivity.tvOutput.setText(String.format("Output: %s", message));
        }
    }
}
