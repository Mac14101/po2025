package gui.gui;

import gui.symulator.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class SamochoGUIControler {
    private static final ArrayList<Samochod> samochody = new ArrayList<>();
    public VBox map;
    public TextField carNameTextField;
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
        aktywnySamochod.wlacz();
        this.refresh();
    }

    @FXML
    private void onStopButton() {
        aktywnySamochod.wylacz();
        this.refresh();
    }

    @FXML
    private void onUpshiftGearButton() {
        aktywnySamochod.zwiekszBieg();
        this.refresh();
    }

    @FXML
    private void onDownshiftGearButton() {
        aktywnySamochod.zmniejszBieg();
        this.refresh();
    }

    @FXML
    private void onPushGasButton() {
        aktywnySamochod.dodajGazu();
        this.refresh();
    }

    @FXML
    private void onReleaseGasButton() {
        aktywnySamochod.ujmijGazu();
        this.refresh();
    }

    @FXML
    private void onPushClutchButton() {
        aktywnySamochod.nacisnijSprzeglo();
        this.refresh();
    }

    @FXML
    private void onReleaseClutchButton() {
        aktywnySamochod.zwolnijsprezglo();
        this.refresh();
    }

    @FXML
    private void onSelectCarComboBox() {
        System.out.println("SelectCarComboBox show");
    }

    @FXML
    private void onNewCarButton() throws IOException {
        this.openNewCarWindow();
    }

    private void refresh() {
        if (this.aktywnySamochod == null) {
            this.carNameTextField.setText("");
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
            this.carNameTextField.setText(this.aktywnySamochod.getName());
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
    }


}
