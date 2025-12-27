package com.example.edziennikui.teacher;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class TeacherClassSelectController {

    @FXML private ComboBox<String> comboClass;
    @FXML private ComboBox<String> comboSubject;
    @FXML private Button btnConfirm;

    @FXML
    public void initialize() {
    }

    @FXML
    private void handleConfirmSelection() {
        String selectedClass = comboClass.getValue();
        String selectedSubject = comboSubject.getValue();

        if (selectedClass != null && selectedSubject != null) {
            System.out.println("Wybrano: " + selectedClass + " - " + selectedSubject);
        } else {
            System.err.println("Błąd: Nie wybrano klasy lub przedmiotu!");
        }
    }
}