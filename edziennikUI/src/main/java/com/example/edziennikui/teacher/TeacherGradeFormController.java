package com.example.edziennikui.teacher;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class TeacherGradeFormController {

    @FXML private Label lblStudentName;
    @FXML private ComboBox<String> comboGradeValue;
    @FXML private TextField txtWeight;
    @FXML private TextField txtCategory;
    @FXML private TextArea txtComment;

    @FXML
    public void initialize() {
        comboGradeValue.getItems().addAll("1", "2", "3", "4", "5", "6");
    }

    // Metoda z poprzedniego widoku, żeby ustawić dane ucznia
    public void setStudentData(String studentName) {
        lblStudentName.setText("Uczeń: " + studentName);
    }

    @FXML
    private void handleSave() {
        String grade = comboGradeValue.getValue();
        String weight = txtWeight.getText();

        if (grade == null) {
            System.err.println("Musisz wybrać ocenę!");
            return;
        }
    }

    @FXML
    private void handleCancel() {
    }
}