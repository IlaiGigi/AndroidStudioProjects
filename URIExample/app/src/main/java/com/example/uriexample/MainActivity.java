package com.example.uriexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.net.URI;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etInput;
    Button btAdd, btDelete, btUpdate;
    Random random;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etInput = findViewById(R.id.etInput);
        btAdd = findViewById(R.id.btAdd);
        btDelete = findViewById(R.id.btDelete);
        btUpdate = findViewById(R.id.btUpdate);
        random = new Random();
        btAdd.setOnClickListener(this);
        btDelete.setOnClickListener(this);
        btUpdate.setOnClickListener(this);
        requestCallLogsWritePermission();
    }

    @Override
    public void onClick(View view) {
        String duration = random.nextInt(240)+"";
        String phoneNum = etInput.getText().toString();
        if (view == btAdd){
            ContentValues values = new ContentValues();
            values.put(CallLog.Calls.NUMBER, phoneNum);
            values.put(CallLog.Calls.DATE, System.currentTimeMillis());
            values.put(CallLog.Calls.DURATION, duration);
            values.put(CallLog.Calls.TYPE, CallLog.Calls.OUTGOING_TYPE);
            values.put(CallLog.Calls.NEW, 1);
            values.put(CallLog.Calls.CACHED_NAME, "NA");
            values.put(CallLog.Calls.CACHED_NUMBER_TYPE, 0);
            values.put(CallLog.Calls.CACHED_NUMBER_LABEL, "NA");
            Uri callLogUri = CallLog.Calls.CONTENT_URI;
            getContentResolver().insert(callLogUri, values);
        }
        if (view == btDelete){
            // implement deleting method
        }
        if (view == btUpdate){
            ContentValues values = new ContentValues();
            values.put(CallLog.Calls.NUMBER, phoneNum);
            Uri callLogUri = CallLog.Calls.CONTENT_URI;
            getContentResolver().update(callLogUri, values, null, null);
        }
    }
    public  boolean isCallLogsWritePermissionGranted()
    {
        // Check if the app is api 23 or above
        // If so, must ask for permission to write to external storage
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (checkSelfPermission(Manifest.permission.WRITE_CALL_LOG)
                    == PackageManager.PERMISSION_GRANTED)
            {
                Log.d("Permissions", "Write to contacts permission is granted");
                return true;
            }

            Log.d("Permissions", "Write to contacts permission is revoked");
            return false;
        }
        // Permission is automatically granted on sdk<23 upon installation
        else
        {
            Log.d("Permissions", "Write to contacts permission is granted");
            return true;
        }
    }

    public boolean requestCallLogsWritePermission()
    {
        if (isCallLogsWritePermissionGranted())
            return true;

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALL_LOG}, 1);
        return false;
    }
}