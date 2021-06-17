package MedicalMineFxMain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class CustomData
 *
 * @author RW Simmons
 */
public class CustomData {

    private static List<String> lstCustomData = null;
    private static List<String> lstMonthsName = null;
    private static List<String> lstMonthsAbbrName = null;
    private final static String CUST_DATE = "(date)";
    private final static String CUST_NAME = "(name)";
    private final static String CUST_GENDER = "(gender)";
    private final static String CUST_ALL = "(all)";
    private final static String CUST_ONLY = "(only)";
    public final static String CUST_FOLLOW = "(follow"; // ')' intentional missing
    private final static String CUST_KEY = "(key)";

    /**
     * setCustomDataList
     */
    public static void setCustomDataList() {
        // List of custom formats
        lstCustomData = new ArrayList<>();
        lstCustomData.add(CUST_DATE);
        lstCustomData.add(CUST_NAME);
        lstCustomData.add(CUST_GENDER);
        lstCustomData.add(CUST_ALL);
        lstCustomData.add(CUST_FOLLOW);
        lstCustomData.add(CUST_KEY);

        // List of months for date format
        lstMonthsName = new ArrayList<>();
        lstMonthsName.add("january");
        lstMonthsName.add("february");
        lstMonthsName.add("march");
        lstMonthsName.add("april");
        lstMonthsName.add("may");
        lstMonthsName.add("june");
        lstMonthsName.add("july");
        lstMonthsName.add("august");
        lstMonthsName.add("september");
        lstMonthsName.add("october");
        lstMonthsName.add("november");
        lstMonthsName.add("december");

        // List of months abbreviation for date format
        lstMonthsAbbrName = new ArrayList<>();
        lstMonthsAbbrName.add("jan");
        lstMonthsAbbrName.add("feb");
        lstMonthsAbbrName.add("mar");
        lstMonthsAbbrName.add("apr");
        lstMonthsAbbrName.add("may");
        lstMonthsAbbrName.add("jun");
        lstMonthsAbbrName.add("jul");
        lstMonthsAbbrName.add("aug");
        lstMonthsAbbrName.add("sept");
        lstMonthsAbbrName.add("oct");
        lstMonthsAbbrName.add("nov");
        lstMonthsAbbrName.add("dec");
    }

    /**
     * checkCustomData
     *
     * @param categoryStr
     * @return
     */
    public static CustomVals checkCustomData(String categoryStr) {
        int iFirstParm = categoryStr.indexOf("(");
        int iSecondParm = categoryStr.indexOf(")");

        // Remove format trigger from category word
        CustomVals customVals = new CustomVals();
        //String strRemove = categoryStr.substring(iFirstParm);
        //customVals.category = categoryStr.replace(strRemove, "").tr;

        if (categoryStr.contains(CUST_DATE)) {
            // Remove(date)           
            customVals.category = categoryStr.replace(CUST_DATE, "");
            customVals.HasDate = true;
        } else if (categoryStr.contains(CUST_NAME)) {
            // Remove(name)            
            customVals.category = categoryStr.replace(CUST_NAME, "");
            customVals.HasName = true;
        } else if (categoryStr.contains(CUST_GENDER)) {
            // Remove(gender)             
            customVals.category = categoryStr.replace(CUST_GENDER, "");
            customVals.HasGender = true;
        } else if (categoryStr.contains(CUST_ALL)) {
            // Remove(all)              
            customVals.category = categoryStr.replace(CUST_ALL, "");
            customVals.HasAll = true;
        } else if (categoryStr.contains(CUST_FOLLOW)) {
            // Remove(follow)             
            int index = categoryStr.indexOf(CUST_FOLLOW);
            int indexEnd = categoryStr.indexOf(")");
            String strRemove = categoryStr.substring(index, indexEnd + 1);
            // Retrieve number of words to display
            customVals.iWords = Integer.valueOf(strRemove.replaceAll("[^0-9]", ""));
            customVals.category = categoryStr.replace(strRemove, "");
            customVals.HasFollow = true;
        } else if (categoryStr.contains(CUST_KEY)) {
            // Remove(key)              
            customVals.category = categoryStr.replace(CUST_KEY, "");
            customVals.HasKey = true;
        }//*/
        return customVals;
    }

    /**
     * getDateFormat
     *
     * @param strCustom
     * @return
     */
    public static String getDateFormat(String strCustom, String strFind) {

        final String DATE_FORMAT = "^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$"; // xx/xx/xxxx       

        // Clean string and create list
        strCustom = cleanString(strCustom);

        // Only select segment of string pertaining to actual search word(s) 
        String[] lstSegmentsTest = strCustom.split(strFind);
        if (lstSegmentsTest.length > 1) {
            strCustom = lstSegmentsTest[1];
        }

        // Cycle throught segments of data to find date  
        String[] lstStringSegments = strCustom.split(" ");
        Pattern pattern = Pattern.compile(DATE_FORMAT);
        Matcher matcher;
        String strReturnVal = null;
        int iIndex = 0;
        for (String word : lstStringSegments) {
            String Month = word.toLowerCase();
            matcher = pattern.matcher(word.trim());
            // Determing if word is a month in normal spelling or abbreviation 
            if (lstMonthsName.contains(Month) || lstMonthsAbbrName.contains(Month)) {
                List<String> lstMonth = null;
                if (lstMonthsName.contains(Month)) {
                    lstMonth = lstMonthsName;
                } else if (lstMonthsAbbrName.contains(Month)) {
                    lstMonth = lstMonthsAbbrName;
                }
                // Get date if Month is in word format
                int iMonth = lstMonth.indexOf(Month) + 1;
                String strMonth = String.valueOf(iMonth);
                strMonth = strMonth.length() < 2 ? "0" + strMonth : strMonth;  // Add zero if only one digit                         
                // Date
                String strDay = lstStringSegments[iIndex + 1].replaceAll("[^0-9]", "");
                strDay = strDay.length() < 2 ? "0" + strDay : strDay; // Add zero if only one digit                        
                // Year
                String strYear = lstStringSegments[iIndex + 2].replaceAll("[^0-9]", "");
                strReturnVal = strMonth + "/" + strDay + "/" + strYear;
                break;
            } else if (matcher.matches()) {
                // Get date if in numeric format and Store date format                
                int val = word.indexOf("/");
                if (val < 2) {
                    strReturnVal = "0" + word;
                } else {
                    strReturnVal = word;
                }
                break;
            }
            iIndex++;
        }
        return strReturnVal;
    }

    /**
     * getNameFormat
     *
     * @param strSearchLine
     * @param searchWords
     * @return
     */
    public static String getNameFormat(String strSearchLine, String searchWords) {
        // Remove unwanted values
        strSearchLine = cleanString(strSearchLine).toLowerCase();
        searchWords = cleanString(searchWords).toLowerCase();
        final int FIRST_NAME = 1;
        final int LAST_NAME = 2;

        // Collect list of search string and category word 
        List<String> lstCatWords = new ArrayList<>(Arrays.asList(searchWords.split(" ")));
        List<String> lstSearchLine = new ArrayList<>(Arrays.asList(strSearchLine.split(" ")));

        // Remove empty element
        lstCatWords = removeEmptyElements(lstCatWords);
        lstSearchLine = removeEmptyElements(lstSearchLine);

        String strReturnVal = "";
        for (String catWords : lstCatWords) {
            catWords = catWords.replace(" ", "").toLowerCase();
            // Collect Name
            if (lstSearchLine.contains(catWords)) {
                int iCatWords = lstSearchLine.indexOf(catWords);
                // Make sure string contains enough elements
                if (lstSearchLine.size() > iCatWords + 2) {
                    // Get the first and last name and capitallize first letter of each
                    String strFirst = lstSearchLine.get(iCatWords + FIRST_NAME);
                    String strSecond = lstSearchLine.get(iCatWords + LAST_NAME);
                    strFirst = strFirst.substring(0, 1).toUpperCase() + strFirst.substring(1).toLowerCase();
                    strSecond = strSecond.substring(0, 1).toUpperCase() + strSecond.substring(1).toLowerCase();
                    strReturnVal = strFirst + " " + strSecond;
                    break;
                }
            }
        }
        return strReturnVal;
    }

    /**
     * getGender
     *
     * @param searchLine
     * @param find
     * @return
     */
    public static String getGender(String searchLine, String find) {
        // Remove unwanted values set to lower case for easy of match
        String strSearchLine = cleanString(searchLine).toLowerCase();
        String strFind = find.toLowerCase().trim();

        // Only select segment of string pertaining to actual search word(s) 
        String[] lstSegmentsTest = strSearchLine.split(strFind);
        if (lstSegmentsTest.length > 1) {
            strSearchLine = lstSegmentsTest[1].trim();
        }

        // Turn string into list 
        List<String> lstWords = new ArrayList<>(Arrays.asList(strSearchLine.split(" ")));
        lstWords = removeEmptyElements(lstWords);

        // Select first element which cantains gender
        String strGender = "";
        int iSize = lstWords.size();
        if (iSize >= 1) {
            strGender = lstWords.get(0);
        }
        return strGender;
    }

    /**
     * getAllFormat
     *
     * @param strSearchLine
     * @param searchWords
     * @return
     */
    public static String getAllFormat(String strSearchLine, String searchWords) {
        // Remove unwanted values
        strSearchLine = cleanString(strSearchLine).toLowerCase();

        // Collect list of search string and category word 
        List<String> lstCatWords = new ArrayList<>(Arrays.asList(searchWords.split(" ")));

        // Remove empty element
        lstCatWords = removeEmptyElements(lstCatWords);

        // Remove category words from search string
        String strReturnVal = strSearchLine;
        for (String val : lstCatWords) {
            val = val.replace(" ", "").toLowerCase();
            // Save new string
            strReturnVal = strReturnVal.replace(val, "");
        }
        return strReturnVal.trim();
    }

    /**
     * getFollowFormat
     *
     * @param strSearchLine
     * @param strSearchWords
     * @return
     */
    public static String getFollowFormat(String strSearchLine, String strSearchWords, int iNumWords) {
        // Remove unwanted values
        strSearchLine = cleanString(strSearchLine).toLowerCase();

        // Collect list of search string and category word 
        List<String> lstCatWords = new ArrayList<>(Arrays.asList(strSearchWords.split(" ")));
        List<String> lstWords = new ArrayList<>(Arrays.asList(strSearchLine.split(" ")));

        // Remove empty element
        lstCatWords = removeEmptyElements(lstCatWords);
        lstWords = removeEmptyElements(lstWords);

        // Remove category words from search string
        for (String catWord : lstCatWords) {
            catWord = catWord.replace(" ", "").toLowerCase();
            if (lstWords.contains(catWord)) {
                lstWords.remove(lstWords.indexOf(catWord));
            }
        }

        int iCounter = 0;
        String strReturnVal = "";

        for (String words : lstWords) {

            if (iNumWords == 0) {
                // Get all the words in sentence
                strReturnVal += words + " ";
            } else {
                // Get the number of words to display
                if (iCounter < iNumWords && !words.isEmpty()) {
                    strReturnVal += words + " ";
                    iCounter++;
                } else {
                    break;
                }
            }
        }

        return strReturnVal;
    }

    /**
     * checkMatch
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
     * removeEmptyElements
     *
     * @param list
     * @return
     */
    private static List<String> removeEmptyElements(List<String> list) {
        // Check to see if list element is empty
        for (int ii = 0; ii < list.size(); ii++) {
            String strCheck = list.get(ii);
            if (strCheck.isEmpty()) {
                // Remove empty element
                list.remove(ii);
                ii = 0; // Reset index
            }
        }
        return list;
    }

    /**
     * cleanString
     *
     * @param clean
     * @return
     */
    private static String cleanString(String clean) {
        final String DOULBE_SPC = "  ";
        return clean.replace("·", "").replace(";", "").replaceAll(DOULBE_SPC, " ").replace(":", " ").replace(".", "/").replace("\t", " ").trim();
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
 *
 * @author RW Simmons
 */
final class CustomVals {

    boolean HasDate = false;
    boolean HasName = false;
    boolean HasGender = false;
    boolean HasAll = false;
    boolean HasFollow = false;
    boolean HasKey = false;
    String category = "";
    int iWords = 0;
}
