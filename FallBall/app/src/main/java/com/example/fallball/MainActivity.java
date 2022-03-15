package com.example.fallball;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SensorEventListener {

    RelativeLayout mainLayout;
    GameThread gameThread;
    Boolean executeOnResume = false;
    TextView tvAuthorizedSmileys, tvPoints;
    ImageView borderView;
    ImageView[] hearts;

    SensorManager sensorManager;
    Sensor lightSensor;
    float lux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Removing the status bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        hearts = new ImageView[3];

        mainLayout = findViewById(R.id.main_layout);
        tvAuthorizedSmileys = findViewById(R.id.authorizedSmileys);
        borderView = findViewById(R.id.border);
        borderView.measure(0,0);
        tvPoints = findViewById(R.id.points);
        hearts[0] = findViewById(R.id.heart1);
        hearts[1] = findViewById(R.id.heart2);
        hearts[2] = findViewById(R.id.heart3);

        gameThread = new GameThread(mainLayout, this, borderView, tvAuthorizedSmileys, tvPoints, hearts);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameThread.pauseGame();
        if (lightSensor != null){
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (lightSensor != null){
            sensorManager.registerListener(this, lightSensor, Sensor.TYPE_LIGHT);
        }
        if (!executeOnResume){ // This prevents the program from running on resume when it launches
            executeOnResume = true;
            return;
        }
        createAlertDialog("Resume Game");
    }

    public void createAlertDialog(String title){
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setCancelable(false)
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, (dialog, which) -> gameThread.resumeGame())
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void onClick(View view) {
        if (view == tvAuthorizedSmileys){
            tvAuthorizedSmileys.setText(String.valueOf(gameThread.getNewRandomNumber()));
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;
        if (sensor.getType() == Sensor.TYPE_LIGHT){
            lux = sensorEvent.values[0];
            if (lux > 150) mainLayout.setBackgroundResource(R.drawable.light_background);
            else if (lux <= 150) mainLayout.setBackgroundResource(R.drawable.dark_background);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // Redundant
    }
}