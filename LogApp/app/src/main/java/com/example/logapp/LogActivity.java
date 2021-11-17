package com.example.logapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class LogActivity extends AppCompatActivity implements View.OnClickListener{
    TextView tvData;
    Button btnGoBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        tvData = findViewById(R.id.tvData);
        btnGoBack = findViewById(R.id.btnGoBack);
        btnGoBack.setOnClickListener(this);
        File logfile = new File(this.getExternalFilesDir(null).toString(), "logfile");
        byte[] grabData = new byte[(int)logfile.length()];
        try {
            FileInputStream fis = new FileInputStream(logfile);
            fis.read(grabData);
            fis.close();
            String data = new String(grabData);
            tvData.setText(String.format("Data: %s", data));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnGoBack){
            Intent intent = new Intent(LogActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}