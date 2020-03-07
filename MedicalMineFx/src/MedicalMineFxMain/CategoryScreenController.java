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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author RW Simmons
 */
public class CategoryScreenController implements Initializable, ControlledScreen {
     private ScreenController screenController;   
    
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
                
                try {
                    input = new FileInputStream("C:\\DeLordSoftware\\MedicalMine\\MedicalMineFx\\src\\Images\\Green_Check.png");
                    image = new Image(input);
                    idImageViewer.setImage(image);
                    Scanner scan = new Scanner(file);
                    
                    while(scan.hasNext()){                          
                        System.out.println(scan.next());
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(CategoryScreenController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            try {
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
    
    @FXML
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
    private AnchorPane idCategoryPage;
    @FXML
    private Label lblAcceptFile;
    @FXML
    private ImageView idImageViewer;
    @FXML
    private Button btnKeyword;
    @FXML
    private Button btnBack;
}
