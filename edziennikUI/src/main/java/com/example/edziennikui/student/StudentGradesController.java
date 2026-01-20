package com.example.edziennikui.student;

import client.ApplicationClient;
import com.example.edziennikui.shared.AlertHelper;
import entities.Grade;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StudentGradesController {

    @FXML private TableView<SubjectGradeRow> gradesTable;
    @FXML private TableColumn<SubjectGradeRow, String> colSubject;
    @FXML private TableColumn<SubjectGradeRow, String> colGrades;
    @FXML private TableColumn<SubjectGradeRow, Double> colAverage;


    @FXML
    public void initialize() {
        colSubject.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
        colGrades.setCellValueFactory(new PropertyValueFactory<>("partialGrades"));
        colAverage.setCellValueFactory(new PropertyValueFactory<>("average"));

        loadGrades();
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
                    .filter(gn -> !gn.toString().equals("np") && !gn.toString().equals("nb"))
                    .mapToInt(gn -> Integer.parseInt(gn.toString()))
                    .average().orElse(0.0);
        }

        public String getSubjectName() { return subjectName; }
        public String getPartialGrades() {
            return gradeList.stream()
                    .map(g -> g.getGrade().toString())
                    .collect(java.util.stream.Collectors.joining(", "));
        }
        public double getAverage() { return Math.round(average * 100.0) / 100.0; }
    }
}

