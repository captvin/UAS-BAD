package views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.*;
import controllers.TransactionController;

import java.sql.Timestamp;
import java.util.List;

public class report {
    private TableView<transaction> table;

    public void show(Stage stage) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label productLabel = new Label("Jenis Transaksi");
        GridPane.setConstraints(productLabel, 0, 0);

        ComboBox<String> jenisTransaksi = new ComboBox<>();
        jenisTransaksi.getItems().addAll("BUY", "SELL", "ALL");
        jenisTransaksi.setValue("ALL"); // Set nilai default
        GridPane.setConstraints(jenisTransaksi, 1, 0);

        Label transaksiLabel = new Label("Jenis Barang");
        GridPane.setConstraints(transaksiLabel, 2, 0);

        ComboBox<String> jenisBarang = new ComboBox<>();
        jenisBarang.getItems().addAll("DEVICE", "ACCESSORICE", "ALL");
        jenisBarang.setValue("ALL"); // Set nilai default
        GridPane.setConstraints(jenisBarang, 3, 0);

        Label waktuLabel = new Label("Waktu");
        GridPane.setConstraints(waktuLabel, 4, 0);

        ComboBox<String> waktu = new ComboBox<>();
        waktu.getItems().addAll("HARIAN", "MINGGUAN", "BULANAN", "TAHUNAN", "ALL");
        waktu.setValue("ALL"); // Set nilai default
        GridPane.setConstraints(waktu, 5, 0);

        Button submitButton = new Button("Submit");
        GridPane.setConstraints(submitButton, 6, 0);
        submitButton.setOnAction(event -> handleSubmit(jenisTransaksi.getValue(), jenisBarang.getValue(), waktu.getValue(), stage));

        grid.getChildren().addAll(productLabel, jenisTransaksi, transaksiLabel, jenisBarang, waktuLabel, waktu, submitButton);

        Scene scene = new Scene(grid, 800, 100);
        stage.setScene(scene);
        stage.setTitle("Report");
        stage.show();
    }

    private void handleSubmit(String jenisTransaksi, String jenisBarang, String waktu, Stage stage) {
        TransactionController transactionController = new TransactionController();
        List<transaction> transactions = transactionController.report(jenisTransaksi, jenisBarang, waktu);

        if (transactions.isEmpty()) {
            showAlert(stage, "TRANSAKSI TIDAK TERSEDIA");
        } else {
            showTable(stage, transactions);
        }
    }

    private void showAlert(Stage stage, String message) {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10, 10, 10, 10));

        Label label = new Label(message);
        vbox.getChildren().add(label);

        Scene scene = new Scene(vbox, 400, 100);
        stage.setScene(scene);
        stage.show();
    }

    private void showTable(Stage stage, List<transaction> transactions) {
        table = new TableView<>();
        ObservableList<transaction> data = FXCollections.observableArrayList(transactions);

        TableColumn<transaction, String> nameColumn = new TableColumn<>("Nama Produk");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<transaction, String> transactionTypeColumn = new TableColumn<>("Jenis Transaksi");
        transactionTypeColumn.setMinWidth(100);
        transactionTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<transaction, String> categoryColumn = new TableColumn<>("Kategori");
        categoryColumn.setMinWidth(100);
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<transaction, Timestamp> waktu = new TableColumn<>("Waktu");
        waktu.setMinWidth(100);
        waktu.setCellValueFactory(new PropertyValueFactory<>("timestamp"));

        TableColumn<transaction, Integer> quantityColumn = new TableColumn<>("Jumlah");
        quantityColumn.setMinWidth(100);
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("qty"));

        table.setItems(data);
        table.getColumns().addAll(nameColumn, transactionTypeColumn, categoryColumn, quantityColumn, waktu);
        // table.getColumns().addAll(nameColumn, transactionTypeColumn,  quantityColumn);

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(table);

        Scene scene = new Scene(vbox, 800, 600);
        stage.setScene(scene);
        stage.show();
    }
}
