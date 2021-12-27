package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    RadioButton rbSunny, rbPartlyCloudy, rbCloudy, rbRaining, rbSnowing, rbThunder;
    RadioButton[] buttons;
    Button btPushWeather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rbSunny = findViewById(R.id.rbSunny);
        rbPartlyCloudy = findViewById(R.id.rbPartlyCloudy);
        rbCloudy = findViewById(R.id.rbCloudy);
        rbRaining = findViewById(R.id.rbRaining);
        rbSnowing = findViewById(R.id.rbSnowing);
        rbThunder = findViewById(R.id.rbThunder);
        btPushWeather = findViewById(R.id.btSend);
        rbSunny.setOnClickListener(this);
        rbPartlyCloudy.setOnClickListener(this);
        rbCloudy.setOnClickListener(this);
        rbRaining.setOnClickListener(this);
        rbSnowing.setOnClickListener(this);
        rbThunder.setOnClickListener(this);
        btPushWeather.setOnClickListener(this);
        buttons = new RadioButton[6];
        buttons[0] = rbSunny;
        buttons[1] = rbPartlyCloudy;
        buttons[2] = rbCloudy;
        buttons[3] = rbRaining;
        buttons[4] = rbSnowing;
        buttons[5] = rbThunder;

    }

    @Override
    public void onClick(View view) {
        if (view == btPushWeather){
            if (getNumClicked() == 1 && isWifiEnabled() && isAirplaneModeEnabled(MainActivity.this)) {
                RadioButton bt = null;
                for (RadioButton button : buttons) {
                    if (button.isChecked()) {
                        bt = button;
                        break;
                    }
                }


            }
        }
    }

    public int getNumClicked(){
        int count = 0;
        for (RadioButton button:
             buttons) {
            if (button.isChecked())
                count++;
        }
        return count;
    }

    private boolean isWifiEnabled(){
        WifiManager wifiManager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        }
        try {
            return wifiManager.isWifiEnabled();
        }catch (Exception e){
            return false;
        }
    }

    private static boolean isAirplaneModeEnabled(Context context) {
        return Settings.System.getInt(context.getContentResolver(),
                Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
    }
}