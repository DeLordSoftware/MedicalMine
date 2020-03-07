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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author RW Simmons
 */
public class KeywordScreenController implements Initializable, ControlledScreen {    
     ScreenController screenController;
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setScreenParent(ScreenController screen){
        screenController = screen;
    }

    @FXML
    private void btnAcceptKeywordAction(ActionEvent event) {
        String strKeyword = txtArKeyword.getText() + txtKeyword.getText();
        txtArKeyword.setText(strKeyword);
    }

    @FXML
    private void btnBackAction(ActionEvent event) {
        screenController.setScreen(MedicalMineFx.CategoryScreen);
    }
     @FXML
    private AnchorPane btnAcceptKeywory;
    @FXML
    private Button btnAcceptKeyword;
    @FXML
    private Button btnBack;
    @FXML
    private TextArea txtArKeyword;
    @FXML
    private TextField txtKeyword;
}
