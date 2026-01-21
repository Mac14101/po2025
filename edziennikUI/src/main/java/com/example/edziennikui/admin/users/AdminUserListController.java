package com.example.edziennikui.admin.users;

import client.ApplicationClient;
import com.example.edziennikui.shared.AlertHelper;
import com.example.edziennikui.shared.UIHelper;
import entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import java.util.ArrayList;

public class AdminUserListController {

    @FXML private TableView<User> userTable;
    @FXML private TableColumn<User, Integer> colId;
    @FXML private TableColumn<User, String> colName;
    @FXML private TableColumn<User, String> colSurname;
    @FXML private TableColumn<User, String> colEmail;
    @FXML private TableColumn<User, String> colRole;

    @FXML private TextField txtSearch;
    @FXML private ComboBox<String> comboFilterRole;

    private FilteredList<User> filteredData;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));

        comboFilterRole.setItems(FXCollections.observableArrayList(
                "Wszyscy", "Admin", "Teacher", "Student"
        ));
        comboFilterRole.getSelectionModel().selectFirst();

        loadUsers();
        setupFiltering();
    }

    private void loadUsers() {
        try {
            ArrayList<User> users = ApplicationClient.getInstance().getAllUsers();
            filteredData = new FilteredList<>(FXCollections.observableArrayList(users), p -> true);

            SortedList<User> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(userTable.comparatorProperty());
            userTable.setItems(sortedData);
        } catch (Exception e) {
            AlertHelper.showError("Błąd bazy danych", "Nie udało się pobrać listy użytkowników.");
        }
    }

    private void setupFiltering() {
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            applyFilters();
        });

        comboFilterRole.valueProperty().addListener((observable, oldValue, newValue) -> {
            applyFilters();
        });
    }

    private void applyFilters() {
        String searchText = txtSearch.getText().toLowerCase();
        String selectedRole = comboFilterRole.getValue();

        filteredData.setPredicate(user -> {
            boolean roleMatch = (selectedRole == null || selectedRole.equals("Wszyscy") ||
                    user.getRole().toString().equalsIgnoreCase(selectedRole));

            boolean searchMatch = searchText.isEmpty() ||
                    user.getName().toLowerCase().contains(searchText) ||
                    user.getSurname().toLowerCase().contains(searchText) ||
                    user.getEmail().toLowerCase().contains(searchText);

            return roleMatch && searchMatch;
        });
    }

    @FXML
    private void handleAddUser() {
        UIHelper.openModal(
                "/com/example/edziennikui/admin/users/AdminUserForm.fxml",
                "Dodaj nowego użytkownika",
                null
        );
        loadUsers();
    }

    @FXML
    private void handleEditUser() {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();

        if (selectedUser == null) {
            AlertHelper.showWarning("Brak wyboru", "Wybierz użytkownika do edycji.");
            return;
        }

        UIHelper.openModal(
                "/com/example/edziennikui/admin/users/AdminUserForm.fxml",
                "Edytuj użytkownika",
                (AdminUserFormController controller) -> controller.setUserData(selectedUser)
        );
        loadUsers();
    }

    @FXML
    private void handleDeleteUser() {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();

        if (selectedUser == null) {
            AlertHelper.showWarning("Brak wyboru", "Proszę wybrać użytkownika z tabeli.");
            return;
        }

        System.out.println("Próba usunięcia użytkownika o ID: " + selectedUser.getId());
        loadUsers();
    }
}