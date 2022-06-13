package betacvscreate;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RW Simmons
 */
public class BetaCvsCreate {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Test");
        Map<String, List> mpOrginalRow = new LinkedHashMap<String, List>();
        List<String> lstSearchWords = new ArrayList<String>();
        FileWriter flRowCsv = null;
        
        // Set test data (Objects are reference}
        switch (2) {
            case 0:
                testDataOne(mpOrginalRow, lstSearchWords);
                break;
            case 1:
                testDataLongSearch(mpOrginalRow, lstSearchWords);
                break;
            case 2:
                testDataLongCat(mpOrginalRow, lstSearchWords);
                break;
            case 3:
                testDataReal(mpOrginalRow, lstSearchWords);
                break;
        }
        // Set test data (Objects are reference}

        try {
            // Get first list which are the search categories
            List<String> colLstSearchCategories = new ArrayList<String>();
            for (Map.Entry<String, List> value : mpOrginalRow.entrySet()) {
                colLstSearchCategories.add(value.getKey() + ",");
            }
            // Store first list in column map
            int iColumnCount = 0;
            Map<Integer, List> mpConvertCols = new LinkedHashMap<Integer, List>();
            mpConvertCols.put(iColumnCount, colLstSearchCategories);

            // Get length of longest list in map to control number of cycles
            int iLengthOfList = 0;
            for (Map.Entry<String, List> value : mpOrginalRow.entrySet()) {
                int iCompare = value.getValue().size();
                if (iLengthOfList < iCompare) {
                    // Set maxium length of longest list
                    iLengthOfList = iCompare;
                }
            }

            // Get list of search words
            int iListElement = 0;
            // Cycle through until maxium list length is met
            while (iLengthOfList > iListElement) {
                iColumnCount++;
                List<String> colLstSearchWords = new ArrayList<String>();
                // Cycle through orginal map and set rows to columns
                for (Map.Entry<String, List> value : mpOrginalRow.entrySet()) {
                    List<String> lstRow = value.getValue();
                    if (lstRow.size() > iListElement) {
                        // If current list size is greater than current elemment, store value in column record
                        String strWord = lstRow.get(iListElement);
                        if (!strWord.isEmpty()) {
                            // Add word to column list
                            colLstSearchWords.add(strWord + ", ");
                        } else {
                            // Add comma to column list
                            colLstSearchWords.add(",");
                        }
                    } else {
                        colLstSearchWords.add(",");
                    }
                }
                // Store value for column records
                mpConvertCols.put(iColumnCount, colLstSearchWords);
                iListElement++;
            }

            // Write data to cvs file
            String fileRowCsv = "RowCsv.csv";
            flRowCsv = new FileWriter(fileRowCsv, false);
            for (Map.Entry<Integer, List> value : mpConvertCols.entrySet()) {
                List<String> lstRow = value.getValue();
                for (String word : lstRow) {
                    flRowCsv.write(word);
                }
                flRowCsv.write("\n");
            }
        } catch (IOException ex) {
            Logger.getLogger(BetaCvsCreate.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (flRowCsv != null) {
                try {
                    flRowCsv.close();
                } catch (IOException ex) {
                    Logger.getLogger(BetaCvsCreate.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    private static void testDataOne(Map<String, List> mpOrginalRow, List<String> lstSearchWords) {

        lstSearchWords.add("list1 word 1");
        lstSearchWords.add("list1 word 2");
        lstSearchWords.add("list1 word 3");
        lstSearchWords.add("list1 word 4");
        lstSearchWords.add("list1 word 5");
        mpOrginalRow.put("list1", lstSearchWords);

        lstSearchWords = new ArrayList<String>();
        lstSearchWords.add("list2 word 1");
        lstSearchWords.add("list2 word 2");
        lstSearchWords.add("list2 word 3");
        lstSearchWords.add("list2 word 4");
        lstSearchWords.add("list2 word 5");
        lstSearchWords.add("list2 word 6");
        lstSearchWords.add("list2 word 7");
        lstSearchWords.add("list2 word 8");
        lstSearchWords.add("list2 word 9");
        lstSearchWords.add("list2 word 10");
        mpOrginalRow.put("list2", lstSearchWords);

        lstSearchWords = new ArrayList<String>();
        lstSearchWords.add("list3 word 1");
        lstSearchWords.add("list3 word 2");
        lstSearchWords.add("list3 word 3");
        lstSearchWords.add("list3 word 4");
        lstSearchWords.add("list3 word 5");
        lstSearchWords.add("list3 word 6");
        lstSearchWords.add("list3 word 7");
        lstSearchWords.add("list3 word 8");
        mpOrginalRow.put("list3", lstSearchWords);
    }

    private static void testDataLongSearch(Map<String, List> mpOrginalRow, List<String> lstSearchWords) {

        lstSearchWords.add("list1 word 1");
        lstSearchWords.add("list1 word 2");
        lstSearchWords.add("list1 word 3");
        lstSearchWords.add("list1 word 4");
        lstSearchWords.add("list1 word 5");
        mpOrginalRow.put("list1", lstSearchWords);

        lstSearchWords = new ArrayList<String>();
        lstSearchWords.add("list2 word 1");
        lstSearchWords.add("list2 word 2");
        lstSearchWords.add("list2 word 3");
        lstSearchWords.add("list2 word 4");
        lstSearchWords.add("list2 word 5");
        lstSearchWords.add("list2 word 6");
        lstSearchWords.add("list2 word 7");
        lstSearchWords.add("list2 word 8");
        lstSearchWords.add("list2 word 9");
        lstSearchWords.add("list2 word 10");
        lstSearchWords.add("list2 word 11");
        lstSearchWords.add("list2 word 12");
        lstSearchWords.add("list2 word 13");
        lstSearchWords.add("list2 word 14");
        lstSearchWords.add("list2 word 15");
        lstSearchWords.add("list2 word 16");
        lstSearchWords.add("list2 word 17");
        lstSearchWords.add("list2 word 18");
        lstSearchWords.add("list2 word 19");
        lstSearchWords.add("list2 word 20");
        lstSearchWords.add("list2 word 21");
        lstSearchWords.add("list2 word 22");
        lstSearchWords.add("list2 word 23");
        lstSearchWords.add("list2 word 24");
        lstSearchWords.add("list2 word 25");
        lstSearchWords.add("list2 word 26");
        lstSearchWords.add("list2 word 27");
        lstSearchWords.add("list2 word 28");
        lstSearchWords.add("list2 word 29");
        lstSearchWords.add("list2 word 30");
        mpOrginalRow.put("list2", lstSearchWords);

        lstSearchWords = new ArrayList<String>();
        lstSearchWords.add("list3 word 1");
        lstSearchWords.add("list3 word 2");
        lstSearchWords.add("list3 word 3");
        lstSearchWords.add("list3 word 4");
        lstSearchWords.add("list3 word 5");
        lstSearchWords.add("list3 word 6");
        lstSearchWords.add("list3 word 7");
        lstSearchWords.add("list3 word 8");
        mpOrginalRow.put("list3", lstSearchWords);
    }

    private static void testDataLongCat(Map<String, List> mpOrginalRow, List<String> lstSearchWords) {

        lstSearchWords.add("list1 word 1");
        mpOrginalRow.put("list1", lstSearchWords);

        lstSearchWords = new ArrayList<String>();
        lstSearchWords.add("list2 word 1");
        lstSearchWords.add("list2 word 2");
        lstSearchWords.add("list2 word 3");
        lstSearchWords.add("list2 word 4");
        lstSearchWords.add("list2 word 5");
        lstSearchWords.add("list2 word 6");
        lstSearchWords.add("list2 word 7");
        lstSearchWords.add("list2 word 8");
        mpOrginalRow.put("list2", lstSearchWords);

        lstSearchWords = new ArrayList<String>();
        lstSearchWords.add("list3 word 1");
        lstSearchWords.add("list3 word 2");
        lstSearchWords.add("list3 word 3");
        lstSearchWords.add("list3 word 4");
        lstSearchWords.add("list3 word 5");
        lstSearchWords.add("list3 word 6");
        lstSearchWords.add("list3 word 7");
        lstSearchWords.add("list3 word 8");
        mpOrginalRow.put("list3", lstSearchWords);

        lstSearchWords = new ArrayList<String>();
        lstSearchWords.add("list4 word 1");
        lstSearchWords.add("list4 word 2");
        lstSearchWords.add("list4 word 3");
        lstSearchWords.add("list4 word 4");
        lstSearchWords.add("list4 word 5");
        lstSearchWords.add("list4 word 6");
        lstSearchWords.add("list4 word 7");
        lstSearchWords.add("list4 word 8");
        mpOrginalRow.put("list4", lstSearchWords);

        lstSearchWords = new ArrayList<String>();
        lstSearchWords.add("list5 word 1");
        lstSearchWords.add("list5 word 2");
        lstSearchWords.add("list5 word 3");
        lstSearchWords.add("list5 word 4");
        lstSearchWords.add("list5 word 5");
        lstSearchWords.add("list5 word 6");
        lstSearchWords.add("list5 word 7");
        lstSearchWords.add("list5 word 8");
        mpOrginalRow.put("list5", lstSearchWords);

        lstSearchWords = new ArrayList<String>();
        lstSearchWords.add("list6 word 1");
        lstSearchWords.add("list6 word 2");
        lstSearchWords.add("list6 word 3");
        lstSearchWords.add("list6 word 4");
        lstSearchWords.add("list6 word 5");
        lstSearchWords.add("list6 word 6");
        lstSearchWords.add("list6 word 7");
        lstSearchWords.add("list6 word 8");
        mpOrginalRow.put("list6", lstSearchWords);

        lstSearchWords = new ArrayList<String>();
        lstSearchWords.add("list7 word 1");
        lstSearchWords.add("list7 word 2");
        lstSearchWords.add("list7 word 3");
        lstSearchWords.add("list7 word 4");
        lstSearchWords.add("list7 word 5");
        lstSearchWords.add("list7 word 6");
        lstSearchWords.add("list7 word 7");
        lstSearchWords.add("list7 word 8");
        mpOrginalRow.put("list7", lstSearchWords);
    }

    private static void testDataReal(Map<String, List> mpOrginalRow, List<String> lstSearchWords) {

        lstSearchWords.add("Patient");
        mpOrginalRow.put("Patient Name (name)", lstSearchWords);

        lstSearchWords = new ArrayList<String>();
        lstSearchWords.add("MRN");
        mpOrginalRow.put("Med Rec Number (follow 1)", lstSearchWords);

        lstSearchWords = new ArrayList<String>();
        lstSearchWords.add("Provider");
        mpOrginalRow.put("Physicial Name (name)", lstSearchWords);

        lstSearchWords = new ArrayList<String>();
        lstSearchWords.add("DOB");
        lstSearchWords.add("Birth date");
        lstSearchWords.add("birthdate");
        lstSearchWords.add("Date of Birth");
        lstSearchWords.add("dob");
        mpOrginalRow.put("Date of Birth (date)", lstSearchWords);

        lstSearchWords = new ArrayList<String>();
        lstSearchWords.add("gender");
        mpOrginalRow.put("Gender (gender)", lstSearchWords);

        lstSearchWords = new ArrayList<String>();
        lstSearchWords.add("Visit Date");
        mpOrginalRow.put("Date of Visit (date)", lstSearchWords);

        lstSearchWords = new ArrayList<String>();
        lstSearchWords.add("Diagnosis");
        mpOrginalRow.put("Diagnoses (all)", lstSearchWords);
    }
}
