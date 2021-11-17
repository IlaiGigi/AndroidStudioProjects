package com.example.task20;

import android.content.Context;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.LinearLayout;

public class MyBar extends LinearLayout implements View.OnClickListener{
    Plus plus;
    Minus minus;
    BarView barView;
    public MyBar(Context context, int linesNumber, int color) {
        super(context);
        LinearLayout.LayoutParams buttonsParams = new LinearLayout.LayoutParams(Tools.dpToPx(context, 50), Tools.dpToPx(context, 50));
        plus = new Plus(context, color);
        plus.setLayoutParams(buttonsParams);
        minus = new Minus(context, color);
        minus.setLayoutParams(buttonsParams);
        barView = new BarView(context, linesNumber, color);
        LinearLayout.LayoutParams barViewParams = new LinearLayout.LayoutParams(Tools.dpToPx(context, 290), Tools.dpToPx(context, 60));
        barViewParams.rightMargin = 5;
        barViewParams.leftMargin = 5;
        barView.setLayoutParams(barViewParams);
        this.setOrientation(LinearLayout.HORIZONTAL);
        this.addView(minus);
        this.addView(barView);
        this.addView(plus);
        minus.setOnClickListener(this);
        plus.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == minus){
            barView.removeLine();
        }
        else if(view == plus){
            barView.addLine();
        }
    }
}
