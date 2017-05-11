package com.testdemo.view.seniorui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 10 16:22
 * @DESC：
 */

public class PaintView extends View {
    private Paint paint;
    private RectF rectF;

    public PaintView(Context context) {
        this(context, null);
    }

    public PaintView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PaintView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        rectF = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //设置画布背景颜色
        canvas.drawColor(Color.WHITE);
        paint.setAntiAlias(true);
        //画一个空心圆，边框宽度为30，边框颜色为灰色
        rectF.top = 30;
        rectF.left = 30;
        rectF.right = 210;
        rectF.bottom = 210;
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(30);
        canvas.drawRoundRect(rectF, 100, 100, paint);

        //填充圆内部
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(rectF, 100, 100, paint);

        //画条直线
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawLine(50,300,300,300,paint);

        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(50,400,300,400,paint);

        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeMiter(45);
        paint.setAlpha(10);
        canvas.drawLine(50,500,300,500,paint);

        paint.setTextSize(48);
        paint.setColor(Color.BLACK);
        Typeface typeface = Typeface.create(Typeface.SERIF,Typeface.BOLD_ITALIC);
        paint.setTypeface(typeface);
        canvas.drawText("我是艺术字",300,100,paint);
    }

    private void reset(){
        paint.reset();
        paint.setAntiAlias(true);
    }
}
