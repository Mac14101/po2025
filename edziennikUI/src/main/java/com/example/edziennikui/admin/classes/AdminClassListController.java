package com.example.edziennikui.admin.classes;

import client.ApplicationClient;
import com.example.edziennikui.shared.AlertHelper;
import entities.SchoolGroup;
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

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/edziennikui/admin/classes/AdminClassDetails.fxml"));
            Parent root = loader.load();

            AdminClassDetailsController controller = loader.getController();
            controller.setClassData(selectedClass);

            Stage stage = new Stage();
            stage.setTitle("Szczegóły klasy: " + selectedClass.getNumber() + " " + selectedClass.getLetter());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            AlertHelper.showError("Błąd", "Nie udało się otworzyć okna szczegółów.");
        }
    }

    @FXML
    private void handleAddClass() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/edziennikui/admin/classes/AdminClassForm.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Dodaj nową klasę");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));

            stage.showAndWait();
            loadClasses();
        } catch (IOException e) {
            AlertHelper.showError("Błąd", "Nie udało się otworzyć formularza dodawania.");
        }
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