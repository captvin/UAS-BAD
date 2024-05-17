package views;

import java.util.List;

import controllers.ProductController;
import controllers.TransactionController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.product;
import models.transaction;

public class pembelian {

    private mainView mainView;

    public pembelian(mainView mainView) {
        this.mainView = mainView;
    }

    public void show(Stage stage) {
        ProductController productController = new ProductController();
        List<product> products = productController.getProducts();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        // Label for product selection
        Label productLabel = new Label("Pilih Produk:");
        GridPane.setConstraints(productLabel, 0, 0);

        // ComboBox for product selection
        ComboBox<product> productComboBox = new ComboBox<>();
        productComboBox.getItems().addAll(products);
        productComboBox.setConverter(new javafx.util.StringConverter<product>() {
            @Override
            public String toString(product product) {
                return product != null ? product.getName() : "";
            }

            @Override
            public product fromString(String string) {
                return products.stream().filter(product -> product.getName().equals(string)).findFirst().orElse(null);
            }
        });
        GridPane.setConstraints(productComboBox, 1, 0);

        // Label for quantity input
        Label qtyLabel = new Label("Kuantitas:");
        GridPane.setConstraints(qtyLabel, 0, 1);

        // TextField for quantity input with validation
        TextField qtyField = new TextField();
        qtyField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                qtyField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        GridPane.setConstraints(qtyField, 1, 1);

        // Button to submit the form
        Button submitButton = new Button("Submit");
        GridPane.setConstraints(submitButton, 1, 2);
        submitButton.setOnAction(e -> handleSubmit(productComboBox, qtyField));

        grid.getChildren().addAll(productLabel, productComboBox, qtyLabel, qtyField, submitButton);

        Scene scene = new Scene(grid, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Pembelian");
        stage.show();
    }

    private void handleSubmit(ComboBox<product> productComboBox, TextField qtyField) {
        product selectedProduct = productComboBox.getValue();
        String qtyText = qtyField.getText();

        if (selectedProduct == null) {
            showAlert("Error", "Silakan pilih produk.");
        } else if (qtyText.isEmpty() || Integer.parseInt(qtyText) <= 0) {
            showAlert("Error", "Silakan masukkan kuantitas yang valid.");
        } else {
            int qty = Integer.parseInt(qtyText);
            int idProduct = selectedProduct.getProductId();

            // Perform necessary action with selected product and quantity
            TransactionController transactionController = new TransactionController();
            transaction transaction = new transaction(idProduct, qty);
            boolean status = transactionController.buy(transaction);

            if(status == true) {
                // Buat instance baru
                mainView.refreshListBarang();
                showAlert("Success", "Produk: " + selectedProduct.getName() + "\n Type: SELL\nKuantitas: " + qty);
            }
        }
    }

    private void showAlert(String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
