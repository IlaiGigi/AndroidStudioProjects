package com.example.memorygame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View view) {

        EditText pairs = findViewById(R.id.pairnum);

            if (Integer.parseInt(pairs.getText().toString()) > 8 || 0 > Integer.parseInt(pairs.getText().toString()))
            {
                Toast toast = Toast.makeText(getApplicationContext(), "Invalid Number", Toast.LENGTH_SHORT);
                toast.show();
            }
            else
            {
                Intent intent = new Intent(MainActivity.this, Game.class);
                intent.putExtra("pairnum", Integer.parseInt(pairs.getText().toString()));
                startActivity(intent);
            }
    }
}