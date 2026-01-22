package com.example.edziennikui.admin.classes;

import client.ApplicationClient;
import com.example.edziennikui.shared.AlertHelper;
import entities.SchoolGroup;
import entities.Student;
import entities.User;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;

public class AdminAddStudentToClassController {

    @FXML private TableView<User> availableStudentsTable;
    @FXML private TableColumn<User, String> colFirstName;
    @FXML private TableColumn<User, String> colLastName;
    @FXML private TableColumn<User, String> colEmail;

    private int classId;
    private boolean studentAdded = false;

    @FXML
    public void initialize() {
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("surname"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        loadAvailableStudents();
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    private void loadAvailableStudents() {
        try {
            ArrayList<User> allUsers = ApplicationClient.getInstance().getAllUsers();
            ArrayList<User> studentsOnly = new ArrayList<>();

            for (User u : allUsers) {
                if (u.getRole() != null && "student".equalsIgnoreCase(u.getRole().toString())) {
                    studentsOnly.add(u);
                }
            }

            availableStudentsTable.setItems(FXCollections.observableArrayList(studentsOnly));
        } catch (Exception e) {
            AlertHelper.showError("Błąd", "Nie udało się pobrać listy użytkowników.");
        }
    }

    @FXML
    private void handleAdd() {
        User selectedUser = availableStudentsTable.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            AlertHelper.showWarning("Brak wyboru", "Wybierz użytkownika z listy.");
            return;
        }

        try {
            SchoolGroup targetGroup = new SchoolGroup();
            targetGroup.setId(this.classId);
            targetGroup.setNumber(0);
            targetGroup.setLetter('A');

            Student studentData = new Student(selectedUser, targetGroup);

            ApplicationClient.getInstance().addStudent(studentData);

            this.studentAdded = true;
            AlertHelper.showInfo("Sukces", "Uczeń został przypisany do klasy.");
            handleCancel();

        } catch (Exception e) {
            AlertHelper.showError("Błąd", "Nie udało się przypisać ucznia: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancel() {
        ((Stage) availableStudentsTable.getScene().getWindow()).close();
    }
}