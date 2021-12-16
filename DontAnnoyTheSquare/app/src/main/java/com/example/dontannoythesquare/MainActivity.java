package com.example.dontannoythesquare;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements Runnable, View.OnClickListener {
    public static TextView tvScore;
    RelativeLayout layout;
    String[] sentences = {"Don't annoy the square", "Annoy the square"};
    TextView tvInstruction;
    ImageView ivRedOval, ivGreenOval, ivBlueOval, ivSquare;
    Thread switchSentences, squareThread;
    Handler handler;
    Random random;
    Square square;
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
        ivRedOval.setOnClickListener(this);
        ivGreenOval.setOnClickListener(this);
        ivBlueOval.setOnClickListener(this);
        switchSentences = new Thread(this);
        handler = new Handler();
        switchSentences.start();
        random = new Random();
        score = 0;
        squareThread = new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });
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
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (tvInstruction.getText().toString().equals(sentences[0]))
                        tvInstruction.setText(sentences[1]);
                    else
                        tvInstruction.setText(sentences[0]);
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        if (view == ivRedOval)
            switchColor(Color.parseColor("#FF0000"));
        if (view == ivGreenOval)
            switchColor(Color.parseColor("#00FF00"));
        if (view == ivBlueOval)
            switchColor(Color.parseColor("#0000FF"));
        if (view == tvScore){
            animateScore();
        }
    }
    public void switchColor(int color){
        int scoreColor = tvScore.getCurrentTextColor();
        ValueAnimator animator = ValueAnimator.ofInt(scoreColor, color);
        animator.setDuration(1500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (int)valueAnimator.getAnimatedValue();
                tvScore.setTextColor(val);
                tvInstruction.setTextColor(val);
            }
        });
        animator.start();
    }
    public void animateScore(){
        ValueAnimator animator = ValueAnimator.ofInt(0, score);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (int)valueAnimator.getAnimatedValue();
                tvScore.setText(String.format("Score: %d", score));
            }
        });
        animator.start();
    }
    public void moveSquare(){

    }
}