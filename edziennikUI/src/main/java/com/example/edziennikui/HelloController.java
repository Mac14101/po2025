package com.example.edziennikui;

import com.example.edziennikui.shared.DashboardController;
import entities.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import client.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.IOException;

public class HelloController {

    private static final Logger LOGGER = Logger.getLogger(HelloController.class.getName());

    @FXML private Button btnDashboard;
    @FXML private Button btnUsers;
    @FXML private Button btnGrades;
    @FXML private Button btnAttendance;
    @FXML private Button btnSchedule;
    @FXML private Button btnSettings;

    private User loggedUser;

    @FXML
    private StackPane contentArea;

    @FXML
    private void handleShowDashboard(ActionEvent event) {
        showDashboard();
    }

    @FXML
    private void handleShowUsers(ActionEvent event) {
        if (loggedUser.getRole() == User.Role.ADMIN) {
            loadPage("/com/example/edziennikui/admin/users/AdminUserList.fxml");
        }
    }

    @FXML
    private void handleShowGrades(ActionEvent event) {
        User.Role role = loggedUser.getRole();
        if (role == User.Role.ADMIN) {
            loadPage("/com/example/edziennikui/admin/subjects/AdminSubjectList.fxml");
        } else if (role == User.Role.TEACHER) {
            loadPage("/com/example/edziennikui/teacher/TeacherClassSelect.fxml");
        } else if (role == User.Role.STUDENT) {
            loadPage("/com/example/edziennikui/student/StudentGrades.fxml");
        }
    }

    @FXML
    private void handleShowAttendance(ActionEvent event) {
        User.Role role = loggedUser.getRole();

        if (role == User.Role.ADMIN) {
            loadPage("/com/example/edziennikui/admin/classes/AdminClassList.fxml");
        } else if (role == User.Role.TEACHER) {
            loadPage("/com/example/edziennikui/teacher/TeacherAttendance.fxml");
        } else if (role == User.Role.STUDENT) {
            loadPage("/com/example/edziennikui/student/StudentAttendance.fxml");
        }
    }

    @FXML
    private void handleShowSchedule(ActionEvent event) {
        if (loggedUser.getRole() == User.Role.ADMIN) {
            loadPage("/com/example/edziennikui/admin/schedule/AdminScheduleManage.fxml");
        } else {
            loadPage("/com/example/edziennikui/shared/Schedule.fxml");
        }
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            ApplicationClient.getInstance().logOut();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/edziennikui/shared/LoginView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("E-Dziennik - Logowanie");
            stage.show();

            System.out.println("Pomyślnie wylogowano i przełączono widok.");

        } catch (Client.ClientError e) {
            System.err.println("Serwer zwrócił błąd przy wylogowaniu: " + e.getResponse().statusCode());
        } catch (Exception _) {}
    }

    @FXML
    private void handleShowAccountSettings(ActionEvent event) {
    }

    private void loadPage(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Node view = loader.load();
            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Błąd ładowania strony: " + fxmlPath, e);
            showErrorAlert("Błąd nawigacji", "Nie udało się otworzyć strony: " + fxmlPath);
        }
    }

    public void initSession(User user) {
        this.loggedUser = user;

        btnUsers.setVisible(false);
        btnUsers.setManaged(false);

        switch (user.getRole()) {
            case ADMIN:
                btnUsers.setVisible(true);
                btnUsers.setManaged(true);
                btnUsers.setText("Użytkownicy");
                btnGrades.setText("Przedmioty");
                btnAttendance.setText("Klasy");
                btnSchedule.setText("Zarządzaj Planem");
                break;

            case TEACHER:
                btnGrades.setText("Wystaw Oceny");
                btnAttendance.setText("Obecność");
                btnSchedule.setText("Plan Lekcji");
                break;

            case STUDENT:
                btnGrades.setText("Oceny");
                btnAttendance.setText("Obecność");
                btnSchedule.setText("Plan Lekcji");
                break;
        }

        showDashboard();
    }

    private void showDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/edziennikui/shared/Dashboard.fxml"));
            Node view = loader.load();

            DashboardController controller = loader.getController();
            controller.setInfo(this.loggedUser);

            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Nie udało się załadować pulpitu", e);
            showErrorAlert("Błąd krytyczny", "Nie można otworzyć pulpitu.");
        }
    }

    private void showErrorAlert(String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
