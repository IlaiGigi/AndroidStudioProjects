package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ClassicGameActivity extends AppCompatActivity {

    RelativeLayout classicBoardLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classic_game);

        classicBoardLayout = findViewById(R.id.classicBoardLayout);

        int levelIdentifier = getIntent().getIntExtra("levelIdentifier", 0);

        ClassicBoard board = new ClassicBoard(this, levelIdentifier);
        classicBoardLayout.addView(board);

        // change window soft input mode to do nothing when keyboard is shown
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_UNCHANGED);
    }
}