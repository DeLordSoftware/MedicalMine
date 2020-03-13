/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MedicalMineFxMain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author RW Simmons
 */
public class CategoryScreenController implements Initializable, ControlledScreen {
    private ScreenController screenController;  
    private File fileInput;
    
    @FXML
    private Button btnProcess;   
    @FXML
    private Button btnFIndCsv;
    
     /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
  
    //http://www.java2s.com/Code/Java/JavaFX/DraganddropfiletoScene.htm
    @FXML
    private void dragDropCategoryPg(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        FileInputStream input; 
        Image image;        
        if (db.hasFiles() && db.getFiles().toString().contains("csv")) {
            success = true;
            String filePath = null;
            for (File file : db.getFiles()) {                
                filePath = file.getAbsolutePath();
                System.out.println(filePath);     
                fileInput = file;                    
                try {                  
                    // Display green check mark
                    input = new FileInputStream("C:\\DeLordSoftware\\MedicalMine\\MedicalMineFx\\src\\Images\\Green_Check.png");
                    image = new Image(input);
                    idImageViewer.setImage(image);        
                    btnProcess.setDisable(false);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(CategoryScreenController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break; // Only one file
            }
        } else {
            try {
                // Display red X-mark
                input = new FileInputStream("C:\\DeLordSoftware\\MedicalMine\\MedicalMineFx\\src\\Images\\Red_X.png");                  
                image = new Image(input);
                idImageViewer.setImage(image);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(CategoryScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        event.setDropCompleted(success);
        event.consume();
    }
    
    @FXML
    private void dragOverCategoryPg(DragEvent event) {
         Dragboard db = event.getDragboard();
        if (db.hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY);
        } else {
            event.consume();
        }
    }
    
    private void btnKeywordAction(ActionEvent event) {       
        screenController.setScreen(MedicalMineFx.KeyScreen);
    }
    
    public void setScreenParent(ScreenController screen){
        screenController = screen;
    }
    
    @FXML
    private void btnBackAction(ActionEvent event) {
        screenController.setScreen(MedicalMineFx.MainScreen);
    }
    
    @FXML
    private void actBtnProcess(ActionEvent event) {
        // Parse Input file to create seach data
        ParseInputFiles.setSearchData(fileInput);

        ProcessInputFiles processInputFiles = new ProcessInputFiles();
        processInputFiles.processFiles(MedicalMineFx.getStage());
        btnProcess.setDisable(true);

        // get a handle to the stage
        Scene stage = (Scene) btnProcess.getScene();

        // do what you have to do
        //stage.;
    }
    
    @FXML
    private void actBtnFIndCsv(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(MedicalMineFx.getStage());
        //ParseInputFiles.setSearchData(file);        
    }
    
    @FXML
    private AnchorPane idCategoryPage;
    @FXML
    private Label lblAcceptFile;
    @FXML
    private ImageView idImageViewer;
    @FXML
    private Button btnBack;

    

    
}
