package com.testdemo.view.seniorui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.testdemo.R;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 09 11:23
 * @DESC：圆形进度条绘制
 */

public class CircleProgressBar extends View {
    private static final int DEFAULT_BG_COLOR = Color.parseColor("#4169E1");
    private static final int DEFAULT_PBG_COLOR = Color.parseColor("#ff0000");
    private static final float MAX_DEFAULT = 100;
    private static final float DEFAULT_CIRCLE_RADIUS = 200;
    private static final float DEFAULT_STORKE_WIDTH = 50;//圆边框宽度
    private int roundBackground = DEFAULT_BG_COLOR;
    private int progressBackground = DEFAULT_PBG_COLOR;
    private float progress;
    private float max = MAX_DEFAULT;
    private float circleRadius = DEFAULT_CIRCLE_RADIUS;
    private float storkeWidth = DEFAULT_STORKE_WIDTH;
    private Paint mPaint;
    private RectF mRectF;

    public CircleProgressBar(Context context) {
        this(context, null);
    }

    public CircleProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mRectF = new RectF();
        if (null != attrs) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressBar);
            max = typedArray.getFloat(R.styleable.CircleProgressBar_max, MAX_DEFAULT);
            progress = typedArray.getFloat(R.styleable.CircleProgressBar_progress, 0);
            roundBackground = typedArray.getInt(R.styleable.CircleProgressBar_roundBackground, DEFAULT_BG_COLOR);
            progressBackground = typedArray.getInt(R.styleable.CircleProgressBar_progressBackground, DEFAULT_PBG_COLOR);
            circleRadius = typedArray.getFloat(R.styleable.CircleProgressBar_circleRadius, DEFAULT_CIRCLE_RADIUS);
            storkeWidth = typedArray.getFloat(R.styleable.CircleProgressBar_storkeWidth, DEFAULT_STORKE_WIDTH);
            typedArray.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //1.设置空心圆
        //设置画布为白色
        canvas.drawColor(Color.WHITE);
        //设置画笔颜色
        mPaint.setColor(roundBackground);
        //设置样式：填充风格
        mPaint.setStyle(Paint.Style.STROKE);
        //设置空心边框宽度
        mPaint.setStrokeWidth(storkeWidth);
        //抗锯齿
        mPaint.setAntiAlias(true);
        //左上角的坐标点(x,y)
        mRectF.left = getWidth() / 2 - circleRadius;
        mRectF.top = getHeight() / 2 - circleRadius;
        //右上角的坐标点（x,y）
        mRectF.right = getWidth() / 2 + circleRadius;
        mRectF.bottom = getHeight() / 2 + circleRadius;

        //画直角矩形
        //canvas.drawRect(mRectF,mPaint);

        //圆角矩形：当角的半径等于圆的半径时，则为圆形
        canvas.drawRoundRect(mRectF, circleRadius, circleRadius, mPaint);
        //画圆形
        //canvas.drawCircle(getWidth()/2,getHeight()/2,max,mPaint);

        //2.设置进度圆
        //起始点 -90为正上方
        float startAngle = -90;
        //顺时针扫描角度
        float sweepAngle = progress / max * 360;
        //设置画笔颜色
        mPaint.setColor(progressBackground);
        canvas.drawArc(mRectF, startAngle, sweepAngle, false, mPaint);


        //3.设置文本
        String strProgress = (int) (progress / max * 100) + "%";
        //设置文本大小
        mPaint.setTextSize(circleRadius / 2);
        //设置文本颜色
        mPaint.setColor(Color.BLACK);
        //设置填充方式为填满
        mPaint.setStyle(Paint.Style.FILL);
        //文本的宽度
        int textWidth = (int) mPaint.measureText(strProgress, 0, strProgress.length());
        //如果文本的宽度小于圆的直径
        //设置文本起始位置的坐标点（x,y）
        int center = getHeight() / 2;
        Paint.FontMetricsInt fmint = mPaint.getFontMetricsInt();
        int textY = center + (fmint.bottom - fmint.top) / 2 - fmint.bottom;
        int textX = (getWidth() / 2 - textWidth / 2);
        canvas.drawText(strProgress, textX, textY, mPaint);

        //画线条
        float lineWidth = 5f;
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(lineWidth);

        /*int startX = (int) (getWidth() / 2 - circleRadius - storkeWidth);
        int startY = (int)(getHeight() / 2 - circleRadius);

        int stopX = (int) (getWidth() / 2 - circleRadius - storkeWidth);
        int stopY = getHeight() / 2;
        canvas.drawLine(startX, startY, stopX, stopY, mPaint);*/

        int x1 = 10;
        int y1 = (int)(getHeight() / 2 - circleRadius / 2 - storkeWidth);

        int x3 = (int)(getWidth()/2 * 1f - circleRadius - storkeWidth / 2);
        int y3 = getHeight() / 2;

        int x2 = x1 + (x3 - x1) / 2;
        x2 = (x2 + x2 / 2);
        int y2 = y1;
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        canvas.drawLine(x1, y1, x2, y2, mPaint);
        canvas.drawLine(x2 - lineWidth / 2, y2, x3, y3, mPaint);

        //设置文本大小
        mPaint.setTextSize(circleRadius / 2);
        //设置文本颜色
        mPaint.setColor(Color.BLACK);
        canvas.drawText(progress + "", x1, y1 - lineWidth, mPaint);

    }

    public CircleProgressBar setRoundBackground(int roundBackground) {
        this.roundBackground = roundBackground;
        return this;
    }

    public float getProgress() {
        return progress;
    }

    public CircleProgressBar setProgress(float progress) {
        this.progress = progress;
        return this;
    }

    public CircleProgressBar setMax(float max) {
        this.max = max;
        return this;
    }

    public void refresh() {
        //重新绘制UI
        postInvalidate();
    }
}
