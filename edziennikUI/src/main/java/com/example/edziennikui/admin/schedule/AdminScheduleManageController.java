package com.example.edziennikui.admin.schedule;

import client.ApplicationClient;
import com.example.edziennikui.shared.AlertHelper;
import com.example.edziennikui.shared.UIHelper;
import entities.SchoolClass;
import entities.SchoolGroup;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.util.ArrayList;

public class AdminScheduleManageController {

    @FXML private TableView<SchoolClass> scheduleTable;
    @FXML private TableColumn<SchoolClass, String> colDay;
    @FXML private TableColumn<SchoolClass, String> colTime;
    @FXML private TableColumn<SchoolClass, String> colSubject;
    @FXML private TableColumn<SchoolClass, String> colTeacher;
    @FXML private TableColumn<SchoolClass, String> colRoom;
    @FXML private ComboBox<SchoolGroup> comboFilterClass;

    @FXML
    public void initialize() {
        setupTableColumns();
        loadInitialData();
        UIHelper.setupClassComboBox(comboFilterClass);

        comboFilterClass.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                loadScheduleForClass(newVal.getId());
            }
        });
    }

    private void setupTableColumns() {
        colDay.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDay()));

        colTime.setCellValueFactory(cellData -> {
            String start = cellData.getValue().getStartTime();
            String end = cellData.getValue().getEndTime();
            return new SimpleStringProperty((start != null ? start : "??:??") + " - " + (end != null ? end : "??:??"));
        });

        colSubject.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getSubject() != null ?
                        cellData.getValue().getSubject().getName() : "Brak przedmiotu"));

        colTeacher.setCellValueFactory(cellData -> {
            if (cellData.getValue().getTeacher() != null) {
                String fullName = cellData.getValue().getTeacher().getName() + " " +
                        cellData.getValue().getTeacher().getSurname();
                return new SimpleStringProperty(fullName);
            }
            return new SimpleStringProperty("Brak przypisanego nauczyciela");
        });
        colRoom.setCellValueFactory(cellData -> {
            Integer room = cellData.getValue().getRoom();
            return new SimpleStringProperty(room != null ? String.valueOf(room) : "-");
        });
    }

    private void loadInitialData() {
        try {
            ArrayList<SchoolGroup> classes = ApplicationClient.getInstance().getAllClass();
            comboFilterClass.setItems(FXCollections.observableArrayList(classes));
        } catch (Exception e) {
            AlertHelper.showError("Błąd", "Nie udało się załadować listy klas.");
        }
    }

    private void loadScheduleForClass(int classId) {
        try {
            ArrayList<SchoolClass> schedule = ApplicationClient.getInstance().getClassSchedule(classId);
            scheduleTable.setItems(FXCollections.observableArrayList(schedule));
        } catch (Exception e) {
            AlertHelper.showError("Błąd", "Nie udało się pobrać planu zajęć dla wybranej klasy.");
        }
    }

    @FXML
    private void handleAddLesson() {
        UIHelper.openModal(
                "/com/example/edziennikui/admin/schedule/AdminLessonForm.fxml",
                "Dodaj lekcję do planu",
                null
        );
        refreshTable();
    }

    @FXML private void handleEditLesson() {
        SchoolClass selected = scheduleTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AlertHelper.showWarning("Brak wyboru", "Wybierz lekcję do edycji.");
            return;
        }

        UIHelper.openModal(
                "/com/example/edziennikui/admin/schedule/AdminLessonForm.fxml",
                "Edytuj lekcję",
                (AdminLessonFormController controller) -> {
                    // Tutaj przekaże dane do formularza, gdy powstanie
                }
        );
        refreshTable();
    }
    @FXML private void handleDeleteLesson() {
        SchoolClass selected = scheduleTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AlertHelper.showWarning("Brak wyboru", "Wybierz lekcję do usunięcia.");
            return;
        }
        // Logika usuwania jak powstanie
        refreshTable();
    }
    private void refreshTable() {
        if (comboFilterClass.getValue() != null) {
            loadScheduleForClass(comboFilterClass.getValue().getId());
        }
    }
}