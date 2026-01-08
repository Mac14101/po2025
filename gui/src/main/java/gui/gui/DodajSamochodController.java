package gui.gui;

import gui.symulator.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DodajSamochodController {
    @FXML
    private Button submitButton;
    @FXML
    private Button carNumberGenerateButton;
    @FXML
    private TextField carClutchPriceTextField;
    @FXML
    private TextField carClutchNameTextField;
    @FXML
    private TextField carModelTextField;
    @FXML
    private TextField carNumberTextField;
    @FXML
    private TextField carEngineNameTextField;
    @FXML
    private TextField carEngineWeightTextField;
    @FXML
    private TextField carEnginePriceTextField;
    @FXML
    private TextField carEngineMaxRPMTextField;
    @FXML
    private TextField carGearboxNameTextField;
    @FXML
    private TextField carGearboxWeightTextField;
    @FXML
    private TextField carGearboxPriceTextField;
    @FXML
    private TextField carGearboxMaxGearTextField;
    @FXML
    private TextField carClutchWeightTextField;
    @FXML
    private Button cancelButton;

    @FXML
    private void onCarNumberGenerateButton() {
        carNumberTextField.setText("KRA XXXXX");
    }

    @FXML
    private void onSubmitButton() {
        try {
            String carModel = carModelTextField.getText();
            String carNumber = carNumberTextField.getText();
            String carEngineName = carEngineNameTextField.getText();
            double carEngineWeight = Double.parseDouble(carEngineWeightTextField.getText());
            double carEnginePrice = Double.parseDouble(carEnginePriceTextField.getText());
            int carEngineMaxRPM = Integer.parseInt(carEngineMaxRPMTextField.getText());
            Silnik silnik = new Silnik(carEngineName, carEngineMaxRPM, carEngineWeight, carEnginePrice);
            String carClutchName = carClutchNameTextField.getText();
            double carClutchWeight = Double.parseDouble(carClutchWeightTextField.getText());
            double carClutchPrice = Double.parseDouble(carClutchPriceTextField.getText());
            Sprzeglo sprzeglo = new Sprzeglo(carClutchName, carClutchWeight, carClutchPrice);
            String carGearboxName = carGearboxNameTextField.getText();
            double carGearboxWeight = Double.parseDouble(carGearboxWeightTextField.getText());
            double carGearboxPrice = Double.parseDouble(carGearboxPriceTextField.getText());
            int carGearboxMaxGear = Integer.parseInt(carGearboxMaxGearTextField.getText());
            SkrzyniaBiegow skrzyniaBiegow = new SkrzyniaBiegow(carGearboxName, carGearboxMaxGear, sprzeglo, carGearboxWeight, carGearboxPrice);
            Samochod samochod = new Samochod(carNumber, carModel, silnik, skrzyniaBiegow, new Pozycja());
            SamochoGUIControler.dodajSamochod(samochod);
            Stage stage = (Stage) submitButton.getScene().getWindow();
            stage.close();
        } catch (NumberFormatException e) {
            System.err.println(e);
        }
    }

    @FXML
    private void onCancelButton() {
        Stage stage = (Stage) submitButton.getScene().getWindow();
        stage.close();
    }
}
