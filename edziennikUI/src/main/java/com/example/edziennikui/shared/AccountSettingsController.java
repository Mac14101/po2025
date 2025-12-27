package com.example.edziennikui.shared;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AccountSettingsController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField pwdCurrent;
    @FXML private PasswordField pwdNew;
    @FXML private PasswordField pwdConfirm;
    @FXML private Label lblStatus;

    @FXML
    public void initialize() {
        txtUsername.setText("Zalogowany_Uzytkownik");
    }

    @FXML
    private void handleSaveSettings() {
        String newPass = pwdNew.getText();
        String confirmPass = pwdConfirm.getText();

        if (newPass.isEmpty() || confirmPass.isEmpty()) {
            lblStatus.setText("Pola hasła nie mogą być puste!");
            return;
        }

        if (!newPass.equals(confirmPass)) {
            lblStatus.setText("Hasła nie są identyczne!");
            return;
        }

        lblStatus.setText("Hasło zostało pomyślnie zmienione.");
        lblStatus.setStyle("-fx-text-fill: green;");
    }
}