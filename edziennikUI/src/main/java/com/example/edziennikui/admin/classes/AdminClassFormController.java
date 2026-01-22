package com.example.edziennikui.admin.classes;

import client.ApplicationClient;
import com.example.edziennikui.shared.AlertHelper;
import entities.SchoolGroup;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AdminClassFormController {

    @FXML private TextField txtNumber;
    @FXML private TextField txtLetter;

    @FXML
    private void handleSave() {
        try {
            if (txtNumber.getText().isEmpty() || txtLetter.getText().isEmpty()) {
                AlertHelper.showWarning("Brak danych", "Proszę uzupełnić numer i literę klasy!");
                return;
            }
            SchoolGroup newClass = new SchoolGroup();
            newClass.setNumber(Integer.parseInt(txtNumber.getText()));
            String letterInput = txtLetter.getText().trim().toUpperCase();
            newClass.setLetter(letterInput.charAt(0));

            ApplicationClient.getInstance().createClass(newClass);

            AlertHelper.showInfo("Sukces", "Klasa została dodana.");
            closeWindow();
        } catch (NumberFormatException e) {
            AlertHelper.showWarning("Błąd danych", "Numer klasy musi być liczbą!");
        } catch (IllegalArgumentException e) {
            AlertHelper.showWarning("Błąd walidacji", "Litera musi być z zakresu A-Z!");
        } catch (Exception e) {
            AlertHelper.showError("Błąd", "Nie udało się zapisać klasy: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) txtNumber.getScene().getWindow();
        stage.close();
    }
}