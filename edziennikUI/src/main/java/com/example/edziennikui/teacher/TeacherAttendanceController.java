package com.example.edziennikui.teacher;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TeacherAttendanceController {

    @FXML
    private ComboBox<Object> comboClass;
    @FXML
    private ComboBox<Object> comboSubject;
    @FXML
    private TableView<Object> attendanceTable;
    @FXML
    private TableColumn<Object, String> colStudentName;
    @FXML
    private TableColumn<Object, Object> colStatus;

    @FXML
    public void initialize() {
    }

    @FXML
    private void handleLoadList() {
    }

    @FXML
    private void handleSaveAttendance() {
    }
}