package com.example.edziennikui.admin.users;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AdminUserFormController {

    @FXML
    private Label lblTitle;
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private ComboBox<String> comboRole;

    @FXML
    public void initialize() {
        // Inicjalizacja ról
        comboRole.getItems().addAll("Admin", "Nauczyciel", "Uczeń");
    }

        @FXML
        private void handleSave() {
            String role = comboRole.getValue();
            System.out.println("Próba zapisu użytkownika: " + txtEmail.getText() + " z rolą: " + role);

            if (txtEmail.getText().isEmpty() || role == null) {
                System.err.println("Błąd: Email i Rola są wymagane!");
            }
        }

        @FXML
        private void handleCancel() {
        }
}