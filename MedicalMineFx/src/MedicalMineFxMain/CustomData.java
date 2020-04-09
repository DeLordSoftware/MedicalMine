package MedicalMineFxMain;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author RW Simmons
 */
public class CustomData {   
  
    /**
     *
     * @param categoryStr
     * @return
     */
    public static CustomVals checkCustomData(String categoryStr) {
        CustomVals customVals = new CustomVals();
        if (categoryStr.contains("#")) {
            customVals.HasDate = true;
            customVals.category = categoryStr.replaceAll("#", "");
        } else if (categoryStr.contains("@")) {
            customVals.HasName = true;
            customVals.category = categoryStr.replaceAll("@", "");
        }
        
        return customVals;
    }

    /**
     *
     * @param strCustom
     * @return
     */
    public static String getDateValue(String strCustom) {
        // Get exact date        
        String strDateFormat = "^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$";
        Pattern pattern = Pattern.compile(strDateFormat);
        String strReturnVal = null;
        Matcher matcher;

        // Clean string
        final String DOULBE_SPC = "  ";
        strCustom = cleanString(strCustom); // strCustom.replaceAll(DOULBE_SPC, " ").replace(":", " ").replace(".", "/").replace("\t", " ");
        String[] lstStringSegments = strCustom.split(" ");

        // Cycle throught segments of data to find date  
        for (String segment : lstStringSegments) {
            matcher = pattern.matcher(segment.trim());
            if (matcher.matches()) {
                // Store date format
                strReturnVal = segment;
                //System.out.println("**** CLASS Excel Value ************** " + strReturnVal);
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
    public static String getSpecialWords(String strSearchLine) {        
        strSearchLine = cleanString(strSearchLine);
        String strReturnVal = null; 
        String[] lstWords = strSearchLine.split(" ");
        boolean bHasVal = false;
        int iCounter =  0;
        for(String val : lstWords){
            if(bHasVal && iCounter < 2 && !val.isEmpty()){
                strReturnVal += val + " ";
                iCounter++;
            }
            
            if (val.equalsIgnoreCase("name")){
                bHasVal = true;   
                strReturnVal = "";
            }
        }
        
        //System.out.println("///////////////// CLASS Excel Value ////////////// " + strReturnVal);
        return strReturnVal;
    }
    
    /**
     * 
     * @param clean
     * @return 
     */
    private static String cleanString(String clean){       
        final String DOULBE_SPC = "  ";
        return clean .replaceAll(DOULBE_SPC, " ").replace(":", " ").replace(".", "/").replace("\t", " ");
    }

    /**
     *
     * @param mpExcel
     * @param strCat
     * @param strFind
     * @return
     */
    public static Map<String, String> GetGender(Map<String, String> mpExcel, String strCat, String strFind) {
        // Get exact gender 
        String[] arryStr = CreateArrayForSearch(strFind, strCat);
        String regex = "\\b(male|m|female|f)\\b";
        Pattern pattern = Pattern.compile(regex);
        final String GENDER = "Gender";

        for (int ii = 0; ii < arryStr.length; ii++) {
            Matcher matcher = pattern.matcher(arryStr[ii]);

            if (matcher.matches()) {
                String strGender = null;
                strGender = arryStr[ii].toLowerCase();

                if (strGender.equals("m") || strGender.equals("male")) {
                    strGender = "Male";
                } else if (strGender.equals("f") || strGender.equals("female")) {
                    strGender = "Female";
                } else {
                    strGender = "Not located";
                }

                // Set gender value in excel map
                mpExcel.replace(GENDER, strGender);
                break;
            }
        }

        if (mpExcel.containsValue(strCat)) {
            mpExcel.replace(GENDER, "Not located");
        }

        return mpExcel;
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
}

/**
 *
 * @author RW Simmons
 */
final class CustomVals {
    boolean HasDate = false;
    boolean HasName = false;
    String category = "";
}
