package com.example.memorygameupdated;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<Drawable> picList;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    LinearLayout ll1, ll2, ll3, ll4;
    LinearLayout[] layouts;
    Intent intent;
    Drawable[] cardPics;
    ImageView[] cards;
    int numOfPairs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = getSharedPreferences("main", MODE_PRIVATE);
        editor = sp.edit();
        numOfPairs = sp.getInt("numOfPairs", -1);
        intent = new Intent(MainActivity.this, CustomizeActivity.class);
        // Initialize the array containing the images.
        cardPics = new Drawable[8];
        cardPics[0] = getDrawable(R.drawable.arabfunny);
        cardPics[1] = getDrawable(R.drawable.mogusdrip);
        cardPics[2] = getDrawable(R.drawable.johnxina);
        cardPics[3] = getDrawable(R.drawable.bruceitsbeen);
        cardPics[4] = getDrawable(R.drawable.itswednesday);
        cardPics[5] = getDrawable(R.drawable.rickastley);
        cardPics[6] = getDrawable(R.drawable.thinkmark);
        cardPics[7] = getDrawable(R.drawable.juan);
        ll1 = findViewById(R.id.ll1);
        ll2 = findViewById(R.id.ll2);
        ll3 = findViewById(R.id.ll3);
        ll4 = findViewById(R.id.ll4);
        layouts = new LinearLayout[4];
        layouts[0] = ll1;
        layouts[1] = ll2;
        layouts[2] = ll3;
        layouts[3] = ll4;
        picList = new ArrayList<>();
        cards = new ImageView[numOfPairs * 2];
        // Creating the ImageParams.
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(convertDpToPx(100), convertDpToPx(100));
        // Grabbing the exact number of pictures.
        for (int i=0; i<numOfPairs; i++){
            picList.add(cardPics[i]);
            for (int j=0; j<2; j++){
                ImageView imageView = new ImageView(this);
                imageView.setTag(cardPics[i]);
                imageView.setImageDrawable(getDrawable(R.drawable.cardback));
                imageView.setOnClickListener(this);
                imageView.setLayoutParams(imageParams);
                cards[i*2+j] = imageView;
                layouts[(int) Math.floor(i/2)].addView(imageView);
            }
        }
        randomizeImages();
        cards[0].setImageDrawable((Drawable) cards[0].getTag());
        cards[1].setImageDrawable((Drawable) cards[1].getTag());
    }

    public int convertDpToPx(int dp) {
        final float scale = getResources().getDisplayMetrics().density;
        int px = (int) (dp * scale + 0.5f);
        return px;
    }

    @Override
    public void onClick(View view) {
        for (int i=0; i<cards.length; i++){
            if (view == cards[i]){
                // Implement card validation
            }
        }
    }

    public void randomizeImages(){
        ArrayList<Integer> randomized = new ArrayList<>();
        Random random = new Random();
        for (int i=0; i<numOfPairs; i++){
            int num1 = -1;
            int num2 = -1;
            while (randomized.contains(num1) && num1 < 0){
                num1 = random.nextInt(numOfPairs*2);
            }
            randomized.add(num1);
            while (randomized.contains(num2) && num2 < 0){
                num2 = random.nextInt(numOfPairs*2);
            }
            randomized.add(num2);
            switchImages(num1, num2);
        }
    }

    public void switchImages(int index1, int index2){
        cards[index1].setTag(cards[index2].getTag());
        cards[index2].setTag(cards[index1].getTag());
    }

    public boolean validateImages(int index1, int index2){
        return cards[index1].getTag().equals(cards[index2].getTag());
    }
}