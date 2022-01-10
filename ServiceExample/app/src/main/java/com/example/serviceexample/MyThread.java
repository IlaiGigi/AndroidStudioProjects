package com.example.serviceexample;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MyThread extends Thread{
    File file;
    public MyThread(Context context){
        file = new File(context.getExternalFilesDir(null), "HumbleFile");
    }
    @Override
    public void run() {
        super.run();
        final int start = 1;
        final int end = 1000000;
        writeToFile(file, start, end);
    }
    private void writeToFile(File file, int start, int end){
        try {
            FileOutputStream fos = new FileOutputStream(file);
            for (int i=start; i<=end && !this.isInterrupted(); i++){
                if (i % 10000 == 0){
                    MainActivity.builder.setProgress(end, i, false);
                    MainActivity.notificationManager.notify(10, MainActivity.builder.build());
                }
                fos.write((i+"" +System.lineSeparator()).getBytes());
            }
            MainActivity.builder.setContentText("Completed");
            MainActivity.notificationManager.notify(10, MainActivity.builder.build());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
