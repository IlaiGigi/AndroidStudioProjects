package com.example.sensorexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Size;
import android.view.View;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager manager;
    Sensor acceleratorSensor;
    View square;
    float deltaX, deltaY, deltaZ;
    Size size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        square = findViewById(R.id.ivSquare);
        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        acceleratorSensor = manager.getDefaultSensor(SensorManager.SENSOR_DELAY_GAME);
        size = getScreenDimensions();
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
                float movX = square.getX() - deltaX;
                float movY = square.getY() + deltaY;
                if (isValidX(movX))
                    square.setX(movX);
                if (isValidY(movY))
                    square.setY(movY);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public boolean isValidX(float val){
        return val < size.getWidth() - square.getWidth() && val > 0;
    }

    public boolean isValidY(float val){
        return val < size.getHeight() - square.getHeight() - dpToPx(55) && val > 0;
    }

    public int dpToPx(int dps) {
        // Get the screen's density scale
        final float scale =
                getApplicationContext().getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        int pixels = (int) (dps * scale + 0.5f);
        return pixels;
    }

    public Size getScreenDimensions(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        return new Size(width, height);
    }
}