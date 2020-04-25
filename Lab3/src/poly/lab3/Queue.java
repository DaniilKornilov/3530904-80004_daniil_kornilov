package poly.lab3;

import java.util.concurrent.LinkedBlockingQueue;

public class Queue {

    private final LinkedBlockingQueue<Student> studentsQueue;

    public Queue() {
        int numberOfStudentsInQueue = 10;
        studentsQueue = new LinkedBlockingQueue<>(numberOfStudentsInQueue);
    }

    public void pushBack(Student student) {
        try {
            studentsQueue.put(student);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Student popBack() {
        try {
            return studentsQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isEmpty() {
        return studentsQueue.isEmpty();
    }

    public Student peek() {
        return studentsQueue.peek();
    }
}
