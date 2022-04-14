package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class HomeScreenActivity extends AppCompatActivity implements View.OnClickListener {

    DBHelper dbHelper;

    ImageButton btSignIn;
    ImageButton btRegister;
    ImageButton btCredits;

    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        dbHelper = new DBHelper(this, null, null, 1);

        btSignIn = findViewById(R.id.btSignIn);
        btRegister = findViewById(R.id.btRegister);
        btCredits = findViewById(R.id.btCredits);

        btSignIn.setOnClickListener(this);
        btRegister.setOnClickListener(this);
        btCredits.setOnClickListener(this);

        sp = Utils.defineSharedPreferences(this, "mainRoot");
    }

    @Override
    public void onClick(View view) {
        if (view == btSignIn){
            // Normal login
            if (Utils.getDataFromSharedPreferences(sp, "rememberMe", null) == null || Utils.getDataFromSharedPreferences(sp, "rememberMe", null).equals("unchecked")){
                LayoutInflater layoutInflater = LayoutInflater.from(this);
                View promptView = layoutInflater.inflate(R.layout.sign_in_dialog, null);
                final AlertDialog alertD = new AlertDialog.Builder(this).create();
                alertD.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                ImageButton ibSignIn = promptView.findViewById(R.id.ibSignIn);
                TextInputLayout etUsername = promptView.findViewById(R.id.etUsername);
                TextInputLayout etPassword = promptView.findViewById(R.id.etPassword);
                CheckBox cbRememberMe = promptView.findViewById(R.id.cbRememberMe);
                ibSignIn.setOnClickListener(view1 -> {
                    // Validate credentials with database
                    String aUsername = etUsername.getEditText().getText().toString();
                    String aPassword = etPassword.getEditText().getText().toString();
                    User user = dbHelper.getUser(aUsername);
                    if (aUsername.equals("") || aPassword.equals("") || user == null || !user.getPassword().equals(aPassword))
                        Toast.makeText(this, "Incorrect Username or Password", Toast.LENGTH_LONG).show();
                    else {
                        Utils.insertDataToSharedPreferences(sp, "username", aUsername);
                        if (cbRememberMe.isChecked())
                            Utils.insertDataToSharedPreferences(sp, "rememberMe", "checked");
                        else
                            Utils.insertDataToSharedPreferences(sp, "rememberMe", "unchecked");
                        // Forward to next activity
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                    }
                });
                alertD.setView(promptView);
                alertD.setCancelable(true);
                alertD.show();
            }
            // "Remember me" login
            else {
                // Using last login's sp data (sp.username) - no need to overwrite
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        }
        else if (view == btRegister){
            LayoutInflater layoutInflater = LayoutInflater.from(this);
            View promptView = layoutInflater.inflate(R.layout.register_dailog, null);
            final AlertDialog alertD = new AlertDialog.Builder(this).create();
            alertD.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            ImageButton ibRegister = promptView.findViewById(R.id.ibRegister);
            TextInputLayout etUsername = promptView.findViewById(R.id.etUsername);
            TextInputLayout etPassword = promptView.findViewById(R.id.etPassword);
            TextInputLayout etReEnterPassword = promptView.findViewById(R.id.etReEnterPassword);
            ibRegister.setOnClickListener(view1 -> {
                // Validate credentials with database
                String aUsername = etUsername.getEditText().getText().toString();
                String aPassword1 = etPassword.getEditText().getText().toString();
                String aPassword2 = etReEnterPassword.getEditText().getText().toString();
                if (aUsername.equals("") || aUsername.contains(" ") || aPassword1.equals("") || aPassword1.contains(" ") || aPassword2.equals("") || aPassword2.contains(" ") || !aPassword1.equals(aPassword2))
                    Toast.makeText(this, "Error, Check credentials and try again", Toast.LENGTH_LONG);
                else {
                    dbHelper.insertNewUser(new User(aUsername, aPassword1, 0)); // All users are initialized with 0 coins
                    alertD.cancel();
                }
            });
            alertD.setView(promptView);
            alertD.setCancelable(true);
            alertD.show();
        }
        else if (view == btCredits){
            LayoutInflater layoutInflater = LayoutInflater.from(this);
            View promptView = layoutInflater.inflate(R.layout.credits_dialog, null);
            final AlertDialog alertD = new AlertDialog.Builder(this).create();
            alertD.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertD.setView(promptView);
            alertD.setCancelable(true);
            alertD.show();
        }
    }
}