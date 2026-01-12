package gui.gui;


import gui.symulator.Car;
import gui.symulator.Clutch;
import gui.symulator.Engine;
import gui.symulator.Gearbox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Random;

public class AddCarController {
    private final Random rand = new Random();
    public TextField carModelTextField;
    public TextField carNumberTextField;
    public TextField carWeightTextField;
    public Button carNumberGenerateButton;
    public TextField engineNameTextField;
    public TextField engineWeightTextField;
    public TextField enginePriceTextField;
    public TextField engineMaxRPMTextField;
    public TextField gearboxNameTextField;
    public TextField gearboxWeightTextField;
    public TextField gearboxPriceTextField;
    public TextField gearboxMaxGearTextField;
    public TextField clutchNameTextField;
    public TextField clutchWeightTextField;
    public TextField clutchPriceTextField;
    public Button submitButton;
    public Button cancelButton;
    public ComboBox<Car> modelComboBox;
    public ApplicationController mainAppController;
    public ObservableList<Car> carList = FXCollections.observableArrayList();

    public void setMainAppController(ApplicationController mainAppController) {
        this.mainAppController = mainAppController;
    }

    @FXML
    public void initialize() {
        modelComboBox.setItems(carList);
    }

    public void onCarNumberGenerateButton(ActionEvent actionEvent) {
        StringBuilder registerNumber = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            registerNumber.append(rand.nextInt(10));
        }
        carNumberTextField.setText(registerNumber.toString());
    }

    public void onSubmitButton(ActionEvent actionEvent) {
        try {
            // Właściwości samochodu
            String carModel = carModelTextField.getText();
            String carNumber = carNumberTextField.getText();
            double carWeight = Double.parseDouble(carWeightTextField.getText());
            // Właściwości Silnika
            String engineName = engineNameTextField.getText();
            double engineWeight = Double.parseDouble(engineWeightTextField.getText());
            double enginePrice = Double.parseDouble(enginePriceTextField.getText());
            int engineMaxRPM = Integer.parseInt(engineMaxRPMTextField.getText());
            // Właściwości Skrzyni biegów
            String gearboxName = gearboxNameTextField.getText();
            double gearboxWeight = Double.parseDouble(gearboxWeightTextField.getText());
            double gearboxPrice = Double.parseDouble(gearboxPriceTextField.getText());
            int gearboxMaxGear = Integer.parseInt(gearboxMaxGearTextField.getText());
            // Właściwości Sprzęgła
            String clutchName = clutchNameTextField.getText();
            double clutchWeight = Double.parseDouble(clutchWeightTextField.getText());
            double clutchPrice = Double.parseDouble(clutchPriceTextField.getText());
            Clutch clutch = new Clutch(clutchName, clutchWeight, clutchPrice);
            Gearbox gearbox = new Gearbox(gearboxName, gearboxMaxGear, clutch, gearboxWeight, gearboxPrice);
            Engine engine = new Engine(engineName, engineMaxRPM, engineWeight, enginePrice);
            Car newCar = new Car(carNumber, carModel, carWeight, engine, gearbox);
            mainAppController.addNewCar(newCar);
            Stage stage = (Stage) submitButton.getScene().getWindow();
            stage.close();
        } catch (NumberFormatException e) {
            showErrorWindow("Podano niepoprawną wartość!");

        }
    }

    public void onCancelButton(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void showErrorWindow(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Błąd");
        alert.setHeaderText(null);
        alert.setContentText(error);
        alert.showAndWait();
    }

    public void onModelComboBox(ActionEvent actionEvent) {
        Car selectedModel = modelComboBox.getSelectionModel().getSelectedItem();
        carModelTextField.setText(selectedModel.getModel());
        carWeightTextField.setText(String.valueOf(selectedModel.getWeight()));
        Engine selectedEngine = selectedModel.getEngine();
        engineNameTextField.setText(selectedEngine.getName());
        enginePriceTextField.setText(String.valueOf(selectedEngine.getPrice()));
        engineWeightTextField.setText(String.valueOf(selectedEngine.getWeight()));
        engineMaxRPMTextField.setText(String.valueOf(selectedEngine.getMaxRPM()));
        Gearbox selectedGearbox = selectedModel.getGearbox();
        gearboxNameTextField.setText(selectedGearbox.getName());
        gearboxWeightTextField.setText(String.valueOf(selectedGearbox.getWeight()));
        gearboxPriceTextField.setText(String.valueOf(selectedGearbox.getPrice()));
        gearboxMaxGearTextField.setText(String.valueOf(selectedGearbox.getMaxGear()));
        Clutch selectedClutch = selectedGearbox.getClutch();
        clutchNameTextField.setText(selectedClutch.getName());
        clutchPriceTextField.setText(String.valueOf(selectedClutch.getPrice()));
        clutchWeightTextField.setText(String.valueOf(selectedClutch.getWeight()));
    }
}
