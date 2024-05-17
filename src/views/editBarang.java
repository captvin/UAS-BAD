package views;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.*;

import java.text.DecimalFormat;

import controllers.ProductController;

public class editBarang {

    private mainView mainView;

    public editBarang(mainView mainView) {
        this.mainView = mainView;
    }

    private TextField nameInput;
    private TextArea descriptionInput;
    private TextField priceInput;
    private int id;

    public void show(Stage stage, product product) {
        ProductController productController = new ProductController();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        id = product.getProductId();

        Label nameLabel = new Label("Nama Produk:");
        GridPane.setConstraints(nameLabel, 0, 1);
        nameInput = new TextField();
        nameInput.setText(product.getName());
        GridPane.setConstraints(nameInput, 0, 2);

        Label descriptionLabel = new Label("Deskripsi:");
        GridPane.setConstraints(descriptionLabel, 0, 3);
        descriptionInput = new TextArea();
        descriptionInput.setText(product.getDescription());
        descriptionInput.setPrefRowCount(3); // Set preferred row count for better alignment
        GridPane.setConstraints(descriptionInput, 0, 4);

        Label priceLabel = new Label("Harga:");
        GridPane.setConstraints(priceLabel, 0, 5);
        priceInput = new TextField();
        DecimalFormat decimalFormat = new DecimalFormat("#");

        priceInput.setText(decimalFormat.format(product.getPrice()));
        GridPane.setConstraints(priceInput, 0, 6);

        priceInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*\\.?\\d*")) {
                priceInput.setText(oldValue);
            }
        });

        Button addButton = new Button("Edit Produk");
        GridPane.setConstraints(addButton, 0, 9);
        addButton.setOnAction(e -> handleSubmit(productController, stage));

        grid.getChildren().addAll(nameLabel, nameInput, descriptionLabel, descriptionInput, priceLabel, priceInput,
               addButton);

        Scene scene = new Scene(grid, 400, 400);
        stage.setScene(scene);
        stage.setTitle("Edit Produk");
        stage.show();
    }

    private void handleSubmit(ProductController productController, Stage stage) {
        String name = nameInput.getText();
        String description = descriptionInput.getText();
        double price = Integer.parseInt(priceInput.getText());

        product product = new product(name, description, price);
        product.setProductId(id);
        boolean status = productController.editProduct(product);

        if(status == true ) {
            mainView.refreshListBarang();
            showAlert("Success", "Berhasil merubah produk\nnama : " + name + "\nHarga : " + price, stage);
        }
       

        nameInput.clear();
        descriptionInput.clear();
        priceInput.clear();

    }

    private void showAlert(String title, String message, Stage stage) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

        stage.close();
    }
}
