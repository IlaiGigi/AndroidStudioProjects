package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    RadioButton rbSunny, rbPartlyCloudy, rbCloudy, rbRaining, rbSnowing, rbThunder;
    RadioButton[] buttons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rbSunny = findViewById(R.id.rbSunny);
        rbPartlyCloudy = findViewById(R.id.rbPartlyCloudy);
        rbCloudy = findViewById(R.id.rbCloudy);
        rbRaining = findViewById(R.id.rbRaining);
        rbSnowing = findViewById(R.id.rbSnowing);
        rbThunder = findViewById(R.id.rbThunder);
        buttons = new RadioButton[6];

    }

    @Override
    public void onClick(View view) {

    }
}