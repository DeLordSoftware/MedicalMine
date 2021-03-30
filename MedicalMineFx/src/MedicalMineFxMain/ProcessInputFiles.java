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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    protected final static String RESULT_FOLDER_LOC = "C:/Search Results/";
    protected static String resultTime;

    /**
     *
     */
    public void processFiles() {
        try {
            Map<String, String> mpSaveToExcel = new LinkedHashMap();
            Map<Integer, Map<String, String>> mpFinalSaveToExcel = new LinkedHashMap();
            int iFileNum = 0;

            // Set time stamp for files
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("_HH_mm_ss");
            LocalDateTime time = LocalDateTime.now();
            resultTime = dtf.format(time);

            // Set Custom list for searching
            CustomData.setCustomDataList();

            // Cycle through files (.txt) selected
            for (File file : lstFileReturned) {
                System.out.println("\n\n-----------------------File number " + iFileNum++ + " -----------------------");
                ParseInputFiles parseInputFiles = new ParseInputFiles(true);
                mpSaveToExcel = parseInputFiles.TextParsing(file);
                mpFinalSaveToExcel.put(iFileNum, mpSaveToExcel);
            }

            //Write data to Excel spreadsheet
            WriteDataToExcel writeDataToExcel = new WriteDataToExcel();
            String strReturnExcelLoc = writeDataToExcel.SaveExcelSpreadSheet(mpFinalSaveToExcel);

            // Create Gui message
            strDisplayMessage = "Number of files searched: " + iFileNum + ".\nProcess complete...\nExcel file located at " + RESULT_FOLDER_LOC;
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
     * @param iSetFileType
     * @return
     * @throws IOException
     */
    public static String selectProcessFiles(final int iSetFileType) throws IOException {
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
        switch (iSetFileType) {
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
                // TODO: create logic to know when csv was created by user in this GUI instead of selected by user
                if (locationJson.containsKey(jsonKeyValue)) {                    
                    strFilePathFromDoc = locationJson.get(jsonKeyValue).toString();
                    if (!strFilePathFromDoc.isEmpty()) {
                        // Display dialog with selected location
                        fileChooser.setInitialDirectory(new File(strFilePathFromDoc));
                    } else {
                        //Display dialog with default location           
                        fileChooser.setInitialDirectory(new File("c:/"));
                    }
                } else {
                    //Display dialog with default location           
                    fileChooser.setInitialDirectory(new File("c:/"));
                }
                // Open dialog box
                fileInput = displayFileDialog(fileChooser, iSetFileType, fileInput, stageParent);
            } else {
                // Display dialog with default location  
                fileChooser.setInitialDirectory(new File("c:/"));
                fileInput = displayFileDialog(fileChooser, iSetFileType, fileInput, stageParent);
            }

            // Set label and process button
            if (fileInput != null) {
                strFilePath = fileInput[0].getAbsolutePath();

                // Get string to display file name or number of files in label
                int iSetPosition = strFilePath.lastIndexOf("\\") + 1;
                strDisplayFile = strFilePath.substring(iSetPosition, strFilePath.length());

                // Save file location and enable button
                strFilePath = strFilePath.substring(0, iSetPosition);
                switch (iSetFileType) {
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
                if (!ParseInputFiles.setCsvSearchData(fileInpt[0])) {
                    // If error with input file, set file array to null
                    fileInpt = null;
                }
                break;
            case SEARCH_FILE:
                List<File> fileList = fileChser.showOpenMultipleDialog(stagePrat);
                if (fileList != null) {
                    // Store search files
                    fileInpt = new File[fileList.size()];
                    for (int ii = 0; ii < fileList.size(); ii++) {
                        fileInpt[ii] = fileList.get(ii);
                    }

                    setListSearchFiles(fileList);
                }
                break;
        }

        return fileInpt;
    }

    /**
     * Set current location of directory of selected file to allow dialog to open at that location when selected again
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
     *
     * @return
     */
    public String getMessage() {
        return strDisplayMessage;
    }

    /**
     *
     * @param list
     */
    private static void setListSearchFiles(List<File> list) {
        lstFileReturned = list;
    }

}
