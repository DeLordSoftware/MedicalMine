/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MedicalMineFxMain;

import Controllers.SelectInputDataController;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * Class : TextParsingMedicalMine Purpose : To read selected text files and search for specific medical data. Author : Robert Wendell Simmons Created : 3/30/2018
 */
public class ParseInputFiles extends ProcessInputFiles {

    private static Map<String, List<String>> mpSearchData;
    private static int count = 1;
    private static boolean bCheckFileExistOnce = true;
    private boolean bDebug = false;   

    /**
     *
     * @param check
     */
    ParseInputFiles(boolean check) {
        bDebug = check;
    }

    /**
     *
     * @param flFileName
     * @return
     * @throws IOException
     */
    public Map<String, String> TextParsing(File flFileName) throws IOException {
        Map<String, String> mpSaveToExcel = new LinkedHashMap();
        FileReader flInputFile = null;
        FileWriter flOutputFile = null;

        // TODO: Have user selected location of result files
        /*
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C://"));        
        DirectoryChooser directChooser = new DirectoryChooser();
        File fileA = directChooser.showDialog(MedicalMineFx.getStage());         
        String strResultFile = fileA.getAbsolutePath() + "\\Medical Mine Results.txt";
         */
        String strResultFile = "C:/MedicalMineResults/Medical Mine Results.txt";

        File file = new File(strResultFile);
        file.mkdirs();

        try {
            // Remove output file if already created
            if (file.exists() && bCheckFileExistOnce) {
                file.delete();
                bCheckFileExistOnce = false;
            }

            // Create files
            flInputFile = new FileReader(flFileName);            // Input file            
            flOutputFile = new FileWriter(strResultFile, true); // Result file           
            String strFileName = flFileName.toString();

            // Retrieve absolute name of input file
            int value = strFileName.lastIndexOf("\\");
            strFileName = strFileName.substring(value + 1);

            // Print info to result file
            flOutputFile.write(System.lineSeparator());
            flOutputFile.write("----------------------- File Name: " + strFileName + " - File #" + count + " -----------------------");
            flOutputFile.write(System.lineSeparator());

            // Increment file number printed to return file
            count++;

            String strData = "";
            int iCharConverter;

            // Collect each character from file and put in variable for processing
            while ((iCharConverter = flInputFile.read()) != -1) {
                strData += ((char) iCharConverter);
            }

            // Set up parsing controls          
            final String strLookForPeriod = "[\r\n]";
            Pattern pattern = Pattern.compile(strLookForPeriod, Pattern.CASE_INSENSITIVE);
            String[] strArray = pattern.split(strData);

            // Verify that search data map has been populate
            if (mpSearchData == null) {
                displayMsg("Search data is null.", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            // Populate first column with file name
            strFileName = strFileName.replace(".txt", "");
            mpSaveToExcel.put("File Name", strFileName);

            List<String> lstTier1Search;
            int CategoryCounter = 1;

            // Loop through search data map of word and phrase searches
            for (Map.Entry<String, List<String>> itrCategory : mpSearchData.entrySet()) {
                String strCategory = itrCategory.getKey();

                // Check if custom data needs to be displayed
                CustomVals customVals = new CustomVals();
                if (strCategory.contains("#") || strCategory.contains("@")) {
                    customVals = CustomData.checkCustomData(strCategory);
                    strCategory = customVals.category;
                }
                
                // Pre set map for Excel
                mpSaveToExcel = SetMapForExcel(mpSaveToExcel, strCategory, "");

                // Print category text to result file
                flOutputFile.write(System.lineSeparator());// line feed
                flOutputFile.write(CategoryCounter + ") Category - " + strCategory + ":");
                flOutputFile.write(System.lineSeparator());// line feed
                flOutputFile.write(System.lineSeparator());// line feed

                // Print to console
                if (bDebug) {
                    System.out.println(CategoryCounter + ") Category -" + strCategory + ":");
                }

                CategoryCounter++;

                // Collect data from map
                lstTier1Search = itrCategory.getValue();

                // Search for each search word and phrase in line
                for (int ii = 0; ii < lstTier1Search.size(); ii++) {
                    String strFind = lstTier1Search.get(ii);

                    // Make sure value is contained in string 
                    if (!strFind.isEmpty()) {
                        boolean bDisplayOnce = true;

                        // Search input file line by line
                        for (String strSearchLine : strArray) {
                            //Create a list of multiple word search                            
                            String[] lstFind = strFind.split(" ");
                            boolean bWordMatch = false;

                            // Check for combination of search
                            for (String findStr : lstFind) {
                                bWordMatch = parseEachLine(strSearchLine, findStr);
                                if (!bWordMatch) {
                                    break;
                                }
                            }

                            // Word search match
                            if (bWordMatch) {
                                boolean bHasDate = false;
                                boolean bHasSpecielWords = false;
                                
                                // Collect specific word/phrase searched for to result file
                                if (bDisplayOnce) {
                                    // Print to result file
                                    flOutputFile.write("    ++ " + strFind + " ++");
                                    flOutputFile.write(System.lineSeparator());

                                    // Print to Console
                                    if (bDebug) {
                                        System.out.println("    ++ " + strFind + " ++");
                                    }

                                    //  Save for Excel 
                                    if (customVals.HasDate) {
                                        // Save Date format
                                        bHasDate = saveToExcelFile(mpSaveToExcel, strSearchLine, strCategory);
                                    } else if (customVals.HasName) {
                                        // Save word format
                                    } else {
                                        mpSaveToExcel = SetMapForExcel(mpSaveToExcel, strCategory, strFind);
                                    }

                                    bDisplayOnce = false;// Display only once per category
                                }

                                // Note: Originally, the first parse search may contain chunks of multiple sentences per line.
                                // The following code seperate the chunks of multiple sentences into individual lines.
                                String strValue;// = null;
                                if (strSearchLine.contains(". ")) {
                                    // Divide chunks of multiple sentences into a list of each individual line and
                                    // search for word/phrase for each line
                                    List<String> lsParseList = parseChunkData(strSearchLine, strFind);
                                    Iterator<String> lsParseListIterator = lsParseList.iterator();

                                    // Loop though list of individual lines
                                    while (lsParseListIterator.hasNext()) {
                                        strValue = lsParseListIterator.next();

                                        // Print to result file
                                        flOutputFile.write("        -- " + strValue);
                                        flOutputFile.write(System.lineSeparator());

                                        // Print to console
                                        if (bDebug) {
                                            System.out.println("        -- " + strValue);
                                        }
                                    }
                                } else {
                                    //  Check again if need to save custom data to for Excel 
                                    if (bHasDate) {
                                        // Save to Excel 
                                        saveToExcelFile(mpSaveToExcel, strSearchLine, strCategory);
                                    }

                                    // If not chunks of sentences exist, print to result file
                                    flOutputFile.write("        1-- " + strSearchLine);
                                    flOutputFile.write(System.lineSeparator());

                                    // Print to Console
                                    if (bDebug) {
                                        System.out.println("        1-- " + strSearchLine);
                                    }
                                }
                            }
                        }// For loop Search input file 
                    }
                }
            }// For loop Search map         
        } catch (NullPointerException e) {
            System.out.println("NullPointerException - " + e.getMessage());
            displayMsg(e.getMessage(), JOptionPane.ERROR_MESSAGE);
        } catch (NoSuchElementException e) {
            System.out.println("NoSuchElementException - " + e.getMessage());
            displayMsg(e.getMessage(), JOptionPane.ERROR_MESSAGE);
        } finally {
            if (flOutputFile != null) {
                flOutputFile.close();
            }

            if (flInputFile != null) {
                flInputFile.close();
            }
        }

        System.out.println("END OF REPORT");
        return mpSaveToExcel;
    }

    /**
     *
     * @param parseLine
     * @param strFind
     * @return
     */
    private boolean parseEachLine(String parseLine, String strFind) {
        /* 
        Main Reg Exp string
        (?i:.*\\b + 'word' + \\b.*)
        ?i: Case-insensitive  
        ? match zero or one occurrence - A quantifier defines how often an element can occur. 
        . Matches any single character - put at both front and end of string 

        \\b Word boundary - Makes sure that only the exact word is found and not 
        concatinated with another word or letters. 
        The strDateFormat \bcat\b would therefore match cat in a black cat, 
        but it wouldn't match it in catatonic, tomcat or certificate.
        Removing one of the boundaries, \bcat would match cat in catfish, 
        and cat\b would match cat in tomcat, but not vice-versa. 
        Both, of course, would match cat on its own. 

        \\ to define a single backslash.
        ( ) Groups regular expressions - need when using '?'
        * matches zero or more occurrences - A quantifier defines how often 
        an element can occur - but at both front and end of string . 
         */
        return Pattern.matches("(?i:.*\\b" + strFind + "\\b.*)", parseLine);
    }     

    /**
     *
     * @param parseLine
     * @param strFind
     * @return
     */
    private List<String> parseChunkData(String parseLine, String strFind) {
        List<String> lstParseLine = new LinkedList();
        int iStart = 0;
        int iEnd = 0;
        final char CHR_PERIOD = '.';
        //char val = 'x';

        // Search through each chunk of data
        for (int jj = 0; parseLine.length() > jj; jj++) {
            // Check each character in chunks of data
            char val = parseLine.charAt(jj);

            // Look for the 'period' to seperate individual lines
            if (CHR_PERIOD == val) {
                iEnd = jj;
                if (iEnd > iStart) {
                    // Get individual line
                    String strLine = parseLine.substring(iStart + 1, iEnd + 1).trim();

                    // Check if line has search word/phrase
                    // TODO: create a list on multiple work search
                    // 
                    boolean bHasSearchWord = parseEachLine(strLine, strFind);

                    // If word/phrase found, place in list
                    if (bHasSearchWord) {
                        lstParseLine.add(strLine);
                    }

                    // Keep track of position of each line 
                    iStart = iEnd;
                }
            }
        }

        return lstParseLine;
    }

    /**
     *
     * @param mpExcel
     * @param strCategory
     * @param strValue
     * @return
     */
    private Map<String, String> SetMapForExcel(Map<String, String> mpExcel, String strCategory, String strValue) {
        // Does map have category 
        if (mpExcel.containsKey(strCategory) && strValue.length() > 0) {
            String strCurrentValue = mpExcel.get(strCategory);

            // Determine if value is already in map
            if (!strCurrentValue.equals(strValue) && strCurrentValue.length() > 0) {
                // Add new string value to string 
                strCurrentValue += ", " + strValue;
                mpExcel.put(strCategory, strCurrentValue);
            } else {
                mpExcel.put(strCategory, strValue);
            }
        } else {
            // Create blank new key value
            mpExcel.put(strCategory, "");
        }

        return mpExcel;
    }   
    
    /**
     *
     * @param mpSaveToExcel
     * @param searchLine
     * @param category
     * @return
     */
    private boolean saveToExcelFile(Map<String, String> mpSaveToExcel, String searchLine, String category) {

        boolean hasDate = true;
        // Save Date format
        String strDateFormated =  CustomData.getDateValue(searchLine);        
        if (strDateFormated != null) {
            mpSaveToExcel = SetMapForExcel(mpSaveToExcel, category, strDateFormated);
            hasDate = false;
        }

        return hasDate;
    }      
   
    /**
     *
     * @param file
     */
    @SuppressWarnings("null")
    public static void setSearchData(File file) {
        try {
            mpSearchData = new HashMap();
            Scanner scan = new Scanner(file);
            while (scan.hasNext()) {
                List<String> lstSearchData = new ArrayList<String>();
                String[] aryData = scan.nextLine().split(",");
                if (lstSearchData.addAll(Arrays.asList(aryData))) {
                    String strCategoryKey = lstSearchData.get(0);
                    lstSearchData.remove(0);
                    mpSearchData.put(strCategoryKey, lstSearchData);
                    System.out.println("strCategoryKey " + strCategoryKey + " -- lstSearchData -- " + lstSearchData);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SelectInputDataController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
