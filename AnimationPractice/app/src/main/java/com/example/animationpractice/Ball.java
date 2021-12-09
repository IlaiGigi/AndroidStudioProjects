package com.example.animationpractice;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.Random;
public class Ball extends SurfaceView implements Runnable {
    Paint paint = new Paint();
    private int currentX;
    private int currentY;
    private int incrementX;
    private int incrementY;
    final int[] colors = {Color.YELLOW, Color.RED, Color.GREEN, Color.BLUE};
    public Ball(Context context) {
        super(context);
        this.currentX = 70;
        this.currentY = 70;
        this.incrementX = 3;
        this.incrementY = 10;
        this.paint.setColor(Color.YELLOW);
        Thread t = new Thread(this);
        t.start();
    }
    @Override
    public void run() {
        while(true){
            SurfaceHolder holder = getHolder();
            Canvas canvas = holder.lockCanvas();
            if (canvas != null){
                drawCanvas(canvas);
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }
    public void drawCanvas(Canvas canvas){
        canvas.drawColor(Color.BLACK);
        canvas.drawCircle(this.currentX, this.currentY, 50, paint);
        if (currentX >= canvas.getWidth() - 70 || currentX <= 50){
            this.incrementX = -this.incrementX;
            this.paint.setColor(colors[new Random().nextInt(4)]);
        }
        if (currentY >= canvas.getHeight() - 70 || currentY <= 50){
            this.incrementY = -this.incrementY;
            this.paint.setColor(colors[new Random().nextInt(4)]);
        }
        this.currentX += this.incrementX;
        this.currentY += this.incrementY;
    }
}