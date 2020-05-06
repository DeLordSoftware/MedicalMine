package MedicalMineFxMain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author RW Simmons
 */
public class CustomData {

    private static List<String> lstCustomData = null;
    private final static String cust1 = "(date)";
    private final static String cust2 = "(name)";
    private final static String cust3 = "(gender)";

    /**
     *
     * @param categoryStr
     * @return
     */
    public static void setCustomDataList() {
        lstCustomData = new ArrayList<String>();
        lstCustomData.add(cust1);
        lstCustomData.add(cust2);
        lstCustomData.add(cust3);
    }

    /**
     *
     * @param categoryStr
     * @return
     */
    public static CustomVals checkCustomData(String categoryStr) {
        CustomVals customVals = new CustomVals();
        if (categoryStr.contains(cust1)) {
            // Set Date
            customVals.HasDate = true;
            customVals.category = categoryStr.replace(cust1, "");
        } else if (categoryStr.contains(cust2)) {
            // Set Name
            customVals.HasName = true;
            customVals.category = categoryStr.replace(cust2, "");
        } else if (categoryStr.contains(cust3)) {
            // Set Gender
            customVals.HasGender = true;
            customVals.category = categoryStr.replace(cust3, "");
        }

        return customVals;
    }

    /**
     *
     * @param strCustom
     * @return
     */
    public static String getDateFormat(String strCustom) {
        // Get exact date        
        String strDateFormat = "^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$";
        Pattern pattern = Pattern.compile(strDateFormat);

        // Clean string
        strCustom = cleanString(strCustom);
        String[] lstStringSegments = strCustom.split(" ");

        // Cycle throught segments of data to find date  
        Matcher matcher;
        String strReturnVal = null;
        for (String segment : lstStringSegments) {
            matcher = pattern.matcher(segment.trim());
            if (matcher.matches()) {
                // Store date format
                strReturnVal = segment;
                break;
            }
        }

        return strReturnVal;
    }

    /**
     *
     * @param strSearchLine
     * @param searchWords
     * @return
     */
    public static String getNameFormat(String strSearchLine, String searchWords) {
        strSearchLine = cleanString(strSearchLine).toLowerCase();
        // Collect list of search string and category word 
        List<String> lstCatWords = new ArrayList<>(Arrays.asList(searchWords.split(" ")));
        List<String> lstWords = new ArrayList<>(Arrays.asList(strSearchLine.split(" ")));

        // Clean string list
        lstCatWords = cleanStrList(lstCatWords);
        lstWords = cleanStrList(lstWords);

        // Remove category words from search string
        for (String val : lstCatWords) {
            val = val.replace(" ", "").toLowerCase();
            if (lstWords.contains(val)) {
                lstWords.remove(lstWords.indexOf(val));
            }
        }

        int iCounter = 0;
        String strReturnVal = "";
        for (String val : lstWords) {
            // Get the next two words which should be name
            if (iCounter < 2 && !val.isEmpty()) {
                // Store and capitalize first letter
                String strFirst = val.substring(0, 1);
                if (checkMatch(strFirst, "[a-z]")) {
                    strReturnVal += strFirst.toUpperCase() + val.substring(1).toLowerCase() + " ";
                    iCounter++;
                } else {
                    // If first char isn't a letter, check next char in string 
                    strFirst = val.substring(1, 2);
                    if (checkMatch(strFirst, "[a-z]")) {
                        strReturnVal += strFirst.toUpperCase() + val.substring(2).toLowerCase() + " ";
                        iCounter++;
                    }
                }
            } else {
                break;
            }
        }

        return strReturnVal;
    }

   
    /**
     *
     * @param strSearchLine
     * @return
     */
    public static String getGender(String strSearchLine) {
        // Get exact gender 
        strSearchLine = cleanString(strSearchLine);
        String strGender = null;
        List<String> lstWords = new ArrayList<>(Arrays.asList(strSearchLine.split(" ")));
        lstWords = cleanStrList(lstWords);
        
        for (String val : lstWords) {
            strGender = val.toLowerCase();
            if (checkMatch(strGender, "\\b(male| m |female| f )\\b")) { 
                if (strGender.equals(" m ") || strGender.equals("male")) {
                    strGender = "Male";
                } else if (strGender.equals(" f ") || strGender.equals("female")) {
                    strGender = "Female";
                } else {
                    strGender = "Not located";
                }
                break;
            }
        }

        return strGender;
    }
    
     /**
     *
     * @param value
     * @param match
     * @return
     */
    private static boolean checkMatch(String value, String match) {
        String regex = match;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    /**
     *
     * @param list
     * @return
     */
    private static List<String> cleanStrList(List<String> list) {
        for (int ii = 0; ii < list.size(); ii++) {
            String test = list.get(ii);
            if (test.isEmpty()) {
                list.remove(ii);
                ii = 0; // Reset index
            }
        }

        return list;
    }


    /**
     *
     * @param strDateFinal
     * @param strCatgy
     * @return
     */
    private static String[] CreateArrayForSearch(String strDateFinal, String strCatgy) {
        // Convert strings to lower case because sometimes string has different capitalization
        String strLineData = strDateFinal.toLowerCase();
        String strCat = strCatgy.toLowerCase();

        // Remove characters before category word
        int iRemoveIntialText = strLineData.indexOf(strCat) + strCat.length();
        int test = strLineData.length();
        strLineData = strLineData.substring(iRemoveIntialText, test);

        // Replace special char with space
        strLineData = strLineData.replace(':', ' ').replace('\t', ' ');

        return strLineData.split(" ");
    }

    /**
     *
     * @param mpExcel
     * @param strCat
     * @param strOne
     * @param strTwo
     * @return
     */
    private Map<String, String> GetAccountForExcel(Map<String, String> mpExcel, String strCat, String strOne, String strTwo) {
        String strFinalDate = null;
        final String PATIENT_ID = "Patient ID";

        // Get string with data
        if (!strOne.isEmpty()) {
            strFinalDate = strOne;
        } else if (!strTwo.isEmpty()) {
            strFinalDate = strTwo;
        } else {
            // Exit if no data is found
            return mpExcel;
        }

        // Get exact date from search line string
        String strLineData = strFinalDate.toLowerCase();
        int iLength = strLineData.length();

        // Find first digit of line that contains date
        for (int indx = 0; iLength > indx; indx++) {
            char strFindDigit = strLineData.substring(indx, indx + 1).charAt(0);

            // Once first digit is found, get rest of date string
            if (Character.isDigit(strFindDigit)) {
                strFinalDate = strLineData.substring(indx, iLength);
                break;
            }
        }

        // Set date value in excel map
        mpExcel.replace(PATIENT_ID, strFinalDate);

        return mpExcel;
    }

    /**
     *
     * @param clean
     * @return
     */
    private static String cleanString(String clean) {
        final String DOULBE_SPC = "  ";
        return clean.replace(";", "").replaceAll(DOULBE_SPC, " ").replace(":", " ").replace(".", "/").replace("\t", " ");
    }

    /**
     *
     * @return
     */
    public static List<String> ckCustDataList() {
        return lstCustomData;
    }

}

/**
 * ***************************************************************
 *
 * @author RW Simmons *************************************************************
 */
final class CustomVals {

    boolean HasDate = false;
    boolean HasName = false;
    boolean HasGender = false;
    String category = "";
}
