package com.example.edziennikui.student;

import client.ApplicationClient;
import com.example.edziennikui.shared.AlertHelper;
import entities.Attendance;
import entities.Lesson;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;

public class StudentAttendanceController {

    @FXML private TableView<Attendance> attendanceTable;
    @FXML private TableColumn<Attendance, String> colDate;
    @FXML private TableColumn<Attendance, String> colLesson;
    @FXML private TableColumn<Attendance, String> colSubject;
    @FXML private TableColumn<Attendance, String> colStatus;

    @FXML private Label lblAbsentCount;

    private final ApplicationClient client = ApplicationClient.getInstance();

    @FXML
    public void initialize() {
        colDate.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getLesson() != null ? data.getValue().getLesson().getDate() : ""));

        colLesson.setCellValueFactory(data -> {
            Attendance a = data.getValue();
            String timeInfo = "";
            if (a.getLesson() != null && a.getLesson().getSchoolClass() != null) {
                timeInfo = a.getLesson().getSchoolClass().getStartTime() + " - " +
                        a.getLesson().getSchoolClass().getEndTime();
            }
            return new SimpleStringProperty(timeInfo);
        });

        colSubject.setCellValueFactory(data -> {
            String subjectName = "Brak danych";
            Lesson l = data.getValue().getLesson();
            if (l != null && l.getSchoolClass() != null && l.getSchoolClass().getSubject() != null) {
                subjectName = l.getSchoolClass().getSubject().getName();
            }
            return new SimpleStringProperty(subjectName);
        });

        colStatus.setCellValueFactory(data -> {
            Attendance.Status s = data.getValue().getStatus();
            return new SimpleStringProperty(switch (s) {
                case PRESENT -> "Obecny";
                case ABSENT -> "Nieobecny";
                case LATE -> "Spóźniony";
                default -> "Nieokreślony";
            });
        });

        loadAttendance();
    }

    private void loadAttendance() {
        try {

            ArrayList<Attendance> list = client.getUserAttendance();

            if (list != null) {
                attendanceTable.setItems(FXCollections.observableArrayList(list));
                updateStats(list);
            }
        } catch (Exception e) {
            AlertHelper.showError("Błąd", "Nie udało się pobrać Twoich obecności: " + e.getMessage());
        }
    }

    private void updateStats(ArrayList<Attendance> list) {
        long absent = list.stream()
                .filter(a -> a.getStatus() == Attendance.Status.ABSENT)
                .count();
        lblAbsentCount.setText("Nieobecności: " + absent);
    }
}