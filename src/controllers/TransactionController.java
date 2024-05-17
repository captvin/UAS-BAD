package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import models.*;
import connection.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TransactionController {
    public boolean sell(transaction transaction) {
        try (Connection connection = DB.getConnection()) {
            String query = "SELECT qty FROM transactions WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, transaction.getProductId());

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next() && resultSet.getInt("qty") < transaction.getQty()) {
                showAlert("Stock Tidak Cukup", "kuantitas pembelian melebihi stok yang tersedia");
                return false;
            }

            try {
                // Insert the transaction record
                String query3 = "INSERT INTO transactions (productId, qty, type) VALUES (?, ?, 'SELL')";
                PreparedStatement statement3 = connection.prepareStatement(query3);
                statement3.setInt(1, transaction.getProductId());
                statement3.setInt(2, transaction.getQty());

                // Update the product quantity
                String query2 = "UPDATE products SET qty = qty - ? WHERE id = ?";
                PreparedStatement statement2 = connection.prepareStatement(query2);
                statement2.setInt(1, transaction.getQty());
                statement2.setInt(2, transaction.getProductId());

                int rowsAffected1 = statement3.executeUpdate();
                int rowsAffected2 = statement2.executeUpdate();
                connection.close();

                if (rowsAffected1 > 0 && rowsAffected2 > 0) {
                    return true;
                } else {
                    return false;
                }
            } catch (SQLException e) {
                connection.close();
                showAlert("Error", "An error occurred: " + e.getMessage());
                return false;
            }
        } catch (SQLException e) {
            showAlert("Error", "An error occurred: " + e.getMessage());
            return false;
        }
    }

    public boolean buy(transaction transaction) {
        try (Connection connection = DB.getConnection()) {
            // Insert the transaction record
            String query = "INSERT INTO transactions (productId, qty, type) VALUES (?, ?, 'BUY')";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, transaction.getProductId());
            statement.setInt(2, transaction.getQty());

            // Update the product quantity
            String query2 = "UPDATE products SET qty = qty + ? WHERE id = ?";
            PreparedStatement statement2 = connection.prepareStatement(query2);
            statement2.setInt(1, transaction.getQty());
            statement2.setInt(2, transaction.getProductId());

            int rowsAffected1 = statement.executeUpdate();
            int rowsAffected2 = statement2.executeUpdate();
            connection.close();

            if (rowsAffected1 > 0 && rowsAffected2 > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            showAlert("Error", "An error occurred: " + e.getMessage());
            return false;
        }
    }

    public ObservableList<transaction> report(String jenisTransaksi, String jenisBarang, String waktu) {
        ObservableList<transaction> transaksi = FXCollections.observableArrayList();
        try (Connection connection = DB.getConnection()) {
            String query = "SELECT p.name, t.qty, t.`timestamp`, t.type, p.category FROM transactions t INNER JOIN products p ON t.productId = p.id WHERE t.id IS NOT NULL";

            if (jenisTransaksi == "SELL") {
                query += " AND t.type = 'SELL'";
            } else if (jenisTransaksi == "BUY") {
                query += " AND t,type = 'BUY'";
            }

            if (jenisBarang == "DEVICE") {
                query += " AND p.category = 'DEVICE'";
            } else if (jenisBarang == "ACCESSORICE") {
                query += " AND p.category = 'ACC'";
            }

            if (waktu == "HARIAN") {
                query += " AND DATE(t.timestamp) = CURDATE()";
            } else if (waktu == "MINGGUAN") {
                query += " AND WEEKOFYEAR(t.timestamp) = WEEKOFYEAR(CURDATE())";
            } else if (waktu == "BULANAN") {
                query += " AND MONTHNAME(t.`timestamp`) = MONTHNAME(CURDATE())";
            } else if (waktu == "TAHUNAN") {
                query += " AND YEAR(t.`timestamp`) = YEAR(CURDATE())";
            }

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            
            while (result.next()) {
                String name = result.getString("name");
                String type = result.getString("type");
                int qty = result.getInt("qty");
                String category = result.getString("category");
                Timestamp timestamp = result.getTimestamp("timestamp");

                transaction transaction = new transaction();
                transaction.setName(name);
                transaction.setType(type);
                transaction.setQty(qty);
                transaction.setTimestamp(timestamp);
                transaction.setCategory(category);

                transaksi.add(transaction);
            }
            connection.close();
        } catch (SQLException e) {
            showAlert("Error", "An error occurred: " + e.getMessage());
        }
        return transaksi;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
