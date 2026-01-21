package com.example.edziennikui.admin.classes;

import client.ApplicationClient;
import com.example.edziennikui.shared.AlertHelper;
import com.example.edziennikui.shared.UIHelper;
import entities.SchoolGroup;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.ArrayList;

public class AdminClassListController {

    @FXML private TableView<SchoolGroup> classTable;
    @FXML private TableColumn<SchoolGroup, Integer> colId;
    @FXML private TableColumn<SchoolGroup, Integer> colNumber;
    @FXML private TableColumn<SchoolGroup, String> colLetter;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        colLetter.setCellValueFactory(new PropertyValueFactory<>("letter"));

        loadClasses();
    }

    private void loadClasses() {
        try {
            ArrayList<SchoolGroup> classes = ApplicationClient.getInstance().getAllClass();

            if (classes != null) {
                classTable.setItems(FXCollections.observableArrayList(classes));
                classTable.refresh();
            }
        } catch (Exception e) {
            AlertHelper.showError("Błąd", "Nie udało się pobrać listy klas. Sprawdź konsolę.");
        }
    }

    @FXML
    private void handleShowDetails() {
        SchoolGroup selectedClass = classTable.getSelectionModel().getSelectedItem();
        if (selectedClass == null) {
            AlertHelper.showWarning("Brak wyboru", "Proszę wybrać klasę z tabeli.");
            return;
        }

        UIHelper.openModal(
                "/com/example/edziennikui/admin/classes/AdminClassDetails.fxml",
                "Szczegóły klasy: " + selectedClass.getNumber() + " " + selectedClass.getLetter(),
                (AdminClassDetailsController controller) -> controller.setClassData(selectedClass)
        );
    }

    @FXML
    private void handleAddClass() {
        UIHelper.openModal(
                "/com/example/edziennikui/admin/classes/AdminClassForm.fxml",
                "Dodaj nową klasę",
                null
        );
        loadClasses();
    }

    @FXML
    private void handleDeleteClass() {
        SchoolGroup selectedClass = classTable.getSelectionModel().getSelectedItem();

        if (selectedClass == null) {
            AlertHelper.showWarning("Brak wyboru", "Proszę wybrać klasę do usunięcia.");
            return;
        }

        AlertHelper.showInfo("Informacja", "Usuwanie klas (ID: " + selectedClass.getId() + ") zostanie dodane po aktualizacji serwera.");
    }
}