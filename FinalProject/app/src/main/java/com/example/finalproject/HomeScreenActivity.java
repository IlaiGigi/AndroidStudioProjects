package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
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

import java.util.concurrent.Executor;

public class HomeScreenActivity extends AppCompatActivity implements View.OnClickListener {

    DBHelper dbHelper;

    ImageButton btSignIn;
    ImageButton btRegister;
    ImageButton btCredits;

    SharedPreferences sp;

    boolean isFingerprintScannerAvailable;

    BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Utils.changeNotificationBarColor(this, R.color.purple_700);

        dbHelper = new DBHelper(this, null, null, 1);

        btSignIn = findViewById(R.id.btSignIn);
        btRegister = findViewById(R.id.btRegister);
        btCredits = findViewById(R.id.btCredits);

        btSignIn.setOnClickListener(this);
        btRegister.setOnClickListener(this);
        btCredits.setOnClickListener(this);

        sp = Utils.defineSharedPreferences(this, "mainRoot");

        FingerprintManager fingerprintManager = (FingerprintManager) this.getSystemService(Context.FINGERPRINT_SERVICE);
        if (!fingerprintManager.isHardwareDetected()) {
            isFingerprintScannerAvailable = false;
        } else if (!fingerprintManager.hasEnrolledFingerprints()) {
            isFingerprintScannerAvailable = false;
        } else {
            isFingerprintScannerAvailable = true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onClick(View view) {
        if (view == btSignIn){
            // Normal login
            if (!sp.getBoolean("rememberMe", false)){
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
                        sp.edit().putBoolean("rememberMe", cbRememberMe.isChecked()).commit();
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
                Executor executor = ContextCompat.getMainExecutor(this);
                BiometricPrompt biometricPrompt = new BiometricPrompt(HomeScreenActivity.this,
                        executor, new BiometricPrompt.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationError(int errorCode,
                                                      @NonNull CharSequence errString) {
                        super.onAuthenticationError(errorCode, errString);
                        sp.edit().putBoolean("rememberMe", false).commit();
                        btSignIn.performClick();
                    }

                    @Override
                    public void onAuthenticationSucceeded(
                            @NonNull BiometricPrompt.AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);
                        Toast.makeText(getApplicationContext(),
                                "Authentication succeeded!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(HomeScreenActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onAuthenticationFailed() {
                        super.onAuthenticationFailed();
                        Toast.makeText(getApplicationContext(), "Authentication failed",
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                });
                promptInfo = new BiometricPrompt.PromptInfo.Builder()
                        .setTitle("Log in by fingerprint")
                        .setSubtitle("Log in using your biometric credential")
                        .setNegativeButtonText("Log into a different account")
                        .build();
                biometricPrompt.authenticate(promptInfo);
                // Using last login's sp data (sp.username) - no need to overwrite
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
                    Toast.makeText(this, "Error, Check credentials and try again", Toast.LENGTH_LONG).show();
                if (aUsername.length() > 15)
                    Toast.makeText(this, "Error, Username cannot be longer than 15 characters", Toast.LENGTH_LONG).show();
                if (aPassword1.length() < 6)
                    Toast.makeText(this, "Error, Password must be longer than 6 characters", Toast.LENGTH_LONG).show();
                if (dbHelper.getUser(aUsername) != null)
                    Toast.makeText(this, "Error, Username already exists, try a different one", Toast.LENGTH_LONG).show();
                else {
                    dbHelper.insertNewUser(new User(aUsername, aPassword1, 0, 0, true)); // All users are initialized with 0 coins
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