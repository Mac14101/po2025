package com.example.edziennikui.shared;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ScheduleController {

    @FXML private TableView<Object> scheduleTable;
    @FXML private TableColumn<Object, String> colTime;
    @FXML private TableColumn<Object, String> colMonday;
    @FXML private TableColumn<Object, String> colTuesday;
    @FXML private TableColumn<Object, String> colWednesday;
    @FXML private TableColumn<Object, String> colThursday;
    @FXML private TableColumn<Object, String> colFriday;

    @FXML
    public void initialize() {
    }
}