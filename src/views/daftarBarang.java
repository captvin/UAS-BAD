package views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.*;

import java.util.HashMap;
import java.util.Map;

import controllers.ProductController;

public class daftarBarang {

    private mainView mainView;

    public daftarBarang(mainView mainView) {
        this.mainView = mainView;
    }

    private TextField nameInput;
    private TextArea descriptionInput;
    private TextField priceInput;
    private ComboBox<String> categoryInput;
    private Map<String, String> categoryMap;

    public void show(Stage stage) {
        ProductController productController = new ProductController();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label nameLabel = new Label("Nama Produk:");
        GridPane.setConstraints(nameLabel, 0, 1);
        nameInput = new TextField();
        GridPane.setConstraints(nameInput, 0, 2);

        Label descriptionLabel = new Label("Deskripsi:");
        GridPane.setConstraints(descriptionLabel, 0, 3);
        descriptionInput = new TextArea();
        descriptionInput.setPrefRowCount(3); // Set preferred row count for better alignment
        GridPane.setConstraints(descriptionInput, 0, 4);

        Label priceLabel = new Label("Harga:");
        GridPane.setConstraints(priceLabel, 0, 5);
        priceInput = new TextField();
        GridPane.setConstraints(priceInput, 0, 6);

        priceInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*\\.?\\d*")) {
                priceInput.setText(oldValue);
            }
        });

        // Label quantityLabel = new Label("Jumlah Stok:");
        // GridPane.setConstraints(quantityLabel, 0, 7);
        // quantityInput = new TextField();
        // GridPane.setConstraints(quantityInput, 0, 8);

        Label categoryLabel = new Label("Kategori:");
        GridPane.setConstraints(categoryLabel, 0, 7);

        categoryMap = new HashMap<>();
        categoryMap.put("DEVICE", "DEVICE");
        categoryMap.put("ACCESSORISE", "ACC");

        ObservableList<String> options = FXCollections.observableArrayList(categoryMap.keySet());
        categoryInput = new ComboBox<>(options);
        GridPane.setConstraints(categoryInput, 0, 8);

        Button addButton = new Button("Tambah Produk");
        GridPane.setConstraints(addButton, 0, 9);
        addButton.setOnAction(e -> handleSubmit(productController));

        grid.getChildren().addAll(nameLabel, nameInput, descriptionLabel, descriptionInput, priceLabel, priceInput,
                categoryLabel, categoryInput, addButton);

        Scene scene = new Scene(grid, 400, 400);
        stage.setScene(scene);
        stage.setTitle("Tambah Produk");
        stage.show();
    }

    private void handleSubmit(ProductController productController) {
        String name = nameInput.getText();
        String description = descriptionInput.getText();
        double price = Integer.parseInt(priceInput.getText());
        String categoryDisplay = categoryInput.getValue();
        String category = categoryMap.get(categoryDisplay);

        product product = new product(name, description, price, category);
        boolean status = productController.addProduct(product);

        if(status == true ) {
            mainView.refreshListBarang();
            showAlert("Success", "Berhasil menambahkan produk\nnama : " + name + "\nHarga : " + price + "\nKategori : " + category );
        }
       

        nameInput.clear();
        descriptionInput.clear();
        priceInput.clear();
        categoryInput.setValue(null);

    }

    private void showAlert(String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
