package com.example.edziennikui.student;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class StudentGradesController {

    @FXML private TableView<Object> gradesTable; // Model GradeRow lub SubjectSummary
    @FXML private TableColumn<Object, String> colSubject;
    @FXML private TableColumn<Object, String> colGrades;
    @FXML private TableColumn<Object, Double> colAverage;
    @FXML private TableColumn<Object, Integer> colFinal;

    @FXML private Label lblAverage;

    @FXML
    public void initialize() {
    }
}