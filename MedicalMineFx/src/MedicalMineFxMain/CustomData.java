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
    private static List<String> lstMonthsName = null;
    private final static String CUST_DATE = "(date)";
    private final static String CUST_NAME = "(name)";
    private final static String CUST_GENDER = "(gender)";
    private final static String CUST_ALL = "(all)";
    private final static String CUST_FOLLOW = "(follow"; // ')' intentional missing
    private final static String CUST_KEY = "(key)";

    /**
     *
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
    }

    /**
     *
     * @param categoryStr
     * @return
     */
    public static CustomVals checkCustomData(String categoryStr) {
        // Remove format trigger from category word
        CustomVals customVals = new CustomVals();
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

        // Clean string and create list
        strCustom = cleanString(strCustom);
        String[] lstStringSegments = strCustom.split(" ");

        // Cycle throught segments of data to find date  
        Matcher matcher;
        String strReturnVal = null;
        int iIndex = 0;
        for (String segment : lstStringSegments) {
            String Month = segment.toLowerCase();
            if (lstMonthsName.contains(Month)) {
                // Get date if Month is in word format
                int iMonth = lstMonthsName.indexOf(Month) + 1;
                String strMonth = String.valueOf(iMonth);
                strMonth = strMonth.length() < 2 ? "0" + strMonth : strMonth;  // Add zero if only one digit                         
                // Date
                String strDay = lstStringSegments[iIndex + 1].replaceAll("[^0-9]", "");
                strDay = strDay.length() < 2 ? "0" + strDay : strDay; // Add zero if only one digit                        
                // Year
                String strYear = lstStringSegments[iIndex + 2].replaceAll("[^0-9]", "");
                strReturnVal = strMonth + "/" + strDay + "/" + strYear;
                break;
            } else {
                // Get date if in numeric format
                matcher = pattern.matcher(segment.trim());
                if (matcher.matches()) {
                    // Store date format
                    strReturnVal = segment;
                    break;
                }
            }
            iIndex++;
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
        for (String catWords : lstCatWords) {
            catWords = catWords.replace(" ", "").toLowerCase();
            if (lstWords.contains(catWords)) {
                lstWords.remove(lstWords.indexOf(catWords));
            }
        }

        int iCounter = 0;
        String strReturnVal = "";
        for (String words : lstWords) {
            // Get the next two words which should be name
            if (iCounter < 2 && !words.isEmpty()) {
                // Store and capitalize first letter
                String strFirst = words.substring(0, 1);
                if (checkMatch(strFirst, "[a-z]")) {
                    strReturnVal += strFirst.toUpperCase() + words.substring(1).toLowerCase() + " ";
                    iCounter++;
                } else {
                    // If first char isn't a letter, check next char in string 
                    strFirst = words.substring(1, 2);
                    if (checkMatch(strFirst, "[a-z]")) {
                        strReturnVal += strFirst.toUpperCase() + words.substring(2).toLowerCase() + " ";
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
            // Save new string
            strReturnVal = strReturnVal.replace(val, "");
        }
        return strReturnVal.trim();
    }

    /**
     *
     * @param strSearchLine
     * @param searchWords
     * @return
     */
    public static String getFollowFormat(String strSearchLine, String searchWords, int numWords) {
        strSearchLine = cleanString(strSearchLine).toLowerCase();
        // Collect list of search string and category word 
        List<String> lstCatWords = new ArrayList<>(Arrays.asList(searchWords.split(" ")));
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
            // Get the number of words to display
            if (iCounter < numWords && !words.isEmpty()) {
                strReturnVal += words + " ";
                iCounter++;
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
    int iWords = 0;
}
