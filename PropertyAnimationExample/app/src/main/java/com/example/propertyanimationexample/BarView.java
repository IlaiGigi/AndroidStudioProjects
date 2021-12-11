package com.example.propertyanimationexample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class BarView extends View {
    Paint paint;
    int linesNumber;
    private int maxLines;
    public BarView(Context context, int linesNumber, int color) {
        super(context);
        this.paint = new Paint();
        this.linesNumber = linesNumber;
        this.maxLines = 10;
        this.paint.setColor(color);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeWidth(6);
        canvas.drawRoundRect(10, 10, 735, 150, 15, 15, this.paint);
        this.paint.setStyle(Paint.Style.FILL);
        int leftStart = 25;
        int gap = 10;
        int width = 60;
        for (int i=0; i<linesNumber; i++){
            canvas.drawRect(leftStart, 25, leftStart+width, 130, paint);
            leftStart+=width+gap;
        }
    }
    public void addLine(){
        if (linesNumber < 10){
            this.linesNumber++;
            this.invalidate();
        }
    }
    public void removeLine(){
        if (linesNumber > 0){
            this.linesNumber--;
            this.invalidate();
        }
    }
    public int getLinesNum() {return this.linesNumber;}
    public int getMaxLines() {return this.maxLines;}
}
