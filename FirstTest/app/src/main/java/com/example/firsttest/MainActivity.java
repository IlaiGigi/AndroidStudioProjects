package com.example.firsttest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout mainLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Tools t1 = new Tools(MainActivity.this);
        Board b1 = new Board(MainActivity.this);
        mainLayout = findViewById(R.id.mainLayout);
        mainLayout.addView(b1);
        Log.d("hello", getExternalFilesDir(null).toString());
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onResume(){
        super.onResume();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {

    }
}