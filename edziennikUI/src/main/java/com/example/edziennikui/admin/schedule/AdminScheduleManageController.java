package com.example.edziennikui.admin.schedule;

import client.ApplicationClient;
import com.example.edziennikui.shared.AlertHelper;
import entities.SchoolClass;
import entities.SchoolGroup;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
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

        comboFilterClass.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                loadScheduleForClass(newVal.getId());
            }
        });
    }

    private void setupTableColumns() {
        colDay.setCellValueFactory(new PropertyValueFactory<>("day"));
        colTime.setCellValueFactory(cellData -> {
            String start = cellData.getValue().getStartTime();
            String end = cellData.getValue().getEndTime();
            return new SimpleStringProperty(start + " - " + end);
        });
        colRoom.setCellValueFactory(new PropertyValueFactory<>("room"));

        colSubject.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getSubject() != null ?
                        cellData.getValue().getSubject().getName() : "Brak danych"));

        colTeacher.setCellValueFactory(cellData -> {
            if (cellData.getValue().getTeacher() != null) {
                String fullName = cellData.getValue().getTeacher().getName() + " " +
                        cellData.getValue().getTeacher().getSurname();
                return new SimpleStringProperty(fullName);
            }
            return new SimpleStringProperty("Brak nauczyciela");
        });
    }

    private void loadInitialData() {
        try {
            ArrayList<SchoolGroup> classes = ApplicationClient.getInstance().getAllClass();
            comboFilterClass.setItems(FXCollections.observableArrayList(classes));

            comboFilterClass.setConverter(new javafx.util.StringConverter<>() {
                @Override public String toString(SchoolGroup sg) {
                    return sg == null ? "" : sg.getNumber() + " " + sg.getLetter();
                }
                @Override public SchoolGroup fromString(String s) { return null; }
            });
        } catch (Exception e) {
            AlertHelper.showError("Błąd", "Nie udało się załadować klas.");
        }
    }

    private void loadScheduleForClass(int classId) {
        try {
            ArrayList<SchoolClass> schedule = ApplicationClient.getInstance().getClassSchedule(classId);

            scheduleTable.setItems(FXCollections.observableArrayList(schedule));
            scheduleTable.refresh();

            System.out.println("Załadowano " + schedule.size() + " lekcji dla klasy ID: " + classId);
        } catch (Exception e) {
            AlertHelper.showError("Błąd", "Nie udało się załadować planu zajęć.");
        }
    }

    @FXML
    private void handleAddLesson() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/edziennikui/admin/schedule/AdminLessonForm.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Dodaj lekcję do planu");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            if (comboFilterClass.getValue() != null) {
                loadScheduleForClass(comboFilterClass.getValue().getId());
            }
        } catch (IOException e) {
            e.printStackTrace();
            AlertHelper.showError("Błąd", "Nie udało się otworzyć formularza.");
        }
    }

    @FXML private void handleEditLesson() { }
    @FXML private void handleDeleteLesson() { }
}