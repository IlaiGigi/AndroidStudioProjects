package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

public class ClassicGameActivity extends AppCompatActivity {

    RelativeLayout classicBoardLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classic_game);

        classicBoardLayout = findViewById(R.id.classicBoardLayout);

        ClassicBoard board = new ClassicBoard(this, 1);
        classicBoardLayout.addView(board);

    }
}