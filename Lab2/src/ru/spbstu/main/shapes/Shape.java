package ru.spbstu.main.shapes;

public interface Shape extends Point {

    float getArea();

    default int getRotation() {
        return 0;
    }
}
