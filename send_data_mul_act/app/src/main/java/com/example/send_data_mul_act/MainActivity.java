package com.example.send_data_mul_act;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btSendData, btSwitchScreen;
    EditText etInputData;
    TextView tvDisplayData, tvHeadline;
    RadioGroup radioGroup;
    RelativeLayout mainlayout;
    RadioButton rbRed, rbBlue, rbGreen;
    Intent intent;
    static int count =1; //During the first iteration, don't check for intent sent
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(this, MainActivity2.class);
        btSendData = (Button) findViewById(R.id.btSendData);
        etInputData = (EditText) findViewById(R.id.etData);
        tvDisplayData = (TextView) findViewById(R.id.tvDataDisplay);
        tvHeadline = (TextView) findViewById(R.id.tvHeadline);
        btSwitchScreen = (Button)findViewById(R.id.btnSwitch);
        radioGroup = findViewById(R.id.radioGroup1);
        rbRed = findViewById(R.id.rbRed);
        rbBlue = findViewById(R.id.rbBlue);
        rbGreen = findViewById(R.id.rbGreen);
        rbRed.setOnClickListener(this);
        rbBlue.setOnClickListener(this);
        rbGreen.setOnClickListener(this);
        mainlayout = findViewById(R.id.layout1);
        radioGroup.setOnClickListener(this);
        if (count != 1){
            mainlayout.setBackgroundColor(getIntent().getExtras().getInt("color"));
            tvDisplayData.setText("Data: "+getIntent().getExtras().getString("data"));
        }
        count++;
        btSendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("data", etInputData.getText().toString());
            }
        });
        btSwitchScreen.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == rbRed){
            mainlayout.setBackgroundColor(Color.RED);
            intent.putExtra("color", Color.RED);
        }
        if (v == rbBlue){
            mainlayout.setBackgroundColor(Color.BLUE);
            intent.putExtra("color", Color.BLUE);
        }
        if (v == rbGreen){
            mainlayout.setBackgroundColor(Color.GREEN);
            intent.putExtra("color", Color.GREEN);
        }
    }
}