package com.example.identification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Intent intentToCred, intentToRegister;
    EditText etEnterUsername, etEnterPassword;
    Button btLogIn, btRegister;
    DBHelper dbHelper = new DBHelper(this, null, null, 1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intentToRegister = new Intent(MainActivity.this, RegisterActivity.class);
        intentToCred = new Intent(MainActivity.this, InfoActivity.class);
        btLogIn = findViewById(R.id.btLogIn);
        btRegister = findViewById(R.id.btSwitchRegister);
        etEnterUsername = findViewById(R.id.etEnterUsername);
        etEnterPassword = findViewById(R.id.etEnterPassword);
        btLogIn.setOnClickListener(this);
        btRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == btLogIn){
            String currentUsername = etEnterUsername.getText().toString();
            String currentPassword = etEnterPassword.getText().toString();
            if (currentUsername.equals("") || currentPassword.equals(""))
                return;
            User u1 = dbHelper.getUser(currentUsername);
            if (u1 == null){
                Toast.makeText(this, "Wrong Username or Password", Toast.LENGTH_SHORT).show();
            }
            else{
                if (!u1.getPassword().equals(currentPassword)){
                    Toast.makeText(this, "Wrong Username or Password", Toast.LENGTH_SHORT).show();
                }
                else{
                    intentToCred.putExtra("username", currentUsername);
                    startActivity(intentToCred);
                }
            }
        }
        else if (view == btRegister){
            startActivity(intentToRegister);
        }
    }
}