package com.example.finalproject;

import android.Manifest;
import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ClassicBoard extends LinearLayout {

    private final Size size;
    private int levelIdentifier;
    private LinearLayout[] rows;

    // Definition: 0 = definition box with text, 1 = answer box, -1 = empty definition box
    public static final int[][] level1Layout =
            {
                    {0, 1, 0, 1, 0},
                    {1, 1, 1, 1, 0},
                    {1, 1, 0, 1, 0},
                    {1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 0},
                    {1, 0, 1, 0, 1},
                    {1, 1, 1, 0, 1},
                    {1, 1, 0, 1, 0},
                    {0, 1, 1, 1, 1},
                    {1, 1, 0, 1, 1},
                    {1, 1, 1, 1, 1},
                    {1, 0, 0, 1, 1},
                    {1, 1, 1, 1, 0},
                    {1, 1, 1, 0, -1},
                    {1, 1, 1, -1, -1}
            };

    public static final String[] level1StringResources = {"שף ידוע (3,4)", "לוגו", "בראוזר", "פלוגות המחץ", "למעני/קניין", "תרסיס", "אביון", "עיר בארץ (2,3)", "מתבייש", "מארבע האימהות/עשירית האחוז", "לחם המדבר", "עיר בהולנד", "ועידה", "מחפיסת הקלפים", "שמחה", "הכין בדים", "מידה של בגדים", "חבל ארץ בגרמניה"};

    private static final int[][][] levelLayouts = {level1Layout};

    public ClassicBoard(Context context, int aLevelIdentifier) {
        super(context);

        setOrientation(VERTICAL);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        setLayoutParams(params);

        levelIdentifier = aLevelIdentifier;
        size = new Size(levelLayouts[levelIdentifier - 1][0].length, levelLayouts[levelIdentifier - 1].length);

        // Create the board
        rows = new LinearLayout[size.getHeight()];
        for (int i=0; i<size.getHeight(); i++) {
            rows[i] = new LinearLayout(context);
            rows[i].setOrientation(HORIZONTAL);
            addView(rows[i]);
        }

        int stringResourceIndex = 0;
        for (int i = 0; i < size.getHeight(); i++) {
            for (int j = 0; j < size.getWidth(); j++) {
                if (levelLayouts[levelIdentifier - 1][i][j] == 0) {
                    ClassicTextTile textTile = ClassicTextTile.getInstance(context, new Point(i, j), level1StringResources[stringResourceIndex]);
                    rows[i].addView(textTile);
                    stringResourceIndex++;
                } else if (levelLayouts[levelIdentifier - 1][i][j] == 1) {
                    ClassicEditTile editTile = ClassicEditTile.getInstance(context, new Point(i, j));
                    rows[i].addView(editTile);
                } else if (levelLayouts[levelIdentifier - 1][i][j] == -1) {
                    ClassicTextTile textTile = ClassicTextTile.getInstance(context, new Point(i, j), "");
                    rows[i].addView(textTile);
                }
            }
        }
    }
}
