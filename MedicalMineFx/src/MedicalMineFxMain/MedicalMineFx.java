package MedicalMineFxMain;

import static MedicalMineFxMain.MedicalMineFx.MainResource;
import java.awt.Frame;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author RW Simmons
 */
public class MedicalMineFx extends Application {

    private static final String VERSION = "1.5.1";
    private static final String TITLE_NAME = "Seek-N-Shall Find by DSC ver ";
    public static String MainScreen = "main";
    public static String MainResource = "/FxmlDisplays/SelectInputData.fxml";
    public static String ProgessScreen = "progess";
    public static String ProgessResource = "/FxmlDisplays/ProcessFileDisplay.fxml";
    public static String LoadScreen = "load";

    private static Stage mainStage;
    private static Stage progessStage;
    private static Stage currentStage;

    public static Stage getStage() {
        return currentStage;
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            CreateStage createStage = new CreateStage();
            currentStage = stage;
            mainStage = createStage.setStage(stage, MainResource, TITLE_NAME + VERSION);
            mainStage.getIcons().add(new Image("/Images/Seek-N-Shall Find Image.png"));
            mainStage.show();
        } catch (Exception e) {
            System.out.println("ERROR 2: " + e.getMessage());
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        if (checkLicense()) {
            launch(args);
        } else {
            // Display error message and close application 
            Frame frame = null;
            //custom title, error icon
            JOptionPane.showMessageDialog(frame,
                    "Expired or missing license!!!\nSee DeLords Software Consultant.",
                    "License error",
                    JOptionPane.ERROR_MESSAGE);
            System.out.println("Expired or missing license!!!");
            System.exit(0);
        }
    }

    /**
     * showProgessPage
     */
    public static void showProgessPage() {
        CreateStage createStage = new CreateStage();
        progessStage = createStage.setStage(currentStage, ProgessResource, "");
    }

    private static boolean checkLicense() {
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
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return returnVal;
    }

    /**
     *
     * @throws InterruptedException
     */
    public static void closeProgessPage() throws InterruptedException {
        CreateStage createStage = new CreateStage();
        mainStage = createStage.setStage(currentStage, MainResource, TITLE_NAME + VERSION);
        mainStage.show();
    }
}

/**
 * ***************************************
 * Class: Purpose: **************************************
 */
class CreateStage {

    public Stage setStage(Stage stage, String strResource, String title) {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource(strResource));
            Parent loadScreen = (Parent) myLoader.load();
            Scene scene = new Scene(loadScreen);
            stage.setScene(scene);
            stage.setTitle(title);
        } catch (IOException ex) {
            Logger.getLogger(CreateStage.class.getName()).log(Level.SEVERE, null, ex);
        }

        return stage;
    }
}
