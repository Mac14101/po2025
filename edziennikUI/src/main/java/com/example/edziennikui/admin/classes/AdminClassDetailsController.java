package com.example.edziennikui.admin.classes;

import client.ApplicationClient;
import com.example.edziennikui.shared.AlertHelper;
import com.example.edziennikui.shared.UIHelper;
import entities.SchoolGroup;
import entities.Student;
import entities.User;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.util.ArrayList;

public class AdminClassDetailsController {

    @FXML private Label lblClassName;
    @FXML private TableView<Student> studentTable;
    @FXML private TableColumn<Student, Integer> colId;
    @FXML private TableColumn<Student, String> colFirstName;
    @FXML private TableColumn<Student, String> colLastName;
    @FXML private TableColumn<Student, String> colEmail;

    private int currentClassId;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("surname"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    public void setClassData(SchoolGroup schoolGroup) {
        this.currentClassId = schoolGroup.getId();
        lblClassName.setText("Klasa: " + schoolGroup.getNumber() + " " + schoolGroup.getLetter());

        loadStudents(currentClassId);
    }

    private void loadStudents(int classId) {
        try {
            ArrayList<Student> students = ApplicationClient.getInstance().getClassStudents(classId);
            if (students != null) {
                studentTable.setItems(FXCollections.observableArrayList(students));
            }
        } catch (Exception e) {
            AlertHelper.showError("Błąd", "Nie udało się pobrać listy uczniów.");
        }
    }

    @FXML
    private void handleBack() {
        Stage stage = (Stage) lblClassName.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleAddStudentToClass() {
        UIHelper.openModal(
                "/com/example/edziennikui/admin/classes/AdminAddStudentToClass.fxml",
                "Dodaj ucznia do klasy",
                (AdminAddStudentToClassController controller) -> {
                    controller.setClassId(currentClassId);
                }
        );
        loadStudents(currentClassId);
    }

    @FXML
    private void handleRemoveStudentFromClass() {
        User selected = studentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AlertHelper.showWarning("Brak wyboru", "Wybierz ucznia, którego chcesz usunąć z klasy.");
            return;
        }
        AlertHelper.showInfo("Informacja", "Usunięcie ucznia " + selected.getSurname() + " wymaga aktualizacji serwera.");
    }
}