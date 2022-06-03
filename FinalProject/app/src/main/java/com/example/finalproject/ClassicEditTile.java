package com.example.finalproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.inputmethodservice.Keyboard;
import android.media.MediaPlayer;
import android.util.Log;
import android.util.Size;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import android.view.ActionMode.Callback;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ClassicEditTile extends androidx.appcompat.widget.AppCompatEditText implements View.OnClickListener {

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

        setOnClickListener(this);

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

        if (lengthAfter != 0){
            if (checkForCompletion()){
                // Dismiss the keyboard
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindowToken(), 0);
                clearFocus();

                // Update coin count
                SharedPreferences sp = Utils.defineSharedPreferences(getContext(), "mainRoot");
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users");
                String uuid = Utils.getDataFromSharedPreferences(sp, "UUID", null);
                Utils.getUserFromDatabase(uuid, user -> {
                    // Update coin count
                    mDatabase.child(uuid).child("coins").setValue(user.getCoins() + 200);

                    // Play level completion sound based of the user's sound setting
                    if (user.isSound()){
                        MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), R.raw.level_completed_sound_effect);
                        mediaPlayer.start();
                    }
                });

                // Update achievement handler
                updateAchievementHandler(levelIdentifier);

                // Display level completed dialog
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View v = inflater.inflate(R.layout.classic_level_completed_dialog, null);
                final AlertDialog dialog = new AlertDialog.Builder(getContext()).create();

                ImageButton ibGoBackToLevelSelection = v.findViewById(R.id.ibGoBackToLevelSelection);
                ibGoBackToLevelSelection.setOnClickListener(v1 -> {
                    dialog.dismiss();
                    getContext().startActivity(new Intent(getContext(), MainActivity.class));
                });

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setView(v);
                dialog.setCancelable(true);
                dialog.show();
            }
            // Move to the next tile
            if (orientation == HORIZONTAL) {
                // Move left
                if (indexInBoard.x > 0) {
                    if (levelLayout[indexInBoard.y][indexInBoard.x - 1] == 1) {
                        ClassicEditTile nextTile = (ClassicEditTile) rows[indexInBoard.y].getChildAt(indexInBoard.x - 1);
                        nextTile.setOrientation(HORIZONTAL);
                        rows[indexInBoard.y].getChildAt(indexInBoard.x - 1).requestFocus();
                    }
                }
            } else {
                if (indexInBoard.y < rows.length - 1) {
                    if (levelLayout[indexInBoard.y + 1][indexInBoard.x] == 1) {
                        ClassicEditTile nextTile = (ClassicEditTile) rows[indexInBoard.y + 1].getChildAt(indexInBoard.x);
                        nextTile.setOrientation(VERTICAL);
                        rows[indexInBoard.y + 1].getChildAt(indexInBoard.x).requestFocus();
                    }
                }
            }
        }
    }

    private boolean validateAnswer(int count){
        return content.equals(ClassicBoard.levelAns[levelIdentifier - 1].charAt(ClassicBoard.levelAns[levelIdentifier - 1].length() - count - 1) + "");
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
            if (rows[indexInBoard.y].getChildAt(j) instanceof ClassicEditTile) {
                rows[indexInBoard.y].getChildAt(j).setBackgroundResource(R.drawable.classic_edit_tile_highlighted);
            } else break;
        }
        // Travel left
        for (int j = indexInBoard.x; j >= 0; j--) {
            if (rows[indexInBoard.y].getChildAt(j) instanceof ClassicEditTile) {
                rows[indexInBoard.y].getChildAt(j).setBackgroundResource(R.drawable.classic_edit_tile_highlighted);
            } else break;
        }
    }

    private void highlightColumn(){
        // Travel top
        for (int j = indexInBoard.y; j < rows.length; j++) {
            if (rows[j].getChildAt(indexInBoard.x) instanceof ClassicEditTile) {
                rows[j].getChildAt(indexInBoard.x).setBackgroundResource(R.drawable.classic_edit_tile_highlighted);
            } else break;
        }
        // Travel bottom
        for (int j = indexInBoard.y; j >= 0; j--) {
            if (rows[j].getChildAt(indexInBoard.x) instanceof ClassicEditTile) {
                rows[j].getChildAt(indexInBoard.x).setBackgroundResource(R.drawable.classic_edit_tile_highlighted);
            } else break;
        }
    }

    private void removeHighlightRow(){
        // Travel right
        for (int j = indexInBoard.x; j < Utils.getChildrenViews(rows[indexInBoard.y]); j++) {
            if (rows[indexInBoard.y].getChildAt(j) instanceof ClassicEditTile) {
                rows[indexInBoard.y].getChildAt(j).setBackgroundResource(R.drawable.classic_edit_tile_background);
            } else break;
        }
        // Travel left
        for (int j = indexInBoard.x; j >= 0; j--) {
            if (rows[indexInBoard.y].getChildAt(j) instanceof ClassicEditTile) {
                rows[indexInBoard.y].getChildAt(j).setBackgroundResource(R.drawable.classic_edit_tile_background);
            } else break;
        }
    }

    private void removeHighlightColumn(){
        // Travel top
        for (int j = indexInBoard.y; j < rows.length; j++) {
            if (rows[j].getChildAt(indexInBoard.x) instanceof ClassicEditTile) {
                rows[j].getChildAt(indexInBoard.x).setBackgroundResource(R.drawable.classic_edit_tile_background);
            } else break;
        }
        // Travel bottom
        for (int j = indexInBoard.y; j >= 0; j--) {
            if (rows[j].getChildAt(indexInBoard.x) instanceof ClassicEditTile) {
                rows[j].getChildAt(indexInBoard.x).setBackgroundResource(R.drawable.classic_edit_tile_background);
            } else break;
        }
    }

    private boolean checkForCompletion(){
        int count = 0;
        for (int i=0; i<rows.length; i++){
            for (int j=0; j<Utils.getChildrenViews(rows[i]); j++){
                if (rows[i].getChildAt(j) instanceof ClassicEditTile){
                    // If the tile of type EditTile, than check it's content
                    ClassicEditTile tile = (ClassicEditTile) rows[i].getChildAt(j);
                    if (!tile.validateAnswer(count)){
                        // If the tile's content is not the same as the resource answer, return false, else continue
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void updateAchievementHandler(int levelIdentifier){
        SharedPreferences sp = Utils.defineSharedPreferences(getContext(), "mainRoot");
        String uuid = Utils.getDataFromSharedPreferences(sp, "UUID", null);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        Utils.getUserFromDatabase(uuid, user -> {
            // Update achievement handler
            ArrayList<Integer> classicLevels = user.getClassicLevels();
            // If the achievement has been claimed, do not update its status
            if (classicLevels.get(levelIdentifier - 1) == -1)
                return;
            // Else, update achievement status
            classicLevels.remove(levelIdentifier - 1);
            classicLevels.add(levelIdentifier - 1, 1);
            mDatabase.child(uuid).child("classicLevels").setValue(classicLevels);
        });
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
    public void onClick(View view) {
        if (isFocused()){
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
        }
    }
}
