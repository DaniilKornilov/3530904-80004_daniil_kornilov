package poly.lab3;

import java.security.InvalidParameterException;

public class Generator extends Thread {

    private final Queue queue;
    private int numberOfStudents;

    public Generator(Queue queue, int numberOfStudents) {
        if (numberOfStudents < 0) {
            throw new InvalidParameterException("Invalid number of students!");
        }
        this.queue = queue;
        this.numberOfStudents = numberOfStudents;
    }

    @Override
    public void run() {
        while (numberOfStudents > 0) {
            Student student = new Student(generateLabQuantity(), generateLabName());
            queue.pushBack(student);
            --numberOfStudents;
        }
    }

    private int generateLabQuantity() {
        int labQuantity;
        switch ((int) (Math.random() * 3) + 1) {
            case 1:
                labQuantity = 10;
                break;
            case 2:
                labQuantity = 20;
                break;
            default:
                labQuantity = 100;
                break;
        }
        return labQuantity;
    }

    private String generateLabName() {
        String subjectName;
        switch ((int) (Math.random() * 3) + 1) {
            case 1:
                subjectName = "OOP";
                break;
            case 2:
                subjectName = "Math";
                break;
            default:
                subjectName = "Physics";
                break;
        }
        return subjectName;
    }
}
