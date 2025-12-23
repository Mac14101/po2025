module com.example.edziennikui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.edziennikui to javafx.fxml;
    opens com.example.edziennikui.admin to javafx.fxml;
    exports com.example.edziennikui;
}