package com.example.graphicspractice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;


public class SampleView extends View {
    Paint paint = new Paint();
    Paint paintLine = new Paint();
    public SampleView(Context context) {
        super(context);
    }
    public void drawTriangle(Canvas canvas, Paint paint, int x, int y, int width) {
        int halfWidth = width / 2;

        Path path = new Path();
        path.moveTo(x, y - halfWidth); // Top
        path.lineTo(x - halfWidth, y + halfWidth); // Bottom left
        path.lineTo(x + halfWidth, y + halfWidth); // Bottom right
        path.lineTo(x, y - halfWidth); // Back to Top
        path.close();

        canvas.drawPath(path, paint);
    }
    public void drawStar(int MID, int MIN, int HALF, Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        float mid = MID;
        float min = MIN;
        float half = HALF;
        mid = mid - half;
        paint.setStyle(Paint.Style.FILL);
        // top left
        path.moveTo(mid + half * 0.5f, half * 0.84f);
        // top right
        path.lineTo(mid + half * 1.5f, half * 0.84f);
        // bottom left
        path.lineTo(mid + half * 0.68f, half * 1.45f);
        // top tip
        path.lineTo(mid + half * 1.0f, half * 0.5f);
        // bottom right
        path.lineTo(mid + half * 1.32f, half * 1.45f);
        // top left
        path.lineTo(mid + half * 0.5f, half * 0.84f);
        path.close();
        canvas.drawPath(path, paint);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.WHITE);
        paintLine.setColor(Color.YELLOW);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(5);
        canvas.drawColor(Color.BLACK);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0,0, 1200, 800, paint);
        paint.setColor(Color.WHITE);
        canvas.drawCircle(900,200, 150, paint);
        paint.setColor(Color.GRAY);
        canvas.drawRect(150, 500, 400, 1700, paint);
        paint.setColor(Color.RED);
        for (int i=0; i<10; i++){
            canvas.drawRect(170, 550+i*100, 210, 610+i*100, paint);
            canvas.drawRect(170, 550+i*100, 210, 610+i*100, paintLine);
            canvas.drawRect(330, 550+i*100, 370, 610+i*100, paint);
            canvas.drawRect(330, 550+i*100, 370, 610+i*100, paintLine);
        }
        paint.setColor(Color.parseColor("#964B00"));
        canvas.drawRect(225,1550, 325, 1700, paint);
        canvas.drawRect(720, 1100, 820, 1700, paint);
        paint.setColor(Color.GREEN);
        canvas.drawCircle(710, 1080, 80, paint);
        canvas.drawCircle(830, 1080, 80, paint);
        canvas.drawCircle(675, 970, 80, paint);
        canvas.drawCircle(865, 970, 80, paint);
        canvas.drawCircle(770, 945, 80, paint);
        paint.setColor(Color.RED);
        drawTriangle(canvas, paint, 275, 325, 350);
        drawStar(90, 300,170, canvas);
        drawStar(500, 300,200, canvas);
        drawStar(720, 300 ,450, canvas);
    }
}
