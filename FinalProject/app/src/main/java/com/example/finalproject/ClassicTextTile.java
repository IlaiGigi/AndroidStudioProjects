package com.example.finalproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.Size;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class ClassicTextTile extends androidx.appcompat.widget.AppCompatTextView{

    private final Size size;
    private final Point IndexInBoard;
    private String text;

    @SuppressLint("RestrictedApi")
    public ClassicTextTile(@NonNull Context context, Point aIndex, String aText) {
        super(context);
        size = new Size(131, 131); // Size of the textview in pixels = (50dp * 50dp)
        IndexInBoard = aIndex;


        // if text contains " " replace it with \n
        if (aText.contains(" ") || aText.contains("/")){
            aText = aText.replace("/", "\n");
            aText = aText.replace(" ", "\n");
        }
        text = aText;

        setBackgroundResource(R.drawable.classic_text_tile_background); // Setting the background of the textview


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size.getWidth(), size.getHeight());
        params.setMargins(1, Utils.dpToPx(context, -25), 1, 1); // Fixing rendering issue, TextTile renders at +25dp from the top, so we must decrement the excess margin
        setLayoutParams(params);
        setTextColor(getResources().getColor(R.color.black));

        setAutoSizeTextTypeUniformWithConfiguration(10, 30, 1, TypedValue.COMPLEX_UNIT_DIP); // This allows text size to be dynamically set depending on the text
        // Set text font
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/calibri.ttf");
        setTypeface(face);
        setTextAlignment(TEXT_ALIGNMENT_CENTER);
        setText(text);
    }

    public Point getIndexInBoard() {
        return IndexInBoard;
    }

    public String getText() {
        return text;
    }

    public static ClassicTextTile getInstance(@NonNull Context context, Point aIndex, String aText) {
        return new ClassicTextTile(context, aIndex, aText);
    }
}
