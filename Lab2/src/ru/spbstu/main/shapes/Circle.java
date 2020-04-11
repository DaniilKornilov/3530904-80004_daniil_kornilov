package ru.spbstu.main.shapes;

import java.security.InvalidParameterException;

public class Circle implements Ellipse {

    private float x, y;
    private float radius;

    public Circle(float x, float y, float radius) {
        if (radius <= 0) {
            throw new InvalidParameterException("Invalid radius!");
        }
        this.x = x;
        this.y = y;
        this.radius = radius;
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
        return (float) (Math.PI * Math.pow(this.radius, 2));
    }

    @Override
    public float getLength() {
        return (float) (2 * Math.PI * this.radius);
    }
}
