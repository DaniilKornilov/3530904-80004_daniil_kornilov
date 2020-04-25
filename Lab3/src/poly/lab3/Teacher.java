package poly.lab3;

import java.util.concurrent.Semaphore;

public class Teacher extends Thread {

    private final Semaphore semaphore;
    private final Queue queue;

    public Teacher(Semaphore semaphore, Queue queue) {
        this.semaphore = semaphore;
        this.queue = queue;
    }

    @Override
    public void run() {
        while (!queue.isEmpty()) {
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (checkStudent()) {
                Student student = queue.popBack();
                System.out.println("Teacher started to check labs "
                        + student.getLabName() + " "
                        + student.getLabQuantity());
                int labQuantity = student.getLabQuantity();
                while (!student.areLabsDone()) {
                    student.doFiveLabs();
                }
                System.out.println("Student passed labs " + this.getName() + " " + labQuantity);
            }
            semaphore.release();
        }
    }

    private boolean checkStudent() {
        Student student = queue.peek();
        if (student != null) {
            return this.getName().equals(student.getLabName());
        }
        return false;
    }
}
