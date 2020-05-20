package lab5;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lab5.db.DataBase;

import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Controller {

    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button showAllButton;
    @FXML
    private Button priceButton;
    @FXML
    private Button changePriceButton;
    @FXML
    private Button filterByPriceButton;

    @FXML
    private TextField addProdIDTextField;
    @FXML
    private TextField addTitleTextField;
    @FXML
    private TextField addCostTextField;
    @FXML
    private TextField deleteTextField;
    @FXML
    private TextField checkPriceTextField;
    @FXML
    private TextField changePriceTextField;
    @FXML
    private TextField changePriceTitleTextField;
    @FXML
    private TextField leftBorderPriceFilterTextField;
    @FXML
    private TextField rightBorderPriceFilterTextField;

    @FXML
    private Label resultLabel;

    @FXML
    private TableView<Product> showAllProductsTableView;
    @FXML
    private TableColumn<Product, Integer> idTableColumn;
    @FXML
    private TableColumn<Product, Integer> prodIDTableColumn;
    @FXML
    private TableColumn<Product, String> titleTableColumn;
    @FXML
    private TableColumn<Product, Integer> costTableColumn;

    @FXML
    private void cleanALlTextFields() {
        addProdIDTextField.setText("");
        addTitleTextField.setText("");
        addCostTextField.setText("");
        deleteTextField.setText("");
        checkPriceTextField.setText("");
        changePriceTextField.setText("");
        changePriceTitleTextField.setText("");
        leftBorderPriceFilterTextField.setText("");
        rightBorderPriceFilterTextField.setText("");
    }

    @FXML
    void initialize() {
        final String url = "jdbc:h2:~/Prods";
        final String user = "sa";
        final String password = "";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DataBase dataBase = new DataBase(connection);

        idTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("prodid"));
        titleTableColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        costTableColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));

        showAllButton.setOnAction(event -> {
            showAllProductsTableView.setItems(dataBase.showAll());
            resultLabel.setText("Table is viewing");
            cleanALlTextFields();
        });

        addButton.setOnAction(event -> {
            int prodid;
            int cost;
            try {
                prodid = Integer.parseInt(addProdIDTextField.getText());
                cost = Integer.parseInt(addCostTextField.getText());
            } catch (NumberFormatException e) {
                resultLabel.setText("Invalid input!");
                return;
            }
            try {
                dataBase.addProduct(prodid, addTitleTextField.getText(), cost);
            } catch (InvalidParameterException | SQLException e) {
                resultLabel.setText("Invalid product!");
                return;
            }
            resultLabel.setText("Product added to data base");
            cleanALlTextFields();
        });

        deleteButton.setOnAction(event -> {
            if (!deleteTextField.getText().isEmpty()) {
                boolean result = dataBase.deleteProduct(deleteTextField.getText());
                if (result) {
                    resultLabel.setText("Product successfully deleted");
                } else {
                    resultLabel.setText(deleteTextField.getText() + " is missing!");
                }
            } else {
                resultLabel.setText("Enter product to delete!");
            }
            cleanALlTextFields();
        });

        priceButton.setOnAction(event -> {
            if (!checkPriceTextField.getText().isEmpty()) {
                int cost = dataBase.price(checkPriceTextField.getText());
                if (cost != -1) {
                    resultLabel.setText("Product price: " + cost);
                } else {
                    resultLabel.setText(checkPriceTextField.getText() + " is missing!");
                }
            } else {
                resultLabel.setText("Enter product to check price!");
            }
            cleanALlTextFields();
        });

        changePriceButton.setOnAction(event -> {
            if (!changePriceTitleTextField.getText().isEmpty()) {
                int cost;
                try {
                    cost = Integer.parseInt(changePriceTextField.getText());
                } catch (NumberFormatException e) {
                    resultLabel.setText("Invalid input!");
                    return;
                }
                try {
                    boolean resultChangePrice = dataBase.changePrice(changePriceTitleTextField.getText(), cost);
                    if (!resultChangePrice) {
                        resultLabel.setText(changePriceTitleTextField.getText() + " is missing!");
                    }
                } catch (InvalidParameterException e) {
                    resultLabel.setText(e.getMessage());
                }
            } else {
                resultLabel.setText("Enter product to check price!");
            }
            cleanALlTextFields();
        });

        filterByPriceButton.setOnAction(event -> {
            int leftBorder;
            int rightBorder;
            try {
                leftBorder = Integer.parseInt(leftBorderPriceFilterTextField.getText());
                rightBorder = Integer.parseInt(rightBorderPriceFilterTextField.getText());
            } catch (NumberFormatException e) {
                resultLabel.setText("Invalid input!");
                return;
            }
            try {
                showAllProductsTableView.setItems(dataBase.filterByPrice(leftBorder, rightBorder));
            } catch (InvalidParameterException e) {
                resultLabel.setText(e.getMessage());
                return;
            }
            resultLabel.setText("Viewing filtered products");
            cleanALlTextFields();
        });
    }
}
