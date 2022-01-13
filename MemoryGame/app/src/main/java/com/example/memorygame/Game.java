package com.example.memorygame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Random;

public class Game extends AppCompatActivity implements View.OnClickListener {

    ImageView[] cards;
    int[] lastIndexes = new int[2];
    boolean player1 = true;
    int p1Points = 0, p2Points = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();
        int pairNum = intent.getIntExtra("pairnum", 0);
        int pairNum1 = pairNum;
        TableLayout cardsTable = findViewById(R.id.main);
        if (pairNum % 2 == 1)
            pairNum++;
        LinearLayout[] rows = new LinearLayout[pairNum / 2];
        LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < rows.length; i++)
        {
            rows[i] = new LinearLayout(this);
            rows[i].setLayoutParams(params);
            rows[i].setOrientation(LinearLayout.HORIZONTAL);
            cardsTable.addView(rows[i]);
        }
        ImageView[] imageViews = new ImageView[pairNum1 * 2];
        for (int i = 0; i < imageViews.length; i++)
        {
            imageViews[i] = new ImageView(this);
            LinearLayout.LayoutParams imageViewParams = new LinearLayout.LayoutParams(dpToPx(100), dpToPx(100));
            imageViews[i].setLayoutParams(imageViewParams);
            imageViews[i].setImageDrawable(getDrawable(R.drawable.back));
            imageViews[i].setTag(getDrawable(R.drawable.back));
            imageViews[i].setOnClickListener(this::onClick);
            rows[i / 4].addView(imageViews[i]);

        }
        Drawable[] pictures = new Drawable[8];
        pictures[0] = getDrawable(R.drawable.cosmo);
        pictures[1] = getDrawable(R.drawable.juan);
        pictures[2] = getDrawable(R.drawable.monke);
        pictures[3] = getDrawable(R.drawable.otis);
        pictures[4] = getDrawable(R.drawable.patrick);
        pictures[5] = getDrawable(R.drawable.peter);
        pictures[6] = getDrawable(R.drawable.spongebob);
        pictures[7] = getDrawable(R.drawable.thanos);
        Drawable[] randomImages = new Drawable[8];
        Random rnd = new Random();
        int index;
        for (int i = 0; i < randomImages.length; i++)
        {
            boolean alreadyIn = true;
            while (alreadyIn)
            {
                alreadyIn = false;
                index = rnd.nextInt(pictures.length);
                for (int j = 0; j < randomImages.length; j++)
                {
                    if (pictures[index].equals(randomImages[j]))
                    {
                        alreadyIn = true;
                    }
                }
                if (!alreadyIn)
                {
                    randomImages[i] = pictures[index];
                }
            }
        }

        rnd = new Random();
        for (int i = 0; i < imageViews.length; i++)
        {
            boolean alreadyIn = true;
            while (alreadyIn)
            {
                alreadyIn = false;
                int counter = 0;
                index = rnd.nextInt(pairNum1);
                for (int j = 0; j < imageViews.length; j++)
                {
                    if (imageViews[j].getTag() == randomImages[index])
                    {
                        counter++;
                    }
                }
                if (counter == 2)
                {
                    alreadyIn = true;
                }
                else
                {
                    imageViews[i].setTag(randomImages[index]);
                }
            }
        }
        cards = imageViews;
        lastIndexes[0] = -10;
        lastIndexes[1] = -10;
    }

    @Override
    public void onClick(View view) {

        if (lastIndexes[0] != -10 && lastIndexes[1] != -10)
        {
            if (cards[lastIndexes[0]].getDrawable() != cards[lastIndexes[1]].getDrawable())
            {
                cards[lastIndexes[0]].setImageDrawable(getDrawable(R.drawable.back));
                cards[lastIndexes[1]].setImageDrawable(getDrawable(R.drawable.back));
                cards[lastIndexes[0]].setClickable(true);
                cards[lastIndexes[1]].setClickable(true);
                player1 = !player1;
            }
            lastIndexes[0] = -10;
            lastIndexes[1] = -10;
        }

        if (R.id.button != view.getId())
        {
            ImageView imageView = (ImageView) view;
            Drawable temp = (Drawable) imageView.getTag();
            imageView.setImageDrawable(temp);
            imageView.setClickable(false);
            for (int i = 0; i < cards.length; i++)
            {
                if (cards[i].equals(imageView))
                {
                    if (lastIndexes[0] == -10)
                        lastIndexes[0] = i;
                    else
                        lastIndexes[1] = i;
                }
            }
        }
        else
        {
            Intent intent = new Intent(Game.this, MainActivity.class);
            startActivity(intent);
        }

        if (lastIndexes[0] != -10 && lastIndexes[1] != -10)
        {
            if (cards[lastIndexes[0]].getDrawable() == cards[lastIndexes[1]].getDrawable())
            {
                if (player1)
                {
                    p1Points++;
                    TextView p = findViewById(R.id.p1);
                    p.setText("Player 1: " + p1Points);
                }
                else
                {
                    p2Points++;
                    TextView p = findViewById(R.id.p2);
                    p.setText("Player 2: " + p2Points);
                }
            }
            else
            {
                TextView turn = findViewById(R.id.turn);
                if (player1)
                    turn.setText("Player 2 turn");
                else
                    turn.setText("Player 1 turn");
            }
        }

    }

    private int dpToPx(int dps)
    {
        final float scale = getResources().getDisplayMetrics().density;
        int pixels = (int) (dps * scale + 0.5f);
        return pixels;
    }
}