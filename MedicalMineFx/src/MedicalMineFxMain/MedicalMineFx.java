
package MedicalMineFxMain;

import static MedicalMineFxMain.MedicalMineFx.MainResource;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author RW Simmons
 */
public class MedicalMineFx extends Application {    
    
    private static final String VERSION = "4.0";
    private static final String TITLE_NAME = "Seek-N-Shall Fine by DSC ver ";
     
    public static String MainScreen      = "main";
    public static String MainResource    = "/FxmlDisplays/SelectInputData.fxml";
    public static String ProgessScreen   = "progess";
    public static String ProgessResource = "/FxmlDisplays/ProcessFileDisplay.fxml";
    public static String LoadScreen      = "load";
    
    private static Stage mainStage;
    private static Stage progessStage;
    private static Stage currentStage;
    
    public static Stage getStage(){
        return currentStage;
    }    
    
    @Override
    public void start(Stage stage) throws Exception {        
        try{
            CreateStage createStage = new CreateStage();           
            currentStage = stage;
            mainStage = createStage.setStage(stage, MainResource, TITLE_NAME + VERSION);           
            mainStage.show();
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
    
    /**
     * 
     */
    public static void showProgessPage(){
        CreateStage createStage = new CreateStage();
        progessStage = createStage.setStage(currentStage, ProgessResource, "");          
    }
    
    /**
     * 
     * @throws InterruptedException 
     */
    public static void closeProgessPage() throws InterruptedException{
       CreateStage createStage = new CreateStage();
       mainStage = createStage.setStage(currentStage, MainResource, TITLE_NAME  + VERSION);
       mainStage.show();
    }
}

/*****************************************
* Class:
* Purpose:  
****************************************/
class CreateStage{
    
    public Stage setStage(Stage stage, String strResource, String title){
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource(strResource));
            Parent loadScreen =(Parent) myLoader.load();
            Scene scene =  new Scene(loadScreen);
            stage.setScene(scene);
            stage.setTitle(title); 
        } catch (IOException ex) {
            Logger.getLogger(CreateStage.class.getName()).log(Level.SEVERE, null, ex);
        }

        return stage;
    }
}
