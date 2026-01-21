package com.example.edziennikui.teacher;

import client.ApplicationClient;
import com.example.edziennikui.shared.AlertHelper;
import entities.Grade;
import entities.Student;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class TeacherGradeFormController {

    @FXML private Label lblStudentName;
    @FXML private ComboBox<Integer> comboGradeValue;
    @FXML private TextField txtCategory;
    @FXML private TextArea txtComment;

    private Student currentStudent;
    private entities.Subject currentSubject;

    @FXML
    public void initialize() {
        comboGradeValue.getItems().addAll(1, 2, 3, 4, 5, 6);
    }

    public void setGradeData(Student student, entities.Subject subject) {
        this.currentStudent = student;
        this.currentSubject = subject;
        lblStudentName.setText("Uczeń: " + student.getName() + " " + student.getSurname() +
                " (" + subject.getName() + ")");
    }

    @FXML
    private void handleSave() {
        if (comboGradeValue.getValue() == null || txtCategory.getText().trim().isEmpty()) {
            AlertHelper.showWarning("Błąd", "Wybierz ocenę i wpisz tytuł oceny!");
            return;
        }

        try {
            Grade newGrade = new Grade();
            String selectedValue = comboGradeValue.getValue().toString();
            newGrade.setGrade(Grade.GradeName.fromGrade(selectedValue));
            newGrade.setTitle(txtCategory.getText().trim());
            if (txtComment != null) {
                newGrade.setComment(txtComment.getText());
            }

            newGrade.setStudent(currentStudent);
            newGrade.setSubject(currentSubject);

            ApplicationClient.getInstance().addGrade(currentStudent.getId(), newGrade);

            AlertHelper.showInfo("Sukces", "Dodano ocenę: " + selectedValue);
            handleCancel();
        } catch (Exception e) {
            AlertHelper.showError("Błąd", "Nie udało się zapisać: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancel() {
        ((Stage) lblStudentName.getScene().getWindow()).close();
    }
}