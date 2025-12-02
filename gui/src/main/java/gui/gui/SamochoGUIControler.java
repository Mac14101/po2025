package gui.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SamochoGUIControler {
    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;
    @FXML
    private Button buttonButton;

    @FXML
    private Button upshiftGearButton;
    @FXML
    private Button downshiftGearButton;

    @FXML
    private Button pushClutchButton;
    @FXML
    private Button releaseClutchButton;

    @FXML
    private void onStartButton() {
        System.out.println("StartButton click.");
    }

    @FXML
    private void onStopButton() {
        System.out.println("StopButton click.");
    }

    @FXML
    private void onButtonButton() {
        System.out.println("ButtonButton click.");
    }

    @FXML
    private void onUpshiftGearButton() {
        System.out.println("UpshiftGearButton click");
    }

    @FXML
    private void onDownshiftGearButton() {
        System.out.println("DownshiftGearButton click");
    }

    @FXML
    private void onPushClutchButton() {
        System.out.println("PushClutchButton click");
    }

    @FXML
    private void onReleaseClutchButton() {
        System.out.println("PushClutchButton click");
    }
}
