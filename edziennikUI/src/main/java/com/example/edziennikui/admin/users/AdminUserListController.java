package com.example.edziennikui.admin.users;

import client.ApplicationClient;
import com.example.edziennikui.shared.AlertHelper;
import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
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

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));

        loadUsers();
    }

    private void loadUsers() {
        try {
            ArrayList<User> users = ApplicationClient.getInstance().getAllUsers();
            userTable.setItems(FXCollections.observableArrayList(users));
        } catch (Exception e) {
            AlertHelper.showError("Błąd bazy danych", "Nie udało się pobrać listy użytkowników.");
        }
    }

    @FXML
    private void handleAddUser() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/edziennikui/admin/users/AdminUserForm.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Dodaj nowego użytkownika");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            loadUsers();
        } catch (IOException e) {
            AlertHelper.showError("Błąd ładowania", "Nie udało się otworzyć formularza dodawania.");
        }
    }

    @FXML
    private void handleEditUser() {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();

        if (selectedUser == null) {
            AlertHelper.showWarning("Brak wyboru", "Wybierz użytkownika do edycji.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/edziennikui/admin/users/AdminUserForm.fxml"));
            Parent root = loader.load();

            AdminUserFormController controller = loader.getController();
            controller.setUserData(selectedUser);

            Stage stage = new Stage();
            stage.setTitle("Edytuj użytkownika");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            loadUsers();
        } catch (IOException e) {
            AlertHelper.showError("Błąd", "Nie udało się otworzyć okna edycji.");
        }
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