package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.util.Log;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;
import java.util.concurrent.Executor;

public class HomeScreenActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton btSignIn;
    ImageButton btRegister;
    ImageButton btCredits;

    SharedPreferences sp;

    boolean isFingerprintScannerAvailable;

    BiometricPrompt.PromptInfo promptInfo;

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users"); // Users branch

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Utils.changeNotificationBarColor(this, R.color.purple_700);

        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                android.Manifest.permission.READ_CONTACTS,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.SEND_SMS,
        };

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

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
        } else isFingerprintScannerAvailable = fingerprintManager.hasEnrolledFingerprints();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onClick(View view) {
        if (view == btSignIn) {
            // Normal login
            boolean state = sp.getBoolean("rememberMe", false);
            if (!state) {
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
                    mDatabase.child(String.valueOf(UUID.nameUUIDFromBytes(aUsername.getBytes()))).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                User user = dataSnapshot.getValue(User.class);
                                assert user != null;
                                if (user.getPassword().equals(aPassword)) {
                                    // Login successful
                                    Utils.insertDataToSharedPreferences(sp, "UUID", UUID.nameUUIDFromBytes(aUsername.getBytes()).toString());
                                    Utils.insertDataToSharedPreferences(sp, "username", aUsername);
                                    sp.edit().putBoolean("rememberMe", cbRememberMe.isChecked()).apply();
                                    Intent intent = new Intent(HomeScreenActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    alertD.dismiss();
                                    finish();
                                } else {
                                    // Login failed
                                    Toast.makeText(HomeScreenActivity.this, "שם משתמש או סיסמה לא נכונים", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(HomeScreenActivity.this, "המשתמש לא קיים במערכת", Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
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
                        sp.edit().putBoolean("rememberMe", false).apply();
                        btSignIn.performClick();
                    }

                    @Override
                    public void onAuthenticationSucceeded(
                            @NonNull BiometricPrompt.AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);
                        Toast.makeText(getApplicationContext(),
                                "ההתחברות בוצעה!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(HomeScreenActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onAuthenticationFailed() {
                        super.onAuthenticationFailed();
                        Toast.makeText(getApplicationContext(), "ההחברות כשלה",
                                        Toast.LENGTH_SHORT)
                                .show();
                    }
                });
                promptInfo = new BiometricPrompt.PromptInfo.Builder()
                        .setTitle("התחבר באמצעות תביעת אצבע")
                        .setSubtitle("התחבר בעזרת הזדהות ביומטרית")
                        .setNegativeButtonText("התחבר למשתמש אחר")
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
                if (aUsername.equals("") || aUsername.contains(" ") || aPassword1.equals("") || aPassword1.contains(" ") || aPassword2.contains(" ") || !aPassword1.equals(aPassword2))
                    Toast.makeText(this, "שגיאה, בדוק את התוכן שוב", Toast.LENGTH_LONG).show();
                if (aUsername.length() > 15)
                    Toast.makeText(this, "שם המשתמש צריך להיות קצר מ-16 תווים", Toast.LENGTH_LONG).show();
                if (aPassword1.length() < 6)
                    Toast.makeText(this, "הסיסמה צריכה להיות ארוכה מ-5 תווים", Toast.LENGTH_LONG).show();
                else {
                    mDatabase.child(String.valueOf(UUID.nameUUIDFromBytes(aUsername.getBytes()))).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (!dataSnapshot.exists()){
                                User user = new User(aUsername, aPassword1, 0, 0, true);
                                mDatabase.child(String.valueOf(UUID.nameUUIDFromBytes(aUsername.getBytes()))).setValue(user);
                            }
                            else {
                                Toast.makeText(HomeScreenActivity.this, "שם המשתמש שניסית תפוס, נסה אחד אחר", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {}
                    });
                    alertD.cancel();
                    Toast.makeText(this, "נרשמת בהצלחה", Toast.LENGTH_LONG).show();
                    sp.edit().putBoolean("rememberMe", false).apply();
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

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onBackPressed() {}
}