import javafx.application.Application;
import javafx.stage.Stage;

import views.*;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        new mainView().show(primaryStage);
    }

    // @Override
    public void refresh(Stage primaryStage) {
        new mainView().show(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
