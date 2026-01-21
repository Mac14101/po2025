package com.example.edziennikui.teacher;

import client.ApplicationClient;
import com.example.edziennikui.shared.AlertHelper;
import com.example.edziennikui.shared.UIHelper;
import entities.SchoolGroup;
import entities.Subject;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class TeacherClassSelectController {

    @FXML private ComboBox<SchoolGroup> comboClass;
    @FXML private ComboBox<Subject> comboSubject;

    @FXML
    public void initialize() {
        UIHelper.setupClassComboBox(comboClass);
        UIHelper.setupSubjectComboBox(comboSubject);
        loadData();
    }

    private void loadData() {
        try {
            comboClass.setItems(FXCollections.observableArrayList(ApplicationClient.getInstance().getAllClass()));
            comboSubject.setItems(FXCollections.observableArrayList(ApplicationClient.getInstance().getAllSubjects()));
        } catch (Exception e) {
            AlertHelper.showError("Błąd połączenia", "Nie udało się załadować listy klas lub przedmiotów. \n" + "Szczegóły: " + e.getMessage());
        }
    }

    @FXML
    private void handleConfirmSelection() {
        SchoolGroup selectedGroup = comboClass.getValue();
        Subject selectedSubject = comboSubject.getValue();

        if (selectedGroup == null || selectedSubject == null) {
            AlertHelper.showWarning("Brak wyboru", "Proszę wybrać klasę i przedmiot.");
            return;
        }

        UIHelper.openModal(
                "/com/example/edziennikui/teacher/TeacherGrades.fxml",
                "Oceny - " + selectedGroup.getNumber() + selectedGroup.getLetter(),
                (TeacherGradesController controller) -> {
                    controller.setContext(selectedGroup, selectedSubject);
                }
        );
    }
}