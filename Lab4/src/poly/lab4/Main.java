package poly.lab4;

import java.security.InvalidParameterException;
import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

import poly.lab4.db.DataBase;

public class Main {

    public static void main(String[] args) {
        final String url = "jdbc:h2:~/Prods99";
        final String user = "sa";
        final String password = "";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Scanner scanner = new Scanner(System.in)) {
            DataBase dataBase = new DataBase(connection);
            String command;
            boolean exit = false;
            while (!exit) {
                System.out.println("Enter command:");
                command = scanner.next();
                switch (command) {
                    case "/add":
                        try {
                            int prodid = scanner.nextInt();
                            String title = scanner.next();
                            int cost = scanner.nextInt();
                            dataBase.addProduct(prodid, title, cost);
                        } catch (InputMismatchException e) {
                            System.err.println("Invalid input!");
                        }
                        scanner.nextLine();
                        break;
                    case "/delete":
                        try {
                            String titleDeletion = scanner.next();
                            boolean result = dataBase.deleteProduct(titleDeletion);
                            if (!result) {
                                System.err.println("Product " + titleDeletion + " is missing!");
                            } else {
                                System.out.println("Product " + titleDeletion + " is deleted!");
                            }
                        } catch (InputMismatchException e) {
                            System.err.println("Invalid input!");
                        }
                        scanner.nextLine();
                        break;
                    case "/show_all":
                        dataBase.showAll();
                        scanner.nextLine();
                        break;
                    case "/price":
                        try {
                            String titlePrice = scanner.next();
                            dataBase.price(titlePrice);
                        } catch (InputMismatchException e) {
                            System.err.println("Invalid input!");
                        }
                        scanner.nextLine();
                        break;
                    case "/change_price":
                        try {
                            String titleChangePrice = scanner.next();
                            int newCost = scanner.nextInt();
                            boolean result = dataBase.changePrice(titleChangePrice, newCost);
                            if (!result) {
                                System.err.println("Product " + titleChangePrice + " is missing!");
                            } else {
                                System.out.println("Product " + titleChangePrice + " price is changed!");
                            }
                        } catch (InputMismatchException | InvalidParameterException e) {
                            System.err.println("Invalid input!");
                        }
                        scanner.nextLine();
                        break;
                    case "/filter_by_price":
                        try {
                            int leftBorder = scanner.nextInt();
                            int rightBorder = scanner.nextInt();
                            dataBase.filterByPrice(leftBorder, rightBorder);
                        } catch (InputMismatchException | InvalidParameterException e) {
                            System.err.println("Invalid input!");
                        }
                        scanner.nextLine();
                        break;
                    case "/exit":
                        exit = true;
                        break;
                    default:
                        System.err.println("Invalid command!");
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
