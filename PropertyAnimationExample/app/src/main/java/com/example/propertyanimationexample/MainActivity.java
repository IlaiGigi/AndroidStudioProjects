package com.example.propertyanimationexample;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout mainLayout;
    BarView bar;
    Button btnStart;
    EditText etInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLayout = findViewById(R.id.MainLayout);
        btnStart = findViewById(R.id.btStart);
        etInput = findViewById(R.id.etInput);
        bar = new BarView(MainActivity.this, 7, Color.GREEN);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        bar.setLayoutParams(params);
        mainLayout.addView(bar);
    }

    @Override
    public void onClick(View view) {
        if (view == btnStart){
            if (!etInput.getText().toString().equals("")){
                int grabNum = Integer.parseInt(etInput.getText().toString());
                if (bar.getLinesNum() < grabNum){
                    for (int i=0; i<grabNum; i++){
                        if (bar.getMaxLines() == i)
                            break;
                        bar.addLine();
                    }
                }
                if (bar.getLinesNum() > grabNum){
                    for (int i=0; i<grabNum; i++){
                        if (bar.getLinesNum() == 0){
                            break;
                        }
                        bar.removeLine();
                    }
                }
            }
        }
    }
}