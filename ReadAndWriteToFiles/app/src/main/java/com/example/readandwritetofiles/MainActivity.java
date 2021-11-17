package com.example.readandwritetofiles;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btSendData, btGetData;
    TextView tvHeadline, tvData;
    EditText etDataInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvHeadline = findViewById(R.id.tvHeadline);
        tvData = findViewById(R.id.tvData);
        etDataInput = findViewById(R.id.etDataInput);
        btSendData = findViewById(R.id.btSendData);
        btGetData = findViewById(R.id.btGetData);
        btSendData.setOnClickListener(this);
        btGetData.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        
    }
}