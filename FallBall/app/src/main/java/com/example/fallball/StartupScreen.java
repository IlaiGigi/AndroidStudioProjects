package com.example.fallball;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class StartupScreen extends AppCompatActivity implements View.OnClickListener {

    Intent startGameIntent;
    ImageButton ibStartGame;
    ImageButton ibAboutUs;
    TextView tvAboutUs;
    ImageView ivAmongUsDrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startup_screen_layout);

        startGameIntent = new Intent(StartupScreen.this, MainActivity.class);
        ibStartGame = findViewById(R.id.ibStartGame);
        ibAboutUs = findViewById(R.id.ibAboutUs);
        tvAboutUs = findViewById(R.id.tvAboutUs);
        ivAmongUsDrip = findViewById(R.id.ivAmongUsDrip);

        ibStartGame.setOnClickListener(this);
        ibAboutUs.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == ibStartGame){
            startActivity(startGameIntent);
        }
        if (view == ibAboutUs){
            tvAboutUs.setVisibility(View.VISIBLE);
            ivAmongUsDrip.setVisibility(View.VISIBLE);
        }
    }
}