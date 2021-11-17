package com.example.task20;

import android.content.Context;

public class Tools {
    public static int dpToPx(Context context, int dps)
    {
        // Get the screen's density scale
        final float scale =
                context.getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        int pixels = (int) (dps * scale + 0.5f);
        return pixels;
    }

}
