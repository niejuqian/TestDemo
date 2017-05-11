package com.testdemo.view.seniorui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.View;

import com.testdemo.R;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 11 09:10
 * @DESC：
 */

public class ShaderView extends View {
    private Paint mPaint;
    private Bitmap mBitmap;
    private BitmapShader bitmapShader;
    private int width = 0;
    private int height = 0;
    private String content = "欢迎菜鸟来到深圳龙光世纪大厦";
    public ShaderView(Context context) {
        this(context,null);
    }

    public ShaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.xyjy2);
        bitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.MIRROR);
        width = mBitmap.getWidth();
        height = mBitmap.getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        //mPaint.setShader(bitmapShader);
        //canvas.drawRect(new RectF(0,0,width,height * 2),mPaint);

        //设置像素矩阵，来调整大小，为了解决宽高不一致的问题。
        /*float scale = Math.max(width,height) / Math.min(width,height);
        Matrix matrix = new Matrix();
        matrix.setScale(scale,scale);
        bitmapShader.setLocalMatrix(matrix);
        canvas.drawRect(new RectF(0,0,width,height * 2),mPaint);*/

        //canvas.drawCircle(getWidth()/2,getHeight()/2,width/3,mPaint);

        /*ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
        shapeDrawable.getPaint().setShader(bitmapShader);
        // left top right bottom 如果left=top  right=bottom 则绘制出来的图形是圆形 否则是椭圆
        shapeDrawable.setBounds(0,0,width,height);
        shapeDrawable.draw(canvas);*/


        /*------------------linearGradient------------------------*/
        /*int radius = 500;
        //圆心X坐标点
        int centerX = getWidth() / 2;
        //圆心Y坐标点
        int centerY = getHeight() / 2;
        //开始坐标点X
        int startX =getWidth()/2;
        //开始坐标点Y
        int startY = getHeight() / 2 - radius;
        //结束坐标点X
        int endX = getWidth()/2;
        //结束坐标点Y
        int endY = getHeight() / 2 + radius;
        LinearGradient linearGradient = new LinearGradient(startX,startY,endX,endY,new int[]{Color.BLUE,Color.BLACK,Color.RED},new float[]{0f,0.5f,1f}, Shader.TileMode.CLAMP);
        mPaint.setShader(linearGradient);
        //mPaint.setStrokeWidth(200);
        //canvas.drawLine(10,50,500,500,mPaint);

        canvas.drawCircle(centerX,centerY,radius,mPaint);*/


        mPaint.setTextSize(100);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawText(content,0,getHeight() / 2,mPaint);
    }
}
