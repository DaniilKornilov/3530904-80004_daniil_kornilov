package poly.lab3;

import java.security.InvalidParameterException;

public class Student {

    private int labQuantity;
    private final String labName;

    public Student(int labQuantity, String labName) {
        if (labQuantity < 0) {
            throw new InvalidParameterException("Invalid number of labs!");
        }
        if (!labName.equals("Math") && !labName.equals("Physics") && !labName.equals("OOP")) {
            throw new InvalidParameterException("Invalid name of lab!");
        }
        this.labQuantity = labQuantity;
        this.labName = labName;
    }

    public int getLabQuantity() {
        return labQuantity;
    }

    public String getLabName() {
        return labName;
    }

    public void doFiveLabs() {
        labQuantity -= 5;
    }

    public boolean areLabsDone() {
        return labQuantity == 0;
    }
}
