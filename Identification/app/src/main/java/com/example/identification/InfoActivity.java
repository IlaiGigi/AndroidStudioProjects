package com.example.identification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity implements View.OnClickListener{
    TextView tvUsername, tvPassword, tvAge, tvMail;
    Button btChangeCreds, btLogout;
    Intent intentToChangeCred, intentToLogin;
    User u1;
    DBHelper dbHelper = new DBHelper(this, null, null, 1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        tvUsername = findViewById(R.id.tvInfoUsername);
        tvPassword = findViewById(R.id.tvInfoPassword);
        tvAge = findViewById(R.id.tvInfoAge);
        tvMail = findViewById(R.id.tvInfoMail);
        btChangeCreds = findViewById(R.id.btInfoChangeCredentials);
        btLogout = findViewById(R.id.btLogOut);
        btChangeCreds.setOnClickListener(this);
        btLogout.setOnClickListener(this);
        intentToChangeCred = new Intent(InfoActivity.this, ChangeCredentialsActivity.class);
        intentToLogin = new Intent(InfoActivity.this, MainActivity.class);
        Intent inReceive = getIntent();
        u1 = dbHelper.getUser(inReceive.getStringExtra("username")); // No point in checking for null, object exits
        tvUsername.setText(tvUsername.getText().toString() + " "+ u1.getUsername());
        tvPassword.setText(tvPassword.getText().toString() + " "+ u1.getPassword());
        tvAge.setText(tvAge.getText().toString() + " "+ u1.getAge());
        tvMail.setText(tvMail.getText().toString() + " "+ u1.getMail());
    }

    @Override
    public void onClick(View view) {
        if (view == btLogout){
            startActivity(intentToLogin);
        }
        else if (view == btChangeCreds){
            intentToChangeCred.putExtra("username", u1.getUsername());
            startActivity(intentToChangeCred);
        }
    }
}