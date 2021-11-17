package com.example.task20;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;

public class Minus extends RoundedButton{
    int color;
    int rectWidth;
    int rectHeight;
    int startX;
    int startY;
    public Minus(Context context, int color) {
        super(context, color);
        this.color = color;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.paint.setColor(Color.WHITE);
        this.rectWidth = (int)(size / 1.5);
        this.rectHeight = size / 6;
        this.startX = (this.size - this.rectWidth) /2;
        this.startY = (this.size - this.rectHeight) /2;
        canvas.drawRect(this.startX, this.startY, this.startX + this.rectWidth, this.startY + this.rectHeight, paint);
    }
}
