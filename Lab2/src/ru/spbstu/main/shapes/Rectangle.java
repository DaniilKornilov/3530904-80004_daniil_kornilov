package ru.spbstu.main.shapes;

import java.security.InvalidParameterException;

public class Rectangle implements Polygon {

    private float x, y;
    private float width, height;
    private int angle;

    public Rectangle(float x, float y, float width, float height) {
        if (width <= 0 || height <= 0) {
            throw new InvalidParameterException("Invalid width or height!");
        }
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.angle = 0;
    }

    public Rectangle(float x, float y, float width, float height, int angle) {
        this(x, y, width, height);
        if (angle < 0 || angle > 360) {
            throw new InvalidParameterException("Invalid angle!");
        }
        this.angle = angle;
    }

    @Override
    public float getX() {
        return this.x;
    }

    @Override
    public float getY() {
        return this.y;
    }

    @Override
    public float getArea() {
        return this.width * this.height;
    }

    @Override
    public int getRotation() {
        return this.angle;
    }

    @Override
    public float getPerimeter() {
        return (this.width + this.height) * 2;
    }
}
