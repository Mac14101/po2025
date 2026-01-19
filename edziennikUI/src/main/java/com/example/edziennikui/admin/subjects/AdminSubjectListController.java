package com.example.edziennikui.admin.subjects;

import client.ApplicationClient;
import com.example.edziennikui.shared.AlertHelper;
import entities.Subject;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;

public class AdminSubjectListController {

    @FXML private TableView<Subject> subjectTable;
    @FXML private TableColumn<Subject, Integer> colId;
    @FXML private TableColumn<Subject, String> colName;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));

        loadSubjects();
    }

    private void loadSubjects() {
        try {
            ArrayList<Subject> subjects = ApplicationClient.getInstance().getAllSubjects();
            subjectTable.setItems(FXCollections.observableArrayList(subjects));
            subjectTable.refresh();
        } catch (Exception e) {
            AlertHelper.showError("Błąd", "Nie udało się pobrać listy przedmiotów.");
        }
    }

    @FXML
    private void handleAddSubject() {
        openSubjectForm(null);
    }

    @FXML
    private void handleEditSubject() {
        Subject selected = subjectTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AlertHelper.showWarning("Brak wyboru", "Wybierz przedmiot do edycji.");
            return;
        }
        openSubjectForm(selected);
    }

    private void openSubjectForm(Subject subject) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/edziennikui/admin/subjects/AdminSubjectForm.fxml"));
            Parent root = loader.load();

            AdminSubjectFormController controller = loader.getController();
            if (subject != null) {
                controller.setSubjectData(subject);
            }

            Stage stage = new Stage();
            stage.setTitle(subject == null ? "Dodaj przedmiot" : "Edytuj przedmiot");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            loadSubjects();
        } catch (IOException e) {
            AlertHelper.showError("Błąd", "Nie udało się otworzyć okna formularza.");
        }
    }

    @FXML
    private void handleDeleteSubject() {
        AlertHelper.showInfo("Informacja", "Funkcja usuwania będzie dostępna po aktualizacji serwera.");
    }
}