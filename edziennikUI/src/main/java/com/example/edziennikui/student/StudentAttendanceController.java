package com.example.edziennikui.student;

import client.ApplicationClient;
import com.example.edziennikui.shared.AlertHelper;
import entities.Attendance;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class StudentAttendanceController {

    @FXML private TableView<Attendance> attendanceTable;
    @FXML private TableColumn<Attendance, String> colDate;
    @FXML private TableColumn<Attendance, Integer> colLesson;
    @FXML private TableColumn<Attendance, String> colSubject;
    @FXML private TableColumn<Attendance, String> colStatus;

    @FXML private Label lblPresentCount;
    @FXML private Label lblAbsentCount;

    @FXML
    public void initialize() {
        // Mapujemy kolumny na pola z klasy Attendance
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colLesson.setCellValueFactory(new PropertyValueFactory<>("lid"));
        colSubject.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        loadAttendance();
    }

    private void loadAttendance() {
    }

    private void updateStats(ArrayList<Attendance> list) {

    }
}