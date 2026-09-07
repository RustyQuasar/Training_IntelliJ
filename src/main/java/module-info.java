//This file needs to be updated with new imports, but is enough for a basic app to compile with
module training {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;

    requires org.controlsfx.controls;

    opens training to javafx.fxml;

    exports training;
}