package com.example.edziennikui.teacher;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TeacherGradesController {

    @FXML private Label lblSelectedClass;
    @FXML private Label lblSelectedSubject;

    @FXML private TableView<Object> gradesTable;
    @FXML private TableColumn<Object, String> colStudentName;
    @FXML private TableColumn<Object, String> colGrades;
    @FXML private TableColumn<Object, Double> colAverage;
    @FXML private TableColumn<Object, Object> colAction;

    @FXML
    public void initialize() {
    }

    @FXML
    private void handleRefresh() {
    }
}