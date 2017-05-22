package com.testdemo;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 22 09:49
 * @DESC：
 */

public class RadiusTest {

    public static void main(String[] args) {
        RadiusTest person = new RadiusTest(300,300, 100);
        Point point = person.computeCoordinates(30);
        System.out.println( "30°：" + point.x + ", " + point.y);
        point = person.computeCoordinates(60);
        System.out.println( "60°：" + point.x + ", " + point.y);
        point = person.computeCoordinates(120);
        System.out.println( "120°：" + point.x + ", " + point.y);
        point = person.computeCoordinates(150);
        System.out.println( "150°：" + point.x + ", " + point.y);
        point = person.computeCoordinates(210);
        System.out.println( "210°：" + point.x + ", " + point.y);
        point = person.computeCoordinates(240);
        System.out.println( "240°：" + point.x + ", " + point.y);
        point = person.computeCoordinates(300);
        System.out.println( "300°：" + point.x + ", " + point.y);
        point = person.computeCoordinates(330);
        System.out.println( "330°：" + point.x + ", " + point.y);
    }

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

    public RadiusTest(float x, float y, float radius) {
        mPointX = x;
        mPointY = y;
        mRadius = radius;
    }

    public Point computeCoordinates(float angle) {
        return new Point(mPointX + (float) (mRadius * Math.cos(angle * Math.PI / 180)),
                mPointY + (float) (mRadius * Math.sin(angle * Math.PI / 180)));
    }
}
