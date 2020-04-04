/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import MedicalMineFxMain.ControlledScreen;
import MedicalMineFxMain.ScreenController;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private Button testBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Start progress bar... FIX: not working... fix    
        progressBar.setProgress(0.0);      
        Thread thread = new Thread(new ProgressBarThread());
        thread.start();
        //*/
    }       
    
    public void setProgressBar(){
        //progressBar.setProgress(0.0);
    }

    @Override
    public void setScreenParent(ScreenController screenPage) {        
        screenController = screenPage;
    }

    @FXML
    private void actTestBtn(ActionEvent event) {
        Thread thread = new Thread(new ProgressBarThread());
        thread.start();
    }
    
    class ProgressBarThread implements Runnable {
        @Override
        public void run() {
            for (int ii = 0 ; ii < 100 ; ii++){
                progressBar.setProgress(ii/100.0);
                //System.out.println("progress bar " + ii);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ProcessFileDisplayController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        }
    }
}
