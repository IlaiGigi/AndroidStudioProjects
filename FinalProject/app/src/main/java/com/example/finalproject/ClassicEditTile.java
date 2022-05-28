package com.example.finalproject;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;
import android.util.Size;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.view.ActionMode;

public class ClassicEditTile extends androidx.appcompat.widget.AppCompatEditText implements View.OnLongClickListener {

    private static final int VERTICAL = 0;
    private static final int HORIZONTAL = 1;
    private final int[][] levelLayout; // The layout and the level identifier are needed to highlight rows and columns accordingly
    private final int levelIdentifier;
    private LinearLayout[] rows;
    private final Size size;
    private final Point indexInBoard;
    private String content;
    private int orientation;

    public ClassicEditTile(@NonNull Context context, int levelIdentifier, LinearLayout[] aRows, Point aIndex) {
        super(context);

        rows = aRows;

        this.levelIdentifier = levelIdentifier;
        levelLayout = ClassicBoard.levelLayouts[levelIdentifier - 1];

        size = new Size(131, 131); // Size of the textview in pixels = (50dp * 50dp)

        indexInBoard = aIndex;
        content = ""; // Edit text is initialized with empty string
        orientation = VERTICAL; // Edit text is initialized with vertical travel orientation

        setOnLongClickListener(this);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size.getWidth(), size.getHeight());
        params.setMargins(1,1, 1, 1);
        setLayoutParams(params);

        setFilters(new android.text.InputFilter[]{new android.text.InputFilter.LengthFilter(1)}); // Edit text is limited to 1 character

        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/calibri.ttf"); // Setting the font
        setTypeface(face);
        setBackgroundResource(R.drawable.classic_edit_tile_background); // Setting the background of the edit text
        setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30); // Set the text size to 30dp
        setTextAlignment(TEXT_ALIGNMENT_CENTER);
        setTextColor(getResources().getColor(R.color.black));
        setText(content);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);

        content = text.toString(); // Update the content of the edit text
        // Check for game completion

        if (lengthAfter != 0){
            // Move to the next tile
            if (orientation == HORIZONTAL) {
                // Move left
                if (indexInBoard.x > 0) {
                    ClassicEditTile nextTile = (ClassicEditTile) rows[indexInBoard.y].getChildAt(indexInBoard.x - 1);
                    nextTile.setOrientation(HORIZONTAL);
                    rows[indexInBoard.y].getChildAt(indexInBoard.x - 1).requestFocus();
                }
            } else {
                if (indexInBoard.y < rows.length - 1) {
                    ClassicEditTile nextTile = (ClassicEditTile) rows[indexInBoard.y + 1].getChildAt(indexInBoard.x);
                    nextTile.setOrientation(VERTICAL);
                    rows[indexInBoard.y + 1].getChildAt(indexInBoard.x).requestFocus();
                }
            }
        }
    }

    private void validateAnswer(int levelIdentifier){
        // Check final answer from the static implementation of the board
    }

    public Point getIndexInBoard() {
        return indexInBoard;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public static ClassicEditTile getInstance(@NonNull Context context, int levelIdentifier, LinearLayout[] rows, Point aIndex) {
        return new ClassicEditTile(context, levelIdentifier, rows, aIndex);
    }

    private void highlightRow(){
        // Travel right
        for (int j = indexInBoard.x; j < Utils.getChildrenViews(rows[indexInBoard.y]); j++) {
            if (levelLayout[indexInBoard.y][j] == 1) {
                rows[indexInBoard.y].getChildAt(j).setBackgroundResource(R.drawable.classic_edit_tile_highlighted);
            } else break;
        }
        // Travel left
        for (int j = indexInBoard.x; j >= 0; j--) {
            if (levelLayout[indexInBoard.y][j] == 1) {
                rows[indexInBoard.y].getChildAt(j).setBackgroundResource(R.drawable.classic_edit_tile_highlighted);
            } else break;
        }
    }

    private void highlightColumn(){
        // Travel top
        for (int j = indexInBoard.y; j < rows.length; j++) {
            if (levelLayout[j][indexInBoard.x] == 1) {
                rows[j].getChildAt(indexInBoard.x).setBackgroundResource(R.drawable.classic_edit_tile_highlighted);
            } else break;
        }
        // Travel bottom
        for (int j = indexInBoard.y; j >= 0; j--) {
            if (levelLayout[j][indexInBoard.x] == 1) {
                rows[j].getChildAt(indexInBoard.x).setBackgroundResource(R.drawable.classic_edit_tile_highlighted);
            } else break;
        }
    }

    private void removeHighlightRow(){
        // Travel right
        for (int j = indexInBoard.x; j < Utils.getChildrenViews(rows[indexInBoard.y]); j++) {
            if (levelLayout[indexInBoard.y][j] == 1) {
                rows[indexInBoard.y].getChildAt(j).setBackgroundResource(R.drawable.classic_edit_tile_background);
            } else break;
        }
        // Travel left
        for (int j = indexInBoard.x; j >= 0; j--) {
            if (levelLayout[indexInBoard.y][j] == 1) {
                rows[indexInBoard.y].getChildAt(j).setBackgroundResource(R.drawable.classic_edit_tile_background);
            } else break;
        }
    }

    private void removeHighlightColumn(){
        // Travel top
        for (int j = indexInBoard.y; j < rows.length; j++) {
            if (levelLayout[j][indexInBoard.x] == 1) {
                rows[j].getChildAt(indexInBoard.x).setBackgroundResource(R.drawable.classic_edit_tile_background);
            } else break;
        }
        // Travel bottom
        for (int j = indexInBoard.y; j >= 0; j--) {
            if (levelLayout[j][indexInBoard.x] == 1) {
                rows[j].getChildAt(indexInBoard.x).setBackgroundResource(R.drawable.classic_edit_tile_background);
            } else break;
        }
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused){
            // "Select" self and highlight the corresponding row or column
            setBackgroundResource(R.drawable.classic_edit_tile_selected);
            if (orientation == HORIZONTAL) {
                highlightRow();
            } else {
                highlightColumn();
            }
        }else
        {
            // "Deselect" self and remove the corresponding highlight
            setBackgroundResource(R.drawable.classic_edit_tile_background);
            if (orientation == HORIZONTAL) {
                removeHighlightRow();
            } else {
                removeHighlightColumn();
            }
        }
    }

    @Override
    public boolean onLongClick(View view) {
        // Switch orientation
        if (orientation == HORIZONTAL) {
            removeHighlightRow();
            orientation = VERTICAL;
            highlightColumn();
        } else {
            removeHighlightColumn();
            orientation = HORIZONTAL;
            highlightRow();
        }
        return false;
    }
}
