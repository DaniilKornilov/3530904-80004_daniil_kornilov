package poly.lab4;

import poly.lab4.db.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static final String URL = "jdbc:h2:~/ProductsDataBase";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Scanner scanner = new Scanner(System.in)) {
            DataBase dataBase = new DataBase(connection);
            Commands commands = new Commands(dataBase);
            String command;
            boolean exit = false;
            while (!exit) {
                System.out.println("Enter command:");
                command = scanner.next();
                switch (command) {
                    case "/add":
                        commands.add(scanner);
                        break;
                    case "/delete":
                        commands.delete(scanner);
                        break;
                    case "/show_all":
                        commands.showAll(scanner);
                        break;
                    case "/price":
                        commands.showPrice(scanner);
                        break;
                    case "/change_price":
                        commands.changePrice(scanner);
                        break;
                    case "/filter_by_price":
                        commands.showFilteredByPrice(scanner);
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
