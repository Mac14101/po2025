package gui.gui;

import gui.symulator.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;

public class ApplicationController implements Listener {
    public TextField carModelTextField;
    public TextField carRegisterNumberTextField;
    public TextField carWeightTextField;
    public TextField carSpeedTextField;
    public TextField gearboxNameTextField;
    public TextField gearboxPriceTextField;
    public TextField gearboxWeightTextField;
    public TextField gearboxGearTextField;
    public TextField engineNameTextField;
    public TextField enginePriceTextField;
    public TextField engineWeightTextField;
    public TextField engineRPMTextField;
    public TextField clutchNameTextField;
    public TextField clutchPriceTextField;
    public TextField clutchWeightTextField;
    public TextField clutchStatusTextField;
    public ComboBox<Car> selectCarComboBox;
    public VBox map;
    public ImageView carImageView;
    private ObservableList<Car> carList = FXCollections.observableArrayList();
    private Car selectedCar;

    public void initialize() {
        selectCarComboBox.setItems(carList);
        selectCarComboBox.setConverter(new StringConverter<Car>() {
            @Override
            public String toString(Car car) {
                if (car == null) return "";
                return car.getModel() + " " + car.getRegisterNumber();
            }

            @Override
            public Car fromString(String string) {
                return null;
            }
        });
        Image carImage = new Image(getClass().getResource("/images/car.png").toExternalForm());
        System.out.println("Image width: " + carImage.getWidth() + ", height: " + carImage.getHeight());
        carImageView = new ImageView(carImage);
        carImageView.setFitWidth(carImage.getWidth() / 10);
        carImageView.setFitHeight(carImage.getHeight() / 10);
        carImageView.setTranslateX(0);
        carImageView.setTranslateY(0);
        map.getChildren().add(carImageView);
    }

    public void refresh() {
        if (selectedCar != null) {
            // Właściwości samochodu
            carModelTextField.setText(selectedCar.getModel());
            carRegisterNumberTextField.setText(selectedCar.getRegisterNumber());
            carWeightTextField.setText(String.valueOf(selectedCar.getWeight()));
            carSpeedTextField.setText("0");
            // Właściwości Silnika
            Engine selectedEngine = selectedCar.getEngine();
            engineNameTextField.setText(selectedEngine.getName());
            enginePriceTextField.setText(String.valueOf(selectedEngine.getPrice()));
            engineWeightTextField.setText(String.valueOf(selectedEngine.getWeight()));
            engineRPMTextField.setText(String.valueOf(selectedEngine.getRPM()));
            // Właściwości Skrzyni biegów
            Gearbox selectedGearbox = selectedCar.getGearbox();
            gearboxNameTextField.setText(selectedGearbox.getName());
            gearboxWeightTextField.setText(String.valueOf(selectedGearbox.getWeight()));
            gearboxPriceTextField.setText(String.valueOf(selectedGearbox.getPrice()));
            gearboxGearTextField.setText(String.valueOf(selectedGearbox.getGear()));
            // Właściwości Sprzęgła
            Clutch selectedClutch = selectedGearbox.getClutch();
            clutchNameTextField.setText(selectedClutch.getName());
            clutchPriceTextField.setText(String.valueOf(selectedClutch.getPrice()));
            clutchWeightTextField.setText(String.valueOf(selectedClutch.getWeight()));
            clutchStatusTextField.setText(selectedClutch.getStatus() ? "Zwolnione" : "Wciśnięte");
        } else {
            // Właściwości samochodu
            carModelTextField.setText("");
            carRegisterNumberTextField.setText("");
            carWeightTextField.setText("");
            carSpeedTextField.setText("");
            // Właściwości Silnika
            engineNameTextField.setText("");
            enginePriceTextField.setText("");
            engineWeightTextField.setText("");
            engineRPMTextField.setText("");
            // Właściwości Skrzyni biegów
            gearboxNameTextField.setText("");
            gearboxWeightTextField.setText("");
            gearboxPriceTextField.setText("");
            gearboxGearTextField.setText("");
            // Właściwości Sprzęgła
            clutchNameTextField.setText("");
            clutchPriceTextField.setText("");
            clutchWeightTextField.setText("");
            clutchStatusTextField.setText("");
        }
    }

    @Override
    public void update() {
        refresh();
    }

    public void onStartButton(ActionEvent actionEvent) {
        try {
            selectedCar.turnOn();
            refresh();
        } catch (NullPointerException npe) {
            showErrorWindow("Wybierz samochód");
        }
    }

    public void onStopButton(ActionEvent actionEvent) {
        try {
            selectedCar.turnOff();
            refresh();
        } catch (NullPointerException npe) {
            showErrorWindow("Wybierz samochód");
        }
    }

    public void onUpshiftGearButton(ActionEvent actionEvent) {
        try {
            selectedCar.increaseGear();
            refresh();
        } catch (NullPointerException npe) {
            showErrorWindow("Wybierz samochód");
        } catch (Gearbox.GearboxError e) {
            showErrorWindow("Najpierw naciśnij sprzęgło");
        }
    }

    public void onDownshiftGearButton(ActionEvent actionEvent) {
        try {
            selectedCar.decreaseGear();
            refresh();
        } catch (NullPointerException npe) {
            showErrorWindow("Wybierz samochód");
        } catch (Gearbox.GearboxError e) {
            showErrorWindow("Najpierw naciśnij sprzęgło");
        }
    }

    public void onPushGasButton(ActionEvent actionEvent) {
        try {
            selectedCar.increaseRPM();
            refresh();
        } catch (NullPointerException npe) {
            showErrorWindow("Wybierz samochód");
        }
    }

    public void onReleaseGasButton(ActionEvent actionEvent) {
        try {
            selectedCar.decraseRPM();
            refresh();
        } catch (NullPointerException npe) {
            showErrorWindow("Wybierz samochód");
        }
    }

    public void onPushClutchButton(ActionEvent actionEvent) {
        try {
            selectedCar.pressClutch();
            refresh();
        } catch (NullPointerException npe) {
            showErrorWindow("Wybierz samochód");
        }
    }

    public void onReleaseClutchButton(ActionEvent actionEvent) {
        try {
            selectedCar.releaseClutch();
            refresh();
        } catch (NullPointerException npe) {
            showErrorWindow("Wybierz samochód");
        }
    }

    public void onNewCarButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("newCar.fxml"));
        Scene scene = new Scene(loader.load());

        AddCarController controller = loader.getController();

        controller.setMainAppController(this);

        Stage stage = new Stage();
        stage.setTitle("Dodaj nowy samochód");
        stage.setScene(scene);
        stage.show();
    }

    public void onDeleteCarButton(ActionEvent actionEvent) {
        if (selectedCar != null) {
            carImageView.setVisible(false);
            selectedCar.interrupt();
            carList.remove(selectedCar);
            selectedCar = null;
        }
    }

    public void onMouseClickedMap(MouseEvent mouseEvent) {
        if (selectedCar != null) {
            Position destination = new Position(mouseEvent.getX(), mouseEvent.getY());
            selectedCar.setDestination(destination);
        }
    }

    public void onselectCarComboBox(ActionEvent actionEvent) {
        selectedCar = selectCarComboBox.getSelectionModel().getSelectedItem();
        carImageView.setVisible(true);
        refresh();
    }

    public void addNewCar(Car car) {
        carList.add(car);
        car.start();
    }

    public void showErrorWindow(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Błąd");
        alert.setHeaderText(null);
        alert.setContentText(error);
        alert.showAndWait();
    }

}
