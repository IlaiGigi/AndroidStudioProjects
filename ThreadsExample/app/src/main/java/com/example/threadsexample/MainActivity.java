package com.example.threadsexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnIncrease, btnDecrease;
    EditText etInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnIncrease = findViewById(R.id.btnIncrease);
        btnDecrease = findViewById(R.id.btnDecrease);
        etInput = findViewById(R.id.etInput);
        btnIncrease.setOnClickListener(this);
        btnDecrease.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btnIncrease){
            new Thread(new Runnable() {
                public void run() {
                    for (int i=1; i <= Integer.parseInt(etInput.getText().toString()); i++){
                        Log.d("Increase", i+"");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
        else if (view == btnDecrease){
            new Thread(new Runnable() {
                public void run() {
                    for (int i=Integer.parseInt(etInput.getText().toString()); i > 0; i--){
                        Log.d("Decrease", i+"");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }
}