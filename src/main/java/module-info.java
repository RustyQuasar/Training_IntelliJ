module training {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;

    requires org.controlsfx.controls;

    opens training to javafx.fxml;

    exports training;
}