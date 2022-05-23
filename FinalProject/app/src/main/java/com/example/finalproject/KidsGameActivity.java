package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.widget.RelativeLayout;

public class KidsGameActivity extends AppCompatActivity {

    RelativeLayout testLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        testLayout = findViewById(R.id.testLayout);

        KidsBoard board = new KidsBoard(this);

        int levelIdentifier = getIntent().getIntExtra("levelIdentifier", 0);

        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                board.addTile(KidsTile.initializeTile(this, new Point(i, j), KidsBoard.levelResourceIndexes[levelIdentifier][i][j]));
            }
        }
        board.loadTiles();

        testLayout.addView(board);
    }
}