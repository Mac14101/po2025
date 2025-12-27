package com.example.edziennikui.shared;

import com.example.edziennikui.HelloApplication;
import com.example.edziennikui.HelloController;
import javafx.event.ActionEvent;
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
    private void handleLogin(ActionEvent event) throws IOException {
        String login = loginField.getText();
        if (login.equals("admin") || login.equals("nauczyciel") || login.equals("uczeń")) {
            // Ładowanie pliku hello-view.fxml
            String fxmlPath = "/com/example/edziennikui/shared/hello-view.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Kontroler głównego okna
            HelloController mainController = loader.getController();

            // Przekazanie roli
            mainController.setRole(login.toLowerCase());

            // Wyświetlenie nowego okna
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } else {
            errorLabel.setText("Nieprawidłowy login lub hasło!");
            loginField.clear();
            passwordField.clear();
        }
    }

    private void switchToMain(ActionEvent event, String role) throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("shared/hello-view.fxml"));
        Scene scene = new Scene(loader.load());

        // Przekazujemy rolę do głównego kontrolera
        HelloController mainController = loader.getController();
        mainController.setRole(role);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}