package com.example.edziennikui.student;

import client.ApplicationClient;
import com.example.edziennikui.shared.AlertHelper;
import entities.Grade;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StudentGradesController {

    @FXML private TableView<SubjectGradeRow> gradesTable;
    @FXML private TableColumn<SubjectGradeRow, String> colSubject;
    @FXML private TableColumn<SubjectGradeRow, String> colGrades;
    @FXML private TableColumn<SubjectGradeRow, String> colDetails;
    @FXML private TableColumn<SubjectGradeRow, Double> colAverage;


    @FXML
    public void initialize() {
        colSubject.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getSubjectName()));

        colGrades.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getPartialGrades()));

        colDetails.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getDetails()));

        colAverage.setCellValueFactory(data ->
                new javafx.beans.property.SimpleDoubleProperty(data.getValue().getAverage()).asObject());

        setupMultiLineColumn(colGrades);
        setupMultiLineColumn(colDetails);

        loadGrades();
    }

    private void setupMultiLineColumn(TableColumn<SubjectGradeRow, String> column) {
        column.setCellFactory(tc -> {
            TableCell<SubjectGradeRow, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(column.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
    }

    private void loadGrades() {
        try {
            ArrayList<Grade> allGrades = ApplicationClient.getInstance().getUserGrades();


            Map<String, List<Grade>> groupedBySubject = allGrades.stream()
                    .collect(Collectors.groupingBy(g -> g.getSubject().getName()));

            List<SubjectGradeRow> tableRows = groupedBySubject.entrySet().stream()
                    .map(entry -> new SubjectGradeRow(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList());

            gradesTable.setItems(FXCollections.observableArrayList(tableRows));

        } catch (Exception e) {
            AlertHelper.showError("Błąd", "Nie udało się pobrać Twoich ocen.");
        }
    }

    public static class SubjectGradeRow {
        private final String subjectName;
        private final List<Grade> gradeList;
        private final double average;

        public SubjectGradeRow(String subjectName, List<Grade> gradeList) {
            this.subjectName = subjectName;
            this.gradeList = gradeList;

            this.average = gradeList.stream()
                    .map(Grade::getGrade)
                    .map(Object::toString)
                    .filter(s -> s.matches("\\d+"))
                    .mapToInt(Integer::parseInt)
                    .average().orElse(0.0);
        }

        public String getSubjectName() { return subjectName; }

        public String getPartialGrades() {
            if (gradeList == null || gradeList.isEmpty()) return "-";
            return gradeList.stream()
                    .map(g -> g.getGrade().toString())
                    .collect(Collectors.joining("\n"));
        }

        public double getAverage() {
            return Math.round(average * 100.0) / 100.0;
        }

        public String getDetails() {
            if (gradeList == null || gradeList.isEmpty()) return "-";
            return gradeList.stream()
                    .map(g -> {
                        String t = (g.getTitle() != null && !g.getTitle().isEmpty()) ? g.getTitle() : "Brak tytułu";
                        String c = (g.getComment() != null && !g.getComment().isEmpty()) ? ": " + g.getComment() : "";
                        return t + c;
                    })
                    .collect(Collectors.joining("\n"));
        }
    }
}

