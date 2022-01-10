package com.example.serviceexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    static NotificationCompat.Builder builder;
    static NotificationManagerCompat notificationManager;
    TextView tvData;
    MyService myService;
    Button btStartService, btStopService;
    Intent intent;
    File file;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btStartService = findViewById(R.id.btStartService);
        btStopService = findViewById(R.id.btStopService);
        tvData = findViewById(R.id.tvData);
        intent = new Intent(MainActivity.this, MyService.class);
        btStartService.setOnClickListener(this);
        btStopService.setOnClickListener(this);
        file = new File(getExternalFilesDir(null), "HumbleFile");
        myService = new MyService();
        createNotificationChannel();
        notificationManager = NotificationManagerCompat.from(this);
        builder = new NotificationCompat.Builder(this, "CHANNEL_ID")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Writing Data")
                .setContentText("Writing")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);
    }

    @Override
    public void onClick(View view) {
        if (view == btStartService){
            startService(intent);
            String data = readFromFile(file, 20);
            tvData.setText("Data: "+data);
        }
        else if (view == btStopService){
            stopService(intent);
        }
    }
    public String readFromFile(File file, int readLength){
        byte[] fileContent;
        if (readLength == -1){
            // -1 means no specified length, return all the content in the file
            fileContent = new byte[(int) file.length()];
        }
        else
            fileContent = new byte[readLength];
        try {
            FileInputStream fis = new FileInputStream(file);
            fis.read(fileContent);
            fis.close();
            return new String(fileContent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // In the case of an error, return null
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return;
        }
        CharSequence name = getString(R.string.channel_name);
        String description = getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("CHANNEL_ID", name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}
