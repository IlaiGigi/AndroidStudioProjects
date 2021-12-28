package com.example.broadcastrecieveexample;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;


class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED) || action.equals(Intent.ACTION_AIRPLANE_MODE_CHANGED))
        {
            final int btState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                    BluetoothAdapter.ERROR);
            final boolean amState = intent.getBooleanExtra("state", false);
            if (btState == BluetoothAdapter.STATE_OFF && !amState){
                MainActivity.btSendStatic.setBackgroundColor(Color.parseColor("#D5413E"));
            }
            else{
                MainActivity.btSendStatic.setBackgroundColor(Color.parseColor("#AAAAAA"));
            }
        }
    }
}