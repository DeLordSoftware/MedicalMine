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

    private String strDisplayMessage;
    
    /********************************
     * Process search
     *******************************/
    public void processFiles(Stage stage){
        try{
            List<File> lstFileReturned = selectFiles(stage); 
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
            strDisplayMessage = "Number of files searched: " + iFileNum  + ".\nProcess complete...\nExcel file located at " + strReturnExcelLoc;
           
            //displayMsg("Data Collected",JOptionPane.INFORMATION_MESSAGE);
        } catch(IOException e) {
            System.out.println("IOException " + e.getMessage());
            displayMsg(e.getMessage(),JOptionPane.ERROR_MESSAGE);
        } catch(NullPointerException e) {
            System.out.println("NullPointerException " + e.getMessage());
            displayMsg(e.getMessage(),JOptionPane.ERROR_MESSAGE);
        }        
    }
    
    /********************************
     * Select files for search
     *******************************/
    private List<File> selectFiles(Stage stage) throws IOException
    {        
        final String strFilePath = "MedicalMineConf.txt";
        String strFolderOriginalLocation = null;
        List <File> FileReturned = null;
        
        try 
        {
            // Get current directory of application
            File currDir = new File(".");
            String path = currDir.getAbsolutePath();
            String strLocationOfConfigFile = path.substring(0, path.length() - 1) + strFilePath;    

            // Check to see if configuration file exist
            // Configuration file used to store pervious folder location of files selected.
            File flConfig = new File(strLocationOfConfigFile);
            boolean bFileExist = flConfig.exists();            
             
            if(bFileExist)
            {
                // If file exist, read info from config file
                BufferedReader bufferedReader;                
                bufferedReader = new BufferedReader(new FileReader(flConfig));
                
                while ((strFolderOriginalLocation = bufferedReader.readLine()) != null)
                {
                    System.out.println("file read " + strFolderOriginalLocation);
                    break;
                }
                
                bufferedReader.close();
            } 
            
            // If no config file or no data in config file, use default value
            if (!bFileExist || strFolderOriginalLocation == null)
            {
                strFolderOriginalLocation = "C:\\Users";
            }
            
            // Open file chooser with selected location
            boolean bcheckExtention = false;
            int showOpenDialog = -1;
            //JFileChooser fileChooser = null;
            FileChooser fileChooser = new FileChooser();
            int iTryCount = 0;
           
            FileReturned = fileChooser.showOpenMultipleDialog(stage); // Open dialog box for file selection
            String strLocationOfSelectedFiles = FileReturned.get(0).toString();
            int iVal = strLocationOfSelectedFiles.lastIndexOf("\\"); // Need to remove actual file to just get file path
            strLocationOfSelectedFiles = strLocationOfSelectedFiles.substring(0, iVal);

            // If no cofig file exist, create it
            if(!bFileExist)
            {
                // Get location of select data files and write to new config file
                FileWriter flWriteConfig = new FileWriter(new File(strLocationOfConfigFile));
                flWriteConfig.write(strLocationOfSelectedFiles);
                flWriteConfig.write(System.lineSeparator()); 
                flWriteConfig.close();
            }
            else if (bFileExist && !strFolderOriginalLocation.equals(strLocationOfSelectedFiles))
            {                
                // If file locations is different from Original location, replace location
                FileWriter flWriteConfig = new FileWriter(flConfig);
                flWriteConfig.write(strLocationOfSelectedFiles);
                flWriteConfig.write(System.lineSeparator()); 
                flWriteConfig.close();
            }                       
        }
        catch (IOException e) 
        {
            e.printStackTrace();
            displayMsg(e.getMessage(),JOptionPane.ERROR_MESSAGE);
        }    
        
        return FileReturned;
    }
    
    /********************************
     * Message dialog
     *******************************/   
    public void displayMsg(String str, int iMessage)
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
    public String  getMessage()
    {    
        return strDisplayMessage;
    }
}
    