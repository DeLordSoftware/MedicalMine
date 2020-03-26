/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import MedicalMineFxMain.ControlledScreen;
import MedicalMineFxMain.MedicalMineFx;
import MedicalMineFxMain.ScreenController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;

/**
 * FXML Controller class
 *
 * @author RW Simmons
 */
public class ProcessFileDisplayController implements Initializable, ControlledScreen {
    private ScreenController screenController;  
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Button btnClose;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
      
    }    

    @Override
    public void setScreenParent(ScreenController screenPage) {        
        screenController = screenPage;
    }

    @FXML
    private void actBtnClose(ActionEvent event) {             
         screenController.setScreen(MedicalMineFx.MainScreen);
    }
    
    public void goBackToMian(){      
         btnClose.fire();              
    }
     
    public ProcessFileDisplayController getProgressObject(){
        return this;
    }   
}
