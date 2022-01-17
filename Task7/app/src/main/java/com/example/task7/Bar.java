package com.example.task7;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class Bar extends View implements View.OnClickListener{
    private final Paint paintStroke;
    private final Paint paintRect;
    private final int color; // Color of the bar.
    private final int numOfLines; // Number of lines in the bar.
    private final int spacingHorizontal = 10;
    private final int spacingTop = 10;
    private int currentLines;
    public Canvas canvas;

    public Bar(Context context, int color) {
        super(context);
        this.paintStroke = new Paint();
        this.paintRect = new Paint();
        this.numOfLines = 12;
        this.color = color;
        this.currentLines = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paintStroke.setColor(this.color);
        paintStroke.setStyle(Paint.Style.STROKE);
        paintStroke.setStrokeWidth(15);
        canvas.drawRoundRect(250, 220, 850, 400, 15, 15, paintStroke);
        for (int i=0; i<this.currentLines; i++){
            paintRect.setColor(this.color);
            paintRect.setStyle(Paint.Style.FILL);
            int startPointX = 250 + this.spacingHorizontal + 50*i;
            canvas.drawRect(startPointX, 220 + this.spacingTop, startPointX + 40, 400 - spacingTop, paintRect);
        }
    }

    public void addRect(){
        if (this.currentLines == this.numOfLines) return;
        this.currentLines++;
        invalidate();
    }

    public void deleteRect(Canvas canvas){
        if (this.currentLines == 0) return;
        paintRect.setColor(Color.WHITE);
        paintRect.setStyle(Paint.Style.FILL);
        int startPointX = 250 + this.spacingHorizontal + 50*(this.currentLines-1);
        canvas.drawRect(startPointX, 220 + this.spacingTop, startPointX + 40, 400 - spacingTop, paintRect);
        this.currentLines--;
    }

    @Override
    public void onClick(View view) {
    }
}
