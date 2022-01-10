package com.example.serviceexample;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyService extends Service {
    private MyThread myThread;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        myThread.start();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast toast = Toast.makeText(getApplicationContext(),
                "Stopped",
                Toast.LENGTH_SHORT);
        toast.show();
        myThread.interrupt();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myThread = new MyThread(this);
    }
}
