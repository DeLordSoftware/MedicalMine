/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MedicalMineFxMain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author RW Simmons
 */
public class ProcessInputFiles {    

    private static String strDisplayMessage;
    private static List<File> lstFileReturned; 
    
    /********************************
     * Process search
     *******************************/
    public static void processFiles(){
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
    public static void setListSearchFiles(List<File> list){
        lstFileReturned = list;
    }
}
    