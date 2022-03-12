package com.example.fallball;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GameThread extends Thread {

    private final int[] explosionAnimation = {R.drawable.explode1, R.drawable.explode2, R.drawable.explode3, R.drawable.explode4, R.drawable.explode5, R.drawable.explode6, R.drawable.explode7, R.drawable.explode8};
    private final ArrayList<SmileyRow> smileyRows;
    private final TextView tvAuthorizedSmileys;
    private final RelativeLayout rowsLayout;
    private final ImageView borderView;
    private final Intent restartIntent;
    private final ImageView[] hearts;
    private final TextView tvPoints;
    private final Context context;
    private final Handler handler;
    private final Random random;
    private int currentAuthorizedNumber;
    private int timesToChangeNumber;
    private boolean createMoreRows;

    public GameThread(RelativeLayout rowsLayout, Context context, ImageView borderView, TextView tvAuthorizedSmileys, TextView tvPoints, ImageView[] hearts){
        this.rowsLayout = rowsLayout;
        this.handler = new Handler();
        this.createMoreRows = true;
        this.smileyRows = new ArrayList<>();
        this.context = context;
        this.timesToChangeNumber = 3;
        this.borderView = borderView;
        this.tvAuthorizedSmileys = tvAuthorizedSmileys;
        this.tvPoints = tvPoints;
        this.hearts = hearts;
        this.random = new Random();
        this.currentAuthorizedNumber = this.random.nextInt(9);
        this.tvAuthorizedSmileys.setText(String.valueOf(this.currentAuthorizedNumber));
        this.restartIntent = new Intent(context, MainActivity.class);

        this.start();
    }

    @Override
    public void run() {
        super.run();
        while (true){
            if (this.createMoreRows){
                handler.post(() -> {
                    SmileyRow smileyRow = new SmileyRow(this.context);
                    this.rowsLayout.addView(smileyRow);
                    this.smileyRows.add(smileyRow);
                    checkRowPass();
                });
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void pauseGame(){
        for (SmileyRow row: this.smileyRows){
            row.pauseAnimation();
        }
        this.createMoreRows = false;
    }

    public void resumeGame(){
        for (SmileyRow row: this.smileyRows){
            row.resumeAnimation();
        }
        this.createMoreRows = true;
    }

    public int getNewRandomNumber(){
        if (this.timesToChangeNumber <= 0){
            Toast.makeText(this.context, "No saves left", Toast.LENGTH_SHORT).show();
            return this.currentAuthorizedNumber;
        }
        this.timesToChangeNumber--;
        int num = this.random.nextInt(9);
        this.currentAuthorizedNumber = num;
        return num;
    }

    private void checkRowPass(){
        // TODO: FIX SPEED (SMILEYS TOO CLOSE TO EACH OTHER) ISSUE, FIX POINTS DOWN ON STARTUP ISSUE
        if (this.smileyRows.get(0).getY() >= this.borderView.getY() && this.smileyRows.size() != 1){
            boolean valid = this.smileyRows.get(0).checkSmileyNumber(this.currentAuthorizedNumber); // TODO: THIS IS CALLED AFTER THE 3 SECOND DELAY (?)
            this.timesToChangeNumber = 3;
            this.tvAuthorizedSmileys.setText(String.valueOf(this.random.nextInt(9)));
            if (valid) this.tvPoints.setText(String.valueOf(Integer.parseInt(this.tvPoints.getText().toString()) + 1));
            else{
                this.tvPoints.setText(String.valueOf(Integer.parseInt(this.tvPoints.getText().toString()) - 1));
                for (int i = 2; i>=0; i--){
                    if (this.hearts[i] != null){
                        this.hearts[i].setVisibility(View.INVISIBLE);
                        this.hearts[i] = null;
                        if (i == 0){
                            this.pauseGame();
                            new AlertDialog.Builder(this.context)
                                    .setTitle("Game Over")
                                    .setMessage("Start Over?")
                                    .setCancelable(false)
                                    // Specifying a listener allows you to take an action before dismissing the dialog.
                                    // The dialog is automatically dismissed when a dialog button is clicked.
                                    .setPositiveButton(android.R.string.yes, (dialog, which) -> this.context.startActivity(this.restartIntent))
                                    .setNegativeButton(android.R.string.no, (dialog, which) -> this.rowsLayout.addView(this.createGameOverMessage()))
                                                // A null listener allows the button to dismiss the dialog and take no further action.
                                    .setIcon(android.R.drawable.ic_dialog_alert).show();
                        }
                        break;
                    }
                }
            }
            this.smileyRows.remove(0);
        }
    }

    @SuppressLint("SetTextI18n")
    private TextView createGameOverMessage(){
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = Utils.getScreenSizeDp(this.context).getHeight() / 2 + 150;
        params.leftMargin = Utils.getScreenSizeDp(this.context).getWidth() / 2 - 150;
        TextView gameOverMessage = new TextView(this.context);
        gameOverMessage.setText("Game Over");
        gameOverMessage.setTextSize(0, 200);
        gameOverMessage.setTextColor(Color.RED);
        gameOverMessage.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        gameOverMessage.setLayoutParams(params);
        return gameOverMessage;
    }
}
