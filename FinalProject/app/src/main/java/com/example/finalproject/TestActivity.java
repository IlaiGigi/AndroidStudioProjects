package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.widget.RelativeLayout;

public class TestActivity extends AppCompatActivity {

    RelativeLayout testLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        testLayout = findViewById(R.id.testLayout);

        KidsBoard board = new KidsBoard(this);

        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                board.addTile(new KidsTile(this, 0, Color.RED, new Point(i, j)));
            }
        }
        board.loadTiles();

        testLayout.addView(board);
    }
}