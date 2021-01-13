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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author RW Simmons
 */
public class WelcomeAndSelectPageController implements Initializable {
   

    private static Stage currentStage;  
    
    @FXML
    private AnchorPane panelWelcome;
    @FXML
    private Button btnCreateSearch;
    @FXML
    private Button btnSelectSearchList;
    @FXML
    private Button btnExit;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void actCreateSearch(ActionEvent event) {
        try {            
            AnchorPane createPane = FXMLLoader.load(getClass().getResource(UtlityClass.strFxmlCreateProject));
            panelWelcome.getChildren().setAll(createPane);             
        } catch (Exception e) {
            System.out.println("ERROR In Welcome create display: " + e.toString());
        }
    }

    @FXML
    private void actSelectSearchList(ActionEvent event) {
         try {            
            AnchorPane selectPane = FXMLLoader.load(getClass().getResource(UtlityClass.strFxmlSelectInput));
            panelWelcome.getChildren().setAll(selectPane);             
        } catch (Exception e) {
            System.out.println("ERROR In Welcome select display: " + e.getMessage());
        }
    }

    @FXML
    private void actExit(ActionEvent event) {
        System.exit(0);
    }
    
}
