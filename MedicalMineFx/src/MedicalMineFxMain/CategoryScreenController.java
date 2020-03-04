/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MedicalMineFxMain;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author RW Simmons
 */
public class CategoryScreenController implements Initializable, ControlledScreen {

    @FXML
    private Button btnKeyword;
    @FXML
    private TextField txtEnterCategory;
    ScreenController screenController;
    @FXML
    private Button btnBack;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    
}
