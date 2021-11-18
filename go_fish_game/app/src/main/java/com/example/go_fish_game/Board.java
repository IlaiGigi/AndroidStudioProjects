package com.example.go_fish_game;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Board extends LinearLayout implements View.OnClickListener {
    final int Board_size = 8;
    SeaTile[][] boardMatrix = new SeaTile[Board_size][Board_size];
int player;
    int player1I=0;
    int player1J=0;
    int player2I=0;
    int player2J=0;


    public Board(Context context,int player) {
        super(context);
        this.player=player;
        invalidate();
        this.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        this.setOrientation(VERTICAL);
        for (int i = 0; i < 8; i++) {
            LinearLayout ly=new LinearLayout(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ly.setLayoutParams(params);
            ly.setOrientation(LinearLayout.HORIZONTAL);
            for (int j = 0; j < 8; j++) {
                boardMatrix[i][j]=new SeaTile(context,i+j);
                LinearLayout.LayoutParams imageViewParams = new LinearLayout.LayoutParams(getScreenWidth(context) / 8, getScreenWidth(context) / 8);
                boardMatrix[i][j].setLayoutParams(imageViewParams);
               ly.addView( boardMatrix[i][j]);
                boardMatrix[i][j].setOnClickListener(this);
            }

            this.addView(ly);
        }
    }
    public void addRipple (int i, int j)
    {
        boardMatrix[i][j].addRipple();
    }
    public void removeRipple (int i, int j)
    {
        boardMatrix[i][j].removeRipple();
    }
    private int getScreenWidth(Context context)
    {
        DisplayMetrics displayMetrics = new DisplayMetrics();

        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        return width;
    }

    public SeaTile[][] getBoardMatrix() {
        return boardMatrix;
    }

   public void switchTurn()
   {
       if(this.player==1)
       {
           boardMatrix[this.player2I][this.player2J].removeRipple();
           boardMatrix[this.player1I][ this.player1J].getImageView().setImageResource(R.drawable.fisherman);
       }
       else {
           boardMatrix[this.player1I][ this.player1J].removeRipple();
           boardMatrix[this.player2I][this.player2J].getImageView().setImageResource(R.drawable.fish);
               }
           }




    public void setPlayer(int player) {
        this.player = player;
    }

    @Override
    public void onClick(View v) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                    if (v == boardMatrix[i][j]) {
                        if(player==1) {
                            boardMatrix[i][j].getImageView().setImageResource(R.drawable.fisherman);
                            this.player1I=i;
                            this.player1J=j;
                        }
                        else {
                            boardMatrix[i][j].getImageView().setImageResource(R.drawable.fish);
                            this.player2I=i;
                            this.player2J=j;
                        }
                    } else
                        boardMatrix[i][j].removeRipple();

                }

        }
    }


}

