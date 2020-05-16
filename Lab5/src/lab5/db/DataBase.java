package lab5.db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lab5.Product;

import java.security.InvalidParameterException;
import java.sql.*;

public class DataBase {
    private final Connection connection;

    public DataBase(Connection connection) {
        this.connection = connection;
        createTable();
    }

    public void addProduct(int prodid, String title, double cost) {
        if (prodid <= 0 || cost <= 0) {
            throw new InvalidParameterException("Invalid product!");
        }
        String sql = "INSERT INTO products (prodid, title, cost) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, prodid);
            preparedStatement.setString(2, title);
            preparedStatement.setDouble(3, cost);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteProduct(String title) {
        if (checkProduct(title)) {
            String sql = "DELETE FROM products WHERE title = '" + title + "'";
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(sql);
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public ObservableList<Product> showAll() {
        String sql = "SELECT id, prodid, title, cost FROM products";
        ObservableList<Product> productsData = FXCollections.observableArrayList();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                productsData.add(new Product(resultSet.getInt("id"),
                        resultSet.getInt("prodid"),
                        resultSet.getString("title"),
                        resultSet.getInt("cost")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productsData;
    }

    public int price(String title) {
        String queuePrice = "SELECT cost FROM products WHERE title ='" + title + "'";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(queuePrice)) {
            if (resultSet.next()) {
                return resultSet.getInt("cost");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean changePrice(String title, int cost) {
        if (checkProduct(title)) {
            String changeQuery = "UPDATE products SET cost = " + cost + " WHERE title = '" + title + "'";
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(changeQuery);
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public ObservableList<Product> filterByPrice(int leftBorder, int rightBorder) {
        if (leftBorder > rightBorder) {
            throw new InvalidParameterException("Invalid borders!");
        }
        ObservableList<Product> productsData = FXCollections.observableArrayList();
        String sql = "SELECT id, prodid, title, cost FROM products"
                + " WHERE cost BETWEEN " + leftBorder + " AND " + rightBorder;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                productsData.add(new Product(resultSet.getInt("id"),
                        resultSet.getInt("prodid"),
                        resultSet.getString("title"),
                        resultSet.getInt("cost")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productsData;
    }

    private void createTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS products "
                    + "(id IDENTITY PRIMARY KEY NOT NULL, "
                    + "prodid INTEGER NOT NULL UNIQUE, "
                    + "title VARCHAR (20) NOT NULL UNIQUE, "
                    + "cost INTEGER NOT NULL);");

            String sql = "DELETE FROM products";
            statement.execute(sql);

            sql = "INSERT INTO products (prodid, title, cost) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                for (int i = 1; i < 11; ++i) {
                    preparedStatement.setInt(1, i);
                    preparedStatement.setString(2, "product" + i);
                    int cost = (int) (Math.random() * 1000);
                    preparedStatement.setDouble(3, cost);
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean checkProduct(String title) {
        String queuePrice = "SELECT title FROM products WHERE title ='" + title + "'";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(queuePrice)) {
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}