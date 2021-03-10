/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors

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

    public static void initialize() {
        mpCreateFile = new LinkedHashMap<String, ArrayList>();
        lstSearchWords = new ArrayList<String>();

        bHasBeenInitialize = true;
        System.out.println(bHasBeenInitialize);
    }

    public static void setCatogery(String catogery) {
        strCatogeryName = catogery;
        mpCreateFile.put(strCatogeryName, lstSearchWords);
        System.out.println("------------Set catogery = " + strCatogeryName);
    }

    public static String getCategory() {
        return strCatogeryName;
    }

    public static void addNewCategory() {
        lstSearchWords = new ArrayList<String>();
    }

    public static void setCatogeryWord(String word) {
        if (bHasBeenInitialize && !strCatogeryName.isEmpty()) {
            mpCreateFile.get(strCatogeryName).add(word);
            System.out.println("------------setCatogeryWord " + word);
        }
        tester();
    }

    public static void setFileName(String name) {
        if (!name.isEmpty() && bHasBeenInitialize) {
            strFileName = name;
            System.out.println("------------strFileName  = " + strFileName);
        } else {
            System.out.println("initialized " + bHasBeenInitialize + " and text value" + strFileName);
        }
    }

    public static String getFileName() {
        System.out.println("------------------getFileName " + strFileName);
        return strFileName;
    }

    public static boolean isFileMapComplete() {
        System.out.println(mpCreateFile.size());
        return !mpCreateFile.isEmpty();
    }

    public static void WriteToFile() {
        convertRowsToColumnMap();
        FileWriter wrtCreateCsvFile = null;
        String strCsvSearchData = strFileName + ".csv";
        try {
            wrtCreateCsvFile = new FileWriter(strCsvSearchData);
            // using iterators 
            Iterator<Map.Entry<Integer, ArrayList>> itr = mpNewCreateFile.entrySet().iterator();

            while (itr.hasNext()) {
                Map.Entry<Integer, ArrayList> entry = itr.next();
                ArrayList list = entry.getValue();

                for (int ii = 0; ii < list.size(); ii++) {
                    String value = (String) list.get(ii);
                    wrtCreateCsvFile.write(value + ",");
                    System.out.println("Map Value = " + value);
                }
                wrtCreateCsvFile.write("\n");
            }

            wrtCreateCsvFile.close();
            System.out.println("Successfully wrote to the file.");
            
            // Create file to display in select input data class
            flCsvSearchData = new File(strCsvSearchData);
            
            if (flCsvSearchData.exists()){                
                System.out.println("File exist " +  flCsvSearchData.getAbsolutePath().toString());
            }
           
        } catch (Exception e) {
            System.out.println("Error in CreateFileClass in method WriteToFile : " + e.toString());
        }

    }

    private static void convertRowsToColumnMap() {
        mpNewCreateFile = new LinkedHashMap<Integer, ArrayList>();
        lstSearchWords = new ArrayList<String>();
        int iiNumberOfRows = setFirstRowNewMap();
        try {
            // Populate new map  
            ArrayList<String> lstSearchWord;
            for (int ii = 1; ii <= iiNumberOfRows; ii++) {
                lstSearchWord = new ArrayList<String>();
                Iterator<Map.Entry<String, ArrayList>> itr2 = mpCreateFile.entrySet().iterator();
                while (itr2.hasNext()) {
                    Map.Entry<String, ArrayList> entry = itr2.next();
                    ArrayList list = entry.getValue();
                    if (list.size() >= ii) {
                        String strSearchWord = (String) list.get(ii-1);
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
    
    public File getSearchFile(){
        return flCsvSearchData;
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
