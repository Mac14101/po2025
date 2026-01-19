module com.example.edziennikui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.net.http;

    requires client;
    requires entities;
    requires com.fasterxml.jackson.databind;
    requires java.logging;

    opens com.example.edziennikui to javafx.fxml;
    opens com.example.edziennikui.admin.classes to javafx.fxml;
    opens com.example.edziennikui.admin.schedule to javafx.fxml;
    opens com.example.edziennikui.admin.subjects to javafx.fxml;
    opens com.example.edziennikui.admin.users to javafx.fxml;
    opens com.example.edziennikui.student to javafx.fxml;
    opens com.example.edziennikui.teacher to javafx.fxml;
    exports com.example.edziennikui;
    exports com.example.edziennikui.shared;
    opens com.example.edziennikui.shared to javafx.fxml;
}