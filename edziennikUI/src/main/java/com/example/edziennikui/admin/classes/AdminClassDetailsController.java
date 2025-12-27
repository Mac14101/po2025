package com.example.edziennikui.admin.classes;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AdminClassDetailsController {

    @FXML private Label lblClassName;
    @FXML private Label lblTutor;

    @FXML
    public void initialize() {
    }

    @FXML
    private void handleBack() {
        // Powrót do widoku listy klas
    }

    @FXML
    private void handleAddStudentToClass() {
        // Logika dodawania
    }

    @FXML
    private void handleRemoveStudentFromClass() {
        // Logika usuwania
    }
}