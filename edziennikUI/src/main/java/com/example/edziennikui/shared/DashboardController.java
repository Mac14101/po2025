package com.example.edziennikui.shared;

import entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardController {

    @FXML private Label lblUserRole;

    public void setInfo(User user) {
        lblUserRole.setText("Zalogowano jako: " + user.getName() + " " + user.getSurname());
    }
}