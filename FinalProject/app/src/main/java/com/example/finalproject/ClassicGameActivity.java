package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
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

import java.io.File;

public class ClassicGameActivity extends AppCompatActivity {

    RelativeLayout classicBoardLayout;
    ClassicBoard board;
    int levelIdentifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classic_game);

        classicBoardLayout = findViewById(R.id.classicBoardLayout);

        levelIdentifier = getIntent().getIntExtra("levelIdentifier", 0);

        board = new ClassicBoard(this, levelIdentifier);
        classicBoardLayout.addView(board);

        // Pull progression from local filestream and update corresponding tiles
        loadSavedProgression();

        // change window soft input mode to do nothing when keyboard is shown
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_UNCHANGED);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    public void loadSavedProgression(){
        SharedPreferences sp = Utils.defineSharedPreferences(this, "mainRoot");
        String fileName = "progression_" + Utils.getDataFromSharedPreferences(sp, "username", null) + levelIdentifier;
        File file = new File(getFilesDir(), fileName);
        String data = Utils.readFile(file, -1);
        if (data != null) {
            int count = 0;
            for (LinearLayout row : board.getRows()) {
                for (int j = 0; j < Utils.getChildrenViews(row); j++) {
                    if (row.getChildAt(j) instanceof ClassicEditTile) {
                        ClassicEditTile tile = (ClassicEditTile) row.getChildAt(j);
                        if (data.charAt(count) != ' ')
                            tile.setText(String.valueOf(data.charAt(count)));
                        count++;
                    }
                }
            }
        }
    }

    public void saveProgression(){
        SharedPreferences sp = Utils.defineSharedPreferences(this, "mainRoot");
        String fileName = "progression_" + Utils.getDataFromSharedPreferences(sp, "username", null) + levelIdentifier;
        File file = new File(getFilesDir(), fileName);
        StringBuilder builder = new StringBuilder();
        for (LinearLayout row : board.getRows()) {
            for (int j = 0; j < Utils.getChildrenViews(row); j++) {
                if (row.getChildAt(j) instanceof ClassicEditTile) {
                    ClassicEditTile tile = (ClassicEditTile) row.getChildAt(j);
                    if (tile.getContent().isEmpty()) {
                        builder.append(" ");
                    } else {
                        builder.append(tile.getContent());
                    }
                }
            }
        }
        Utils.writeToFile(file, builder.toString());
    }

    @Override
    protected void onDestroy() {
        saveProgression();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        saveProgression();
        super.onStop();
    }
}