package com.example.edziennikui.admin.schedule;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AdminScheduleManageController {

    @FXML private TableView<Object> scheduleTable; // Model Lesson
    @FXML private TableColumn<Object, String> colDay;
    @FXML private TableColumn<Object, String> colTime;
    @FXML private TableColumn<Object, String> colSubject;
    @FXML private TableColumn<Object, String> colTeacher;
    @FXML private TableColumn<Object, String> colRoom;
    @FXML private ComboBox<Object> comboFilterClass;

    @FXML
    public void initialize() {
    }

    @FXML
    private void handleAddLesson() {
    }

    @FXML
    private void handleEditLesson() {
    }

    @FXML
    private void handleDeleteLesson() {
    }
}