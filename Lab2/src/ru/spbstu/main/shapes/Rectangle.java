package ru.spbstu.main.shapes;

import java.security.InvalidParameterException;

public class Rectangle implements Polygon {

    private float x_, y_;
    private float width_, height_;
    private int angle_;

    public Rectangle(float x, float y, float width, float height) {
        if (width <= 0 || height <= 0) {
            throw new InvalidParameterException("Invalid width or height!");
        }
        x_ = x;
        y_ = y;
        width_ = width;
        height_ = height;
        angle_ = 0;
    }

    public Rectangle(float x, float y, float width, float height, int angle) {
        this(x, y, width, height);
        if (angle < 0 || angle > 360) {
            throw new InvalidParameterException("Invalid angle!");
        }
        angle_ = angle;
    }

    @Override
    public float getX() {
        return x_;
    }

    @Override
    public float getY() {
        return y_;
    }

    @Override
    public float getArea() {
        return width_ * height_;
    }

    @Override
    public int getRotation() {
        return angle_;
    }

    @Override
    public float getPerimeter() {
        return (width_ + height_) * 2;
    }
}
