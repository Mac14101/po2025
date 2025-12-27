package com.example.edziennikui.admin.classes;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AdminClassListController {

    @FXML private TableView<Object> classTable;
    @FXML private TableColumn<Object, Integer> colId;
    @FXML private TableColumn<Object, String> colClassName;
    @FXML private TableColumn<Object, Integer> colStudentCount;

    @FXML
    public void initialize() {
    }

    @FXML
    private void handleShowDetails() {
    }

    @FXML
    private void handleAddClass() {
    }

    @FXML
    private void handleDeleteClass() {
    }
}