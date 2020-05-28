package MedicalMineFxMain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author RW Simmons
 */
public class CustomData {

    private static List<String> lstCustomData = null;
    private final static String CUST_DATE = "(date)";
    private final static String CUST_NAME = "(name)";
    private final static String CUST_GENDER = "(gender)";
    private final static String CUST_ALL = "(all)";
    private final static String CUST_FOLLOW = "(follow";
    private final static String CUST_KEY = "(key)";

    /**
     *
     *
     */
    public static void setCustomDataList() {
        lstCustomData = new ArrayList<>();
        lstCustomData.add(CUST_DATE);
        lstCustomData.add(CUST_NAME);
        lstCustomData.add(CUST_GENDER);
        lstCustomData.add(CUST_ALL);
        lstCustomData.add(CUST_FOLLOW);
        lstCustomData.add(CUST_KEY);
    }

    /**
     *
     * @param categoryStr
     * @return
     */
    public static CustomVals checkCustomData(String categoryStr) {
        CustomVals customVals = new CustomVals();
        if (categoryStr.contains(CUST_DATE)) {
            // Set Date
            customVals.HasDate = true;
            customVals.category = categoryStr.replace(CUST_DATE, "");
        } else if (categoryStr.contains(CUST_NAME)) {
            // Set Name
            customVals.HasName = true;
            customVals.category = categoryStr.replace(CUST_NAME, "");
        } else if (categoryStr.contains(CUST_GENDER)) {
            // Set Gender
            customVals.HasGender = true;
            customVals.category = categoryStr.replace(CUST_GENDER, "");
        } else if (categoryStr.contains(CUST_ALL)) {
            // Set All
            customVals.HasAll = true;
            customVals.category = categoryStr.replace(CUST_ALL, "");
        } else if (categoryStr.contains(CUST_FOLLOW)) {
            // Set Follow
            customVals.HasFollow = true;
            customVals.category = categoryStr.replace(CUST_FOLLOW, "");
        } else if (categoryStr.contains(CUST_KEY)) {
            // Set Key
            customVals.HasKey = true;
            customVals.category = categoryStr.replace(CUST_KEY, "");
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
        final String DATE_FORMAT = "^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$";
        Pattern pattern = Pattern.compile(DATE_FORMAT);

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

        // Remove empty element
        lstCatWords = removeEmptyElements(lstCatWords);
        lstWords = removeEmptyElements(lstWords);

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
     * @param searchWords
     * @return
     */
    public static String getAllFormat(String strSearchLine, String searchWords) {
        strSearchLine = cleanString(strSearchLine).toLowerCase();
        // Collect list of search string and category word 
        List<String> lstCatWords = new ArrayList<>(Arrays.asList(searchWords.split(" ")));
        
        // Remove empty element
        lstCatWords = removeEmptyElements(lstCatWords);

        // Remove category words from search string
        String strReturnVal = strSearchLine;
        for (String val : lstCatWords) {
            val = val.replace(" ", "").toLowerCase();
            strReturnVal = strReturnVal.replace(val, "");
        }

        return strReturnVal.trim();
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
        lstWords = removeEmptyElements(lstWords);

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
    public static List<String> removeEmptyElements(List<String> list) {
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
}
