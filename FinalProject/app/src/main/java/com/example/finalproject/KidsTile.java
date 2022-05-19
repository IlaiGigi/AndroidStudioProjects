package com.example.finalproject;

import android.content.Context;
import android.graphics.Point;
import android.service.quicksettings.Tile;
import android.util.Size;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class KidsTile extends androidx.appcompat.widget.AppCompatImageView {

    private static final int[] ansOptions = {R.string.blue, R.string.green, R.string.black, R.string.white, R.string.red, R.string.orange, R.string.pink, R.string.purple, R.string.yellow};; // Options for the answers - load into dialog
    private static final int[] recIds = {R.raw.coin_sound, R.raw.coin_sound, R.raw.coin_sound, R.raw.coin_sound, R.raw.coin_sound, R.raw.coin_sound, R.raw.coin_sound, R.raw.coin_sound, R.raw.coin_sound,};
    private final ArrayList<String> options;
    private final int resourceIndex; // Resource index in the const arrays
    private final int color; // Color to switch to after getting the right answer
    private final Point index; // Index of the tile in the 9x9 square
    private boolean isSolved;

    public KidsTile(Context context, int aResourceIndex, int aColor, Point aIndex) {
        super(context);

        setLayoutParams(new LinearLayout.LayoutParams(KidsBoard.BOARD_WIDTH_PX/KidsBoard.COLS_NUM, KidsBoard.BOARD_HEIGHT_PX/KidsBoard.ROWS_NUM));

        resourceIndex = aResourceIndex;
        color = aColor;
        index = aIndex;
        isSolved = false;
        options = new ArrayList<>();
        options.add(context.getString(ansOptions[resourceIndex]));
        setBackgroundResource(R.drawable.button_shadow_selector_cream);

        while (options.size() != 3){
            Random random = new Random();
            int randNum = random.nextInt(9);
            if (!options.contains(context.getString(ansOptions[randNum])))
                options.add(context.getString(ansOptions[randNum]));
        }
    }

    // Getters
    public int getResourceIndex() {return resourceIndex;}
    public int getColor() {return color;}
    public Point getIndex() {return index;}
    public boolean isCompleted() {return isSolved;}
    public ArrayList<String> getOptions() {return options;}

    // Setters
    public void setIsSolved(boolean state) {isSolved = state;}

    public static KidsTile initializeTile(Context context, int resourceIndex, int color, Point index){
        return new KidsTile(context, resourceIndex, color, index);
    }
}
