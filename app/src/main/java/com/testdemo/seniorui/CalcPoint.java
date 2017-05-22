package com.testdemo.seniorui;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 22 11:01
 * @DESC：
 */

public class CalcPoint {
    class Point {
        public float x, y;

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

    private static final float PI = 3.14f;
    private float mPointX, mPointY;
    private float mRadius;

    public CalcPoint(float x, float y, float radius) {
        mPointX = x;
        mPointY = y;
        mRadius = radius;
    }

    public CalcPoint.Point computeCoordinates(float angle) {
        return new CalcPoint.Point(mPointX + (float) (mRadius * Math.cos(angle * Math.PI / 180)),
                mPointY + (float) (mRadius * Math.sin(angle * Math.PI / 180)));
    }
}
