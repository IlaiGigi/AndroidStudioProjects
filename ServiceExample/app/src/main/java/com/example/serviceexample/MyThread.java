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
        final long end = 100000000;
        writeToFile(file, start, end);
    }
    private void writeToFile(File file, int start, long end){
        try {
            FileOutputStream fos = new FileOutputStream(file);
            for (int i=start; i<=end && !this.isInterrupted(); i++){
                fos.write((i+"" +System.lineSeparator()).getBytes());
            }
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private File createFile(String fileName){
        return new File(fileName);
    }
}
