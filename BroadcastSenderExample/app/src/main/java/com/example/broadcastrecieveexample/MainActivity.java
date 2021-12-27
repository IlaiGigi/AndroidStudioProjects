package com.example.broadcastrecieveexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btSend;
    EditText etInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btSend = findViewById(R.id.btSend);
        etInput = findViewById(R.id.etInput);
        btSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btSend){
            // implement intent sending
            Intent intent = new Intent();
            intent.setAction("com.example.broadcastrecieveexample.WEATHERCHANGE");
            intent.putExtra("data", etInput.getText().toString());
            sendBroadcast(intent);
        }
    }
}