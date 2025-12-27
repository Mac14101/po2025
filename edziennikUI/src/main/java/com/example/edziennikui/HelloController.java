package com.example.edziennikui;

import com.example.edziennikui.shared.DashboardController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;

import java.awt.*;
import java.io.IOException;

public class HelloController {

    @FXML private Button btnDashboard;
    @FXML private Button btnUsers;
    @FXML private Button btnGrades;
    @FXML private Button btnAttendance;
    @FXML private Button btnSchedule;
    @FXML private Button btnSettings;

    private String currentRole;

    @FXML
    private StackPane contentArea;

    @FXML
    private void handleShowDashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/edziennikui/shared/Dashboard.fxml"));
            Node view = loader.load();
            DashboardController controller = loader.getController();
            controller.setInfo(this.currentRole);
            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void handleShowUsers(ActionEvent event) {
        if ("admin".equals(currentRole)) {
            loadPage("/com/example/edziennikui/admin/users/AdminUserList.fxml");
        }
    }

    @FXML
    private void handleShowGrades(ActionEvent event) {
        if ("admin".equals(currentRole)) {
            loadPage("/com/example/edziennikui/admin/subjects/AdminSubjectList.fxml");
        } else if ("nauczyciel".equals(currentRole)) {
            loadPage("/com/example/edziennikui/teacher/TeacherClassSelect.fxml");
        } else if ("uczeń".equals(currentRole)) {
            loadPage("/com/example/edziennikui/student/StudentGrades.fxml");
        }
    }

    @FXML
    private void handleShowAttendance(ActionEvent event) {
        if ("admin".equals(currentRole)) {
            loadPage("/com/example/edziennikui/admin/classes/AdminClassList.fxml");
        } else if ("nauczyciel".equals(currentRole)) {
            loadPage("/com/example/edziennikui/teacher/TeacherAttendance.fxml");
        } else if ("uczeń".equals(currentRole)) {
            loadPage("/com/example/edziennikui/student/StudentAttendance.fxml");
        }
    }

    @FXML
    private void handleShowSchedule(ActionEvent event) {
        if ("admin".equals(currentRole)) {
            loadPage("/com/example/edziennikui/admin/schedule/AdminScheduleManage.fxml");
        } else {
            loadPage("/com/example/edziennikui/student/StudentSchedule.fxml");
        }
    }

    @FXML
    private void handleShowAccountSettings(ActionEvent event) {
        loadPage("/com/example/edziennikui/shared/AccountSettings.fxml");
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        System.exit(0);
    }

    private void loadPage(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Node view = loader.load();
            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            System.err.println("Błąd ładowania: " + fxmlPath);
            e.printStackTrace();
        }
    }

    public void setRole(String role) {
        this.currentRole = role.toLowerCase();

        switch (currentRole) {
            case "admin":
                btnUsers.setText("Użytkownicy");
                btnGrades.setText("Przedmioty");
                btnAttendance.setText("Klasy");
                btnSchedule.setText("Zarządzaj Planem");
                break;

            case "nauczyciel":
                btnUsers.setVisible(false);
                btnUsers.setManaged(false);
                btnGrades.setText("Wystaw Oceny");
                btnAttendance.setText("Obecność");
                btnSchedule.setText("Plan");
                break;

            case "uczeń":
                btnUsers.setVisible(false);
                btnUsers.setManaged(false);
                btnGrades.setText("Oceny");
                btnAttendance.setText("Obecność");
                btnSchedule.setText("Plan Lekcji");
                break;

        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/edziennikui/shared/Dashboard.fxml"));
            Node view = loader.load();
            DashboardController controller = loader.getController();
            controller.setInfo(this.currentRole);
            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
