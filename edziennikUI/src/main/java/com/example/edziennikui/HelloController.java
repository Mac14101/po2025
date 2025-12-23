package com.example.edziennikui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import java.io.IOException;

public class HelloController {

    @FXML
    private StackPane contentArea;

    // Metoda dla przycisku Pulpit
    @FXML
    private void handleShowDashboard(ActionEvent event) {
        System.out.println("Kliknięto: Pulpit");
    }

    // Metoda dla przycisku Użytkownicy
    @FXML
    private void handleShowUsers(ActionEvent event) {
        loadPage("admin/AdminUsers.fxml");
    }

    // Metoda dla przycisku Oceny
    @FXML
    private void handleShowGrades(ActionEvent event) {
        System.out.println("Kliknięto: Oceny");
    }

    // Metoda dla przycisku Wyloguj
    @FXML
    private void handleLogout(ActionEvent event) {
        System.exit(0);
    }

    private void loadPage(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Node view = loader.load();
            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            System.err.println("Błąd ładowania: " + fxmlPath);
            e.printStackTrace();
        }
    }
}
