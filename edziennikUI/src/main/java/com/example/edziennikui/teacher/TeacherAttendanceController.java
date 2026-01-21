package com.example.edziennikui.teacher;

import client.ApplicationClient;
import com.example.edziennikui.shared.UIHelper;
import com.example.edziennikui.shared.AlertHelper;
import entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.util.ArrayList;

public class TeacherAttendanceController {

    @FXML private TabPane mainTabPane;
    @FXML private Tab tabAttendance;

    @FXML private ComboBox<SchoolGroup> comboClass;
    @FXML private ComboBox<Subject> comboSubject;
    @FXML private TextField txtTopic;
    @FXML private Label lblLessonStatus;

    @FXML private TableView<Attendance> attendanceTable;
    @FXML private TableColumn<Attendance, String> colStudentName;
    @FXML private TableColumn<Attendance, String> colStatus;
    @FXML private Label lblActiveLessonInfo;

    private final ApplicationClient client = ApplicationClient.getInstance();
    private int currentLessonId = -1;

    @FXML
    public void initialize() {
        UIHelper.setupClassComboBox(comboClass);
        UIHelper.setupSubjectComboBox(comboSubject);

        try {
            comboClass.setItems(FXCollections.observableArrayList(client.getAllClass()));
            comboSubject.setItems(FXCollections.observableArrayList(client.getAllSubjects()));
        } catch (Exception e) {
            AlertHelper.showError("Błąd", "Nie udało się pobrać danych startowych: " + e.getMessage());
        }

        mainTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab == tabAttendance) {
                RefreshStudentList();
            }
        });

        setupTableColumns();
    }

    private void setupTableColumns() {
        colStudentName.setCellValueFactory(data -> {
            User studentUser = data.getValue().getStudent();
            return new SimpleStringProperty(studentUser.getName() + " " + studentUser.getSurname());
        });

        colStatus.setCellFactory(column -> new TableCell<>() {
            private final ComboBox<String> statusCombo = new ComboBox<>(
                    FXCollections.observableArrayList("Obecny", "Nieobecny", "Spóźniony", "Usprawiedliwiony")
            );

            {
                statusCombo.setMaxWidth(Double.MAX_VALUE);
                statusCombo.setOnAction(e -> {
                    if (getTableRow().getItem() != null) {
                        Attendance att = getTableRow().getItem();
                        String selected = statusCombo.getValue();
                        switch (selected) {
                            case "Obecny" -> att.setStatus(Attendance.Status.PRESENT);
                            case "Nieobecny" -> att.setStatus(Attendance.Status.ABSENT);
                            case "Spóźniony" -> att.setStatus(Attendance.Status.LATE);
                            default -> att.setStatus(Attendance.Status.UNDEFINED);
                        }
                    }
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    statusCombo.setValue(getTableRow().getItem().getStatus().toString());
                    setGraphic(statusCombo);
                }
            }
        });
    }

    @FXML
    private void handleCreateLesson() {
        try {
            SchoolGroup selectedGroup = comboClass.getValue();
            Subject selectedSubject = comboSubject.getValue();
            String topic = txtTopic.getText();

            if (selectedGroup == null || selectedSubject == null || topic.isEmpty()) {
                AlertHelper.showError("Błąd", "Wypełnij wszystkie pola lekcji!");
                return;
            }

            Lesson lesson = new Lesson();
            lesson.setTopic(topic);
            lesson.setDate(LocalDate.now().toString());

            client.addLesson(lesson);

            ArrayList<Lesson> lessons = client.getLessons();
            if (!lessons.isEmpty()) {
                currentLessonId = lessons.getLast().getId();
            }

            tabAttendance.setDisable(false);
            mainTabPane.getSelectionModel().select(tabAttendance);
            lblActiveLessonInfo.setText("Klasa: " + selectedGroup.getNumber() + selectedGroup.getLetter() + " | Temat: " + topic);

            loadAttendanceList();

        } catch (Exception e) {
            AlertHelper.showError("Błąd", "Nie udało się utworzyć lekcji: " + e.getMessage());
        }
    }

    private void loadAttendanceList() {
        try {
            if (currentLessonId != -1) {
                ArrayList<Attendance> list = client.getAttendanceList(currentLessonId);
                attendanceTable.setItems(FXCollections.observableArrayList(list));
            }
        } catch (Exception e) {
            AlertHelper.showError("Błąd", "Nie można załadować listy obecności.");
        }
    }

    @FXML
    private void handleSaveAttendance() {
        try {
            for (Attendance att : attendanceTable.getItems()) {
                client.updateAttendance(currentLessonId, att);
            }
            AlertHelper.showInfo("Sukces", "Obecność została zapisana w systemie.");

            tabAttendance.setDisable(true);
            mainTabPane.getSelectionModel().select(0);
            txtTopic.clear();
        } catch (Exception e) {
            AlertHelper.showError("Błąd", "Nie udało się zapisać zmian.");
        }
    }

    private void RefreshStudentList() {
        try {
            if (currentLessonId != -1) {
                loadAttendanceList();
            } else {
                SchoolGroup selected = comboClass.getValue();
                if (selected != null) {
                    ArrayList<Student> students = client.getClassStudents(selected.getId());
                    ObservableList<Attendance> preview = FXCollections.observableArrayList();
                    for (Student s : students) {
                        Attendance a = new Attendance();
                        a.setStudent(s);
                        a.setStatus(Attendance.Status.UNDEFINED);
                        preview.add(a);
                    }
                    attendanceTable.setItems(preview);
                }
            }
        } catch (Exception e) {
            AlertHelper.showError("Błąd", "Nie udało się odświeżyć listy: " + e.getMessage());
        }
    }
}