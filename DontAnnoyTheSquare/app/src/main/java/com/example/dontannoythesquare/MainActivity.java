package com.example.dontannoythesquare;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements Runnable, View.OnClickListener {
    public static TextView tvScore;
    RelativeLayout layout;
    String[] sentences = {"Don't annoy the square", "Annoy the square"};
    public static TextView tvInstruction;
    ImageView ivRedOval, ivGreenOval, ivBlueOval, ivSquare;
    Thread switchSentences, squareThread;
    Handler handler;
    Random random;
    Square square;
    int currentColor;
    int score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = findViewById(R.id.layout);
        tvScore = findViewById(R.id.tvScore);
        ivSquare = findViewById(R.id.ivSquare);
        tvInstruction = findViewById(R.id.tvInstruction);
        ivRedOval = findViewById(R.id.ivRedOval);
        ivGreenOval = findViewById(R.id.ivGreenOval);
        ivBlueOval = findViewById(R.id.ivBlueOval);
        tvScore = findViewById(R.id.tvScore);
        ivRedOval.setOnClickListener(this);
        ivGreenOval.setOnClickListener(this);
        ivBlueOval.setOnClickListener(this);
        tvScore.setOnClickListener(this);
        switchSentences = new Thread(this);
        handler = new Handler();
        switchSentences.start();
        random = new Random();
        currentColor = Color.WHITE;
        score = 0;
        squareThread = new Thread(() -> handler.post(() -> {

        }));
    }

    @Override
    public void run() {
        while (true){
            int num = Math.abs(random.nextInt(4) - random.nextInt(3));
            try {
                Thread.sleep(num*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.post(() -> {
                if (tvInstruction.getText().toString().equals(sentences[0]))
                    tvInstruction.setText(sentences[1]);
                else
                    tvInstruction.setText(sentences[0]);
            });
        }
    }

    @Override
    public void onClick(View view) {
        if (view == ivRedOval){
            switchColor(Color.RED);
        }
        if (view == ivGreenOval){
            switchColor(Color.GREEN);
        }
        if (view == ivBlueOval){
            switchColor(Color.BLUE);
        }
        if (view == tvScore){
            animateScore();
        }
    }
    public void switchColor(int color){
        ValueAnimator animator = ValueAnimator.ofArgb(currentColor, color);
        animator.setDuration(1500);
        animator.addUpdateListener(valueAnimator -> {
            int val = (int)valueAnimator.getAnimatedValue();
            tvScore.setTextColor(val);
            tvInstruction.setTextColor(val);
            currentColor = val;
        });
        animator.start();
    }
    public void animateScore(){
        String grabScore = tvScore.getText().toString();
        StringBuilder buildScore = new StringBuilder();
        for (int i=grabScore.length()-1; i>=0; i--){
            if (grabScore.charAt(i) == ' '){
                break;
            }
            buildScore.append(grabScore.charAt(i));
        }
        ValueAnimator animator = ValueAnimator.ofInt(0, Integer.parseInt(String.valueOf(buildScore)));
        animator.setDuration(1000);
        animator.addUpdateListener(valueAnimator -> {
            int val = (int)valueAnimator.getAnimatedValue();
            tvScore.setText(String.format("Score: %d", val));
        });
        animator.start();
    }
    public void moveSquare(){

    }
}