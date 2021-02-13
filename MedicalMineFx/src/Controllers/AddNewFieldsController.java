/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import MedicalMineFxMain.UtlityClass;
import java.awt.Frame;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author RW Simmons
 */
public class AddNewFieldsController implements Initializable {    
   
    @FXML
    private AnchorPane paneAddSearchWord;
    @FXML
    private Button btnAddWord;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnNextCategory;   
    @FXML
    private Button btnFinish;
    @FXML
    private TextArea txtAreaWordReview;
    @FXML
    private TextField txtSearchWord;
    @FXML
    private Label lblCategory;
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Disable Text Area review 
        txtAreaWordReview.setEditable(false);
        
        // Display Category text 
        lblCategory.setText(CreateFileClass.getCategory());
        
        // Disable buttons
        btnFinish.setDisable(true);
        btnNextCategory.setDisable(true);
    }    

    @FXML
    private void actFinish(ActionEvent event) {        
        try {
            // Verify that file map is populated
            if(CreateFileClass.isFileMapComplete()){
                CreateFileClass.WriteToFile();
                AnchorPane paneSelect = FXMLLoader.load(getClass().getResource(UtlityClass.strFxmlSelectInput));
                if(paneSelect != null){                
                    paneAddSearchWord.getChildren().setAll(paneSelect);
                }
            }
            else{
                // Display error message
                Frame frame = new Frame();
                JOptionPane.showMessageDialog(frame, "Project is incomplete");// TODo: add error symbol
            }
        } catch (Exception e) {
            System.out.println("Error CreateCategoryDisplayController with Return button:: " + e.toString());
        }
    }

    private void actReturn(ActionEvent event) {
        try {
            // Return to welcome page
            AnchorPane paneWelcome = FXMLLoader.load(getClass().getResource(UtlityClass.strFxmlWelcome));
            if(paneWelcome != null){
                paneAddSearchWord.getChildren().setAll(paneWelcome);
            }
        } catch (Exception e) {
            System.out.println("Error CreateCategoryDisplayController with Return button:: " + e.toString());
        }
    }

    @FXML
    private void actAddWord(ActionEvent event) {
        // Set search work in file map
        CreateFileClass.setCatogeryWord(txtSearchWord.getText());
        
        // Verify that search word has been entered
        if(!txtAreaWordReview.getText().isEmpty()){
            // Add search word to text area 
           txtAreaWordReview.setText(txtAreaWordReview.getText() + "\n" + txtSearchWord.getText());
        }
        else{
            // Add first search word to text area
             txtAreaWordReview.setText(txtSearchWord.getText());
        }
        // Clear search word text
        txtSearchWord.setText("");
    }

    @FXML    
    private void actEdit(ActionEvent event) {
           // TODO: Create a way for user to edit search words 
    }

    /**
     * Create next Category
     * @param event 
     */
    @FXML
    private void btnNextCategory(ActionEvent event) {
        try {
            // Add search word before going to get next category
            if(!txtSearchWord.getText().isEmpty()){
                CreateFileClass.setCatogeryWord(txtSearchWord.getText());
            }
            
            // Setup next category word
            CreateFileClass.addNewCategory();
            
            
            AnchorPane paneCategory = FXMLLoader.load(getClass().getResource(UtlityClass.strFxmlCategory));
            if(paneCategory != null){
                paneAddSearchWord.getChildren().setAll(paneCategory);
            }
        } catch (Exception e) {
            System.out.println("Error CreateCategoryDisplayController with Return button:: " + e.toString());
        }
    }

    /**
     *  Check to see if text has been entered into text field to enable buttons
     * @param event 
     */
    @FXML
    private void keyTypedTextField(KeyEvent event) {        
        // Enable buttons once search word entered
        btnFinish.setDisable(false);
        btnNextCategory.setDisable(false);
    }
    
}
