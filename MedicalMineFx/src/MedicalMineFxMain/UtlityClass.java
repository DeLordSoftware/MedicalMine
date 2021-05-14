/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MedicalMineFxMain;

import static MedicalMineFxMain.ProcessInputFiles.displayMsg;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author RW Simmons
 */
public class UtlityClass {

    public static final String strFxmlWelcome = "/FxmlDisplays/WelcomeSelectDisplay.fxml";
    public static final String strFxmlCategory = "/FxmlDisplays/CreateCategorysDisplay.fxml";
    public static final String strFxmlAddFields = "/FxmlDisplays/AddNewFields.fxml";
    public static final String strFxmlSelectInput = "/FxmlDisplays/SelectInputData.fxml";
    public static final String strFxmlCreateProject = "/FxmlDisplays/ProjectNameDisplay.fxml";
    public static final String RESULT_FOLDER_LOC = "C:/DSC_Data/";
    public static final String CVS_FILE_LOC = "Created CSV/";
    public static final String SEARCH_FILE_LOC = "Search Result/";

    public static boolean checkDirectory() {
        File file = new File(RESULT_FOLDER_LOC);
        File file1 = new File(RESULT_FOLDER_LOC + SEARCH_FILE_LOC);
        File file2 = new File(RESULT_FOLDER_LOC + CVS_FILE_LOC);
        
        try {
            if (!file.exists()) {
                file.mkdirs();
            }

            if (!file1.exists()) {
                file1.mkdirs();
            }

            if (!file2.exists()) {
                file2.mkdirs();
            }
        } catch (Exception e) {
            System.out.println("Error in with making application directories " + e.toString());
            displayMsg(e.getMessage(), JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    public static boolean checkLicense() {
        boolean returnVal = false;
        File file;
        Scanner scanner = null;
        try {
            file = new File("License.dsc");
            if (!file.exists()) {
                // Display error dialog
                return false;
            }
            scanner = new Scanner(file);

            // Get actual expiration date
            String strExpire = scanner.nextLine();
            //System.out.println(strExpire);

            // Get actual expiration encoder
            String strEncoder = scanner.nextLine();
            //System.out.println(strEncoder);

            char[] lstEncoder = strEncoder.toCharArray();
            int iMonth = 0;
            int iDay = 0;
            int iYear = 0;
            int iValue = 0;

            // Convert string to integer list
            for (int ii = 0; ii < 3; ii++) {
                if (lstEncoder[ii] == 'A') {
                    // Convert letter to integer 
                    iValue = 10;
                } else {
                    iValue = Integer.parseInt(String.valueOf(lstEncoder[ii]));
                }
                switch (ii) {
                    case 0:
                        // Month
                        iMonth = iValue;
                        break;
                    case 1:
                        // Day
                        iDay = iValue;
                        break;
                    case 2:
                        // Year
                        iYear = iValue;
                        break;
                }
            }

            // Collect expiration date with in encoded data
            String strMonthLic = String.valueOf(lstEncoder[iMonth]);
            strMonthLic = strMonthLic.concat(String.valueOf(lstEncoder[iMonth + 1]));
            int iMonthLic = Integer.parseInt(strMonthLic);

            String strDayLic = String.valueOf(lstEncoder[iDay]);
            strDayLic = strDayLic.concat(String.valueOf(lstEncoder[iDay + 1]));
            int iDayLic = Integer.parseInt(strDayLic);

            String strYearLic = String.valueOf(lstEncoder[iYear]);
            strYearLic = strYearLic.concat(String.valueOf(lstEncoder[iYear + 1]));
            int iYearLic = Integer.parseInt(strYearLic);

            // Get current date
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");
            Date date = new Date();
            String strCurrentDate = formatter.format(date);
            String[] lstDate = strCurrentDate.split("/");

            int iCurrentMonth = Integer.parseInt(lstDate[0]);
            int iCurrentDay = Integer.parseInt(lstDate[1]);
            int iCurrentYear = Integer.parseInt(lstDate[2]);

            // Check to see if license has expried
            if (iYearLic > iCurrentYear) {
                returnVal = true;
            } else if (iYearLic == iCurrentYear) {
                if (iMonthLic >= iCurrentMonth) {
                    returnVal = true;
                } else {
                    returnVal = false;
                }
            } else {
                returnVal = false;
            }
        } catch (IOException ex) {
            Logger.getLogger(MedicalMineFx.class.getName()).log(Level.SEVERE, null, ex);
            displayMsg(ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return returnVal;
    }
    
    /*  PDF to Text
We created a method named generateTxtFromPDF(…) and divided it into three main parts: loading of the PDF file, extraction of text, and final file creation.

Let’s start with loading part:

File f = new File(filename);
String parsedText;
PDFParser parser = new PDFParser(new RandomAccessFile(f, "r"));
parser.parse();
In order to read a PDF file, we use PDFParser, with an “r” (read) option. Moreover, we need to use the parser.parse() method that will cause the PDF to be parsed as a stream and populated into the COSDocument object.

Let’s take a look at the extracting text part:

COSDocument cosDoc = parser.getDocument();
PDFTextStripper pdfStripper = new PDFTextStripper();
PDDocument pdDoc = new PDDocument(cosDoc);
parsedText = pdfStripper.getText(pdDoc);
In the first line, we’ll save COSDocument inside the cosDoc variable. It will be then used to construct PDocument, which is the in-memory representation of the PDF document. Finally, we will use PDFTextStripper to return the raw text of a document. After all of those operations, we’ll need to use close() method to close all the used streams.

In the last part, we’ll save text into the newly created file using the simple Java PrintWriter:

PrintWriter pw = new PrintWriter("src/output/pdf.txt");
pw.print(parsedText);
pw.close();
Please note that you cannot preserve formatting in a plain text file because it contains text only.
*/
}
