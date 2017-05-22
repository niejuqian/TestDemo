package com.testdemo.seniorui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 22 10:12
 * @DESC：时钟
 */

public class ClockView extends View {
    private Paint mPaint;
    private float centerX,centerY,radius=200;
    private CalcPoint calcPoint;
    private CalcPoint.Point point;
    public ClockView(Context context) {
        this(context,null);
    }

    public ClockView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        centerX = getMeasuredWidth() / 2;
        centerY = getMeasuredHeight() / 2;
        canvas.drawColor(Color.WHITE);
        //step1.画外圆
        drawCircle(canvas);
        //step2.画圆心
        drawCircleCenter(canvas);
        //step3.画刻度
        drawScale(canvas);
        //step4.画时针、分针、秒针
        drawHand(canvas);
    }

    private void drawHand(Canvas canvas) {
        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        //时针
        drawHourHand(canvas);
        //分针
        drawMinuteHand(canvas);
        //秒针
        drawSecondHand(canvas);
    }

    private void drawHourHand(Canvas canvas){
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(8);
        Point point = new Point(centerX,centerY - 60,centerX,centerY + 20);
        canvas.drawLine(point.startX,point.startY,point.endX,point.endY,mPaint);
    }
    private void drawMinuteHand(Canvas canvas){
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(5);
        Point point = new Point(centerX - 30,centerY,centerX + 80,centerY);
        canvas.drawLine(point.startX,point.startY,point.endX,point.endY,mPaint);
    }
    private void drawSecondHand(Canvas canvas){
        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeWidth(2);
        Point point = new Point(centerX - 40,centerY + 15,centerX + radius - 70,centerY - 50);
        canvas.drawLine(point.startX,point.startY,point.endX,point.endY,mPaint);
    }

    /**
     * 画外圆
     * @param canvas
     */
    private void drawCircle(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        mPaint.setColor(Color.BLUE);
        canvas.drawCircle(centerX,centerY,radius,mPaint);
    }

    /**
     * 画圆心
     * @param canvas
     */
    private void drawCircleCenter(Canvas canvas){
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(centerX,centerY,10,mPaint);
    }

    /**
     * 画刻度
     * @param canvas
     */
    private void drawScale(Canvas canvas){
        calcPoint = new CalcPoint(centerX,centerY,radius);
        mPaint.setStyle(Paint.Style.FILL);
        //1点
        point = calcPoint.computeCoordinates(60);
        float oneX = point.x;
        float oneY = (centerY - radius) + (radius - (point.y - centerY));
        canvas.drawLine(oneX,oneY,oneX - 20, oneY + 30,mPaint);
        //2点
        point = calcPoint.computeCoordinates(30);
        float secondX = point.x;
        float secondY = (centerY - radius) + (radius - (point.y - centerY));
        canvas.drawLine(secondX,secondY,secondX - 30, secondY + 20,mPaint);
        //3点
        canvas.drawLine(centerX + radius - 40,centerY,centerX + radius, centerY,mPaint);
        //4点
        float fourX = secondX;
        float fourY = centerY + (centerY - secondY);
        canvas.drawLine(fourX,fourY,fourX - 30, fourY - 20,mPaint);
        //5点
        float fiveX = oneX;
        float fiveY = centerY + (centerY - oneY);
        canvas.drawLine(fiveX,fiveY,fiveX - 20, fiveY - 30,mPaint);
        //6点
        canvas.drawLine(centerX,centerY + radius - 40,centerX, centerY + radius,mPaint);
        //7点
        float sevenX = (centerX - radius) + (radius - (fiveX - centerX));
        float sevenY = fiveY;
        canvas.drawLine(sevenX,sevenY,sevenX + 20, sevenY - 30,mPaint);
        //8点
        float eightX = (centerX - radius) + (radius - (fourX - centerX));
        float eightY = fourY;
        canvas.drawLine(eightX,eightY,eightX + 30, eightY - 20,mPaint);
        //9点
        canvas.drawLine(centerX - radius,centerY,centerX - radius + 40, centerY,mPaint);
        //10点
        float tenX = centerX - (oneX - centerX);
        float tenY = oneY;
        canvas.drawLine(tenX,tenY,tenX + 20, tenY + 30,mPaint);
        //11点
        float elevenX = centerX - (secondX - centerX);
        float elevenY = secondY;
        canvas.drawLine(elevenX,elevenY,elevenX + 30, elevenY + 20,mPaint);
        //12点
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawLine(centerX,centerY - radius,centerX, centerY - radius + 40,mPaint);
    }
}
