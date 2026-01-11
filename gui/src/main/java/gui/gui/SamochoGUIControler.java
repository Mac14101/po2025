package gui.gui;

import gui.symulator.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.Objects;

public class SamochoGUIControler {
    private static ObservableList<Samochod> samochody =
            FXCollections.observableArrayList();
    public VBox map;
    public TextField carModelTextField;
    public TextField carRegisterNumberTextField;
    public TextField carWeightTextField;
    public Label carSpeedTextField;
    public TextField gearboxGearTextField;
    public TextField gearboxWeightTextField;
    public TextField gearboxNameTextField;
    public TextField gearboxPriceTextField;
    public TextField engineNameTextField;
    public TextField enginePriceTextField;
    public TextField engineWeightTextField;
    public TextField engineRPMTextField;
    public TextField clutchStatusTextField;
    public TextField clutchWeightTextField;
    public TextField clutchPriceTextField;
    public TextField clutchNameTextField;
    private Samochod aktywnySamochod;
    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;
    @FXML
    private Button upshiftGearButton;
    @FXML
    private Button downshiftGearButton;
    @FXML
    private Button pushGasButton;
    @FXML
    private Button releaseGasButton;
    @FXML
    private Button pushClutchButton;
    @FXML
    private Button releaseClutchButton;
    @FXML
    private ComboBox selectCarComboBox;
    @FXML
    private Button newCarButton;
    private ImageView carImageView = new ImageView();

    public static void dodajSamochod(Samochod samochod) {
        samochody.add(samochod);
    }

    @FXML
    private void onStartButton() {
        try {
            aktywnySamochod.wlacz();
        } catch (NullPointerException e) {
            showErrorWindow("Wybierz samochód");
        } finally {
            this.refresh();
        }
    }

    @FXML
    private void onStopButton() {
        try {
            aktywnySamochod.wylacz();
        } catch (NullPointerException e) {
            showErrorWindow("Wybierz samochód");
        } finally {
            this.refresh();
        }
    }

    @FXML
    private void onUpshiftGearButton() {
        try {
            aktywnySamochod.zwiekszBieg();
        } catch (NullPointerException e) {
            showErrorWindow("Wybierz samochód");
        } finally {
            this.refresh();
        }
    }

    @FXML
    private void onDownshiftGearButton() {
        try {
            aktywnySamochod.zmniejszBieg();
        } catch (NullPointerException e) {
            showErrorWindow("Wybierz samochód");
        } finally {
            this.refresh();
        }
    }

    @FXML
    private void onPushGasButton() {
        try {
            aktywnySamochod.dodajGazu();
        } catch (NullPointerException e) {
            showErrorWindow("Wybierz samochód");
        } finally {
            this.refresh();
        }
    }

    @FXML
    private void onReleaseGasButton() {
        try {
            aktywnySamochod.ujmijGazu();
        } catch (NullPointerException e) {
            showErrorWindow("Wybierz samochód");
        } finally {
            this.refresh();
        }
    }

    @FXML
    private void onPushClutchButton() {
        try {
            aktywnySamochod.nacisnijSprzeglo();
        } catch (NullPointerException e) {
            showErrorWindow("Wybierz samochód");
        } finally {
            this.refresh();
        }
    }

    @FXML
    private void onReleaseClutchButton() {
        try {
            aktywnySamochod.zwolnijsprezglo();
        } catch (NullPointerException e) {
            showErrorWindow("Wybierz samochód");
        } finally {
            this.refresh();
        }
    }

    @FXML
    private void onNewCarButton() throws IOException {
        this.openNewCarWindow();
        this.refresh();
    }

    private void refresh() {
        if (this.aktywnySamochod == null) {
            this.carModelTextField.setText("");
            this.carRegisterNumberTextField.setText("");
            this.carWeightTextField.setText("");
            this.carSpeedTextField.setText("");
            this.gearboxNameTextField.setText("");
            this.gearboxPriceTextField.setText("");
            this.gearboxWeightTextField.setText("");
            this.gearboxGearTextField.setText("");
            this.engineNameTextField.setText("");
            this.engineRPMTextField.setText("");
            this.engineWeightTextField.setText("");
            this.enginePriceTextField.setText("");
            this.clutchNameTextField.setText("");
            this.clutchPriceTextField.setText("");
            this.clutchStatusTextField.setText("");
            this.clutchWeightTextField.setText("");
        } else {
            this.carModelTextField.setText(this.aktywnySamochod.getModel());
            this.carRegisterNumberTextField.setText(this.aktywnySamochod.getNrRejestr());
            this.carWeightTextField.setText("1000");
            this.carSpeedTextField.setText(String.valueOf(this.aktywnySamochod.getAktPredkosc()));
            SkrzyniaBiegow skrzyniaBiegow = this.aktywnySamochod.getSkrzyniaBiegow();
            this.gearboxNameTextField.setText(skrzyniaBiegow.getNazwa());
            this.gearboxPriceTextField.setText(String.valueOf(skrzyniaBiegow.getCena()));
            this.gearboxWeightTextField.setText(String.valueOf(skrzyniaBiegow.getWaga()));
            this.gearboxGearTextField.setText(String.valueOf(skrzyniaBiegow.getAktualnyBieg()));
            Silnik silnik = this.aktywnySamochod.getSilnik();
            this.engineNameTextField.setText(silnik.getNazwa());
            this.engineRPMTextField.setText(String.valueOf(silnik.getObroty()));
            this.engineWeightTextField.setText(String.valueOf(silnik.getWaga()));
            this.enginePriceTextField.setText(String.valueOf(silnik.getCena()));
            Sprzeglo sprzeglo = this.aktywnySamochod.getSkrzyniaBiegow().getSprzeglo();
            this.clutchNameTextField.setText(sprzeglo.getNazwa());
            this.clutchPriceTextField.setText(String.valueOf(sprzeglo.getCena()));
            this.clutchStatusTextField.setText(sprzeglo.getStanSprzegla() ? "zwolnione" : "naciśnięte");
            this.clutchWeightTextField.setText(String.valueOf(sprzeglo.getWaga()));
        }
    }

    private void openNewCarWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("newCar.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        stage.setTitle("Dodaj nowy samochód");
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    public void initialize() {
        System.out.println("HelloController initialized");
        // Load and set the car image
        Image carImage = new
                Image(Objects.requireNonNull(getClass().getResource("/car.jpg")).toExternalForm());
        System.out.println("Image width: " +
                carImage.getWidth() + ", height: " + carImage.getHeight());
        this.carImageView.setImage(carImage);
        this.carImageView.setFitWidth(carImage.getWidth());
        this.carImageView.setFitHeight(carImage.getHeight());
        this.carImageView.setTranslateX(100);
        this.carImageView.setTranslateY(100);
        this.carImageView.setVisible(true);
        map.setOnMouseClicked(event -> {
            double x = event.getX();
            double y = event.getY();
            Pozycja nowaPozycja = new Pozycja(x, y);
            aktywnySamochod.jedzDo(nowaPozycja);
        });
        selectCarComboBox.setItems(samochody);
        selectCarComboBox.setOnAction(event -> {
            aktywnySamochod = (Samochod) selectCarComboBox.getSelectionModel().getSelectedItem();
            refresh();
        });
        selectCarComboBox.setConverter(new StringConverter<Samochod>() {
            @Override
            public String toString(Samochod car) {
                if (car == null) return "";
                return car.getModel() + " " + car.getNrRejestr();
            }

            @Override
            public Samochod fromString(String string) {
                return null;
            }

        });
    }

    public void showErrorWindow(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Błąd");
        alert.setHeaderText(null);
        alert.setContentText(error);
        alert.showAndWait();
    }

}
