package ru.spbstu.main;

import ru.spbstu.main.shapes.Circle;
import ru.spbstu.main.shapes.Rectangle;
import ru.spbstu.main.shapes.Shape;
import ru.spbstu.main.shapes.Triangle;

import java.security.InvalidParameterException;

public class Main {
    public static void main(String[] args) {
        try {
            Circle circle1 = new Circle(10, 20, 30);
            Circle circle2 = new Circle(15, 24, 20);
            Circle circle3 = new Circle(10, 20, 40);
            Circle circle4 = new Circle(10, 20, 50);
            Rectangle rectangle1 = new Rectangle(10, 20, 40, 30);
            Rectangle rectangle2 = new Rectangle(15, 20, 90, 30, 10);
            Rectangle rectangle3 = new Rectangle(10, 25, 50, 30, 60);
            Triangle triangle1 = new Triangle(10, 10, 20, 20, 30, 10);
            Triangle triangle2 = new Triangle(10, 10, 24, 24, 14, 32);
            Triangle triangle3 = new Triangle(18, 13, 20, 20, 10, 30, 100);
            Shape[] shapes = new Shape[]{
                    circle1, circle2, circle3, circle4, rectangle1, rectangle2, rectangle3, triangle1, triangle2, triangle3
            };
            Shape biggestShape = findBiggestShape(shapes);
            System.out.println("Biggest shape area: " + biggestShape.getArea());
        } catch (InvalidParameterException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private static Shape findBiggestShape(Shape[] shape_array) {
        if (shape_array == null) {
            throw new InvalidParameterException("Invalid array of shapes!");
        }
        if (shape_array.length == 0) {
            throw new InvalidParameterException("Array of shapes is empty!");
        }
        Shape biggestShape = shape_array[0];
        if (shape_array.length == 1) {
            return biggestShape;
        }
        for (Shape shape : shape_array) {
            if (shape.getArea() > biggestShape.getArea()) {
                biggestShape = shape;
            }
        }
        return biggestShape;
    }
}
