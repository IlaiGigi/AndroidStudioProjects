package com.example.identification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    Intent intentToLogin;
    Button btRegister;
    EditText etEnterUsername, etEnterPassword, etReEnterPassword, etEnterAge, etEnterMail;
    DBHelper dbHelper = new DBHelper(this, null, null, 1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        intentToLogin = new Intent(RegisterActivity.this, MainActivity.class);
        etEnterUsername = findViewById(R.id.etRegisterUsername);
        etEnterPassword = findViewById(R.id.etRegisterPassword);
        etReEnterPassword = findViewById(R.id.etRegisterReEnterPassword);
        etEnterAge = findViewById(R.id.etRegisterAge);
        etEnterMail = findViewById(R.id.etRegisterMail);
        btRegister = findViewById(R.id.btRegister);
        btRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if  (view == btRegister){
            if (etEnterUsername.getText().toString().equals("") || etEnterPassword.getText().toString().equals("") || etReEnterPassword.getText().toString().equals("") || etEnterAge.getText().toString().equals("") || etEnterMail.getText().toString().equals(""))
                Toast.makeText(this, "Please fill in all boxes", Toast.LENGTH_SHORT).show();
            else{
                String currentUsername = etEnterUsername.getText().toString();
                String currentPassword = etEnterPassword.getText().toString();
                String currentAge = etEnterAge.getText().toString();
                String currentMail = etEnterMail.getText().toString();
                dbHelper.insertNewUser(new User(currentUsername, currentPassword, currentAge, currentMail));
                startActivity(intentToLogin);
            }
        }
    }
}