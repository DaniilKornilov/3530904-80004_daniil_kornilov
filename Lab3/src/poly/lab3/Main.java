package poly.lab3;

import java.util.concurrent.Semaphore;

public class Main {

    public static void main(String[] args) {

        Queue queue = new Queue();
        int numberOfStudents = 120;

        Generator generator = new Generator(queue, numberOfStudents);
        generator.setDaemon(true);
        generator.start();

        int time = 1000;

        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int permits = 1;
        Semaphore oopSemaphore = new Semaphore(permits);
        Semaphore physicsSemaphore = new Semaphore(permits);
        Semaphore mathSemaphore = new Semaphore(permits);

        Teacher teacher1 = new Teacher(mathSemaphore, queue);
        teacher1.setName("Math");
        teacher1.start();
        Teacher teacher2 = new Teacher(oopSemaphore, queue);
        teacher2.setName("OOP");
        teacher2.start();
        Teacher teacher3 = new Teacher(physicsSemaphore, queue);
        teacher3.setName("Physics");
        teacher3.start();
    }
}
