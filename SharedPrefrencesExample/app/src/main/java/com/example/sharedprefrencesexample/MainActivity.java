package com.example.sharedprefrencesexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    int textSize = 30, headlineSize = 30;
    String background = "#FFBB86FC", textColor = "#000000", headlineColor;
    TextView tvHeadline, tvFirstName, tvSurname;
    Button btnEditSettings;
    Intent intent;
    RelativeLayout layoutMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvHeadline = findViewById(R.id.tvMainHeadline);
        tvFirstName = findViewById(R.id.tvFirstName);
        tvSurname = findViewById(R.id.tvSurname);
        btnEditSettings = findViewById(R.id.btnEditSettings);
        layoutMain = findViewById(R.id.layoutMain);
        intent = new Intent(MainActivity.this, EditScreen.class);
        btnEditSettings.setOnClickListener(this);
        SharedPreferences sharedPref = getSharedPreferences("yosi",MODE_PRIVATE);
        textSize = sharedPref.getInt("textSize", -1);
        textColor = sharedPref.getString("textColor", "none");
        if (textSize > 0){
            tvFirstName.setTextSize(textSize);
            tvSurname.setTextSize(textSize);
        }
        if (!textColor.equals("none")){
            tvFirstName.setTextColor(Color.parseColor(textColor));
            tvSurname.setTextColor(Color.parseColor(textColor));
        }
        background = sharedPref.getString("background", "none");
        if (!background.equals("none") && background.charAt(0) == '#'){
            layoutMain.setBackgroundColor(Color.parseColor(background));
        }
        else if (!background.equals("none") && background.equals("image")){
            layoutMain.setBackgroundResource(R.drawable.newton);
        }
        headlineSize = sharedPref.getInt("headlineSize", -1);
        headlineColor = sharedPref.getString("headlineColor", "none");
        if (headlineSize != -1){
            tvHeadline.setTextSize(headlineSize);
        }
        if (!headlineColor.equals("none")){
            tvHeadline.setTextColor(Color.parseColor(headlineColor));
        }
    }
    @Override
    public void onClick(View view) {
        if (view == btnEditSettings){
            startActivity(intent);
        }
    }
}