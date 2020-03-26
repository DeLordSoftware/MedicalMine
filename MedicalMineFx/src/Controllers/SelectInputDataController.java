/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import MedicalMineFxMain.ControlledScreen;
import MedicalMineFxMain.MedicalMineFx;
import MedicalMineFxMain.ProcessInputFiles;
import MedicalMineFxMain.ScreenController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
public class SelectInputDataController implements Initializable, ControlledScreen  {

    private static ScreenController screenController;  
    //private ProcessInputFiles processInputFiles;
    public static final int iCSV_FILE    = 0;
    public static final int iSEARCH_FILE = 1;
    public static boolean bHasCsvFile    = false;
    public static boolean bHasSearchFile = false;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    /*****************************************
    * Method : 
    * Input  : 
    * Return :  
    * Purpose:  
    ****************************************/
    @FXML
    private void actBtnCsvSelect(ActionEvent event) throws IOException {        
        
        String strDisplay  = ProcessInputFiles.selectProcessFiles(iCSV_FILE);
        
        // Enable process button  
        if(strDisplay != null){
            lblShowCsv.setText(strDisplay);                         
            if(bHasSearchFile && bHasCsvFile){
                btnProcess.setDisable(false);
            } 
        }
    }

    /*****************************************
    * Method : 
    * Input  : 
    * Return :  
    * Purpose:  
    ****************************************/
    @FXML
    private void actBtnFileSelect(ActionEvent event) throws IOException {      
        
        String strDisplay = ProcessInputFiles.selectProcessFiles(iSEARCH_FILE);
       
        // Enable process button      
        if (strDisplay != null) {
            lblShowText.setText(strDisplay);                      
            if(bHasSearchFile && bHasCsvFile) {
                 btnProcess.setDisable(false);
            } 
        }        
    }

    /*****************************************
    * Method : 
    * Input  : 
    * Return :  
    * Purpose:  
    ****************************************/
    @FXML
    private void actBtnExit(ActionEvent event) {
        System.exit(0);    
    }

    /*****************************************
    * Method : 
    * Input  : 
    * Return :  
    * Purpose:  
    ****************************************/   
    @FXML
    private void actBtnProcess(ActionEvent event) throws InterruptedException {  
       MedicalMineFx.showProgessPage();
       Thread.sleep(10);            
        
        Platform.runLater(new Runnable() {
            @Override
            public void run() { 
                ProcessInputFiles processInputFiles = new ProcessInputFiles();
                processInputFiles.processFiles();
                try {   
                    MedicalMineFx.closeProgessPage();
                } catch (InterruptedException ex) {
                    Logger.getLogger(SelectInputDataController.class.getName()).log(Level.SEVERE, null, ex);
                }               
            }
        });
    }

    /*****************************************
    * Method : 
    * Input  : 
    * Return :  
    * Purpose:  
    ****************************************/
    @Override
    public void setScreenParent(ScreenController screenPage) {            
        screenController = screenPage;
    }  

    
     
    @FXML
    private Button btnCsvSelect;
    @FXML
    private Button btnFileSelect;
    @FXML
    private Button btnExit;
    @FXML
    private Button btnProcess;
    @FXML
    private Label lblShowCsv;
    @FXML
    private Label lblShowText;
}
