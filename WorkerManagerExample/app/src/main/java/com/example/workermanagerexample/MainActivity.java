package com.example.workermanagerexample;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TimePicker;

import java.time.LocalTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TimePicker picker;
    Button btPushWork;
    CheckBox cbRepetitive;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        picker = findViewById(R.id.timePicker1);
        btPushWork = findViewById(R.id.btPushWork);
        cbRepetitive = findViewById(R.id.cbRepetitive);

        btPushWork.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public int calcDelay(int hour, int minute){
        Date date = Calendar.getInstance().getTime();
        int currentMinute = date.getMinutes();
        int currentHour = date.getHours();
        int minDiff = minute > currentMinute ? minute - currentMinute : currentHour - minute;
        return Math.abs(currentHour - hour) * 60 + minDiff;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        if (view == btPushWork){
            int scheduleHour = picker.getCurrentHour();
            int scheduleMinute = picker.getCurrentMinute();
            int delay = calcDelay(scheduleHour, scheduleMinute);
            if (!cbRepetitive.isChecked())
                initializeOneTimeWork(delay);
            else
                initializeRepetitiveWork(delay);
        }
    }

    public void initializeOneTimeWork(int delay){
        WorkRequest myWorkRequest =
                new OneTimeWorkRequest.Builder(MyWorker.class)
                        .setInitialDelay(delay, TimeUnit.MINUTES)
                        .build();
        WorkManager
                .getInstance(this)
                .enqueue(myWorkRequest);
    }

    public void initializeRepetitiveWork(int delay){
        WorkRequest myWorkRequest =
                new PeriodicWorkRequest.Builder(MyWorker.class, 3, TimeUnit.MINUTES)
                        .setInitialDelay(delay, TimeUnit.MINUTES)
                        .build();
        WorkManager
                .getInstance(this)
                .enqueue(myWorkRequest);
    }
}