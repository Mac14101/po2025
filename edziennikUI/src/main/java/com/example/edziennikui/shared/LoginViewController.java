package com.example.edziennikui.shared;

import client.ApplicationClient;
import client.Client;
import entities.User;
import entities.UserCredentials;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.example.edziennikui.HelloController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;

public class LoginViewController {
    @FXML private TextField loginField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    @FXML
    private void handleLogin(javafx.event.ActionEvent event) {
        String email = loginField.getText();
        String password = passwordField.getText();

        try {
            UserCredentials credentials = new UserCredentials(email, password);
            ApplicationClient client = ApplicationClient.getInstance();

            client.setBaseUrl("http://localhost:8080");
            client.setRefreshUrl("/session/");

            client.authenticate(credentials);
            User loggedUser = client.getUserData();

            proceedToMainApp(loggedUser, event);

        } catch (Client.ClientError e) {
            if (e.getResponse().statusCode() == 400) {
                showError("Błędny login lub hasło.");
            } else {
                showError("Błąd serwera: " + e.getResponse().statusCode());
            }
        } catch (JsonProcessingException e) {
            showError("Błąd danych JSON.");
        }
    }


    private void proceedToMainApp(User user, javafx.event.ActionEvent event) {
        try {
            // Ładuje główny widok aplikacji
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/edziennikui/shared/hello-view.fxml"));
            Parent root = loader.load();
            HelloController mainController = loader.getController();
            mainController.initSession(user);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("e-Dziennik - " + user.getRole().toString());
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            showError("Nie udało się załadować głównego okna.");
        }
    }

    private void showError(String message) {
        if (errorLabel != null) {
            errorLabel.setText(message);
            errorLabel.setStyle("-fx-text-fill: red;");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }
    }
}