package com.example.identification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangeCredentialsActivity extends AppCompatActivity implements View.OnClickListener{
    Button btChangeCreds;
    EditText etChangeUsername, etChangePassword, etChangeAge, etChangeMail, etChangePasswordRepeat;
    DBHelper dbHelper = new DBHelper(this, null, null, 1);
    Intent intentToMain;
    User u1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_credentials);
        btChangeCreds = findViewById(R.id.btChangeCredentials);
        etChangeUsername = findViewById(R.id.etChangeUsername);
        etChangePassword = findViewById(R.id.etChangePassword);
        etChangeAge = findViewById(R.id.etChangeAge);
        etChangeMail = findViewById(R.id.etChangeMail);
        etChangePasswordRepeat = findViewById(R.id.etChangePasswordRepeat);
        Intent intent = getIntent();
        u1 = dbHelper.getUser(intent.getStringExtra("username"));
        etChangeUsername.setText(u1.getUsername());
        etChangePassword.setText(u1.getPassword());
        etChangeMail.setText(u1.getMail());
        etChangeAge.setText(u1.getAge());
        etChangePasswordRepeat.setText(u1.getPassword());
        intentToMain = new Intent(ChangeCredentialsActivity.this, MainActivity.class);
        btChangeCreds.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btChangeCreds){
            String newUsername = etChangeUsername.getText().toString();
            String newPassword = etChangePassword.getText().toString();
            String newAge = etChangeAge.getText().toString();
            String newMail = etChangeMail.getText().toString();
            if (newUsername.equals("") || newPassword.equals("") || newAge.equals("") || newMail.equals(""))
                Toast.makeText(this, "Please fill in all the text boxes", Toast.LENGTH_SHORT).show();
            else if (!newPassword.equals(etChangePasswordRepeat.getText().toString())){
                Toast.makeText(this, "Please Enter Matching Passwords", Toast.LENGTH_SHORT).show();
            }
            else{
                dbHelper.deleteUser(newUsername);
                dbHelper.insertNewUser(new User(newUsername, newPassword, newAge, newMail));
                startActivity(intentToMain);
            }
        }
    }
}