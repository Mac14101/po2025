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
    @FXML private ComboBox<String> comboLessonHour;
    @FXML private TextField txtRoom;

    @FXML
    public void initialize() {
        loadInitialData();
        setupDays();
        setupLessonHours();
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
            String selectedHourRange = comboLessonHour.getSelectionModel().getSelectedItem();
            //String room = txtRoom.getText();

            if (selectedGroup == null || selectedSubject == null || selectedUser == null ||
                    selectedDay == null || selectedHourRange == null /* || room.isEmpty()*/ ) {
                AlertHelper.showWarning("Brak danych", "Proszę uzupełnić wszystkie pola!");
                return;
            }

            String startTime = selectedHourRange.substring(3, 8);
            String endTime = selectedHourRange.substring(11, 16);

            SchoolClass scheduleEntry = new SchoolClass();
            scheduleEntry.setDay(selectedDay);
            scheduleEntry.setStartTime(startTime);
            scheduleEntry.setEndTime(endTime);
            //scheduleEntry.setRoom(room);
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

    private void setupLessonHours() {
        comboLessonHour.setItems(FXCollections.observableArrayList(
                "1 (08:00 - 08:45)",
                "2 (08:55 - 09:40)",
                "3 (09:50 - 10:35)",
                "4 (10:50 - 11:35)",
                "5 (11:45 - 12:30)",
                "6 (12:45 - 13:30)",
                "7 (13:50 - 14:35)",
                "8 (14:45 - 15:30)",
                "9 (15:35 - 16:20)"
        ));
    }

    @FXML
    private void handleCancel() {
        ((Stage) comboClass.getScene().getWindow()).close();
    }
}