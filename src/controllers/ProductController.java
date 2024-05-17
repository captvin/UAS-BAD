package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import models.*;
import connection.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ProductController {

    public boolean addProduct(product product) {
        try (Connection connection = DB.getConnection()) {
            String query = "INSERT INTO products (name, description, price, qty, category) VALUES (?, ?, ?, 0, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setDouble(3, product.getPrice());
            // statement.setInt(4, product.getStockQuantity());
            statement.setString(4, product.getCategory());
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0){
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            showAlert("Error", "An error occurred: " + e.getMessage());
            return false;
        }
    }

    public ObservableList<product> getProducts() {
        ObservableList<product> products = FXCollections.observableArrayList();
        try (Connection connection = DB.getConnection()) {
            String query = "SELECT * FROM products";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                int stockQuantity = resultSet.getInt("qty");
                String category = resultSet.getString("category");

                product product = new product(id, name, description, price, stockQuantity, category);
                products.add(product);
            }
        } catch (SQLException e) {
            showAlert("Error", "An error occurred: " + e.getMessage());
        }
        return products;
    }

    public boolean editProduct(product product){
        try (Connection connection = DB.getConnection()) {
            String query = "UPDATE products SET name = ?, description = ?, price = ? WHERE id =?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setDouble(3, product.getPrice());
            // statement.setInt(4, product.getStockQuantity());
            statement.setInt(4, product.getProductId());
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0){
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            showAlert("Error", "An error occurred: " + e.getMessage());
            return false;
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
