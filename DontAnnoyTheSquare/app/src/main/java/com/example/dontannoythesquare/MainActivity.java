package com.example.dontannoythesquare;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements Runnable, View.OnClickListener {
    String[] sentences = {"Don't annoy the square", "Annoy the square"};
    TextView tvScore, tvInstruction;
    ImageView ivRedOval, ivGreenOval, ivBlueOval, ivSquare;
    Thread switchSentences;
    Handler handler;
    Random random;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvScore = findViewById(R.id.tvScore);
        tvInstruction = findViewById(R.id.tvInstruction);
        ivRedOval = findViewById(R.id.ivRedOval);
        ivGreenOval = findViewById(R.id.ivGreenOval);
        ivBlueOval = findViewById(R.id.ivBlueOval);
        ivSquare = findViewById(R.id.ivSquare);
        ivRedOval.setOnClickListener(this);
        ivGreenOval.setOnClickListener(this);
        ivBlueOval.setOnClickListener(this);
        switchSentences = new Thread(this);
        handler = new Handler();
        switchSentences.start();
        random = new Random();
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
        else if (view == ivGreenOval)
            switchColor(Color.parseColor("#00FF00"));
        else if (view == ivBlueOval)
            switchColor(Color.parseColor("#0000FF"));
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
}