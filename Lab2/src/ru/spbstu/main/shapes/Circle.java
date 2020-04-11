package ru.spbstu.main.shapes;

import java.security.InvalidParameterException;

public class Circle implements Ellipse {

    private float x_, y_;
    private float radius_;

    public Circle(float x, float y, float radius) {
        if (radius <= 0) {
            throw new InvalidParameterException("Invalid radius!");
        }
        x_ = x;
        y_ = y;
        radius_ = radius;
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
        return (float) (Math.PI * Math.pow(radius_, 2));
    }

    @Override
    public float getLength() {
        return (float) (2 * Math.PI * radius_);
    }
}
