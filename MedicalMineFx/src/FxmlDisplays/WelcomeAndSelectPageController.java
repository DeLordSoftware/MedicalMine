/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FxmlDisplays;

import Controllers.SelectInputDataController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author RW Simmons
 */
public class WelcomeAndSelectPageController implements Initializable {
    public static String strFxmlFile = "/FxmlDisplays/SelectInputData.fxml";

    
    private static Stage currentStage;  
    @FXML
    private Button btnSelectSearchList;
    @FXML
    private Button btnExit;
    @FXML
    private AnchorPane RootPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void actSelectSearchList(ActionEvent event) {
        try {
            //FXMLLoader loader = new FXMLLoader(SelectInputDataController.class.getResource(strFxmlFile));
            AnchorPane secondPane = FXMLLoader.load(getClass().getResource(strFxmlFile));
            RootPane.getChildren().setAll(secondPane);
             /*Scene scene = new Scene(login);
            Stage stage = new Stage();
            stage.setScene(scene);//Stage loads the new scene, which has the layout of the fxml
           CreateStage createStage = new CreateStage();
            currentStage = stage;
            mainStage = createStage.setStage(stage, MainResource, TITLE_NAME + VERSION);
            mainStage.getIcons().add(new Image("/Images/Seek-N-Shall Find Image.png"));
            mainStage.show();*/
        } catch (Exception e) {
            System.out.println("ERROR 2: " + e.getMessage());
        }//*/
    }

    @FXML
    private void actExit(ActionEvent event) {
        System.exit(0);
    }
    
}
