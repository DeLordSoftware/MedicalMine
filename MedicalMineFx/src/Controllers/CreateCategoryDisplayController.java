/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import MedicalMineFxMain.UtlityClass;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author RW Simmons
 */
public class CreateCategoryDisplayController implements Initializable {

    
    @FXML
    private Button btnEnter;
    @FXML
    private Button btnReturn;
    @FXML
    private AnchorPane CatogeryNameDisplay;
    @FXML
    private ComboBox<String> cmbCatogeryType;
    @FXML
    private TextField txtCatogery;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        
        cmbCatogeryType.getItems().add("Name");
        cmbCatogeryType.getItems().add("Date");
        cmbCatogeryType.getItems().add("Gender");   
        cmbCatogeryType.getItems().add("Follow");
    }    

    @FXML
    private void actEnter(ActionEvent event) {
         try {             
             
            CreateFileClass.setCatogery(txtCatogery.getText() + " (" + cmbCatogeryType.getValue() + ")");            
            AnchorPane paneFields = FXMLLoader.load(getClass().getResource(UtlityClass.strFxmlAddFields));
            if(paneFields != null){
                CatogeryNameDisplay.getChildren().setAll(paneFields);             
            }
        } catch (Exception e) {
            System.out.println("ERROR In CreateCategoryDisplayController with enter button: " + e.toString());
        }
    }

    @FXML
    private void btnReturn(ActionEvent event) {
        try {
            AnchorPane paneWelcome = FXMLLoader.load(getClass().getResource(UtlityClass.strFxmlWelcome));
            if(paneWelcome != null){
                CatogeryNameDisplay.getChildren().setAll(paneWelcome);
            }
        } catch (Exception e) {
            System.out.println("Error CreateCategoryDisplayController with Return button:: " + e.toString());
        }
        
    }


    @FXML
    private void actCmbCatogery(ActionEvent event) {
    }
    
}
