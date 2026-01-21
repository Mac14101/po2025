package com.example.edziennikui.teacher;

import client.ApplicationClient;
import com.example.edziennikui.shared.AlertHelper;
import com.example.edziennikui.shared.UIHelper;
import entities.SchoolGroup;
import entities.Subject;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;

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

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/edziennikui/teacher/TeacherGrades.fxml"));
            Parent root = loader.load();

            TeacherGradesController controller = loader.getController();
            controller.setContext(selectedGroup, selectedSubject);

            Stage stage = new Stage();
            stage.setTitle("Oceny - " + selectedGroup.getNumber() + selectedGroup.getLetter());
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (IOException e) {
            AlertHelper.showError("Błąd", "Nie udało się otworzyć okna ocen.");
        }
    }
}