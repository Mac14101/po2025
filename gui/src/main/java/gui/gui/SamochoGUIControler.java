package gui.gui;

import gui.symulator.Samochod;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class SamochoGUIControler {
    private static final ArrayList<Samochod> samochody = new ArrayList<>();
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

    public static void dodajSamochod(Samochod samochod) {
        samochody.add(samochod);
    }

    @FXML
    private void onStartButton() {
        System.out.println("StartButton click.");
        this.refresh();
    }

    @FXML
    private void onStopButton() {
        System.out.println("StopButton click.");
        this.refresh();
    }

    @FXML
    private void onUpshiftGearButton() {
        System.out.println("UpshiftGearButton click");
        this.refresh();
    }

    @FXML
    private void onDownshiftGearButton() {
        System.out.println("DownshiftGearButton click");
        this.refresh();
    }

    @FXML
    private void onPushGasButton() {
        System.out.println("PushGasButton click");
        this.refresh();
    }

    @FXML
    private void onReleaseGasButton() {
        System.out.println("ReleaseGasButton click");
        this.refresh();
    }

    @FXML
    private void onPushClutchButton() {
        System.out.println("PushClutchButton click");
        this.refresh();
    }

    @FXML
    private void onReleaseClutchButton() {
        System.out.println("PushClutchButton click");
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
        System.out.println("Refresh");
    }

    private void openNewCarWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("newCar.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        stage.setTitle("Dodaj nowy samochód");
        stage.setResizable(false);
        stage.show();
    }
}
