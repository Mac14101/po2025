module gui.gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens gui.gui to javafx.fxml;
    exports gui.gui;
}