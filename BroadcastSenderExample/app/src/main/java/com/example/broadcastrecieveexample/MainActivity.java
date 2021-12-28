package com.example.broadcastrecieveexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    int[] imageIds = {R.drawable.sunny, R.drawable.partly_cloudy, R.drawable.cloudy, R.drawable.raining, R.drawable.snowing, R.drawable.thunder};
    static Button btSendStatic;
    BroadcastReceiver receiver;
    IntentFilter filter;
    RadioButton[] buttons;
    Button btSend;
    EditText etInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission(Manifest.permission.BLUETOOTH);
        checkForBroadcast();
        btSendStatic = findViewById(R.id.btSend);
        buttons = new RadioButton[6];
        buttons[0] = findViewById(R.id.rbSunny);
        buttons[1] = findViewById(R.id.rbPartlyCloudy);
        buttons[2] = findViewById(R.id.rbCloudy);
        buttons[3] = findViewById(R.id.rbRainy);
        buttons[4] = findViewById(R.id.rbSnowy);
        buttons[5] = findViewById(R.id.rbThunder);
        for (RadioButton button: buttons) { button.setOnClickListener(this); }
        btSend = findViewById(R.id.btSend);
        etInput = findViewById(R.id.etInput);
        btSend.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(receiver);
    }

        private void checkForBroadcast(){
        receiver = new MyBroadcastReceiver();
        filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        getApplicationContext().registerReceiver(receiver, filter);
    }

    @Override
    public void onClick(View view) {
        if (view == btSend){
            RadioButton button = buttons[getSelectedButton()];
            byte[] imageBytes = drawableToBytesArray(imageIds[getSelectedButton()]);
            // implement intent sending
            Intent intent = new Intent();
            intent.setAction("com.example.broadcastrecieveexample.WEATHERCHANGE");
            intent.putExtra("data", imageBytes);
            intent.putExtra("data", (String)button.getTag());
            sendBroadcast(intent);
        }
    }

    public int getSelectedButton(){
        for (int i=0; i<6; i++){
            if (buttons[i].isChecked())
                return i;
        }
        return -1;
    }

    public byte[] drawableToBytesArray(int drawableId)
    {
        Bitmap bitmap =
                BitmapFactory.decodeResource(MainActivity.this.getResources(), drawableId);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    public boolean permissionGranted(String permission)
    {
        // Check if the app is api 23 or above
        // If so, must ask for permission.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (checkSelfPermission(permission) ==
                    PackageManager.PERMISSION_GRANTED)
            {
                Log.d("Permissions", "permission granted");
                return true;
            }
            Log.d("Permissions", "Permission was not granted");
            return false;
        }
        // Permission is automatically granted on sdk<23 upon installation
        Log.d("Permissions", "permission granted");
        return true;
    }
    public boolean requestPermission(String permission)
    {
        if (permissionGranted(permission))
            return true;
        // Ask for permission
        ActivityCompat.requestPermissions(this, new String[]{permission}, 1);
        return false;
    }
}