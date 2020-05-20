package poly.lab4;

import poly.lab4.db.DataBase;

import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Commands {
    private final DataBase dataBase;

    public Commands(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public void add(Scanner scanner) {
        try {
            int prod_id = scanner.nextInt();
            String title = scanner.next();
            int cost = scanner.nextInt();
            if (prod_id <= 0 || cost <= 0) {
                throw new InvalidParameterException("Invalid product!");
            }
            dataBase.addProduct(prod_id, title, cost);
            System.out.println("Product " + title + " added");
        } catch (InputMismatchException e) {
            System.err.println("Invalid input!");
        } catch (SQLException e) {
            System.err.println("Invalid key!");
        } catch (InvalidParameterException e) {
            System.err.println(e.getMessage());
        }
        scanner.nextLine();
    }

    public void delete(Scanner scanner) throws SQLException {
        try {
            String title = scanner.next();
            boolean result = dataBase.deleteProduct(title);
            if (!result) {
                System.err.println(title + " is missing!");
            } else {
                System.out.println(title + " is deleted!");
            }
        } catch (InputMismatchException e) {
            System.err.println("Invalid input!");
        }
        scanner.nextLine();
    }

    public void showAll(Scanner scanner) throws SQLException {
        dataBase.showAll();
        scanner.nextLine();
    }

    public void showPrice(Scanner scanner) throws SQLException {
        try {
            String title = scanner.next();
            int cost = dataBase.showPrice(title);
            if (cost != -1) {
                System.out.println(title + " new cost: " + cost);
            } else {
                System.err.println(title + " is missing");
            }
        } catch (InputMismatchException e) {
            System.err.println("Invalid input!");
        }
        scanner.nextLine();
    }

    public void changePrice(Scanner scanner) throws SQLException {
        try {
            String title = scanner.next();
            int newCost = scanner.nextInt();
            if (newCost <= 0) {
                throw new InvalidParameterException("Invalid cost!");
            }
            boolean result = dataBase.changePrice(title, newCost);
            if (!result) {
                System.err.println(title + " is missing!");
            } else {
                System.out.println(title + " price is changed!");
            }
        } catch (InputMismatchException e) {
            System.err.println("Invalid input!");
        } catch (InvalidParameterException e) {
            System.err.println(e.getMessage());
        }
        scanner.nextLine();
    }

    public void showFilteredByPrice(Scanner scanner) throws SQLException {
        try {
            int leftBorder = scanner.nextInt();
            int rightBorder = scanner.nextInt();
            if (leftBorder > rightBorder || leftBorder < 0) {
                throw new InvalidParameterException("Invalid borders!");
            }
            dataBase.showFilteredByPrice(leftBorder, rightBorder);
        } catch (InputMismatchException e) {
            System.err.println("Invalid input!");
        } catch (InvalidParameterException e) {
            System.err.println(e.getMessage());
        }
        scanner.nextLine();
    }
}
