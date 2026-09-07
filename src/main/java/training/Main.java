package training;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        //Launches JavaFX
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        //Initializes the JavaFX stuff
        FXMLLoader loader = new FXMLLoader(getClass().getResource("calculator.fxml"));
        Parent root = loader.load();

        //Initializes the controller with all our java code
        CalculatorController controller = loader.getController();
        controller.init();

        //Actually starts the app with everything
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Training Calculator");
        stage.show();
    }
}