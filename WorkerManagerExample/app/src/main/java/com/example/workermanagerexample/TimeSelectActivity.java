package com.example.workermanagerexample;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

public class TimeSelectActivity extends AppCompatActivity implements View.OnClickListener {

    public TimePicker picker;
    Button btSendTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_select);

        picker = findViewById(R.id.myTimePicker);
        btSendTime = findViewById(R.id.btSendTime);

        btSendTime.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        if (view == btSendTime){
            int hour = picker.getHour();
            int minute = picker.getMinute();
            int[] timestamp = {hour, minute};
            Intent returnIntent = new Intent();
            returnIntent.putExtra("result", timestamp);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }
}