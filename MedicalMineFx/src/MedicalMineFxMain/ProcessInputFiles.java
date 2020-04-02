/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MedicalMineFxMain;

import Controllers.SelectInputDataController;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author RW Simmons
 */
public class ProcessInputFiles extends SelectInputDataController {

    private static String strDisplayMessage;
    private static List<File> lstFileReturned;
    private static String strSaveCsvLocal = "";
    private static String strSaveFilesLocal = "";
    private final static String KEY_CSV_VAL = "CSV_Location";
    private final static String KEY_FILES_VAL = "FILE_Location";
    private final static String JSON_DATA_FILE = "MedicalMineData.json";

    /**
     *
     */
    public void processFiles() {
        try {
            Map<String, String> mpSaveToExcel = new LinkedHashMap();
            Map<Integer, Map<String, String>> mpFinalSaveToExcel = new LinkedHashMap();
            int iFileNum = 0;

            // Cycle through files seleced
            for (File file : lstFileReturned) {
                System.out.println("\n\n-----------------------File number " + iFileNum++ + " -----------------------");
                ParseInputFiles parseInputFiles = new ParseInputFiles(true);
                mpSaveToExcel = parseInputFiles.TextParsing(file);
                mpFinalSaveToExcel.put(iFileNum, mpSaveToExcel);
            }

            
            
            /* TODO: user select location of saved folder***
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File("C://"));

            DirectoryChooser directChooser = new DirectoryChooser();
            File fileA = directChooser.showDialog(stage);

            System.out.println(fileA.getAbsolutePath());
            String strResultFile = fileA.getAbsolutePath() + "\\Medical Mine Results.txt";
            //*/
            
            
            
            //Write data to Excel spreadsheet
            WriteDataToExcel writeDataToExcel = new WriteDataToExcel();
            String strReturnExcelLoc = writeDataToExcel.SaveExcelSpreadSheet(mpFinalSaveToExcel);

            // Create Gui message
            strDisplayMessage = "Number of files searched: " + iFileNum + ".\nProcess complete...\nExcel file located at C:/MedicialMineResults";
            displayMsg(strDisplayMessage, JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            System.out.println("IOException " + e.getMessage());
            displayMsg(e.getMessage(), JOptionPane.ERROR_MESSAGE);
        } catch (NullPointerException e) {
            System.out.println("NullPointerException " + e.getMessage());
            displayMsg(e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     *
     * @param isetValue
     * @return
     * @throws IOException
     */
    public static String selectProcessFiles(final int isetValue) throws IOException {

        FileChooser fileChooser = new FileChooser();
        String strDialogTitle = null;
        String strExtention = null;
        String strExtentionText = null;
        Stage stageParent = MedicalMineFx.getStage();
        File[] fileInput = null;
        String strDisplayFile = null;
        String strFilePath;
        String strFilePathFromDoc = null;
        String jsonKeyValue = null;

        // Set String values according to selection
        switch (isetValue) {
            case CSV_FILE:
                jsonKeyValue = KEY_CSV_VAL;
                strDialogTitle = "Select Search Criteria CSV";
                strExtentionText = "CSV Files";
                strExtention = "*.csv";
                fileInput = new File[1];
                break;
            case SEARCH_FILE:
                jsonKeyValue = KEY_FILES_VAL;
                strDialogTitle = "Select Input files to search";
                strExtentionText = "TXT Files";
                strExtention = "*.txt";
                break;
            default:
                return "ERROR";
        }

        try {
            // Set dialog parametes
            fileChooser.setTitle(strDialogTitle);
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(strExtentionText, strExtention));
            
            File fileJson = new File(JSON_DATA_FILE);

            if (fileJson.exists()) {
                // Get JSON data file
                FileReader reader = new FileReader(JSON_DATA_FILE);
                JSONParser jsonParser = new JSONParser();
                Object jsonObj = jsonParser.parse(reader);
                JSONObject locationJson = (JSONObject) jsonObj;

                if (locationJson.containsKey(jsonKeyValue)) {
                    // Display dialog with selected location
                    strFilePathFromDoc = locationJson.get(jsonKeyValue).toString();
                    fileChooser.setInitialDirectory(new File(strFilePathFromDoc));
                    fileInput = displayFileDialog(fileChooser, isetValue, fileInput, stageParent);
                } else {
                    //Display dialog with default location           
                    fileChooser.setInitialDirectory(new File("c:/"));
                    fileInput = displayFileDialog(fileChooser, isetValue, fileInput, stageParent);
                }
            } else {
                // Display dialog with default location  
                fileChooser.setInitialDirectory(new File("c:/"));
                fileInput = displayFileDialog(fileChooser, isetValue, fileInput, stageParent);
            }

            // Set label and process button
            if (fileInput != null) {
                strFilePath = fileInput[0].getAbsolutePath();

                // Get string to display file name or number of files in label
                int iSetPosition = strFilePath.lastIndexOf("\\") + 1;
                strDisplayFile = strFilePath.substring(iSetPosition, strFilePath.length());

                // Save file location and enable button
                strFilePath = strFilePath.substring(0, iSetPosition);
                switch (isetValue) {
                    case CSV_FILE:
                        strSaveCsvLocal = strFilePath;
                        bHasCsvFile = true;
                        strDisplayFile = " " + strDisplayFile;
                        break;
                    case SEARCH_FILE:
                        strSaveFilesLocal = strFilePath;
                        bHasSearchFile = true;
                        strDisplayFile = " Search through " + fileInput.length + " files";
                        break;
                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(ProcessInputFiles.class.getName()).log(Level.SEVERE, null, ex);
        }

        return strDisplayFile;
    }

    /**
     *
     * @param fileChooser
     * @param isetVal
     * @param fileInpt
     * @param stagePrat
     */
    private static File[] displayFileDialog(FileChooser fileChser, int isetVal, File[] fileInpt, Stage stagePrat) {

        switch (isetVal) {
            case CSV_FILE:
                fileInpt[0] = fileChser.showOpenDialog(stagePrat);
                // Store search critia file
                ParseInputFiles.setSearchData(fileInpt[0]);
                break;
            case SEARCH_FILE:
                List<File> fileList = fileChser.showOpenMultipleDialog(stagePrat);
                if (fileList != null) {
                    fileInpt = new File[fileList.size()];
                    for (int ii = 0; ii < fileList.size(); ii++) {
                        fileInpt[ii] = fileList.get(ii);
                    }
                    // Store search files
                    setListSearchFiles(fileList);
                }
                break;
        }

        return fileInpt;
    }

    /**
     * Store values for dialog location
     */
    public static void setJsonLocationFile() {
        // Set values
        JSONObject jsonLocation = new JSONObject();
        jsonLocation.put(KEY_CSV_VAL, strSaveCsvLocal);
        jsonLocation.put(KEY_FILES_VAL, strSaveFilesLocal);

        //Write JSON file
        try (FileWriter file = new FileWriter(JSON_DATA_FILE)) {
            file.write(jsonLocation.toString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Message dialog
     */
    public static void displayMsg(String str, int iMessage) {

        JFrame frame = null;
        String strTitle = null;
        int intMsg = 0;
        switch (iMessage) {
            case JOptionPane.ERROR_MESSAGE:
                intMsg = JOptionPane.ERROR_MESSAGE;
                strTitle = "Error Message";
                break;
            case JOptionPane.INFORMATION_MESSAGE:
                intMsg = JOptionPane.INFORMATION_MESSAGE;
                strTitle = "Information";
                break;
        }

        JOptionPane.showMessageDialog(frame, str, strTitle, intMsg);
    }

    /**
     * ***************************************
     * Method : GetMessage Input : n/a Return : String Purpose: Return message to main ui **************************************
     */
    public String getMessage() {
        return strDisplayMessage;
    }

    /**
     * ***************************************
     * Method : Input : Return : Purpose: 
     **************************************
     */
    private static void setListSearchFiles(List<File> list) {
        lstFileReturned = list;
    }

}
