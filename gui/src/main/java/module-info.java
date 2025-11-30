module gui.gui {
    requires javafx.controls;
    requires javafx.fxml;


    opens gui.gui to javafx.fxml;
    exports gui.gui;
}