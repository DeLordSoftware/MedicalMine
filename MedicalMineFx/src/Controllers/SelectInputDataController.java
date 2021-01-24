package Controllers;

import MedicalMineFxMain.ControlledScreen;
import MedicalMineFxMain.MedicalMineFx;
import MedicalMineFxMain.ProcessInputFiles;
import MedicalMineFxMain.ScreenController;
import MedicalMineFxMain.UtlityClass;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author RW Simmons
 */
public class SelectInputDataController implements Initializable, ControlledScreen {

    private static ScreenController screenController;
    public static final int CSV_FILE = 0;
    public static final int SEARCH_FILE = 1;
    public static boolean bHasCsvFile = false;
    public static boolean bHasSearchFile = false;
    
    //final private static String strWelcome = "/FxmlDisplays/WelcomeSelectDisplay.fxml";
    final private String CLR_GREEN = "-fx-text-fill: green";
    final private String CLR_GRAY = "-fx-text-fill: gray";
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnProcess.setStyle(CLR_GRAY);
    }

    /**
     * ***************************************
     * Method : Input : Return : Purpose: **************************************
     */
    @FXML
    private void actBtnCsvSelect(ActionEvent event) throws IOException {

        String strDisplay = ProcessInputFiles.selectProcessFiles(CSV_FILE);

        // Enable process button  
        if (strDisplay != null) {
            lblShowCsv.setText(strDisplay);
            readyToProcess();
        }
    }

    /**
     * ***************************************
     * Method : Input : Return : Purpose: **************************************
     */
    @FXML
    private void actBtnFileSelect(ActionEvent event) throws IOException {

        String strDisplay = ProcessInputFiles.selectProcessFiles(SEARCH_FILE);

        // Enable process button      
        if (strDisplay != null) {
            lblShowText.setText(strDisplay);
            readyToProcess();
        }
    }
    private void readyToProcess(){
        
        if (bHasSearchFile && bHasCsvFile) {
            btnProcess.setDisable(false);
            btnProcess.setStyle(CLR_GREEN);
        }
    }
    /**
     * ***************************************
     * Method : Input : Return : Purpose: 
     ***************************************/
     @FXML
    private void actReturn(ActionEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource(UtlityClass.strFxmlWelcome));
            panelSelect.getChildren().setAll(pane);
        } catch (Exception e) {
            System.out.println("Error with Select return button: " + e.getMessage());
        }//*/
    }    
    /**
     * ***************************************
     * Method : Input : Return : Purpose: 
     ***************************************/
    @FXML
    private void actBtnProcess(ActionEvent event) throws InterruptedException {
        MedicalMineFx.showProgessPage();
        Thread.sleep(10);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ProcessInputFiles processInputFiles = new ProcessInputFiles();
                processInputFiles.processFiles();
                ProcessInputFiles.setJsonLocationFile();
                try {
                    MedicalMineFx.closeProgessPage();
                } catch (InterruptedException ex) {
                    Logger.getLogger(SelectInputDataController.class.getName()).log(Level.SEVERE, null, ex);
                }

                // Reset process button
                bHasSearchFile = false;
                bHasCsvFile = false;
            }
        });
    }

    /**
     * ***************************************
     * Method : Input : Return : Purpose: **************************************
     */
    @Override
    public void setScreenParent(ScreenController screenPage) {
        screenController = screenPage;
    }

    // Components 
    @FXML
    private AnchorPane panelSelect;
    @FXML
    private Button btnReturn;
    @FXML
    private Button btnCsvSelect;
    @FXML
    private Button btnFileSelect;
    @FXML
    private Button btnProcess;
    @FXML
    private Label lblShowCsv;
    @FXML
    private Label lblShowText;

   
}
