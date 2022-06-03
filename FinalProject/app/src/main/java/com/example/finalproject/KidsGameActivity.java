package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class KidsGameActivity extends AppCompatActivity {

    RelativeLayout testLayout;

    TextView tvHeadline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        testLayout = findViewById(R.id.testLayout);

        tvHeadline = findViewById(R.id.tvHeadline);

        int levelIdentifier = getIntent().getIntExtra("levelIdentifier", 0);


        KidsBoard board = new KidsBoard(this, levelIdentifier);

        tvHeadline.setText(tvHeadline.getText().toString() + " " + levelIdentifier);

        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                board.addTile(KidsTile.initializeTile(this, new Point(i, j), KidsBoard.levelResourceIndexes[levelIdentifier - 1][i][j]));
            }
        }
        board.loadTiles();

        testLayout.addView(board);
    }
}