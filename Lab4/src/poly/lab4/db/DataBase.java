package poly.lab4.db;

import java.sql.*;

public class DataBase {

    private final Connection connection;

    private static final String TABLE_NAME = "products";
    private static final String DELETE_TABLE_QUERY = "TRUNCATE TABLE " + TABLE_NAME + " RESTART IDENTITY";
    private static final String ADD_QUERY = "INSERT INTO " + TABLE_NAME
            + " (prod_id, title, cost) VALUES (?, ?, ?)";
    private static final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
            + " (id IDENTITY PRIMARY KEY NOT NULL, "
            + "prod_id INTEGER NOT NULL UNIQUE, "
            + "title VARCHAR (20) NOT NULL UNIQUE, "
            + "cost INTEGER NOT NULL);";
    private static final String SHOW_ALL_QUERY = "SELECT * FROM " + TABLE_NAME;
    private static final String DELETE_QUERY = "DELETE FROM " + TABLE_NAME + " WHERE title = ?";
    private static final String PRICE_QUERY = "SELECT cost FROM " + TABLE_NAME + " WHERE title = ?";
    private static final String CHANGE_PRICE_QUERY = "UPDATE " + TABLE_NAME + " set cost = ? WHERE title = ?";
    private static final String FILTER_BY_PRICE_QUERY = "SELECT * FROM " + TABLE_NAME
            + " WHERE cost BETWEEN ? AND ?";

    public DataBase(Connection connection) throws SQLException {
        this.connection = connection;
        createTable();
    }

    public void addProduct(int prod_id, String title, double cost) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_QUERY)) {
            preparedStatement.setInt(1, prod_id);
            preparedStatement.setString(2, title);
            preparedStatement.setDouble(3, cost);
            preparedStatement.executeUpdate();
        }
    }

    public boolean deleteProduct(String title) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)) {
            preparedStatement.setString(1, title);
            return preparedStatement.executeUpdate() != 0;
        }
    }

    public void showAll() throws SQLException {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SHOW_ALL_QUERY)) {
            System.out.println("id\t\tprod_id\t\ttitle\t\t\tcost");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("id") + "\t\t" +
                        resultSet.getInt("prod_id") + "\t\t\t" +
                        resultSet.getString("title") + "\t\t" +
                        resultSet.getInt("cost"));
            }
        }
    }

    public int showPrice(String title) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(PRICE_QUERY)) {
            preparedStatement.setString(1, title);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("cost");
                }
            }
        }
        return -1;
    }

    public boolean changePrice(String title, int newCost) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CHANGE_PRICE_QUERY)) {
            preparedStatement.setInt(1, newCost);
            preparedStatement.setString(2, title);
            return preparedStatement.executeUpdate() != 0;
        }
    }

    public void showFilteredByPrice(int leftBorder, int rightBorder) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FILTER_BY_PRICE_QUERY)) {
            preparedStatement.setInt(1, leftBorder);
            preparedStatement.setInt(2, rightBorder);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("id") + "\t\t" +
                        resultSet.getInt("prod_id") + "\t\t\t" +
                        resultSet.getString("title") + "\t\t" +
                        resultSet.getInt("cost"));
            }
        }
    }

    private void createTable() throws SQLException {
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
        }
    }
}
