package com.example.serviceexample;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvData;
    MyService myService;
    Button btStartService, btStopService;
    Intent intent;
    File file;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btStartService = findViewById(R.id.btStartService);
        btStopService = findViewById(R.id.btStopService);
        tvData = findViewById(R.id.tvData);
        intent = new Intent(MainActivity.this, MyService.class);
        btStartService.setOnClickListener(this);
        btStopService.setOnClickListener(this);
        file = new File(getExternalFilesDir(null), "HumbleFile");
        myService = new MyService();
    }

    @Override
    public void onClick(View view) {
        if (view == btStartService){
            startService(intent);
            String data = readFromFile(file, 20);
            tvData.setText("Data: "+data);
        }
        else if (view == btStopService){
            stopService(intent);
        }
    }
    public String readFromFile(File file, int readLength){
        byte[] fileContent;
        if (readLength == -1){
            // -1 means no specified length, return all the content in the file
            fileContent = new byte[(int) file.length()];
        }
        else
            fileContent = new byte[readLength];
        try {
            FileInputStream fis = new FileInputStream(file);
            fis.read(fileContent);
            fis.close();
            return new String(fileContent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // In the case of an error, return null
    }
}