package com.example.task7;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    LinearLayout mainLayout;
    ImageView ivPlus;
    Bar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        bar = new Bar(MainActivity.this, Color.parseColor("#FFA500"));
        super.onCreate(savedInstanceState);
        setContentView(bar);
        ivPlus = findViewById(R.id.ivPlus);
        ivPlus.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == ivPlus){
            bar.addRect();
        }
    }
}