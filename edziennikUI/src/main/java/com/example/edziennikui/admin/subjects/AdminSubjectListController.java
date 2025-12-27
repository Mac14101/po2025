package com.example.edziennikui.admin.subjects;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AdminSubjectListController {

    @FXML private TableView<Object> subjectTable;
    @FXML private TableColumn<Object, Integer> colId;
    @FXML private TableColumn<Object, String> colName;
    @FXML private TableColumn<Object, String> colDescription;

    @FXML
    public void initialize() {
    }

    @FXML
    private void handleAddSubject() {
    }

    @FXML
    private void handleEditSubject() {
    }

    @FXML
    private void handleDeleteSubject() {
    }
}