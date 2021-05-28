/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import static MedicalMineFxMain.ProcessInputFiles.displayMsg;
import MedicalMineFxMain.UtlityClass;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

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

    final private String CATEGORY_FOLLOW = "Follow";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        btnEnter.setDisable(true);
        cmbCatogeryType.getItems().add("Name");
        cmbCatogeryType.getItems().add("Date");
        cmbCatogeryType.getItems().add("Gender");
        cmbCatogeryType.getItems().add("Follow");
        cmbCatogeryType.getItems().add("All");
    }

    @FXML
    private void actEnter(ActionEvent event) {

        String strNumberToFollow = "";

        try {
            if (cmbCatogeryType.getValue() != null) {
                if (cmbCatogeryType.getValue().contains(CATEGORY_FOLLOW)) {
                    JFrame frame = new JFrame();
                    Pattern pattern = Pattern.compile("[a-zA-Z]");
                    boolean hasNoLetters = false;
                    do {
                        // Only allow numbers                       
                        strNumberToFollow = JOptionPane.showInputDialog(frame, "Enter number of words to follow\nOr hit CANCEL to select all words in sentence.").trim();

                        if (!strNumberToFollow.trim().isEmpty()) {
                            if (pattern.matcher(strNumberToFollow).find()) {
                                // If letter is inputed, display message to try again
                                hasNoLetters = false;
                                JOptionPane.showMessageDialog(frame, "Only enter numbers. Please Try again.");//TODO: add error symbol
                            } else {
                                // Only has numbers
                                hasNoLetters = true;
                            }
                        } else {
                            // If on value entered, set to default
                            strNumberToFollow = "all";
                            break;
                        }
                    } while (!hasNoLetters);
                }
//                } else if (cmbCatogeryType.getValue().contains(CATEGORY_FOLLOW)) {
//                    // Default
//                    strNumberToFollow = "all";
//                    cmbCatogeryType.setValue(CATEGORY_FOLLOW);
//                }

                String strCategory = null;
                if (strNumberToFollow.isEmpty()) {
                    // Prevent space in category type
                    strCategory = txtCatogery.getText() + " (" + cmbCatogeryType.getValue() + ")";
                } else {
                    // Add space if category type is (follow)
                    strCategory = txtCatogery.getText() + " (" + cmbCatogeryType.getValue() + " " + strNumberToFollow + ")";
                }
                CreateFileClass.setCatogery(strCategory);

                // Goto Add Field display
                AnchorPane paneFields = FXMLLoader.load(getClass().getResource(UtlityClass.strFxmlAddFields));
                if (paneFields != null) {
                    CatogeryNameDisplay.getChildren().setAll(paneFields);
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR In CreateCategoryDisplayController with enter button: " + e.toString());
            displayMsg(e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    private void btnReturn(ActionEvent event) {
        try {
            // Go to Welcome page
            AnchorPane paneWelcome = FXMLLoader.load(getClass().getResource(UtlityClass.strFxmlWelcome));
            if (paneWelcome != null) {
                CatogeryNameDisplay.getChildren().setAll(paneWelcome);
            }
        } catch (Exception e) {
            System.out.println("Error CreateCategoryDisplayController with Return button:: " + e.toString());
            displayMsg(e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }

    }

    @FXML
    private void actKeyTyped(KeyEvent event) {
        btnEnter.setDisable(false);
    }

    @FXML
    private void actCmbCatogery(ActionEvent event) {
    }

    @FXML
    private void cmbClicked(ContextMenuEvent event) {
    }

}
