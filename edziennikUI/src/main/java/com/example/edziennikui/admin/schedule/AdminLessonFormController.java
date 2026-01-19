package com.example.edziennikui.admin.schedule;

import client.ApplicationClient;
import com.example.edziennikui.shared.AlertHelper;
import entities.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class AdminLessonFormController {

    @FXML private ComboBox<SchoolGroup> comboClass;
    @FXML private ComboBox<Subject> comboSubject;
    @FXML private ComboBox<User> comboTeacher;
    @FXML private ComboBox<String> comboDay;
    @FXML private TextField txtLessonHour;
    @FXML private TextField txtRoom;

    @FXML
    public void initialize() {
        loadInitialData();
        setupDays();
        setupConverters();
    }

    private void loadInitialData() {
        try {
            comboClass.setItems(FXCollections.observableArrayList(ApplicationClient.getInstance().getAllClass()));

            comboSubject.setItems(FXCollections.observableArrayList(ApplicationClient.getInstance().getAllSubjects()));

            ArrayList<User> allUsers = ApplicationClient.getInstance().getAllUsers();
            ArrayList<User> teachers = allUsers.stream()
                    .filter(u -> u.getRole() != null && "teacher".equalsIgnoreCase(u.getRole().toString()))
                    .collect(Collectors.toCollection(ArrayList::new));
            comboTeacher.setItems(FXCollections.observableArrayList(teachers));

        } catch (Exception e) {
            AlertHelper.showError("Błąd", "Nie udało się pobrać list danych: " + e.getMessage());
        }
    }

    private void setupDays() {
        comboDay.setItems(FXCollections.observableArrayList(
                "Poniedziałek", "Wtorek", "Środa", "Czwartek", "Piątek"
        ));
    }

    private void setupConverters() {
        comboSubject.setConverter(new StringConverter<>() {
            @Override public String toString(Subject s) { return s == null ? "" : s.getName(); }
            @Override public Subject fromString(String s) { return null; }
        });

        comboTeacher.setConverter(new StringConverter<>() {
            @Override public String toString(User u) { return u == null ? "" : u.getName() + " " + u.getSurname(); }
            @Override public User fromString(String s) { return null; }
        });

        comboClass.setConverter(new StringConverter<>() {
            @Override public String toString(SchoolGroup g) { return g == null ? "" : g.getNumber() + " " + g.getLetter(); }
            @Override public SchoolGroup fromString(String s) { return null; }
        });
    }


    @FXML
    private void handleSave() {
        try {
            SchoolGroup selectedGroup = comboClass.getSelectionModel().getSelectedItem();
            Subject selectedSubject = comboSubject.getSelectionModel().getSelectedItem();
            User selectedUser = comboTeacher.getSelectionModel().getSelectedItem();
            String selectedDay = comboDay.getSelectionModel().getSelectedItem();
            String startHour = txtLessonHour.getText().trim();

            if (selectedGroup == null || selectedSubject == null || selectedUser == null ||
                    selectedDay == null || startHour.isEmpty()) {
                AlertHelper.showWarning("Brak danych", "Proszę uzupełnić wszystkie pola!");
                return;
            }

            String formattedStartTime = formatToSqlTime(startHour);
            String formattedEndTime = calculateEndTime(formattedStartTime);

            SchoolClass scheduleEntry = new SchoolClass();
            scheduleEntry.setDay(selectedDay);
            scheduleEntry.setStartTime(formattedStartTime);
            scheduleEntry.setEndTime(formattedEndTime);
            scheduleEntry.setSchoolGroup(selectedGroup);
            scheduleEntry.setSubject(selectedSubject);

            Teacher teacher = new Teacher(
                    selectedUser.getId(),
                    selectedUser.getEmail(),
                    selectedUser.getName(),
                    selectedUser.getSurname(),
                    null
            );
            scheduleEntry.setTeacher(teacher);

            ApplicationClient.getInstance().addClassSchedule(scheduleEntry);

            AlertHelper.showInfo("Sukces", "Dodano lekcję do planu zajęć.");
            handleCancel();
        } catch (Exception e) {
            AlertHelper.showError("Błąd", "Nie udało się zapisać planu: " + e.getMessage());
        }
    }

    // Pomocnicza metoda do formatowania czasu
    private String formatToSqlTime(String time) {
        if (time.split(":").length == 2) return time + ":00"; // HH:MM -> HH:MM:00
        return time;
    }

    // Pomocnicza metoda wyliczająca koniec lekcji (standardowe 45 min)
    private String calculateEndTime(String startTime) {
        try {
            String[] parts = startTime.split(":");
            int hour = Integer.parseInt(parts[0]);
            int min = Integer.parseInt(parts[1]);

            min += 45;
            if (min >= 60) {
                hour++;
                min -= 60;
            }
            return String.format("%02d:%02d:00", hour, min);
        } catch (Exception e) {
            return startTime;
        }
    }


    @FXML
    private void handleCancel() {
        ((Stage) comboClass.getScene().getWindow()).close();
    }
}