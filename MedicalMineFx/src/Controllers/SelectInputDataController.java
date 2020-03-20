/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import MedicalMineFxMain.ControlledScreen;
import MedicalMineFxMain.MedicalMineFx;
import MedicalMineFxMain.ParseInputFiles;
import MedicalMineFxMain.ProcessInputFiles;
import MedicalMineFxMain.ScreenController;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author RW Simmons
 */
public class SelectInputDataController implements Initializable, ControlledScreen  {

    private ScreenController screenController;  
    private ProcessInputFiles processInputFiles;
    private final int iCSV_FILE    = 0;
    private final int iSEARCH_FILE = 1;
    private boolean bHasCsvFile    = false;
    private boolean bHasSearchFile = false;
     
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    private String selectProcessFiles( final int isetValue) throws IOException {    
        
        FileChooser fileChooser = new FileChooser();  
        String strFileName      = null;
        String strDialogTitle   = null;
        String strExtention     = null;
        String strExtentionText = null;     
        Stage stageParent       = MedicalMineFx.getStage();
        File[] fileInput        = null;
        String strDisplayFile   = null;
        String strFilePath;
        String strFilePathFromDoc = null;
        
        // Set String values according to selection
        switch (isetValue){
            case iCSV_FILE:
                strFileName      = "LocationCsv.txt";
                strDialogTitle   = "Select Search Criteria CSV";
                strExtentionText = "CSV Files";
                strExtention     = "*.csv";
                fileInput        = new File[1];
                break;
            case iSEARCH_FILE:
                strFileName      = "LocationInputFiles.txt";      
                strDialogTitle   = "Select Input files to search";
                strExtentionText = "TXT Files";
                strExtention     = "*.txt";                
                break;
            default:
                return "ERROR";
        }
        
        File fileCsv =  new File(strFileName);       
       
        try {       
            fileChooser.setTitle(strDialogTitle);
            fileChooser.getExtensionFilters().addAll(new ExtensionFilter(strExtentionText, strExtention));
            
            //Check to see if pervious location has been saved            
            if(fileCsv.exists()){
                // Read file and open dialog 
                Scanner scanCsv = new Scanner(fileCsv);
                if (scanCsv.hasNextLine()){
                    strFilePathFromDoc = scanCsv.nextLine();
                    fileChooser.setInitialDirectory(new File(strFilePathFromDoc));
                    switch (isetValue){
                        case iCSV_FILE:
                            fileInput[0] = fileChooser.showOpenDialog(stageParent);    
                            ParseInputFiles.setSearchData(fileInput[0]); 
                            break;
                        case iSEARCH_FILE:
                            List<File> fileList = fileChooser.showOpenMultipleDialog(stageParent);
                            if(fileList != null){
                                fileInput = new File[fileList.size()];
                                // fileInput = (File[]) fileList.toArray(); // Doesn't work... research.
                                for ( int ii = 0; ii < fileList.size(); ii++){
                                    fileInput[ii] = fileList.get(ii);
                                }
                                
                                // Store search files in ProcessInputFiles class
                                ProcessInputFiles.setListSearchFiles(fileList);
                            } 
                            break;
                    } 
                    scanCsv.close(); 
                }              
            } else {
                // Open file dialog at default location                
                fileChooser.setInitialDirectory(new File("c:/"));
                switch (isetValue){
                    case iCSV_FILE:
                        fileInput[0] = fileChooser.showOpenDialog(stageParent);  
                        ParseInputFiles.setSearchData(fileInput[0]);   
                        break;
                    case iSEARCH_FILE:
                        List<File> fileList = fileChooser.showOpenMultipleDialog(stageParent);
                            if(fileList != null){
                                fileInput = new File[fileList.size()];
                                // fileInput = (File[]) fileList.toArray(); // Doesn't work... research.
                                for ( int ii = 0; ii < fileList.size(); ii++){
                                    fileInput[ii] = fileList.get(ii);
                                }
                                
                                // Store search files in ProcessInputFiles class
                                ProcessInputFiles.setListSearchFiles(fileList);
                            }                          
                        break;
                } 
            }           
            
            // Collect/Parse data from csv file (Search Criteria)
            if(fileInput !=null){
                strFilePath = fileInput[0].getAbsolutePath();               

                // Display file name in label
                int iSetPosition = strFilePath.lastIndexOf("\\")+1;
                strDisplayFile = strFilePath.substring(iSetPosition, strFilePath.length());              

                // Save file location (if different from previous file location)
                strFilePath = strFilePath.substring(0, iSetPosition); 
                if(!strFilePath.equals(strFilePathFromDoc)){
                    FileWriter flWriteConfig = new FileWriter(new File(strFileName));
                    flWriteConfig.write(strFilePath);
                    flWriteConfig.close();
                    System.out.println(strFilePath);
                } 
                
                // Enable process button
                switch (isetValue){
                    case iCSV_FILE:
                        bHasCsvFile = true;
                        break;
                    case iSEARCH_FILE:
                        bHasSearchFile = true;
                        strDisplayFile = " Search through " + fileInput.length + " files";
                        break;
                } 
            }
        } catch (IOException ex) {
            Logger.getLogger(SelectInputDataController.class.getName()).log(Level.SEVERE, null, ex);        
        }
        
        return strDisplayFile;
    }
    

    @FXML
    private void actBtnCsvSelect(ActionEvent event) throws IOException {        
        
        String strDisplay  = selectProcessFiles(iCSV_FILE);
        // Enable process button  
        if(strDisplay != null){
            lblShowCsv.setText(strDisplay);                         
            if(bHasSearchFile && bHasCsvFile){
                btnProcess.setDisable(false);
            } 
        }
    }

    @FXML
    private void actBtnFileSelect(ActionEvent event) throws IOException {      
        
        String strDisplay = selectProcessFiles(iSEARCH_FILE);
       
        // Enable process button      
        if (strDisplay != null) {
            lblShowText.setText(strDisplay);                      
            if(bHasSearchFile && bHasCsvFile){
                 btnProcess.setDisable(false);
            } 
        }        
    }

    @FXML
    private void actBtnExit(ActionEvent event) {
        System.exit(0);    
    }

    @FXML
    private void actBtnProcess(ActionEvent event) {        
        ProcessInputFiles.processFiles();      
    }

    @Override
    public void setScreenParent(ScreenController screenPage) {       
        screenController = screenPage;
    }    
    
}
