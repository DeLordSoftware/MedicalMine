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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author RW Simmons
 */
public class AddNewFieldsController implements Initializable {

    @FXML
    private AnchorPane paneAddFields;
    @FXML
    private Button btnFinish;
    @FXML
    private Button btnReturn;
    @FXML
    private Button btnEnter;
    @FXML
    private TextField lblNameField;
    @FXML
    private TextField lblLabelField;
    @FXML
    private TextField lblTypeField;
    @FXML
    private TextField lblOneCriteria;
    @FXML
    private TextField lblThreeCriteria;
    @FXML
    private TextField lblTwoCriteria;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void actFinish(ActionEvent event) {
        
        try {
            AnchorPane paneSelect = FXMLLoader.load(getClass().getResource(UtlityClass.strFxmlSelectInput));
            if(paneSelect != null){
                System.out.println("pane is not null");
                paneAddFields.getChildren().setAll(paneSelect);
            }
        } catch (Exception e) {
            System.out.println("Error CreateCategoryDisplayController with Return button:: " + e.toString());
        }
    }

    @FXML
    private void actReturn(ActionEvent event) {
        try {
            AnchorPane paneWelcome = FXMLLoader.load(getClass().getResource(UtlityClass.strFxmlWelcome));
            if(paneWelcome != null){
                System.out.println("pane is not null");
                paneAddFields.getChildren().setAll(paneWelcome);
            }
        } catch (Exception e) {
            System.out.println("Error CreateCategoryDisplayController with Return button:: " + e.toString());
        }
    }

    @FXML
    private void actEnter(ActionEvent event) {
        
    }
    
}
