package com.example.turtlerace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Tools t1;
    Button btStart;
    RelativeLayout rl1, rl2, rl3, rl4, rl5;
    RelativeLayout[] layouts;
    ImageView[] turtles;
    int[] speeds;
    Handler handler;
    RelativeLayout.LayoutParams[] paramsArray;
    Intent intent;
    boolean sleep = false;
    boolean[] delays;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1 = new Tools(MainActivity.this);
        btStart = findViewById(R.id.btStart);
        rl1 = findViewById(R.id.ll1);
        rl2 = findViewById(R.id.ll2);
        rl3 = findViewById(R.id.ll3);
        rl4 = findViewById(R.id.ll4);
        rl5 = findViewById(R.id.ll5);
        btStart.setOnClickListener(this);
        paramsArray = new RelativeLayout.LayoutParams[5];
        turtles = new ImageView[5];
        layouts = new RelativeLayout[5];
        speeds = new int[5];
        int[] speedOptions = {500, 1000, 1500, 2000, 2500};
        int[] indexOptions = t1.getUniqueValuesRandomArray(5, 5, 0);
        delays = new boolean[5];
        layouts[0] = rl1;
        layouts[1] = rl2;
        layouts[2] = rl3;
        layouts[3] = rl4;
        layouts[4] = rl5;
        for (int i=0; i<5; i++){
            paramsArray[i] = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            paramsArray[i].topMargin = t1.dpToPx(450);
            turtles[i] = t1.createImageView(70, 70, R.drawable.turtle, paramsArray[i]);
            turtles[i].setOnClickListener(this::onClick);
            layouts[i].addView(turtles[i]);
            speeds[i] = speedOptions[indexOptions[i]];
            delays[i] = false;
        }
        handler = new Handler();
        intent = new Intent(MainActivity.this, MainActivity.class);
    }
    public void moveTurtle(int index){
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0; i<25; i++){
                    int finalI = i;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (450 - finalI*30 == 0){
                                int[] placements = new int[5];
                                placements[0] = index+1;
                                for (int i=1; i<5; i++){
                                    int min = 1000, minIndex = -1;
                                    for (int j=0; j<5; j++){
                                        if (paramsArray[j].topMargin < min && !t1.checkIfEleExistsInArray(placements, j+1)){
                                            min = paramsArray[j].topMargin;
                                            minIndex = j;
                                        }
                                    }
                                    placements[i] = minIndex+1;
                                }
                                String message = "";
                                for (int i=0; i<5; i++){
                                    message+=String.format("Turtle number %d came in %d place", placements[i], i+1);
                                    if (i != 4)
                                        message+="\n";
                                }
                                t1.createAlertDialog("Scoreboard", message, intent);
                                sleep = true;
                            }
                            paramsArray[index].topMargin = t1.dpToPx(450 - finalI*30);
                            turtles[index].setLayoutParams(paramsArray[index]);
                        }
                    });
                    try {
                        if(!sleep){
                            if (delays[index]){
                                Thread.sleep(speeds[index] + 2000);
                                delays[index] = false;
                            }
                            else
                                Thread.sleep(speeds[index]);
                        }
                        else{
                            Thread.sleep(Long.MAX_VALUE);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread1.start();
    }
    @Override
    public void onClick(View view) {
        if (view == btStart){
            for (int i=0; i<5; i++){
                moveTurtle(i);
            }
        }
        for (int i=0; i<5; i++){
            if (view == turtles[i]){
                delays[i] = true;
                break;
            }
        }
    }
}