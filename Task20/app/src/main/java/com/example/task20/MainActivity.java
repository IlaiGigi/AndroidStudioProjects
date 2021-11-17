package com.example.task20;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.Random;

public class MainActivity extends AppCompatActivity{
    LinearLayout mainLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLayout = findViewById(R.id.MainLayout);
        int blue = getResources().getColor(R.color.blue);
        int orange = getResources().getColor(R.color.orange);
        int purple = getResources().getColor(R.color.purple_200);
        int green = getResources().getColor(R.color.lightgreen);
        int[] colors = {blue, orange, purple, green};
        Random random = new Random();
        LinearLayout.LayoutParams myBarParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        myBarParams.topMargin = 70;
        for (int i=0; i<4; i++){
            MyBar myBar = new MyBar(MainActivity.this, random.nextInt(11), colors[i]);
            myBar.setLayoutParams(myBarParams);
            mainLayout.addView(myBar);
        }
    }
}