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
    private Button upshiftGear;
    @FXML
    private Button downshiftGear;

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
    private void onUpshiftGear() {
        System.out.println("UpshiftGearButton");
    }

    @FXML
    private void onDownshiftGear() {
        System.out.println("DownshiftGearButton");
    }
}
