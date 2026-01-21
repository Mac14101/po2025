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
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.util.ArrayList;

public class TeacherAttendanceController {

    @FXML private TabPane mainTabPane;
    @FXML private Tab tabAttendance;

    @FXML private ComboBox<SchoolClass> comboClass;
    @FXML private ComboBox<Subject> comboSubject;
    @FXML private TextField txtTopic;

    @FXML private TableView<Attendance> attendanceTable;
    @FXML private TableColumn<Attendance, String> colStudentName;
    @FXML private TableColumn<Attendance, String> colStatus;
    @FXML private Label lblActiveLessonInfo;

    private final ApplicationClient client = ApplicationClient.getInstance();
    private int currentLessonId = -1;

    @FXML
    public void initialize() {
        comboClass.setConverter(new StringConverter<SchoolClass>() {
            @Override
            public String toString(SchoolClass sc) {
                if (sc == null || sc.getSchoolGroup() == null) return "";
                return sc.getSchoolGroup().getNumber() + " " + sc.getSchoolGroup().getLetter() +
                        " (" + sc.getDay() + " " + sc.getStartTime() + ")";
            }

            @Override
            public SchoolClass fromString(String string) {
                return null;
            }
        });
        UIHelper.setupSubjectComboBox(comboSubject);

        try {
            ArrayList<SchoolClass> schedule = client.getUserSchedule();
            comboClass.setItems(FXCollections.observableArrayList(schedule));
            comboSubject.setItems(FXCollections.observableArrayList(client.getAllSubjects()));
        } catch (Exception e) {
            AlertHelper.showError("Błąd", "Nie udało się pobrać danych startowych: " + e.getMessage());
        }

        mainTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab == tabAttendance) {
                refreshStudentList();
            }
        });

        setupTableColumns();
    }

    private void setupTableColumns() {
        colStudentName.setCellValueFactory(data -> {
            User s = data.getValue().getStudent();
            return new SimpleStringProperty(s.getName() + " " + s.getSurname());
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
                    Attendance.Status status = getTableRow().getItem().getStatus();
                    String displayText = switch (status) {
                        case PRESENT -> "Obecny";
                        case ABSENT -> "Nieobecny";
                        case LATE -> "Spóźniony";
                        default -> "Nieokreślony";
                    };
                    statusCombo.setValue(displayText);
                    setGraphic(statusCombo);
                }
            }
        });
    }

    @FXML
    private void handleCreateLesson() {
        try {
            SchoolClass selectedClass = comboClass.getValue();
            Subject selectedSubject = comboSubject.getValue();
            String topic = txtTopic.getText();

            if (selectedClass == null || selectedSubject == null || topic.isEmpty()) {
                AlertHelper.showError("Błąd", "Wypełnij pola!");
                return;
            }

            Lesson lesson = new Lesson();
            lesson.setTopic(topic);
            lesson.setDate(LocalDate.now().toString());

            lesson.setSchoolClass(selectedClass);

            client.addLesson(lesson);

            ArrayList<Lesson> lessons = client.getLessons();
            if (lessons != null && !lessons.isEmpty()) {
                currentLessonId = lessons.getLast().getId();

                String groupName = selectedClass.getSchoolGroup().getNumber() + " " + selectedClass.getSchoolGroup().getLetter();
                lblActiveLessonInfo.setText("Klasa: " + groupName + " | Temat: " + topic);

                tabAttendance.setDisable(false);
                mainTabPane.getSelectionModel().select(tabAttendance);
                loadAttendanceList();
            }
        } catch (Exception e) {
            AlertHelper.showError("Błąd", "Nie udało się utworzyć lekcji.");
        }
    }

    private void loadAttendanceList() {
        try {
            if (currentLessonId != -1) {
                ArrayList<Attendance> list = client.getAttendanceList(currentLessonId);
                attendanceTable.setItems(FXCollections.observableArrayList(list));
            }
        } catch (Exception e) {
            AlertHelper.showError("Błąd ładowania", "Nie udało się pobrać listy obecności z serwera: " + e.getMessage());
        }
    }

    @FXML
    private void handleSaveAttendance() {
        try {
            if (currentLessonId == -1) return;

            boolean confirm = AlertHelper.showConfirmation("Zapis obecności",
                    "Czy na pewno chcesz zapisać listę obecności? Możesz ją później edytować.");
            if (!confirm) return;

            for (Attendance att : attendanceTable.getItems()) {
                client.updateAttendance(currentLessonId, att);
            }

            AlertHelper.showInfo("Sukces", "Obecność została zapisana w bazie.");

            boolean closeLesson = AlertHelper.showConfirmation("Koniec lekcji",
                    "Czy chcesz zakończyć edycję tej lekcji i wrócić do wyboru zajęć?");

            if (closeLesson) {
                tabAttendance.setDisable(true);
                mainTabPane.getSelectionModel().select(0);
                txtTopic.clear();
                currentLessonId = -1;
            }

        } catch (Exception e) {
            AlertHelper.showError("Błąd", "Nie udało się zapisać zmian: " + e.getMessage());
        }
    }

    private void refreshStudentList() {
        try {
            if (currentLessonId != -1) {
                loadAttendanceList();
            } else {
                SchoolClass selectedClass = comboClass.getValue();
                if (selectedClass != null && selectedClass.getSchoolGroup() != null) {
                    int groupId = selectedClass.getSchoolGroup().getId();
                    ArrayList<Student> students = client.getClassStudents(groupId);

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
            System.err.println("Błąd odświeżania: " + e.getMessage());
        }
    }
}