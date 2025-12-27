package com.example.edziennikui.admin.users;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class AdminUserListController {

    /*@FXML private TableView<User> userTable;
    @FXML private TableColumn<User, Integer> colId;
    @FXML private TableColumn<User, String> colFirstName;
    @FXML private TableColumn<User, String> colLastName;
    @FXML private TableColumn<User, String> colEmail;
    @FXML private TableColumn<User, String> colRole;*/

    @FXML private TextField txtSearch;
    @FXML private ComboBox<String> comboFilterRole;

    @FXML
    public void initialize() {
        // Ustawienie ról w filtrze
        comboFilterRole.getItems().addAll("Wszyscy", "Admin", "Nauczyciel", "Uczeń");
    }

    @FXML
    private void handleAddUser() {
    }

    @FXML
    private void handleEditUser() {
    }

    @FXML
    private void handleDeleteUser() {
    }
}