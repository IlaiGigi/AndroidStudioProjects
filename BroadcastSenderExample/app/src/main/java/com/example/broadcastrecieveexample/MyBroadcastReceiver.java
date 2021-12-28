package com.example.broadcastrecieveexample;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiManager;


class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED) || action.equals(Intent.ACTION_AIRPLANE_MODE_CHANGED) || action.equals(WifiManager.WIFI_STATE_CHANGED_ACTION))
        {
            final int btState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                    BluetoothAdapter.ERROR);
            final boolean amState = intent.getBooleanExtra("state", false);
            WifiManager wifiManager = ((WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE));
            final int wifiState = wifiManager.getWifiState();
            if (btState == BluetoothAdapter.STATE_OFF || amState || wifiState == WifiManager.WIFI_STATE_DISABLED){
                MainActivity.btSendStatic.setBackgroundColor(Color.parseColor("#D5413E"));
            }
            else{
                MainActivity.btSendStatic.setBackgroundColor(Color.parseColor("#AAAAAA"));
            }
        }
    }
}