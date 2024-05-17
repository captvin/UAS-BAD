package views;

import java.text.NumberFormat;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.*;
import controllers.*;

public class listBarang {
    private TableView<product> table;
    private mainView mainView;

    public listBarang(mainView mainView){
        this.mainView = mainView;
    }

    @SuppressWarnings("unchecked")
    public void show(Stage stage) {
        

        // TableView setup
        TableColumn<product, String> nameColumn = new TableColumn<>("Nama Produk");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<product, String> descriptionColumn = new TableColumn<>("Deskripsi");
        descriptionColumn.setMinWidth(300);
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<product, Double> priceColumn = new TableColumn<>("Harga");
        priceColumn.setMinWidth(100);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<product, Integer> quantityColumn = new TableColumn<>("Jumlah Stok");
        quantityColumn.setMinWidth(100);
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("stockQuantity"));

        TableColumn<product, String> categoryColumn = new TableColumn<>("Kategori");
        categoryColumn.setMinWidth(100);
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        table = new TableView<>();
        table.getColumns().addAll(nameColumn, descriptionColumn, priceColumn, quantityColumn, categoryColumn);

        // Placeholder data
        ObservableList<product> products = FXCollections.observableArrayList(
            new product(1, "Laptop", "Laptop dengan spesifikasi tinggi", 15000000, 10, "Elektronik"),
            new product(2, "Smartphone", "Smartphone terbaru", 7000000, 20, "Elektronik")
        );
        table.setItems(products);

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(table);

        Scene scene = new Scene(vbox, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Daftar Barang");
        stage.show();
    }

    @SuppressWarnings("unchecked")
    public VBox getView() {
        TableColumn<product, String> nameColumn = new TableColumn<>("Nama Produk");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<product, String> descriptionColumn = new TableColumn<>("Deskripsi");
        descriptionColumn.setMinWidth(300);
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<product, Double> priceColumn = new TableColumn<>("Harga");
        priceColumn.setMinWidth(100);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        // Custom cell factory to format price
        priceColumn.setCellFactory(new Callback<TableColumn<product, Double>, TableCell<product, Double>>() {
            @Override
            public TableCell<product, Double> call(TableColumn<product, Double> column) {
                return new TableCell<product, Double>() {
                    @Override
                    protected void updateItem(Double item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(NumberFormat.getCurrencyInstance().format(item));
                        }
                    }
                };
            }
        });

        TableColumn<product, Integer> quantityColumn = new TableColumn<>("Jumlah Stok");
        quantityColumn.setMinWidth(100);
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("stockQuantity"));

        TableColumn<product, String> categoryColumn = new TableColumn<>("Kategori");
        categoryColumn.setMinWidth(100);
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

         TableColumn<product, Void> editColumn = new TableColumn<>("Edit");
        editColumn.setMinWidth(100);

        Callback<TableColumn<product, Void>, TableCell<product, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<product, Void> call(final TableColumn<product, Void> param) {
                final TableCell<product, Void> cell = new TableCell<>() {

                    private final Button editButton = new Button("Edit");

                    {
                        editButton.setOnAction(event -> {
                            product selectedProduct = getTableView().getItems().get(getIndex());
                            editBarang editBarang = new editBarang(mainView);
                            editBarang.show(new Stage(), selectedProduct);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(editButton);
                        }
                    }
                };
                return cell;
            }
        };

        editColumn.setCellFactory(cellFactory);

        table = new TableView<>();
        table.getColumns().addAll(nameColumn, descriptionColumn, priceColumn, quantityColumn, categoryColumn, editColumn);

        // Placeholder data
        // ProductController productController = new ProductController();
        // ObservableList<product> products = productController.getProducts();
        // table.setItems(products);
        refreshView();

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(table);

        return vbox; 
    }

    public void refreshView() {
        ProductController productController = new ProductController();
        List<product> products = productController.getProducts();

        // Update item dalam TableView
        table.setItems(FXCollections.observableArrayList(products));
    }
}
