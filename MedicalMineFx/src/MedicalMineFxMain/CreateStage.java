/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MedicalMineFxMain;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author RW Simmons
 */
public class CreateStage {
    
    public Stage setStage(Stage stage, String strResource, String title) {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource(strResource));
            Parent loadScreen = (Parent) myLoader.load();
            Scene scene = new Scene(loadScreen);
            stage.setScene(scene);
            stage.setTitle(title);
        } catch (IOException ex) {
            Logger.getLogger(MedicalMineFx.class.getName()).log(Level.SEVERE, null, ex);
        }

        return stage;
    }
}
