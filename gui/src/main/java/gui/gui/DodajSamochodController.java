package gui.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class DodajSamochodController {
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
        carNumberGenerateButton.setText("KRA XXXXX");
    }

    @FXML
    private void onSubmitButton() {
        String carModel = carModelTextField.getText();
        String carNumber = carNumberTextField.getText();
        String carEngineName = carEngineNameTextField.getText();
        String carEngineWeight = carEngineWeightTextField.getText();
        String carEnginePrice = carEnginePriceTextField.getText();
        String carEngineMaxRPM = carEngineMaxRPMTextField.getText();
        String carGearboxName = carGearboxNameTextField.getText();
        String carGearboxWeight = carGearboxWeightTextField.getText();
        String carGearboxPrice = carGearboxPriceTextField.getText();
        String carGearboxMaxGear = carGearboxMaxGearTextField.getText();
        String carClutchName = carClutchNameTextField.getText();
        String carClutchWeight = carClutchWeightTextField.getText();
        String carClutchPrice = carClutchPriceTextField.getText();
    }

    @FXML
    private void onCancelButton() {
        System.out.println("onCancelButton click");
    }
}
