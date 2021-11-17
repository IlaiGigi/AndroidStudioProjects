package com.example.gameofmemory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.Random;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    int numOfPairs;
    ArrayList<Integer> boardIndex = new ArrayList<Integer>();
    int[] imgsIndex;
    ImageView[] imgViews = new ImageView[numOfPairs*2];
    Drawable[] cards = new Drawable[numOfPairs];
    Random random = new Random();
    TextView tvHeadline, tvTries;
    Button btNewGame;
    int countFlipped = 0;
    int open1 = -1;
    int open2 = -1;
    int tries = 0;
    Intent intent;
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = getSharedPreferences("main",  MODE_PRIVATE);
        edit = sp.edit();
        numOfPairs = sp.getInt("n", -1);
        intent = new Intent(MainActivity.this, CustomizeActivity.class);
        tvHeadline = findViewById(R.id.tvHeadline);
        tvTries = findViewById(R.id.tvTries);
        btNewGame = findViewById(R.id.btNewGame);
        imgsIndex = new int[numOfPairs*2];
        imgViews = new ImageView[numOfPairs*2];
        cards = new Drawable[numOfPairs];
        /*imgViews[0] = findViewById(R.id.zero);
        imgViews[1] = findViewById(R.id.one);
        imgViews[2] = findViewById(R.id.two);
        imgViews[3] = findViewById(R.id.three);
        imgViews[4] = findViewById(R.id.four);
        imgViews[5] = findViewById(R.id.five);
        imgViews[6] = findViewById(R.id.six);
        imgViews[7] = findViewById(R.id.seven);
        imgViews[8] = findViewById(R.id.eight);
        imgViews[9] = findViewById(R.id.nine);
        imgViews[10] = findViewById(R.id.ten);
        imgViews[11] = findViewById(R.id.eleven);
        imgViews[12] = findViewById(R.id.twelve);
        imgViews[13] = findViewById(R.id.thirdteen);
        imgViews[14] = findViewById(R.id.fourteen);
        imgViews[15] = findViewById(R.id.fifthteen);*/
        cards[0] = getDrawable(R.drawable.arabfunny);
        cards[1] = getDrawable(R.drawable.mogusdrip);
        cards[2] = getDrawable(R.drawable.johnxina);
        cards[3] = getDrawable(R.drawable.bruceitsbeen);
        cards[4] = getDrawable(R.drawable.itswednesday);
        cards[5] = getDrawable(R.drawable.rickastley);
        cards[6] = getDrawable(R.drawable.thinkmark);
        cards[7] = getDrawable(R.drawable.juan);
        btNewGame.setOnClickListener(this);
        randomizeImages();
        RelativeLayout main_layout = findViewById(R.id.main_layout);
        LinearLayout newLayout = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.leftMargin = 150;
        params.topMargin = 500;
        newLayout.setLayoutParams(params);
        LinearLayout.LayoutParams imageViewParams = new LinearLayout.LayoutParams(convertDptoPx(100), convertDptoPx(100));
        int margY = 0;
        for (int i=0; i<numOfPairs*2; i++){
            if (i % 3 == 0){
                margY+=100;
            }
            imageViewParams.topMargin = margY;
            ImageView image = new ImageView(this);
            image.setOnClickListener(this);
            if (i % 2 == 0){
                imgViews[i] = image;
                imgViews[i+1] = image;
            }
            image.setLayoutParams(imageViewParams);
            image.setImageResource(R.drawable.cardback);
            newLayout.addView(image);
        }
        main_layout.addView(newLayout);

    }
    public int convertDptoPx(int dp) {
        final float scale = getResources().getDisplayMetrics().density;
        int px = (int) (dp * scale + 0.5f);
        return px;
    }
    @Override
    public void onClick(View view) {
        if (view == btNewGame){
            boardIndex.clear();
            randomizeImages();
            open1 = -1;
            open2 = -1;
            tries = 0;
            countFlipped = 0;
            tvTries.setText("Tries: 0");
            for(int i=0; i<numOfPairs*2; i++)
                imgViews[i].setImageResource(R.drawable.cardback);
        }
        if (view == imgViews[0]){
            initiateClick(0);
        }
        if (view == imgViews[1]){
            initiateClick(1);
        }
        if (view == imgViews[2]){
            initiateClick(2);
        }
        if (view == imgViews[3]){
            initiateClick(3);
        }
        if (view == imgViews[4]){
            initiateClick(4);
        }
        if (view == imgViews[5]){
            initiateClick(5);
        }
        if (view == imgViews[6]){
            initiateClick(6);
        }
        if (view == imgViews[7]){
            initiateClick(7);
        }
        if (view == imgViews[8]){
            initiateClick(8);
        }
        if (view == imgViews[9]){
            initiateClick(9);
        }
        if (view == imgViews[10]){
            initiateClick(10);
        }
        if (view == imgViews[11]){
            initiateClick(11);
        }
        if (view == imgViews[12]){
            initiateClick(12);
        }
        if (view == imgViews[13]){
            initiateClick(13);
        }
        if (view == imgViews[14]){
            initiateClick(14);
        }
        if (view == imgViews[15]){
            initiateClick(15);
        }
    }
    public void initiateClick(int index){
        if (countFlipped == 2) {
            if (!validateImages(open1, open2)) {
                resetImages();
                tries++;
                tvTries.setText(String.format("Tries: %d", tries));
            }
            open1 = index;
            open2 = -1;
            countFlipped = 0;
        }
        countFlipped++;
        if (countFlipped == 1)
            open1 = index;
        else if (countFlipped == 2)
            if (open1 == index)
                countFlipped--;
            else
                open2 = index;
        imgViews[index].setImageDrawable(cards[imgsIndex[index]]);
    }
    public boolean isPresent(int num){
        for (int value : boardIndex){
            if (value == num)
                return true;
        }
        return false;
    }
    public void randomizeImages(){
        for (int i=0; i<numOfPairs; i++){
            for (int j=0; j<2; j++){
                int n = random.nextInt(numOfPairs*2);
                while (isPresent(n))
                    n = random.nextInt(numOfPairs*2);
                imgsIndex[n] = i;
                boardIndex.add(n);
            }
        }
    }
    public boolean validateImages(int index1, int index2){
        return imgViews[index1].getDrawable().equals(imgViews[index2].getDrawable()) && index2 != index1;
    }
    public void resetImages(){
        imgViews[open1].setImageResource(R.drawable.cardback);
        imgViews[open2].setImageResource(R.drawable.cardback);
    }
}