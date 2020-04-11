package ru.spbstu.main.shapes;

import java.security.InvalidParameterException;

public class Triangle implements Polygon {

    private float xA_, yA_, xB_, yB_, xC_, yC_;
    private int angle_;

    public Triangle(float xA, float yA, float xB, float yB, float xC, float yC) {
        if (!checkTriangle(xA, yA, xB, yB, xC, yC)) {
            throw new InvalidParameterException("Invalid triangle!");
        }
        xA_ = xA;
        yA_ = yA;
        xB_ = xB;
        yB_ = yB;
        xC_ = xC;
        yC_ = yC;
        angle_ = 0;
    }

    public Triangle(float xA, float yA, float xB, float yB, float xC, float yC, int angle) {
        this(xA, yA, xB, yB, xC, yC);
        if (angle < 0 || angle > 360) {
            throw new InvalidParameterException("Invalid angle!");
        }
        angle_ = angle;
    }

    @Override
    public float getX() {
        return (xA_ + xB_ + xC_) / 3;
    }

    @Override
    public float getY() {
        return (yA_ + yB_ + yC_) / 3;
    }

    @Override
    public float getArea() {
        return Math.abs((xA_ * (yB_ - yC_) + xB_ * (yC_ - yA_) + xC_ * (yA_ - yB_)) / 2);
    }

    @Override
    public int getRotation() {
        return angle_;
    }

    @Override
    public float getPerimeter() {
        return countSideLength(xA_, yA_, xB_, yB_) + countSideLength(xB_, yB_, xC_, yC_) + countSideLength(xA_, yA_, xC_, yC_);
    }

    private static float countSideLength(float xA, float yA, float xB, float yB) {
        return (float) Math.sqrt((Math.pow(xB - xA, 2) + Math.pow(yB - yA, 2)));
    }

    private static boolean checkTriangle(float xA, float yA, float xB, float yB, float xC, float yC) {
        return ((xC - xA) / (xB - xA) != (yC - yA) / (yB - yA));
    }
}
