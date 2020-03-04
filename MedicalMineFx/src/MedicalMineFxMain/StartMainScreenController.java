/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MedicalMineFxMain;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author RW Simmons
 */
public class StartMainScreenController implements Initializable, ControlledScreen {

    @FXML
    private Button btnCreate;
    @FXML
    private Button btnLoad;
    @FXML
    private Button btnExit;   
    @FXML
    private Label lblMedicalMine;
    
    ScreenController screenController;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setScreenParent(ScreenController screen){
        screenController = screen;
    }

    @FXML
    private void BtnCreateAction(ActionEvent event) {
        screenController.setScreen(MedicalMineFx.CategoryScreen);
    }

    @FXML
    private void btnLoadAction(ActionEvent event) {
    }

    @FXML
    private void btnExitAction(ActionEvent event) {
        System.exit(0);
    }
    
}
