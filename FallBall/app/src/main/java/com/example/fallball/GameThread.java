package com.example.fallball;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class GameThread extends Thread {

    public static int points = 1;
    private RelativeLayout rowsLayout;
    private Handler handler;
    private ArrayList<SmileyRow> smileyRows;
    private Boolean createMoreRows;
    private Context context;
    private int timesToChangeNumber;
    private int currentAuthorizedNumber;

    public GameThread(RelativeLayout rowsLayout, Context context){
        this.rowsLayout = rowsLayout;
        this.handler = new Handler();
        this.createMoreRows = true;
        this.smileyRows = new ArrayList<>();
        this.context = context;
        this.timesToChangeNumber = 3;

        this.start();
    }

    @Override
    public void run() {
        super.run();
        while (true){
            if (this.createMoreRows) {
                handler.post(() -> {
                    SmileyRow smileyRow = new SmileyRow(this.context);
                    this.rowsLayout.addView(smileyRow);
                    this.smileyRows.add(smileyRow);
                });
            }
            try {
                Thread.sleep(2000); // TODO: fix delay issue
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void pauseGame(){
        for (int i=0; i<this.smileyRows.size(); i++){
            SmileyRow row = this.smileyRows.get(i);
            row.pauseAnimation();
        }
        this.createMoreRows = false;
    }

    public void resumeGame(){
        for (int i=0; i<this.smileyRows.size(); i++){
            SmileyRow row = this.smileyRows.get(i);
            row.resumeAnimation();
        }
        this.createMoreRows = true;
    }

    public int getNewRandomNumber(){
        if (this.timesToChangeNumber <= 0){
            Toast.makeText(this.context, "No saves left", Toast.LENGTH_SHORT).show();
            return currentAuthorizedNumber;
        }
        this.timesToChangeNumber--;
        Random random = new Random();
        int num = random.nextInt(8) + 1; // Min scenario: 0 + 1 = 1, Max scenario: 7 + 1 = 8
        this.currentAuthorizedNumber = num;
        return num;
    }
}
