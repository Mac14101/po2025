package com.example.edziennikui.admin;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class AdminUsersController {

    @FXML
    private TableView<Object> userTable;

    @FXML
    public void initialize() {
        System.out.println("Widok administratora został zainicjalizowany.");
    }
}