package training;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args); // hands control to JavaFX; JavaFX will call start() below
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("calculator.fxml"));
        Parent root = loader.load();

        CalculatorController controller = loader.getController();
        controller.init();

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Training Calculator");

        stage.show();
    }
}