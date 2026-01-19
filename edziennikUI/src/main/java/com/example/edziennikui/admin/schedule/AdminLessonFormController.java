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
        // tu bedzie metoda zapisu
    }

    @FXML
    private void handleCancel() {
        ((Stage) comboClass.getScene().getWindow()).close();
    }
}