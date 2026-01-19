package com.example.edziennikui.admin.subjects;

import client.ApplicationClient;
import client.Client;
import com.example.edziennikui.shared.AlertHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import entities.Subject;

public class AdminSubjectFormController {

    @FXML private Label lblTitle;
    @FXML private TextField txtSubjectName;

    private Subject currentSubject;
    private boolean isEditMode = false;

    public void setSubjectData(Subject subject) {
        this.currentSubject = subject;
        this.isEditMode = true;

        lblTitle.setText("Edytuj Przedmiot");
        txtSubjectName.setText(subject.getName());
    }

    @FXML
    public void initialize() {
    }

    @FXML
    private void handleSave() {
        try {
            if (!isEditMode) {
                currentSubject = new Subject();
            }

            currentSubject.setName(txtSubjectName.getText());

            if (isEditMode) {
                AlertHelper.showInfo("Edycja", "Edycja zostanie w pełni odblokowana po aktualizacji serwera.");
            } else {
                ApplicationClient.getInstance().createSubject(currentSubject);
                AlertHelper.showInfo("Sukces", "Przedmiot został dodany.");
            }

            closeWindow();
        } catch (IllegalArgumentException e) {
            AlertHelper.showWarning("Błąd walidacji", e.getMessage());
        } catch (Client.ClientError e) {
            AlertHelper.showError("Błąd serwera", "Problem z połączeniem: " + e.getMessage());
        } catch (Exception e) {
            AlertHelper.showError("Błąd", "Wystąpił nieoczekiwany problem.");
        }
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) txtSubjectName.getScene().getWindow();
        stage.close();
    }
}