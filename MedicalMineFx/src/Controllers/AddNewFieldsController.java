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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author RW Simmons
 */
public class AddNewFieldsController implements Initializable {

    
    @FXML
    private Button btnFinish;
    @FXML
    private AnchorPane paneAddSearchWord;
    @FXML
    private Label lblCatogery;
    @FXML
    private Button btnAddWord;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnNextCatetory;
    @FXML
    private TextArea txtAreaWordReview;
    @FXML
    private TextField txtSearchWord;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtSearchWord.requestFocus();
    }    

    @FXML
    private void actFinish(ActionEvent event) {
        
        try {
            AnchorPane paneSelect = FXMLLoader.load(getClass().getResource(UtlityClass.strFxmlSelectInput));
            if(paneSelect != null){                
                paneAddSearchWord.getChildren().setAll(paneSelect);
            }
        } catch (Exception e) {
            System.out.println("Error CreateCategoryDisplayController with Return button:: " + e.toString());
        }
    }

    private void actReturn(ActionEvent event) {
        try {
            AnchorPane paneWelcome = FXMLLoader.load(getClass().getResource(UtlityClass.strFxmlWelcome));
            if(paneWelcome != null){
                System.out.println("pane is not null");
                paneAddSearchWord.getChildren().setAll(paneWelcome);
            }
        } catch (Exception e) {
            System.out.println("Error CreateCategoryDisplayController with Return button:: " + e.toString());
        }
    }

    @FXML
    private void actAddWord(ActionEvent event) {
        CreateFileClass.setCatogeryWord(txtSearchWord.getText());
        txtSearchWord.setText("");
    }

    @FXML
    private void actEdit(ActionEvent event) {
    }

    @FXML
    private void actNextCatetoy(ActionEvent event) {
        try {
            AnchorPane paneWelcome = FXMLLoader.load(getClass().getResource(UtlityClass.strFxmlCategory));
            if(paneWelcome != null){
                System.out.println("pane is not null");
                paneAddSearchWord.getChildren().setAll(paneWelcome);
            }
        } catch (Exception e) {
            System.out.println("Error CreateCategoryDisplayController with Return button:: " + e.toString());
        }
    }
    
}
