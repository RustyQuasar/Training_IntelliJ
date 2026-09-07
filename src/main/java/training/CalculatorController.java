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


public class CalculatorController {

    @FXML
    private GridPane numberPad;
    @FXML
    private Label numberDisplay;
    @FXML
    private Button one, two, three, four, five, six, seven, eight, nine, zero, clear, backspace, exponent, divide, multiply, add, subtract, invert, period, equals;

    private Button[] operatorPad, numPad;

    private String firstNumber = "", secondNumber = "";
    private int operatorIndex = -1;
    private boolean continuedEquation = false;

    public void init() {
        //This is where we put stuff that needs to run before the UI loads, like populating a dropdown
        //Nothing really needed for a calculator tho
    }

    @FXML
    public void initialize() {
        Button[] buttons = {one, two, three, four, five, six, seven, eight, nine, zero,
                clear, backspace, exponent, divide, multiply, add, subtract,
                invert, period, equals};

        numPad = new Button[]{one, two, three, four, five, six, seven, eight, nine, zero};
        operatorPad = new Button[]{divide, multiply, add, subtract, exponent};

        for (Button btn : buttons) {
            btn.setStyle("-fx-font-size: 18px;");
        }

        numberDisplay.styleProperty().bind(Bindings.concat("-fx-font-size: ", numberPad.heightProperty().divide(5), "px;"));
        numberDisplay.setText("0");
        numberDisplay.setTextOverrun(OverrunStyle.LEADING_ELLIPSIS);

        numberPad.heightProperty().addListener((obs, oldVal, newVal) -> {
            double gridHeight = newVal.doubleValue();
            if (gridHeight <= 0) return;

            // Baseline math (1.0 scale at 300px grid height)
            double currentScale = gridHeight / 300.0;

            // Scale all button text nodes
            for (Button btn : buttons) {
                var btnTextNode = btn.lookup(".text");
                if (btnTextNode != null) {
                    btnTextNode.setScaleX(currentScale);
                    btnTextNode.setScaleY(currentScale);
                }
            }
        });

        clear.setOnAction(event -> {
            if (secondNumber.isEmpty()) {
                firstNumber = "";
                operatorIndex = -1;
            } else {
                secondNumber = "";
            }
            updateNumberDisplay();
        });

        backspace.setOnAction(event -> {
            if (operatorIndex == -1) {
                if (!firstNumber.isEmpty())
                    firstNumber = firstNumber.substring(0, firstNumber.length() - 1);
            } else {
                if (!secondNumber.isEmpty())
                    secondNumber = secondNumber.substring(0, secondNumber.length() - 1);
                else operatorIndex = -1;
            }
            updateNumberDisplay();
        });

        for (Button button : numPad) {
            button.setOnAction(e -> {
                if (continuedEquation) {
                    continuedEquation = false;
                    firstNumber = "";
                    operatorIndex = -1;
                }
                if (operatorIndex == -1) {
                    firstNumber += button.getText();
                } else {
                    secondNumber += button.getText();
                }
                updateNumberDisplay();
            });
        }

        for (int i = 0; i < operatorPad.length; i++) {
            int finalI = i;
            operatorPad[i].setOnAction(e -> {
                if (continuedEquation) continuedEquation = false;
                if (secondNumber.isEmpty()) {
                    operatorIndex = finalI;
                } else {
                    calculate(finalI);
                    secondNumber = "";
                }
                //System.out.println("Clicked " + operatorPad[finalI].getText() + " at index " + finalI);
                updateNumberDisplay();
            });
        }

        equals.setOnAction(event -> {
            if (calculate(-1)) continuedEquation = true;
            updateNumberDisplay();
        });

        invert.setOnAction(event -> {
            if (continuedEquation) {
                continuedEquation = false;
                firstNumber = "";
                operatorIndex = -1;
            }
            if (operatorIndex == -1) {
                if (firstNumber.isEmpty()) firstNumber = "-";
                else if (firstNumber.charAt(0) == '-') firstNumber = firstNumber.substring(1);
                else firstNumber = "-" + firstNumber;
            } else {
                if (secondNumber.isEmpty()) secondNumber = "-";
                else if (secondNumber.charAt(0) == '-') secondNumber = secondNumber.substring(1);
                else secondNumber = "-" + secondNumber;
            }
            updateNumberDisplay();
        });



    }

    private void updateNumberDisplay() {
        if (operatorIndex == -1) {
            numberDisplay.setText(firstNumber);
            secondNumber = "";
        } else {
            numberDisplay.setText(String.format("%s %s %s", firstNumber, operatorPad[operatorIndex].getText(), secondNumber));
        }
    }

    private boolean calculate(int newIndex) {
        if (firstNumber.isEmpty() || secondNumber.isEmpty()) return false;
        double firstNum, secondNum, result;
        try {
            firstNum = Double.parseDouble(firstNumber);
            secondNum = Double.parseDouble(secondNumber);
        } catch (Exception e) {
            Notifications.create()
                    .title("Error")
                    .text("Number out of bounds")
                    .hideAfter(Duration.seconds(3))
                    .showInformation();
            return false;
        }
        switch (operatorIndex) {
            case 0:
                result = firstNum / secondNum;
                break;
            case 1:
                result = firstNum * secondNum;
                break;
            case 2:
                result = firstNum + secondNum;
                break;
            case 3:
                result = firstNum - secondNum;
                break;
            case 4:
                result = Math.pow(firstNum, secondNum);
                break;
            default: return false;
        }
        if (result == Double.POSITIVE_INFINITY || result == Double.NEGATIVE_INFINITY || Double.isNaN(result)) {
            Notifications.create()
                    .title("Error")
                    .text("Number out of bounds")
                    .hideAfter(Duration.seconds(3))
                    .showInformation();
            return false;
        }
        firstNumber = String.valueOf(result).replaceAll("\\.0$", "");
        operatorIndex = newIndex;
        return true;
    }

}
