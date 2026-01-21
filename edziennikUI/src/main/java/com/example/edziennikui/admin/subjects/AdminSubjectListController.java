package com.example.edziennikui.admin.subjects;

import client.ApplicationClient;
import com.example.edziennikui.shared.AlertHelper;
import com.example.edziennikui.shared.UIHelper;
import entities.Subject;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
        UIHelper.openModal(
                "/com/example/edziennikui/admin/subjects/AdminSubjectForm.fxml",
                "Dodaj przedmiot",
                null
        );
        loadSubjects();
    }

    @FXML
    private void handleEditSubject() {
        Subject selected = subjectTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AlertHelper.showWarning("Brak wyboru", "Wybierz przedmiot do edycji.");
            return;
        }
        UIHelper.openModal(
                "/com/example/edziennikui/admin/subjects/AdminSubjectForm.fxml",
                "Edytuj przedmiot",
                (AdminSubjectFormController controller) -> controller.setSubjectData(selected)
        );
        loadSubjects();
    }

    @FXML
    private void handleDeleteSubject() {
        AlertHelper.showInfo("Informacja", "Funkcja usuwania będzie dostępna po aktualizacji serwera.");
    }
}