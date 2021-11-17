package com.example.logapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.Format;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;
import java.util.Date;

//Author: Eli Gigi
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnRon, btnIvy, btnShowLog, btnExit;
    TextView tvHeadline;
    String selectedUser = "None";
    File logfile;
    Date date;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logfile = new File(this.getExternalFilesDir(null).toString(), "logfile");
        try {
            FileOutputStream fos = new FileOutputStream(logfile, false);
            fos.write("".getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        btnRon = findViewById(R.id.btnRon);
        btnIvy = findViewById(R.id.btnIvy);
        btnShowLog = findViewById(R.id.btnShowLog);
        btnExit = findViewById(R.id.btnExit);
        tvHeadline = findViewById(R.id.tvHeadline);
        btnRon.setOnClickListener(this);
        btnIvy.setOnClickListener(this);
        btnShowLog.setOnClickListener(this);
        btnExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btnRon){
            writeFile("pressed ron");
            selectedUser = "Ron";
        }
        if (view == btnIvy){
            writeFile("pressed ivy");
            selectedUser = "Ivy";
        }
        if (view == btnExit){
            writeFile("pressed exit");
            MainActivity.this.finish();
        }
        if (view == btnShowLog){
            writeFile("pressed show log");
            Intent intent = new Intent(MainActivity.this, LogActivity.class);
            startActivity(intent);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        writeFile("paused the application");
    }
    @Override
    protected void onResume(){
        super.onResume();
        writeFile("resumed the application");
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        writeFile("closed the application");
    }
    public void writeFile(String txt){
        date = new Date();
        try {
            FileOutputStream fos = new FileOutputStream(logfile, true);
            fos.write((String.format("At: %s %s %s\n",formatter.format(date), selectedUser, txt)).getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}