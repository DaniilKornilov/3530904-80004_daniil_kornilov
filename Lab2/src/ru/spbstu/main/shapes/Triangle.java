package ru.spbstu.main.shapes;

import java.security.InvalidParameterException;

public class Triangle implements Polygon {

    private float xA, yA, xB, yB, xC, yC;
    private int angle;

    public Triangle(float xA, float yA, float xB, float yB, float xC, float yC) {
        if (!checkTriangle(xA, yA, xB, yB, xC, yC)) {
            throw new InvalidParameterException("Invalid triangle!");
        }
        this.xA = xA;
        this.yA = yA;
        this.xB = xB;
        this.yB = yB;
        this.xC = xC;
        this.yC = yC;
        this.angle = 0;
    }

    public Triangle(float xA, float yA, float xB, float yB, float xC, float yC, int angle) {
        this(xA, yA, xB, yB, xC, yC);
        if (angle < 0 || angle > 360) {
            throw new InvalidParameterException("Invalid angle!");
        }
        this.angle = angle;
    }

    @Override
    public float getX() {
        return (this.xA + this.xB + this.xC) / 3;
    }

    @Override
    public float getY() {
        return (this.yA + this.yB + this.yC) / 3;
    }

    @Override
    public float getArea() {
        return Math.abs((this.xA * (this.yB - this.yC) + this.xB * (this.yC - this.yA) + this.xC * (this.yA - this.yB)) / 2);
    }

    @Override
    public int getRotation() {
        return this.angle;
    }

    @Override
    public float getPerimeter() {
        return countSideLength(this.xA, this.yA, this.xB, this.yB)
                + countSideLength(this.xB, this.yB, this.xC, this.yC)
                + countSideLength(this.xA, this.yA, this.xC, this.yC);
    }

    private static float countSideLength(float xA, float yA, float xB, float yB) {
        return (float) Math.sqrt((Math.pow(xB - xA, 2) + Math.pow(yB - yA, 2)));
    }

    private static boolean checkTriangle(float xA, float yA, float xB, float yB, float xC, float yC) {
        return ((xC - xA) / (xB - xA) != (yC - yA) / (yB - yA));
    }
}
