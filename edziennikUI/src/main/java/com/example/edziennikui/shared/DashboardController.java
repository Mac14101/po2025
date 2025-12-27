package com.example.edziennikui.shared;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardController {

    @FXML private Label lblUserRole;

    @FXML
    public void initialize() {
    }

    public void setInfo(String role) {
        lblUserRole.setText("Zalogowano jako: " + role);
    }
}