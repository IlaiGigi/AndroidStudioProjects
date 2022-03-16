package com.example.fallball;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class CitizensStatusService extends Service implements Runnable{

    private int maxTime;
    private int currentTime;
    NotificationCompat.Builder statusBarNotification;
    Thread statusBarUpdateThread;
    int state;

    @Override
    public void onCreate() {
        super.onCreate();
        maxTime = 15 * 60; // 15 minutes * 60 seconds per minute
        currentTime = maxTime;
        createNotificationChannel();
        statusBarNotification = new NotificationCompat.Builder(this, "CHANNEL_ID")
                .setSmallIcon(R.drawable.amongusdrip)
                .setContentTitle("Energy Status")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setProgress(100, 0, false);
        int notificationId = 1;
        startForeground(notificationId, statusBarNotification.build());
        startStatusBarNotificationUpdateThread();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("dani", String.valueOf(intent.getStringExtra("Call") == null));
        assert intent != null;
        if (intent.getStringExtra("Call").equals("InnerGameCall")){
            statusBarNotification.setProgress(maxTime, currentTime, false);
            state = -1;
        }
        else if (intent.getStringExtra("Call").equals("OuterGameCall")) {
            statusBarNotification.setProgress(maxTime, currentTime, false);
            state = 1;
        }
        return START_REDELIVER_INTENT;
    }

    private void startStatusBarNotificationUpdateThread(){
        statusBarUpdateThread = new Thread(this);
        statusBarUpdateThread.start();
    }

    private void stopStatusBarNotificationUpdateThread(){
        statusBarUpdateThread.stop();
    }

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(30 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (currentTime >= 0 && currentTime <= maxTime) currentTime += 30 * state;
            else{
                if (currentTime < 0){
                    Utils.createGameOverDialog(getApplicationContext());
                    statusBarNotification.setContentTitle("Energy Depleted");
                }
                else statusBarNotification.setContentTitle("Energy Full");
                this.stopSelf();
                statusBarUpdateThread.stop();
                startForeground(1, statusBarNotification.build());
                break;
            }
            statusBarNotification.setProgress(maxTime, currentTime, false);
            startForeground(1, statusBarNotification.build());
        }
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
