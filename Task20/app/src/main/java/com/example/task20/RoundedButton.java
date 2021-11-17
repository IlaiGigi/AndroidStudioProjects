package com.example.task20;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class RoundedButton extends View{
    protected Paint paint;
    protected int color;
    protected int size;
    public RoundedButton(Context context, int color) {
        super(context);
        this.paint = new Paint();
        this.color = color;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.paint.setColor(this.color);
        canvas.drawCircle(size / 2, size /2, size / 2, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        size = Math.min(width, height);
        setMeasuredDimension(size, size);
    }
}
