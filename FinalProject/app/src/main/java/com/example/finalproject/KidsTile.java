package com.example.finalproject;

import android.content.Context;
import android.graphics.Point;
import android.service.quicksettings.Tile;
import android.util.Size;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class KidsTile extends androidx.appcompat.widget.AppCompatImageView {

    public static final int[] ansResources = {R.string.blue, R.string.green, R.string.black, R.string.white, R.string.red, R.string.orange, R.string.pink, R.string.purple, R.string.yellow}; // Options for the answers - load into dialog
    public static final int[] colorResources = {R.color.blue, R.color.green, R.color.black, R.color.white, R.color.red, R.color.orange, R.color.pink, R.color.purple, R.color.yellow};
    public static final int[] audioResources = {R.raw.blue_rec, R.raw.green_rec, R.raw.black_rec, R.raw.white_rec, R.raw.red_rec, R.raw.orange_rec, R.raw.pink_rec, R.raw.purple_rec, R.raw.yellow_rec,};
    private final ArrayList<Integer> options;
    private final int resourceIndex; // Resource index in the const arrays
    private final Point index; // Index of the tile in the 9x9 square
    private boolean isSolved;

    public KidsTile(Context context, Point aIndex) {
        super(context);

        setLayoutParams(new LinearLayout.LayoutParams(KidsBoard.BOARD_WIDTH_PX/KidsBoard.COLS_NUM, KidsBoard.BOARD_HEIGHT_PX/KidsBoard.ROWS_NUM));

        resourceIndex = new Random().nextInt(9);
        index = aIndex;
        isSolved = false;
        options = new ArrayList<>();
        setBackgroundResource(R.drawable.button_shadow_selector_cream);

        while (options.size() != 2){
            Random random = new Random();
            int randNum = random.nextInt(9);
            if (randNum != resourceIndex && !options.contains(randNum))
                options.add(randNum);
        }
    }

    // Getters
    public int getResourceIndex() {return resourceIndex;}
    public Point getIndex() {return index;}
    public boolean isSolved() {return isSolved;}
    public ArrayList<Integer> getOptions() {return options;}

    // Setters
    public void setIsSolved(boolean state) {isSolved = state;}

    public static KidsTile initializeTile(Context context, Point index){
        return new KidsTile(context, index);
    }
}
