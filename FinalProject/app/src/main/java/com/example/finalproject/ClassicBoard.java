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

    private static final int[][] level1Layout =
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

    private static final String[] level1StringResources = {"שף ידוע (3,4) ↓", "לוגו↶", "בראוזר↶", "פלוגות המחץ ←", "למעני←/קניין↓", "תרסיס ↲", "אביון ↓", "עיר בארץ (2,3) ↓", "מתבייש ↰", "מארבע האימהות←/עשירית האחוז↓", "לחם המדבר ←", "עיר בהולנד ↓", "ועידה ↓", "מחפיסת הקלפים ←", "שמחה ↓", "הכין בדים ↓", "מידה של בגדים←", "חבל ארץ בגרמניה←"};

    private static final String level1Ans = "סדחמלפילדיירפסמלכנכודהרשלנמפתירוקגולוננמזנינגראלרורסנג";

    private static final int[][] level2Layout =
            {
                    {0, 1, 0, 1, 0},
                    {1, 1, 1, 1, 0},
                    {1, 1, 0, 1, 0},
                    {1, 1, 1, 1, 1},
                    {1, 0, 1, 1, 0},
                    {1, 1, 0, 1, 1},
                    {1, 1, 1, 0, 1},
                    {1, 1, 0, 1, 0},
                    {0, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1},
                    {1, 0, 0, 1, 1},
                    {1, 1, 1, 1, 0},
                    {1, 1, 1, 0, -1},
                    {1, 1, 1, -1, -1}
            };

    private static final String[] level2StringResources = {"אוניית מעפילים ↓", "מין תכשיט ↶", "חבל קשירה ↶", "עיר באיטליה ←", "בהמה טיבטית←/נמלט↓", "שובב ↲", "חומר רעיל ביותר ↓", "קול גברי נמוך←/אביון↓", "כיוון ←", "תחילית הקשורה לחיים←/הבדל דק במשמעות↓", "קלף גבוה←/חוטי היסוד באריגה↓", "משחק לוח לשניים ↓", "מין גבינה ↓", "צמית ↓", "איש שלג אגדי ↓", "לחימה בחרב ←", "נעו באוויר ←"};

    private static final String level2Ans = "צחאמורקיצסדנוקוסבדצהרויבשאסננשידריתומודיאקקנהפויסוסטרלי";

    private static final int[][] level3Layout =
            {
                    {0, 1, 0, 1, 0},
                    {1, 1, 1, 1, 0},
                    {1, 1, 1, 1, 0},
                    {1, 1, 1, 1, 1},
                    {1, 0, 1, 1, 0},
                    {0, 1, 1, 0, 1},
                    {1, 1, 1, 0, 1},
                    {1, 1, 1, 1, 0},
                    {1, 1, 0, 1, 0},
                    {1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1},
                    {1, 0, 0, 1, 1},
                    {1, 1, 1, 1, 0},
                    {1, 1, 1, 0, -1},
                    {1, 1, 1, -1, -1}
            };

    private static final String[] level3StringResources = {"קטגור ↓", "מין תוכי↶/נבחר ציבור(4,3) ↓", "אין בו פחד ↶", "רטיבות ←", "נהר בספרד←/הרס↲", "שכונה בדרום תל אביב ↓", "תפר בבגד←/מזון לבהמות↓", "מדינה בארצות הברית↓", "רשע ←", "גבול←/מהנביאים ↓", "כהן מדין←", "מלאך חבלה ←/גיל בר מצווה ↓", "מאכל נוזלי ↓", "מדינה באסיה ↓", "הרים ↓", "מטעמי הגלידה ←", "סוג של יונק ←"};

    private static final String level3Ans = "ללתוחלורבאבירחהעכתכנקרפסשורתידשראליממימגיריהקלינונפשלדנא";

    public static final String[] levelAns = {level1Ans, level2Ans, level3Ans}; // The answers are in hebrew so we check from finish to start to overrule the reversal

    public static final int[][][] levelLayouts = {level1Layout, level2Layout, level3Layout};

    private static final String[][] levelStringResources = {level1StringResources, level2StringResources, level3StringResources};

    private final Size size;
    private int levelIdentifier;
    private LinearLayout[] rows;

    // Definition: 0 = definition box with text, 1 = answer box, -1 = empty definition box

    public ClassicBoard(Context context, int aLevelIdentifier) {
        super(context);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        setLayoutParams(params);

        setOrientation(VERTICAL);

        levelIdentifier = aLevelIdentifier;
        size = new Size(levelLayouts[levelIdentifier - 1][0].length, levelLayouts[levelIdentifier - 1].length);

        // Create the board
        rows = new LinearLayout[size.getHeight()];
        for (int i=0; i<size.getHeight(); i++) {
            rows[i] = new LinearLayout(context);
            rows[i].setOrientation(HORIZONTAL);
            rows[i].setBaselineAligned(false);
            addView(rows[i]);
        }

        int stringResourceIndex = 0;
        for (int i = 0; i < size.getHeight(); i++) {
            for (int j = 0; j < size.getWidth(); j++) {
               if (levelLayouts[levelIdentifier - 1][i][j] == 0) {
                    ClassicTextTile textTile = ClassicTextTile.getInstance(context, new Point(j, i), levelStringResources[levelIdentifier - 1][stringResourceIndex]);
                    rows[i].addView(textTile);
                    stringResourceIndex++;
               } else if (levelLayouts[levelIdentifier - 1][i][j] == 1) {
                   // Array is defined as (y, x) and points are (x, y) so we must reverse the x and y (j, i)
                  ClassicEditTile editTile = ClassicEditTile.getInstance(context, levelIdentifier, rows, new Point(j, i));
                    rows[i].addView(editTile);
               } else if (levelLayouts[levelIdentifier - 1][i][j] == -1) {
                    ClassicTextTile textTile = ClassicTextTile.getInstance(context, new Point(j, i), "");
                   rows[i].addView(textTile);
                }
            }
        }

        // Pull progression from local filestream and update corresponding tiles
    }

    public LinearLayout[] getRows() {
        return rows;
    }
}
