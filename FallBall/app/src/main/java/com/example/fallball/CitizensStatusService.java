package com.example.fallball;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class CitizensStatusService extends Service implements Runnable{

    private final int CHECK_INTERVAL = 30 * 1000; // Check interval = 30 seconds
    private final int MAX_TIME = 15 * 60; // 15 minutes * 60 seconds per minute
    private int currentTime;
    private NotificationCompat.Builder statusBarNotification;
    private Thread statusBarUpdateThread;
    private int state;
    private Handler handler;
    private boolean run;
    private NotificationManager notificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        arabWork();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        assert intent != null;
        if (!run) startStatusBarNotificationUpdateThread();
        if (intent.getStringExtra("Call").equals("InnerGameCall")){
            statusBarNotification.setProgress(MAX_TIME, currentTime, false);
            state = -1;
        }
        else if (intent.getStringExtra("Call").equals("OuterGameCall")) {
            statusBarNotification.setProgress(MAX_TIME, currentTime, false);
            state = 1;
        }
        return START_REDELIVER_INTENT;
    }

    public void arabWork(){
        currentTime = MAX_TIME;
        handler = new Handler();
        run = true;
        createNotificationChannel();
        statusBarNotification = new NotificationCompat.Builder(this, "CHANNEL_ID")
                .setSmallIcon(R.drawable.amongusdrip)
                .setContentTitle("Energy Status")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setProgress(100, 0, false);
        int notificationId = 1;
        startForeground(notificationId, statusBarNotification.build());
        startStatusBarNotificationUpdateThread();
        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(2);
    }

    private void startStatusBarNotificationUpdateThread(){
        statusBarUpdateThread = new Thread(this);
        statusBarUpdateThread.start();
    }

    private void stopStatusBarNotificationUpdateThread(){
        run = false;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void run() {
        while (run){
            Log.d("yosi", "Got in");
            try {
                Thread.sleep(CHECK_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (currentTime >= 0 && currentTime <= MAX_TIME) currentTime += 30 * state;
            else stopStatusBarNotificationUpdateThread();
            statusBarNotification.setProgress(MAX_TIME, currentTime, false);
            startForeground(1, statusBarNotification.build());
        }
        if (currentTime < 0){
            handler.post(() -> {
                GameThread.remainingHearts = 0;
            });
            statusBarNotification.setContentTitle("Energy Depleted");
        }
        else{
            statusBarNotification.setContentTitle("Energy Full");
            currentTime = MAX_TIME;
        }
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(1);
        notificationManager.notify(2, statusBarNotification.build());
        stopSelf();
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "ChannelName";
            String description = "ChannelDescription";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel =
                    new NotificationChannel("CHANNEL_ID", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
