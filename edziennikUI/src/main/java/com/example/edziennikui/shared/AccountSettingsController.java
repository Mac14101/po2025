package com.example.edziennikui.shared;

import client.ApplicationClient;
import entities.User;
import entities.UserChangePassword;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AccountSettingsController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField pwdNew;
    @FXML private PasswordField pwdConfirm;
    @FXML private Label lblStatus;

    @FXML
    public void initialize() {    }

    @FXML
    private void handleSaveSettings() {
        String newPass = pwdNew.getText();
        String confirmPass = pwdConfirm.getText();

        if (newPass.isEmpty() || confirmPass.isEmpty()) {
            showError("Pola hasła nie mogą być puste!");
            return;
        }

        if (!newPass.equals(confirmPass)) {
            showError("Hasła nie są identyczne!");
            return;
        }

        try {
            UserChangePassword changeReq = new UserChangePassword();
            changeReq.newPassword = newPass;
            changeReq.newPasswordConfirm = confirmPass;

            ApplicationClient.getInstance().updateUserPassword(changeReq);

            lblStatus.setText("Hasło zostało pomyślnie zmienione.");
            lblStatus.setStyle("-fx-text-fill: green;");

            pwdNew.clear();
            pwdConfirm.clear();

        } catch (Exception e) {
            showError("Błąd serwera: " + e.getMessage());
        }
    }

    private void showError(String message) {
        lblStatus.setText(message);
        lblStatus.setStyle("-fx-text-fill: red;");
    }

    public void setUserInfo(User user) {
        if (user != null) {
            txtUsername.setText(user.getEmail());
            txtUsername.setEditable(false);
        }
    }
}