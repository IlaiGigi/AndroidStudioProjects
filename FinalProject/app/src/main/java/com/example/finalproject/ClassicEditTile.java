package com.example.finalproject;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Typeface;
import android.util.Size;
import android.util.TypedValue;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

public class ClassicEditTile extends androidx.appcompat.widget.AppCompatEditText {

    private static final int VERTICAL = 0;
    private static final int HORIZONTAL = 1;
    private final Size size;
    private final Point IndexInBoard;
    private String content;
    private int orientation;

    public ClassicEditTile(@NonNull Context context, Point aIndex) {
        super(context);

        size = new Size(131, 131); // Size of the textview in pixels = (50dp * 50dp)

        IndexInBoard = aIndex;
        content = ""; // Edit text is initialized with empty string
        orientation = VERTICAL; // Edit text is initialized with vertical travel orientation

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


        if (lengthAfter == 0){
            // don't move to the next tile
        }
        else {
            // move to the next tile
        }
    }

    private void validateAnswer(int levelIdentifier){
        // Check final answer from the static implementation of the board
    }

    public void updateOrientation(int orientation){
        this.orientation = orientation;
    }

    public Point moveToNextTile(int levelIdentifier){
        // Look for a possible travel destination considering the current orientation
        return new Point(0, 0);
    }

    public Point getIndexInBoard() {
        return IndexInBoard;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static ClassicEditTile getInstance(@NonNull Context context, Point aIndex) {
        return new ClassicEditTile(context, aIndex);
    }
}
