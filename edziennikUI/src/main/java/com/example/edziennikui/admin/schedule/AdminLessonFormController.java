package com.example.edziennikui.admin.schedule;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class AdminLessonFormController {

    @FXML private ComboBox<Object> comboClass;
    @FXML private ComboBox<Object> comboSubject;
    @FXML private ComboBox<Object> comboTeacher;
    @FXML private ComboBox<String> comboDay;
    @FXML private TextField txtLessonHour;
    @FXML private TextField txtRoom;

    @FXML
    public void initialize() {
        // Inicjalizacja dni tygodnia
        comboDay.getItems().addAll("Poniedziałek", "Wtorek", "Środa", "Czwartek", "Piątek");
    }

    @FXML
    private void handleSave() {
    }

    @FXML
    private void handleCancel() {
    }
}