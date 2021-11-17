package com.example.gameofmemory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CustomizeActivity extends AppCompatActivity implements View.OnClickListener{
    EditText etNumPairs;
    Button btnStartGame;
    TextView tvMessages;
    Intent intent;
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize);
        etNumPairs = findViewById(R.id.etNumPairs);
        tvMessages = findViewById(R.id.tvMessages);
        btnStartGame = findViewById(R.id.btnStartGame);
        btnStartGame.setOnClickListener(this);
        intent = new Intent(CustomizeActivity.this, MainActivity.class);
        sp = getSharedPreferences("main", MODE_PRIVATE);
        edit = sp.edit();

    }

    @Override
    public void onClick(View v) {
        if (v == btnStartGame){
            if (Integer.parseInt(etNumPairs.getText().toString()) > 8 && Integer.parseInt(etNumPairs.getText().toString()) < 1 && etNumPairs.getText().toString().equals("")){
                tvMessages.setText("Messages: Please enter a valid number of pairs.\nA valid number of pairs is between 1 and 8.");
            }
            else{
                edit.putInt("n",Integer.parseInt(etNumPairs.getText().toString()));
                edit.commit();
                startActivity(intent);
            }
        }
    }

}