package com.example.edziennikui.teacher;

import client.ApplicationClient;
import com.example.edziennikui.shared.AlertHelper;
import entities.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TeacherGradesController {

    @FXML private Label lblSelectedClass;
    @FXML private Label lblSelectedSubject;
    @FXML private TableView<StudentGradesRow> gradesTable;
    @FXML private TableColumn<StudentGradesRow, String> colStudentName;
    @FXML private TableColumn<StudentGradesRow, String> colGrades;
    @FXML private TableColumn<StudentGradesRow, Double> colAverage;
    @FXML private TableColumn<StudentGradesRow, Void> colAction;

    private SchoolGroup currentGroup;
    private Subject currentSubject;

    public static class StudentGradesRow {
        public Student student;
        public List<Grade> grades;

        public StudentGradesRow(Student s, List<Grade> g) {
            this.student = s;
            this.grades = g;
        }
    }

    public void setContext(SchoolGroup group, Subject subject) {
        this.currentGroup = group;
        this.currentSubject = subject;
        lblSelectedClass.setText(group.getNumber() + " " + group.getLetter());
        lblSelectedSubject.setText(subject.getName());
        handleRefresh();
    }

    @FXML
    public void initialize() {
        setupTableColumns();
    }

    private void setupTableColumns() {
        colStudentName.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().student.getName() + " " + data.getValue().student.getSurname()));

        colGrades.setCellValueFactory(data -> {
            List<Grade> grades = data.getValue().grades;
            if (grades == null || grades.isEmpty()) {
                return new SimpleStringProperty("-");
            }
            String text = grades.stream()
                    .map(g -> g.getGrade().toString())
                    .collect(Collectors.joining(", "));
            return new SimpleStringProperty(text);
        });

        colAverage.setCellValueFactory(data -> {
            List<Grade> grades = data.getValue().grades;
            if (grades == null || grades.isEmpty()) return new SimpleDoubleProperty(0.0).asObject();

            double avg = grades.stream()
                    .map(Grade::getGrade)
                    .filter(gn -> gn != Grade.GradeName.NP && gn != Grade.GradeName.NB)
                    .mapToInt(gn -> {
                        try {
                            return Integer.parseInt(gn.toString());
                        } catch (Exception e) { return 0; }
                    })
                    .filter(v -> v > 0)
                    .average().orElse(0.0);
            return new SimpleDoubleProperty(Math.round(avg * 100.0) / 100.0).asObject();
        });

        setupActionColumn();
    }

    private void setupActionColumn() {
        colAction.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("+ Ocena");
            {
                btn.setOnAction(event -> {
                    StudentGradesRow row = getTableView().getItems().get(getIndex());
                    openGradeForm(row.student);
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) setGraphic(null);
                else setGraphic(btn);
            }
        });
    }

    @FXML
    private void handleRefresh() {
        if (currentGroup == null || currentSubject == null) return;
        try {
            ArrayList<Student> students = ApplicationClient.getInstance().getClassStudents(currentGroup.getId());
            ArrayList<StudentGradesRow> rows = new ArrayList<>();

            for (Student s : students) {
                ArrayList<Grade> allGrades = ApplicationClient.getInstance().getGradesList(s.getId());

                List<Grade> filtered = allGrades.stream()
                        .filter(g -> g.getSubject() != null &&
                                Objects.equals(g.getSubject().getName(), currentSubject.getName()))
                        .collect(Collectors.toList());

                /*List<Grade> filtered = allGrades.stream()
                        .filter(g -> g.getSubject() != null &&
                                Objects.equals(g.getSubject().getId(), currentSubject.getId()))
                        .collect(Collectors.toList()); */

                rows.add(new StudentGradesRow(s, filtered));
            }
            gradesTable.setItems(FXCollections.observableArrayList(rows));
        } catch (Exception e) {
            AlertHelper.showError("Błąd", "Nie udało się pobrać ocen: " + e.getMessage());
        }
    }

    private void openGradeForm(Student student) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/edziennikui/teacher/TeacherGradeForm.fxml"));
            Parent root = loader.load();
            TeacherGradeFormController controller = loader.getController();
            controller.setGradeData(student, this.currentSubject);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            handleRefresh();
        } catch (Exception e) {
            AlertHelper.showError("Błąd", "Nie można otworzyć formularza.");
        }
    }

    @FXML
    private void handleBack() {
        ((Stage) lblSelectedClass.getScene().getWindow()).close();
    }
}