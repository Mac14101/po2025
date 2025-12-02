package gui.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SamochoGUIControler {
    @FXML
    public Button startButton;
    @FXML
    public Button stopButton;
    @FXML
    public Button buttonButton;

    @FXML
    private void  onStartButton(){
        System.out.println("StartButton click.");
    }
    @FXML
    private void  onStopButton(){
        System.out.println("StopButton click.");
    }
    @FXML
    private void  onButtonButton(){
        System.out.println("ButtonButton click.");
    }
}
