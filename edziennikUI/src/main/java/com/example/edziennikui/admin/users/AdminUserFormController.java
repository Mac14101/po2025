package com.example.edziennikui.admin.users;

import client.ApplicationClient;
import client.Client;
import entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import com.example.edziennikui.shared.AlertHelper;
import javafx.stage.Stage;

public class AdminUserFormController {

    @FXML private TextField txtName;
    @FXML private TextField txtSurname;
    @FXML private TextField txtEmail;
    @FXML private TextField txtPassword;
    @FXML private ComboBox<User.Role> comboRole;

    private User currentUser;
    private boolean isEditMode = false;

    @FXML
    public void initialize() {
        comboRole.getItems().setAll(User.Role.values());
    }

    public void setUserData(User user) {
        this.currentUser = user;
        this.isEditMode = true;

        txtEmail.setText(user.getEmail());
        txtName.setText(user.getName());
        txtSurname.setText(user.getSurname());
        comboRole.setValue(user.getRole());

        txtPassword.setPromptText("Wpisz nowe, aby zmienić");
    }

    @FXML
    private void handleSave() {
        try {
            if (!isEditMode) {
                currentUser = new User();
            }

            currentUser.setEmail(txtEmail.getText());
            currentUser.setName(txtName.getText());
            currentUser.setSurname(txtSurname.getText());
            currentUser.setRole(comboRole.getValue());

            if (!txtPassword.getText().isEmpty()) {
                currentUser.setPassword(txtPassword.getText());
            }

            /*
            if (isEditMode) {
                // Zmiana hasła
                if (!txtPassword.getText().isEmpty()) {
                    UserChangePassword changeReq = new UserChangePassword();
                    changeReq.uid = currentUser.getId();
                    changeReq.newPassword = txtPassword.getText();
                    changeReq.newPasswordConfirm = txtPassword.getText();

                    ApplicationClient.getInstance().updateUserPassword(changeReq);
                }
            } else {
                // Nowy użytkownik
                if (txtPassword.getText().isEmpty()) {
                    throw new IllegalArgumentException("Hasło jest wymagane dla nowego użytkownika.");
                }
                currentUser.setPassword(txtPassword.getText());
                ApplicationClient.getInstance().createUser(currentUser);
            }
             Nie zaimplementowane */
            if (isEditMode) {
                System.out.println("Aktualizacja użytkownika: " + currentUser.getId());
            } else {
                ApplicationClient.getInstance().createUser(currentUser);
            }

            AlertHelper.showInfo("Sukces", "Dane użytkownika zostały zapisane.");
            closeWindow();

        } catch (IllegalArgumentException e) {
            AlertHelper.showWarning("Błąd danych", e.getMessage());
        } catch (Client.ClientError e) {
            AlertHelper.showError("Błąd serwera", "Nie udało się skomunikować z bazą: " + e.getMessage());
        } catch (Exception e) {
            AlertHelper.showError("Błąd", "Wystąpił nieoczekiwany problem: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) txtEmail.getScene().getWindow();
        stage.close();
    }

}