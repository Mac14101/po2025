package com.example.edziennikui.shared;

import client.ApplicationClient;
import entities.SchoolClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class ScheduleController {

    public record ScheduleRow(String time, String monday, String tuesday, String wednesday, String thursday,
                              String friday) {
    }

    @FXML private TableView<ScheduleRow> scheduleTable;
    @FXML private TableColumn<ScheduleRow, String> colTime;
    @FXML private TableColumn<ScheduleRow, String> colMonday;
    @FXML private TableColumn<ScheduleRow, String> colTuesday;
    @FXML private TableColumn<ScheduleRow, String> colWednesday;
    @FXML private TableColumn<ScheduleRow, String> colThursday;
    @FXML private TableColumn<ScheduleRow, String> colFriday;

    @FXML
    public void initialize() {
        setupColumns();
        loadSchedule();
    }

    private void setupColumns() {
        colTime.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().time()));
        colMonday.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().monday()));
        colTuesday.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().tuesday()));
        colWednesday.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().wednesday()));
        colThursday.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().thursday()));
        colFriday.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().friday()));
    }

    private void loadSchedule() {
        try {
            ArrayList<SchoolClass> classes = ApplicationClient.getInstance().getUserSchedule();

            Map<String, String[]> timeRows = new TreeMap<>();

            for (SchoolClass sc : classes) {
                String timeKey = sc.getStartTime() + " - " + sc.getEndTime();
                timeRows.putIfAbsent(timeKey, new String[]{"", "", "", "", ""});

                int dayIndex = switch (sc.getDay().toLowerCase()) {
                    case "monday", "poniedziałek" -> 0;
                    case "tuesday", "wtorek" -> 1;
                    case "wednesday", "środa" -> 2;
                    case "thursday", "czwartek" -> 3;
                    case "friday", "piątek" -> 4;
                    default -> -1;
                };

                if (dayIndex != -1) {
                    timeRows.get(timeKey)[dayIndex] = sc.getSubject().getName();
                }
            }

            ObservableList<ScheduleRow> rows = FXCollections.observableArrayList();
            for (var entry : timeRows.entrySet()) {
                String[] d = entry.getValue();
                rows.add(new ScheduleRow(entry.getKey(), d[0], d[1], d[2], d[3], d[4]));
            }

            scheduleTable.setItems(rows);

        } catch (Exception e) {
            AlertHelper.showError("Błąd planu zajęć", "Nie udało się pobrać planu lekcji: " + e.getMessage());
        }
    }
}