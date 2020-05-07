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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 * Class : TextParsingMedicalMine Purpose : To read selected text files and search for specific medical data. Author : Robert Wendell Simmons Created : 3/30/2018
 */
public class ParseInputFiles extends ProcessInputFiles {

    private static Map<String, List<String>> mpSearchData;
    private static int count = 1;
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

        String strResultFile = RESULT_FOLDER_LOC + "Search Result" + resultTime + ".txt";

        // Check result folder 
        // TODO: Ackward. Have to have delete file or unable to run software multiple times without restarting
        File file = new File(RESULT_FOLDER_LOC);
        if (!file.exists()) {
            file.mkdirs();
        } else {
            file.delete();
        }

        try {
            // Create files
            flInputFile = new FileReader(flFileName);           // Input file                  
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

            // Collect each character from file and put in variable for processing
            String strData = "";
            int iCharConverter;
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

                // Programmers hack to end program without searching all categories
                if (strCategory.contains("Done")) {
                    return mpSaveToExcel;
                }

                // Check if custom data needs to be displayed
                CustomVals customVals = new CustomVals();
                for (String val : CustomData.ckCustDataList()) {
                    if (strCategory.contains(val)) {
                        customVals = CustomData.checkCustomData(strCategory);
                        strCategory = customVals.category;
                        break;
                    }
                }

                // Pre set map for Excel
                mpSaveToExcel = SetMapForExcel(mpSaveToExcel, strCategory, "");

                // Print category text to result file
                final String LINE_SEPATATOR = System.lineSeparator();
                flOutputFile.write(LINE_SEPATATOR);// line feed
                flOutputFile.write(CategoryCounter + ") Category - " + strCategory + ":");
                flOutputFile.write(LINE_SEPATATOR);// line feed
                flOutputFile.write(LINE_SEPATATOR);// line feed

                // Print to console
                if (bDebug) {
                    System.out.println(CategoryCounter + ") Category -" + strCategory + ":");
                }

                CategoryCounter++;

                // Collect data from map
                lstTier1Search = itrCategory.getValue();

                // Search for each search word and phrase in line
                for (String strFind : lstTier1Search) {
                    // Make sure value is contained in string 
                    if (!strFind.isEmpty()) {
                        boolean bDisplayOnce = true;

                        // Search input file line by line
                        for (String strSearchLine : strArray) {
                            //Create a list of multiple word search                            
                            String[] lstFind = strFind.split(" ");
                            boolean bWordMatch = false;

                            // Check for multiple words for search
                            for (String findStr : lstFind) {
                                bWordMatch = parseEachLine(strSearchLine, findStr);
                                if (!bWordMatch) {
                                    break;
                                }
                            }

                            // Word search match
                            if (bWordMatch) {
                                // Collect specific word/phrase searched for to result file
                                if (bDisplayOnce) {
                                    // Print to result file
                                    flOutputFile.write("    ++ " + strFind + " ++");
                                    flOutputFile.write(LINE_SEPATATOR);

                                    // Print to Console
                                    if (bDebug) {
                                        System.out.println("    ++ " + strFind + " ++");
                                    }

                                    //  Save for Excel                                   
                                    processExcelData(customVals, mpSaveToExcel, strSearchLine, strCategory, strFind);

                                    bDisplayOnce = false;// Display only once per category
                                }

                                // Note: Originally, the first parse search may contain chunks of multiple sentences per line.
                                // The following code seperate the chunks of multiple sentences into individual lines.
                                String strValue;
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
                                        flOutputFile.write(LINE_SEPATATOR);

                                        // Print to console
                                        if (bDebug) {
                                            System.out.println("        -- " + strValue);
                                        }
                                    }
                                } else {
                                    //  Check again if need to save custom data to for Excel                                    
                                    processExcelData(customVals, mpSaveToExcel, strSearchLine, strCategory, strFind);

                                    // If not chunks of sentences exist, print to result file
                                    flOutputFile.write("        --- " + strSearchLine);
                                    flOutputFile.write(LINE_SEPATATOR);

                                    // Print to Console
                                    if (bDebug) {
                                        System.out.println("        --- " + strSearchLine);
                                    }
                                }
                            }
                        }// For loop Search input file 
                    }
                }
            }// For loop Search map    
        } catch (NullPointerException e) {
            System.out.println("NullPointerException - " + e.toString());
            e.printStackTrace();
            displayMsg(e.toString(), JOptionPane.ERROR_MESSAGE);

            System.exit(1);
        } catch (NoSuchElementException e) {
            System.out.println("NoSuchElementException - " + e.toString());
            displayMsg(e.toString(), JOptionPane.ERROR_MESSAGE);
            System.exit(1);
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

    private EnumCustomType processExcelData(CustomVals customVals, Map<String, String> mpSaveToExcel, String strSearchLine, String strCategory, String strFind) {

        // Check to see if data already populated
        boolean bIsValueEmpty = mpSaveToExcel.get(strCategory).isEmpty();

        //  Save for Excel 
        if (customVals.HasDate) {
            // Save Date format           
            if (bIsValueEmpty) {
                saveDateValExcel(mpSaveToExcel, strSearchLine, strCategory);
            }
            return EnumCustomType.DATE;
        } else if (customVals.HasName) {
            // Save word format           
            if (bIsValueEmpty) {
                saveNameValExcel(mpSaveToExcel, strSearchLine, strCategory, strFind);
            }
            return EnumCustomType.NAME;
        } else if (customVals.HasGender) {
            // Save gender format
            if (bIsValueEmpty) {
                saveGenderValExcel(mpSaveToExcel, strSearchLine, strCategory);
            }
            return EnumCustomType.GENDER;
        } else {
            SetMapForExcel(mpSaveToExcel, strCategory, strFind);
            return EnumCustomType.NONE;
        }
    }

    enum EnumCustomType {
        DATE, NAME, GENDER, NONE
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
     * @param strFindgit
     * @return
     */
    private List<String> parseChunkData(String parseLine, String strFind) {
        List<String> lstParseLine = new LinkedList();
        int iStart = 0;
        int iEnd = 0;
        final char CHR_PERIOD = '.';

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
    private boolean saveDateValExcel(Map<String, String> mpSaveToExcel, String searchLine, String category) {

        boolean hasDate = true;
        // Save Date format
        String strDateFormated = CustomData.getDateFormat(searchLine);
        if (strDateFormated != null) {
            mpSaveToExcel = SetMapForExcel(mpSaveToExcel, category, strDateFormated);
            hasDate = false;
        }

        return hasDate;
    }

    /**
     *
     * @param mpSaveToExcel
     * @param searchLine
     * @param category
     * @return
     */
    private boolean saveNameValExcel(Map<String, String> mpSaveToExcel, String searchLine, String category, String strSearchData) {

        boolean hasSpecial = true;
        // Save Date format
        String strNameFormated = CustomData.getNameFormat(searchLine, strSearchData);
        if (strNameFormated != null) {
            mpSaveToExcel = SetMapForExcel(mpSaveToExcel, category, strNameFormated);
            hasSpecial = false;
        }

        return hasSpecial;
    }

    /**
     *
     * @param mpSaveToExcel
     * @param searchLine
     * @param category
     * @return
     */
    private boolean saveGenderValExcel(Map<String, String> mpSaveToExcel, String searchLine, String category) {

        boolean hasGender = true;
        // Save Date format
        // TODO: Convert to format gender
        String strGenderFormated = CustomData.getGender(searchLine);
        if (strGenderFormated != null) {
            mpSaveToExcel = SetMapForExcel(mpSaveToExcel, category, strGenderFormated);
            hasGender = false;
        }

        return hasGender;
    }

    /**
     *
     *
     * @param file
     */
    @SuppressWarnings("null")
    public static void setSearchData(File file) {

        try {

            List<String> lstCategory;
            mpSearchData = new LinkedHashMap();
            Map<Integer, List<String>> mpTransferData = new LinkedHashMap();
            Scanner scan = new Scanner(file);
            int count = 0;
            
            while (scan.hasNext()) {
                lstCategory = new ArrayList<String>();
                String[] aryData = scan.nextLine().split(",");
                if (lstCategory.addAll(Arrays.asList(aryData))) {
                    mpTransferData.put(count, lstCategory);
                    count++;
                }
            }

            lstCategory = mpTransferData.get(0);
            mpTransferData.remove(0);
            for (int iCategory = 0; iCategory < lstCategory.size(); iCategory++) {
                List<String> lstSetData = new ArrayList<String>();
                List<String> lstData = new ArrayList<String>();
                String strSearch;
                for (Map.Entry iList : mpTransferData.entrySet()) {
                    lstSetData = (List<String>) iList.getValue();
                    if (iCategory < lstSetData.size()) {
                        strSearch = lstSetData.get(iCategory);

                        if (!strSearch.isEmpty()) {
                            lstData.add(strSearch);
                        }
                    }
                }

                mpSearchData.put(lstCategory.get(iCategory), lstData);
                System.out.println("Column file " + lstCategory.get(iCategory) + " -- lstCategory -- " + lstData);

            }
            
            /*
            System.out.println();
            
            mpSearchData = new LinkedHashMap();
            //scan = new Scanner(file);
            while (scan.hasNext()) {
                lstCategory = new ArrayList<String>();
                String[] aryData = scan.nextLine().split(",");
                if (lstCategory.addAll(Arrays.asList(aryData))) {
                    String strCategoryKey = lstCategory.get(0); // Set Categories
                    lstCategory.remove(0);
                    mpSearchData.put(strCategoryKey, lstCategory);
                    System.out.println("Row file  " + strCategoryKey + " -- lstCategory -- " + lstCategory);
                }
            }
            //*/
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SelectInputDataController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
