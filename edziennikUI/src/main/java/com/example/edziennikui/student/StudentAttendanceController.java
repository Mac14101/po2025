package com.example.edziennikui.student;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class StudentAttendanceController {

    @FXML private TableView<Object> attendanceTable;
    @FXML private TableColumn<Object, String> colDate;
    @FXML private TableColumn<Object, Integer> colLesson;
    @FXML private TableColumn<Object, String> colSubject;
    @FXML private TableColumn<Object, String> colStatus;

    @FXML private Label lblPresentCount;
    @FXML private Label lblAbsentCount;

    @FXML
    public void initialize() {
    }
}