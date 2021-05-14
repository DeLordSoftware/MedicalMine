/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import static Controllers.SelectInputDataController.bHasCsvFile;
import MedicalMineFxMain.ParseInputFiles;
import MedicalMineFxMain.ProcessInputFiles;
import static MedicalMineFxMain.ProcessInputFiles.displayMsg;
import MedicalMineFxMain.UtlityClass;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import javax.swing.JOptionPane;

/**
 *
 * @author RW Simmons
 */
public class CreateFileClass {

    private static File flCsvSearchData = null;
    private static Map<String, ArrayList> mpCreateFile;
    private static Map<Integer, ArrayList> mpNewCreateFile;
    private static ArrayList<String> lstNewSearchWords;
    private static ArrayList<String> lstSearchWords;
    private static String strFileName;
    private static String strCatogeryName;
    private static boolean bHasBeenInitialize = false;

    final static int FIRST_ROW = 0;

    CreateFileClass() {
    }

    /**
     * ***************************************
     * Method : Input : Return : Purpose: *************************************
     */
    public static void initialize() {
        mpCreateFile = new LinkedHashMap<String, ArrayList>();
        lstSearchWords = new ArrayList<String>();

        bHasBeenInitialize = true;
        System.out.println(bHasBeenInitialize);
    }

    /**
     * ***************************************
     * Method : Input : Return : Purpose: *************************************
     */
    public static void setCatogery(String catogery) {
        strCatogeryName = catogery;
        mpCreateFile.put(strCatogeryName, lstSearchWords);
        System.out.println("------------Set catogery = " + strCatogeryName);
    }

    /**
     * ***************************************
     * Method : Input : Return : Purpose: *************************************
     */
    public static String getCategory() {
        return strCatogeryName;
    }

    /**
     * ***************************************
     * Method : Input : Return : Purpose: *************************************
     */
    public static void addNewCategory() {
        lstSearchWords = new ArrayList<String>();
    }

    /**
     * ***************************************
     * Method : Input : Return : Purpose: *************************************
     */
    public static void setCatogeryWord(String word) {
        if (bHasBeenInitialize && !strCatogeryName.isEmpty()) {
            mpCreateFile.get(strCatogeryName).add(word);
            System.out.println("------------setCatogeryWord " + word);
        }
        tester();
    }

    /**
     * ***************************************
     * Method : Input : Return : Purpose: *************************************
     */
    public static void setFileName(String name) {
        if (!name.isEmpty() && bHasBeenInitialize) {
            strFileName = name;
            System.out.println("------------strFileName  = " + strFileName);
        } else {
            System.out.println("initialized " + bHasBeenInitialize + " and text value" + strFileName);
        }
    }

    /**
     * ***************************************
     * Method : Input : Return : Purpose: *************************************
     */
    public static String getFileName() {
        System.out.println("------------------getFileName " + strFileName);
        return " " + strFileName + ".csv";
    }

    /**
     * ***************************************
     * Method : Input : Return : Purpose: *************************************
     */
    public static boolean isFileMapComplete() {
        System.out.println(mpCreateFile.size());
        return !mpCreateFile.isEmpty();
    }

    /**
     * ***************************************
     * Method : Input : Return : Purpose: *************************************
     */
    public static void WriteToFile() {
        convertRowsToColumnMap();
        FileWriter wrtCreateCsvFile = null;        
        String strCsvSearchData = UtlityClass.RESULT_FOLDER_LOC + UtlityClass.CVS_FILE_LOC + strFileName + ".csv";
        try {
            wrtCreateCsvFile = new FileWriter(strCsvSearchData);
            // Cycle through newly created map of data 
            Iterator<Map.Entry<Integer, ArrayList>> itr = mpNewCreateFile.entrySet().iterator();

            while (itr.hasNext()) {
                Map.Entry<Integer, ArrayList> entry = itr.next();
                ArrayList list = entry.getValue();
                // Place data in csv file
                for (int ii = 0; ii < list.size(); ii++) {
                    String value = (String) list.get(ii);
                    wrtCreateCsvFile.write(value + ",");
                    System.out.println("Map Value = " + value);
                }
                // Go to next line
                wrtCreateCsvFile.write("\n");
            }

            wrtCreateCsvFile.close();
            System.out.println("Successfully wrote to the file.");

            // Create file to display in select input data class
            flCsvSearchData = new File(strCsvSearchData);

        } catch (Exception e) {
            System.out.println("Error in CreateFileClass in method WriteToFile : " + e.toString());
            displayMsg(e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * ***************************************
     * Method : Input : Return : Purpose: *************************************
     */
    private static void convertRowsToColumnMap() {
        mpNewCreateFile = new LinkedHashMap<Integer, ArrayList>();
        lstSearchWords = new ArrayList<String>();
        int iiNumberOfRows = setFirstRowNewMap();
        try {
            // Populate new map  
            ArrayList<String> lstSearchWord;
            for (int ii = 1; ii <= iiNumberOfRows; ii++) {
                lstSearchWord = new ArrayList<String>();
                Iterator<Map.Entry<String, ArrayList>> itrMap = mpCreateFile.entrySet().iterator();
                while (itrMap.hasNext()) {
                    Map.Entry<String, ArrayList> entry = itrMap.next();
                    ArrayList list = entry.getValue();
                    if (list.size() >= ii) {
                        String strSearchWord = (String) list.get(ii - 1);
                        lstSearchWord.add(strSearchWord);
                        System.out.println("Index " + ii + " Search word " + strSearchWord);//                
                    } else {
                        lstSearchWord.add("");
                    }
                }

                mpNewCreateFile.put(ii, lstSearchWord);
                System.out.println("**New Row**");
            }

            testerNewMap();
        } catch (Exception e) {
            System.out.println("Error in CreateFileClass in method convertRowsToColumnMap : " + e.toString());
        }
    }

    /**
     * ***************************************
     * Method : Input : Return : Purpose: *************************************
     */
    private static int setFirstRowNewMap() {

        // Get First row which is category names
        Iterator<Map.Entry<String, ArrayList>> itr = mpCreateFile.entrySet().iterator();
        ArrayList lstKeyCategory = new ArrayList<String>();
        int current = 0;
        int iNumOfRows = 0;
        try {
            while (itr.hasNext()) {
                // Get First row which is category names (keys)
                Map.Entry<String, ArrayList> entry = itr.next();
                lstKeyCategory.add(entry.getKey());

                // Get longest array to determine number of rows
                current = entry.getValue().size();
                if (current > iNumOfRows) {
                    iNumOfRows = current;
                }
            }

            // Set up new map with each row
            lstNewSearchWords = new ArrayList<String>();

            for (int ii = 0; ii < iNumOfRows; ii++) {
                lstNewSearchWords = new ArrayList<String>();
                mpNewCreateFile.put(ii, lstNewSearchWords);
            }

            // Set first row with the category data 
            if (!mpNewCreateFile.isEmpty()) {
                ArrayList<String> lstFristRow = mpNewCreateFile.get(0);
                for (int jj = 0; jj < lstKeyCategory.size(); jj++) {
                    lstFristRow.add((String) lstKeyCategory.get(jj));
                }
            } else {

            }
            mpNewCreateFile.put(FIRST_ROW, lstKeyCategory);
        } catch (Exception e) {
            System.out.println("Error in CreateFileClass in method setFirstRowNewMap : " + e.toString());
        }
        //testerList(lstFristRow);
        return iNumOfRows;
    }

    /**
     * ***************************************
     * Method : Input : Return : Purpose: *************************************
     */
    public static File setSearchFileForProcessing() {
        // This is error check... TODO: create a catch
        if (flCsvSearchData.exists()) {
            // Set search file for processing
            if (ParseInputFiles.setCsvSearchData(flCsvSearchData)) {
                System.out.println("File exist " + flCsvSearchData.getAbsolutePath().toString());
                bHasCsvFile = true;
                // TODO: Send string name of file to lblShowCsv
                return flCsvSearchData;
            }
        } else {
            System.out.println("NO File exist ");
        }
        return null;
    }

    private static void tester() {

        // using iterators 
        Iterator<Map.Entry<String, ArrayList>> itr = mpCreateFile.entrySet().iterator();

        while (itr.hasNext()) {
            Map.Entry<String, ArrayList> entry = itr.next();
            System.out.println("Key = " + entry.getKey());
            ArrayList list = entry.getValue();

            for (int ii = 0; ii < list.size(); ii++) {
                String value = (String) list.get(ii);
                System.out.println("Value = " + value);
            }
        }
    }

    private static void testerNewMap() {

        // using iterators 
        Iterator<Map.Entry<Integer, ArrayList>> itr = mpNewCreateFile.entrySet().iterator();
        System.out.println("New Map");
        while (itr.hasNext()) {
            Map.Entry<Integer, ArrayList> entry = itr.next();
            System.out.println("Map Key = " + entry.getKey());
            ArrayList list = entry.getValue();

            for (int ii = 0; ii < list.size(); ii++) {
                String value = (String) list.get(ii);
                System.out.println("Map Value = " + value);
            }
        }
    }
}
