package com.example.finalproject;

import android.content.Context;
import android.util.Size;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.LinearLayoutCompat;

public class KidsBoard extends LinearLayout implements View.OnClickListener{

    public static final int BOARD_WIDTH_PX = 1050; // = 400dp
    public static final int BOARD_HEIGHT_PX = 1050; // = 400dp
    public static final int ROWS_NUM = 3;
    public static final int COLS_NUM = 3;
    private KidsTile[][] tiles; // 3x3 2d array
    private LinearLayout[] rows;
    private final Size boardSize;

    public KidsBoard(Context context){
        super(context);
        setOrientation(VERTICAL);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(BOARD_WIDTH_PX, BOARD_HEIGHT_PX);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        setLayoutParams(params);

        boardSize = new Size(ROWS_NUM, COLS_NUM);
        tiles = new KidsTile[ROWS_NUM][COLS_NUM];


        rows = new LinearLayout[]{new LinearLayout(context), new LinearLayout(context), new LinearLayout(context)};
        for (int i=0; i<ROWS_NUM; i++){
            rows[i].setOrientation(HORIZONTAL);
            rows[i].setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, BOARD_HEIGHT_PX / 3));
            addView(rows[i]);
        }
    }

    // Getters
    public Size getBoardSize() {return boardSize;}
    public KidsTile[][] getTiles() {return tiles;}


    @Override
    public void onClick(View view) {
        // Construct dialog using the parameters of the tile
        KidsTile tile = (KidsTile) view;
    }

    public void addTile(KidsTile tile){
        tiles[tile.getIndex().x][tile.getIndex().y] = tile;
    }

    public void loadTiles(){
        // Make sure there are no empty tile cells
        for (int i=0; i<ROWS_NUM; i++){
            for (int j=0; j<COLS_NUM; j++){
                if (tiles[i][j] == null)
                    return;
            }
        }

        for (int i=0; i<ROWS_NUM; i++){
            for (int j=0; j<COLS_NUM; j++){
                tiles[i][j].setOnClickListener(this);
                rows[i].addView(tiles[i][j]);
            }
        }
    }
}
