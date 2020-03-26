/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MedicalMineFxMain;

import Controllers.SelectInputDataController;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author RW Simmons
 */
public class ProcessInputFiles extends SelectInputDataController {    

    private static String strDisplayMessage;
    private static List<File> lstFileReturned; 
    private Thread processThread;
    
    /********************************     
    * Method : Process search
    * Input  : 
    * Return :  
    * Purpose: 
    ****************************************/
    public  void processFiles(){
//        try { 
//            processThread.wait();
//        } catch (InterruptedException ex) {
//            Logger.getLogger(ProcessInputFiles.class.getName()).log(Level.SEVERE, null, ex);
//        }
         
        try{
            Map<String, String> mpSaveToExcel = new LinkedHashMap() ;
            Map<Integer, Map<String,String> > mpFinalSaveToExcel = new LinkedHashMap(); 
            int iFileNum = 0; 
            
            // Cycle through files seleced
            for(File file : lstFileReturned) {               
                System.out.println("\n\n-----------------------File number " +  iFileNum++ + " -----------------------");                
                ParseInputFiles parseInputFiles = new ParseInputFiles(true);        
                mpSaveToExcel = parseInputFiles.TextParsing(file); 
                mpFinalSaveToExcel.put(iFileNum, mpSaveToExcel);
            }

            //Write data to Excel spreadsheet
            WriteDataToExcel writeDataToExcel = new WriteDataToExcel();
            String strReturnExcelLoc = writeDataToExcel.SaveExcelSpreadSheet(mpFinalSaveToExcel);
            
            // Create Gui message
            strDisplayMessage = "Number of files searched: " + iFileNum  + ".\nProcess complete...\nExcel file located at C:/MedicialMineResults";           
            displayMsg(strDisplayMessage, JOptionPane.INFORMATION_MESSAGE);            
        } catch(IOException e) {
            System.out.println("IOException " + e.getMessage());
            displayMsg(e.getMessage(),JOptionPane.ERROR_MESSAGE);
        } catch(NullPointerException e) {
            System.out.println("NullPointerException " + e.getMessage());
            displayMsg(e.getMessage(),JOptionPane.ERROR_MESSAGE);
        }        
    }
    
    /*****************************************
    * Method : 
    * Input  : 
    * Return :  
    * Purpose: 
    ****************************************/  
    public static String selectProcessFiles( final int isetValue) throws IOException {    
        
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
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(strExtentionText, strExtention));
            
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
                                setListSearchFiles(fileList);
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
                                setListSearchFiles(fileList);
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
    
    /********************************
     * Message dialog
     *******************************/   
    public static void displayMsg(String str, int iMessage)
    {
        JFrame frame = null; 
        String strTitle = null;
        int intMsg = 0;
        switch(iMessage)
        {         
            case JOptionPane.ERROR_MESSAGE :
                intMsg = JOptionPane.ERROR_MESSAGE;
                strTitle = "Error Message";
                break; 
            case JOptionPane.INFORMATION_MESSAGE :
                intMsg = JOptionPane.INFORMATION_MESSAGE;
                strTitle = "Information";
                break; 
        }
        
        JOptionPane.showMessageDialog(frame, str, strTitle, intMsg);
    }
    
    /*****************************************
    * Method : GetMessage
    * Input  : n/a
    * Return : String 
    * Purpose: Return message to main ui 
    ****************************************/
    public String getMessage() {    
        return strDisplayMessage;
    }
    
    /*****************************************
    * Method : 
    * Input  : 
    * Return :  
    * Purpose: 
    ****************************************/
    private static void setListSearchFiles(List<File> list){
        lstFileReturned = list;    }
  
}
    