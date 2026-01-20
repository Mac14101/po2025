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
import javafx.util.StringConverter;

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
        // KIA Ceed
        Engine kiaEngine = new Engine("1.6 CRDi 128KM", 6000, 320d, 15000d);
        Clutch kiaClutch = new Clutch("SACHS", 5d, 389d);
        Gearbox kiaGearBox = new Gearbox("S71767", 6, kiaClutch, 72d, 3500d);
        Car kiaCeed = new Car("", "KIA Ceed", 1523d, kiaEngine, kiaGearBox);
        carList.add(kiaCeed);
        modelComboBox.setItems(carList);
        modelComboBox.setConverter(new StringConverter<Car>() {
            @Override
            public String toString(Car car) {
                if (car == null) return "";
                return car.getModel();
            }

            @Override
            public Car fromString(String string) {
                return null;
            }
        });
    }

    public void onCarNumberGenerateButton(ActionEvent actionEvent) {
        StringBuilder registerNumber = new StringBuilder();
        registerNumber.append("KR ");
        for (int i = 0; i < 6; i++) {
            if (rand.nextBoolean()) {
                registerNumber.append((char) rand.nextInt(65, 90));
            } else {
                registerNumber.append((char) rand.nextInt(48, 57));
            }
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
        // Właściwości samochodu
        Car selectedModel = modelComboBox.getSelectionModel().getSelectedItem();
        carModelTextField.setText(selectedModel.getModel());
        carWeightTextField.setText(String.valueOf(selectedModel.getWeight()));
        // Właściwości Silnika
        Engine selectedEngine = selectedModel.getEngine();
        engineNameTextField.setText(selectedEngine.getName());
        enginePriceTextField.setText(String.valueOf(selectedEngine.getPrice()));
        engineWeightTextField.setText(String.valueOf(selectedEngine.getWeight()));
        engineMaxRPMTextField.setText(String.valueOf(selectedEngine.getMaxRPM()));
        // Właściwości Skrzyni biegów
        Gearbox selectedGearbox = selectedModel.getGearbox();
        gearboxNameTextField.setText(selectedGearbox.getName());
        gearboxWeightTextField.setText(String.valueOf(selectedGearbox.getWeight()));
        gearboxPriceTextField.setText(String.valueOf(selectedGearbox.getPrice()));
        gearboxMaxGearTextField.setText(String.valueOf(selectedGearbox.getMaxGear()));
        // Właściwości Sprzęgła
        Clutch selectedClutch = selectedGearbox.getClutch();
        clutchNameTextField.setText(selectedClutch.getName());
        clutchPriceTextField.setText(String.valueOf(selectedClutch.getPrice()));
        clutchWeightTextField.setText(String.valueOf(selectedClutch.getWeight()));
    }
}
