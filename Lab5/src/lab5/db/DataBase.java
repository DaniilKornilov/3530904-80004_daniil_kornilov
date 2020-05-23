package lab5.db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lab5.Product;

import java.security.InvalidParameterException;
import java.sql.*;

public class DataBase {
    private final Connection connection;

    private static final String TABLE_NAME = "products";
    private static final String DELETE_TABLE_QUERY = "TRUNCATE TABLE " + TABLE_NAME + " RESTART IDENTITY";
    private static final String ADD_QUERY = "INSERT INTO " + TABLE_NAME
            + " (prodid, title, cost) VALUES (?, ?, ?)";
    private static final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
            + " (id IDENTITY PRIMARY KEY NOT NULL, "
            + "prodid INTEGER NOT NULL UNIQUE, "
            + "title VARCHAR (20) NOT NULL UNIQUE, "
            + "cost INTEGER NOT NULL);";
    private static final String SHOW_ALL_QUERY = "SELECT * FROM " + TABLE_NAME;
    private static final String DELETE_QUERY = "DELETE FROM " + TABLE_NAME + " WHERE title = ?";
    private static final String PRICE_QUERY = "SELECT cost FROM " + TABLE_NAME + " WHERE title = ?";
    private static final String CHANGE_PRICE_QUERY = "UPDATE " + TABLE_NAME + " set cost = ? WHERE title = ?";
    private static final String FILTER_BY_PRICE_QUERY = "SELECT * FROM " + TABLE_NAME
            + " WHERE cost BETWEEN ? AND ?";

    public DataBase(Connection connection) {
        this.connection = connection;
        createTable();
    }

    public void addProduct(int prodid, String title, double cost) throws SQLException {
        if (prodid <= 0 || cost <= 0) {
            throw new InvalidParameterException("Invalid product!");
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_QUERY)) {
            preparedStatement.setInt(1, prodid);
            preparedStatement.setString(2, title);
            preparedStatement.setDouble(3, cost);
            preparedStatement.executeUpdate();
        }
    }

    public boolean deleteProduct(String title) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)) {
            preparedStatement.setString(1, title);
            return preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ObservableList<Product> showAll() {
        ObservableList<Product> productsData = FXCollections.observableArrayList();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SHOW_ALL_QUERY)) {
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
        try (PreparedStatement preparedStatement = connection.prepareStatement(PRICE_QUERY)) {
            preparedStatement.setString(1, title);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("cost");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean changePrice(String title, int cost) throws InvalidParameterException {
        if (cost <= 0) {
            throw new InvalidParameterException("Invalid product!");
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(CHANGE_PRICE_QUERY)) {
            preparedStatement.setInt(1, cost);
            preparedStatement.setString(2, title);
            return preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ObservableList<Product> filterByPrice(int leftBorder, int rightBorder) {
        if (leftBorder > rightBorder || leftBorder < 0) {
            throw new InvalidParameterException("Invalid borders!");
        }
        ObservableList<Product> productsData = FXCollections.observableArrayList();
        try (PreparedStatement preparedStatement = connection.prepareStatement(FILTER_BY_PRICE_QUERY)) {
            preparedStatement.setInt(1, leftBorder);
            preparedStatement.setInt(2, rightBorder);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    productsData.add(new Product(resultSet.getInt("id"),
                            resultSet.getInt("prodid"),
                            resultSet.getString("title"),
                            resultSet.getInt("cost")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productsData;
    }

    private void createTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute(CREATE_TABLE_QUERY);
            statement.execute(DELETE_TABLE_QUERY);
            try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_QUERY)) {
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
}
