/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MedicalMineFxMain;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author RW Simmons
 */
public class MedicalMineFx extends Application {
    
    public static String MainScreen = "main";
    public static String MainResource = "/FxmlDisplays/SelectInputData.fxml";
//    public static String CategoryScreen = "category";
//    public static String CategoryResource = "/FxmlDisplays/CategoryScreen.fxml";
//    public static String KeyScreen = "keys";
//    public static String KeyResourse = "/FxmlDisplays/KeywordScreen.fxml";
    public static String LoadScreen = "load";
    
    private static Stage currentStage;
    
    public static Stage getStage(){
        return currentStage;
    }
    
    
    @Override
    public void start(Stage stage) throws Exception {        
        try{
            //https://www.youtube.com/watch?v=5GsdaZWDcdY
            ScreenController screenController = new ScreenController();
            screenController.loadScreen(MainScreen, MainResource);         
            screenController.setScreen(MainScreen);

            Group group = new Group();
            group.getChildren().addAll(screenController);
            Scene scene = new Scene(group);              
            stage.setScene(scene);
            stage.setTitle("Medical Mine by DSC ver 1.0");
            stage.show();
            
            currentStage = stage;
        } catch(Exception e) {
            System.out.println("ERROR 2: " + e.getMessage());
        }       
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
