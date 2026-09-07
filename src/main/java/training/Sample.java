
package training;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

//Note: Unlike in scouting, main is a class you have to learn here. It doesn't have much going on but is needed to actually start the app.

public class Sample {

    //The UI elements, unlike in Android Studio they directly connect to their fxml id
    @FXML
    private BorderPane rootContainer;
    @FXML
    private GridPane gridPane;
    @FXML
    private Label label;
    @FXML
    private Button button, gridButton;


    @FXML
    public void initialize() {

        //This is where things are initialized with UI, such as button activities

        //UI Java:
            //Sets the font size of a button at the start, helps determine size ratio when rescaling the app
            gridButton.setStyle("-fx-font-size: 18px;");

            //Sets the font size of a label at the start, using another scalable object (such as the gridPane's height) as reference
            label.styleProperty().bind(Bindings.concat("-fx-font-size: ", gridPane.heightProperty().divide(14), "px;"));

            //This controls what happens when the text is too big to fit on screen, this example makes it trail off to the left - what calculators usually do
            label.setTextOverrun(OverrunStyle.LEADING_ELLIPSIS);

            //A smoother system of updating the text size of buttons, bit more complicated but simpler ways can appear more glitchy
            gridPane.heightProperty().addListener((obs, oldVal, newVal) -> {
                double gridHeight = newVal.doubleValue();
                if (gridHeight <= 0) return;

                // Baseline math (1.0 scale at 300px grid height)
                double currentScale = gridHeight / 300.0;


                var btnTextNode = button.lookup(".text");
                if (btnTextNode != null) {
                    btnTextNode.setScaleX(currentScale);
                    btnTextNode.setScaleY(currentScale);
                }
            });

        //Backend java:
            //The equivalent of an on-click listener
            button.setOnAction(event -> {

                //Basic notification system, not as good-looking as Toast but does the basics well. Can probably be edited to look better and/or function as a popup
                Notifications.create()
                        .title("Clicked")
                        .text("You clicked the button!")
                        .hideAfter(Duration.seconds(3))
                        .showInformation();

            });

            //Sets the text of the label
            label.setText("Hello World");

    }

}
