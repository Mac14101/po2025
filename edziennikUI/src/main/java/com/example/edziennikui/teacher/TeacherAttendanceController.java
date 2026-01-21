package com.example.edziennikui.teacher;

import client.ApplicationClient;
import com.example.edziennikui.shared.AlertHelper;
import com.example.edziennikui.shared.UIHelper;
import entities.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.ArrayList;

public class TeacherAttendanceController {

    @FXML private ComboBox<SchoolGroup> comboClass;
    @FXML private ComboBox<Subject> comboSubject;
    @FXML private TextField txtTopic;
    @FXML private TableView<Attendance> attendanceTable;
    @FXML private TableColumn<Attendance, String> colStudentName;
    @FXML private TableColumn<Attendance, String> colStatus;

    private int currentLessonId;

    @FXML
    public void initialize() {
        setupTable();
        loadInitialData();
    }

    private void setupTable() {
        colStudentName.setCellValueFactory(cellData -> {
            Student s = cellData.getValue().getStudent();
            return new SimpleStringProperty(s.getName() + " " + s.getSurname());
        });
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void loadInitialData() {
        try {
            comboClass.setItems(FXCollections.observableArrayList(ApplicationClient.getInstance().getAllClass()));
            UIHelper.setupClassComboBox(comboClass);

            comboSubject.setItems(FXCollections.observableArrayList(ApplicationClient.getInstance().getAllSubjects()));
            UIHelper.setupSubjectComboBox(comboSubject);
        } catch (Exception e) {
            AlertHelper.showError("Błąd", "Nie udało się załadować list danych.");
        }
    }

    @FXML
    private void handleLoadList() {
        try {
            SchoolGroup selectedGroup = comboClass.getSelectionModel().getSelectedItem();
            Subject selectedSubject = comboSubject.getSelectionModel().getSelectedItem();
            String topic = txtTopic.getText();

            if (selectedGroup == null || selectedSubject == null || topic.isEmpty()) {
                AlertHelper.showWarning("Brak danych", "Wybierz klasę, przedmiot i wpisz temat.");
                return;
            }

            Lesson lesson = new Lesson();
            lesson.setTopic(topic);
            lesson.setDate(java.time.LocalDate.now().toString());

            SchoolClass sc = new SchoolClass();
            sc.setSchoolGroup(selectedGroup);
            sc.setSubject(selectedSubject);
            lesson.setSchoolClass(sc);

            ApplicationClient.getInstance().addLesson(lesson);

            ArrayList<Lesson> lessons = ApplicationClient.getInstance().getLessons();
            if (!lessons.isEmpty()) {
                currentLessonId = lessons.get(lessons.size() - 1).getId();

                ArrayList<Attendance> list = ApplicationClient.getInstance().getAttendanceList(currentLessonId);
                attendanceTable.setItems(FXCollections.observableArrayList(list));
            }
        } catch (Exception e) {
            AlertHelper.showError("Błąd", "Nie udało się rozpocząć lekcji.");
        }
    }

    @FXML
    private void handleSaveAttendance() {
        try {
            for (Attendance att : attendanceTable.getItems()) {
                ApplicationClient.getInstance().updateAttendance(currentLessonId, att);
            }
            AlertHelper.showInfo("Sukces", "Obecność została zapisana pomyślnie.");
        } catch (Exception e) {
            AlertHelper.showError("Błąd", "Wystąpił problem podczas zapisywania obecności.");
        }
    }
}