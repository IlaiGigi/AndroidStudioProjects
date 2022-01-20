package com.example.sensorexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager manager;
    Sensor acceleratorSensor;
    View square;
    float deltaX, deltaY, deltaZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        square = findViewById(R.id.ivSquare);
        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        acceleratorSensor = manager.getDefaultSensor(SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (acceleratorSensor != null){
            manager.registerListener(this, acceleratorSensor, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (acceleratorSensor != null){
            manager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;
        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            deltaX = sensorEvent.values[0];
            deltaY = sensorEvent.values[1];
            deltaZ = sensorEvent.values[2];
            if (square != null){
                square.setX(square.getX() - deltaX);
                square.setY(square.getY() + deltaY);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}