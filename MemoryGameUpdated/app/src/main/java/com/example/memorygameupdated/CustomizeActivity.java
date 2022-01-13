package com.example.memorygameupdated;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CustomizeActivity extends AppCompatActivity implements View.OnClickListener{

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    Button btStartGame;
    EditText etInput;
    TextView tvMessage;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize);
        btStartGame = findViewById(R.id.btnStartGame);
        etInput = findViewById(R.id.etInput);
        tvMessage = findViewById(R.id.tvMessages);
        btStartGame.setOnClickListener(this);
        intent = new Intent(CustomizeActivity.this, MainActivity.class);
        sp = getSharedPreferences("main", MODE_PRIVATE);
        editor = sp.edit();
    }

    @Override
    public void onClick(View view) {
        if (view == btStartGame){
            if (etInput.getText().toString().equals(""))
                tvMessage.setText("Messages: Please fill in an input.");
            else{
                int numOfPairs = Integer.parseInt(etInput.getText().toString());
                if (numOfPairs >= 1 && numOfPairs <= 8){
                    editor.putInt("numOfPairs", numOfPairs);
                    editor.commit();
                    startActivity(intent);
                }
                else
                    tvMessage.setText("Messages: Please fill in a valid input between 1 - 8.");
            }
        }
    }
}