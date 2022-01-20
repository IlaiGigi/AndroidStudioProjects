package com.example.workermanagerexample;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.app.Activity;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.TimePicker;

import java.time.LocalTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btPushWork;
    CheckBox cbRepetitive;
    TextView tvSwitchActivity;
    int LAUNCH_SECOND_ACTIVITY;
    int scheduleHour, scheduleMinute;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btPushWork = findViewById(R.id.btPushWork);
        cbRepetitive = findViewById(R.id.cbRepetitive);
        tvSwitchActivity = findViewById(R.id.tvSwitchActivity);

        btPushWork.setOnClickListener(this);
        tvSwitchActivity.setOnClickListener(this);

        LAUNCH_SECOND_ACTIVITY = 1;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public int calcDelay(int hour, int minute){
        Date date = Calendar.getInstance().getTime();
        int currentMinute = date.getMinutes();
        int currentHour = date.getHours();
        return Math.abs(currentHour - hour) * 60 + minute - currentMinute;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        if (view == btPushWork){
            int delay = calcDelay(scheduleHour, scheduleMinute);
            if (!cbRepetitive.isChecked())
                initializeOneTimeWork(delay);
            else
                initializeRepetitiveWork(delay);
        }
        if (view == tvSwitchActivity){
            Intent intent = new Intent(this, TimeSelectActivity.class);
            startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY);
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
                new PeriodicWorkRequest.Builder(MyWorker.class, 15, TimeUnit.MINUTES)
                        .setInitialDelay(delay, TimeUnit.MINUTES)
                        .build();
        WorkManager
                .getInstance(this)
                .enqueue(myWorkRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK){
                assert data != null;
                int[] timestamp = data.getIntArrayExtra("result");
                scheduleHour = timestamp[0];
                scheduleMinute = timestamp[1];
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // Write your code if there's no result
            }
        }
    }
}