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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author RW Simmons
 */
public class ProjectNameDisplayController implements Initializable {

    @FXML
    private AnchorPane ProjectNameDisplay;
    @FXML
    private Button btnReturn;
    @FXML
    private Button btnEnter;
    @FXML
    private TextField txtFileName;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnEnter.setDisable(true);
    }

    @FXML
    private void btnReturn(ActionEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource(UtlityClass.strFxmlWelcome));
            ProjectNameDisplay.getChildren().setAll(pane);
        } catch (Exception e) {
            System.out.println("Error ProjectNameDisplayController with Return button:: " + e.toString());
        }
    }

    @FXML
    private void actEnter(ActionEvent event) {
        try {
            if (txtFileName.getText().length() > 0) {
                CreateFileClass.initialize();
                CreateFileClass.setFileName(txtFileName.getText());
                AnchorPane pane = FXMLLoader.load(getClass().getResource(UtlityClass.strFxmlCategory));
                ProjectNameDisplay.getChildren().setAll(pane);
            }
        } catch (Exception e) {
            System.out.println("Error ProjectNameDisplayController with Enter button: " + e.toString());
        }
    }

    @FXML
    private void actKeyTyped(KeyEvent event) {
        btnEnter.setDisable(false);
    }
}
