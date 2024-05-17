package views;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class mainView {
    private BorderPane borderPane = new BorderPane();
    private listBarang listView;

    public void show(Stage stage) {
        // MenuBar setup
        MenuBar menuBar = new MenuBar();

        // Menus
        Menu menuFile = new Menu("File");
        Menu menuView = new Menu("View");

        // MenuItems
        MenuItem daftarBarangItem = new MenuItem("Tambah Barang");
        MenuItem penjualanItem = new MenuItem("Penjualan");
        MenuItem pembelianItem = new MenuItem("Pembelian");
        MenuItem report = new MenuItem("Report");
        MenuItem exitItem = new MenuItem("Exit");

        // Adding MenuItems to Menus
        menuFile.getItems().addAll(daftarBarangItem, penjualanItem, pembelianItem, exitItem);
        menuView.getItems().addAll(daftarBarangItem, penjualanItem, pembelianItem, report);

        // Adding Menus to MenuBar
        menuBar.getMenus().addAll(menuFile, menuView);

        // BorderPane setup
        // borderPane = new BorderPane();
        borderPane.setTop(menuBar);

        // Event handling for MenuItems
        daftarBarangItem.setOnAction(e -> new daftarBarang(this).show(new Stage()));
        penjualanItem.setOnAction(e -> new penjualan(this).show(new Stage()));
        pembelianItem.setOnAction(e -> new pembelian(this).show(new Stage()));
        report.setOnAction(e -> new report().show(new Stage()));
        // listBarang.setOnAction(e -> new listBarang().show(new Stage()));
        exitItem.setOnAction(e -> stage.close());

        // Default view (Daftar Barang)
        showListBarang();

        Scene scene = new Scene(borderPane, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Main Menu");
        stage.show();
    }

    private void showListBarang() {
        listView = new listBarang(this); // Instantiate listBarang
        borderPane.setCenter(listView.getView());
    }

    public void refreshListBarang() {
        if (listView != null) {
            listView.refreshView(); // Refresh listBarang view
        }
    }

    
}
