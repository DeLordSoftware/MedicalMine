package MedicalMineFxMain;

import static MedicalMineFxMain.MedicalMineFx.MainResource;
import java.awt.Frame;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author RW Simmons
 */
public class MedicalMineFx extends Application {

    private static final String VERSION = "2.1.2";
    private static final String TITLE_NAME = "Search-N-Find by DSC ver ";
    public static String MainScreen = "main";
    public static String MainResource = "/FxmlDisplays/WelcomeSelectDisplay.fxml";
    public static String ProgessScreen = "progess";
    public static String ProgessResource = "/FxmlDisplays/ProcessFileDisplay.fxml";
    public static String LoadScreen = "load";

    private static Stage mainStage;
    private static Stage progessStage;
    private static Stage currentStage;

    public static Stage getStage() {
        return currentStage;
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            CreateStage createStage = new CreateStage();
            currentStage = stage;
            mainStage = createStage.setStage(stage, MainResource, TITLE_NAME + VERSION);
            mainStage.getIcons().add(new Image("/Images/Seek-N-Shall Find Image.png"));
            mainStage.show();
        } catch (Exception e) {
            System.out.println("ERROR 2: " + e.getMessage());
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        if (UtlityClass.checkLicense()) {
            launch(args);
        } else {
            // Display error message and close application 
            Frame frame = null;
            //custom title, error icon
            JOptionPane.showMessageDialog(frame,
                    "Expired or missing license!!!\nSee DeLords Software Consultant.",
                    "License error",
                    JOptionPane.ERROR_MESSAGE);
            System.out.println("Expired or missing license!!!");
            System.exit(0);
        }
    }

    /**
     * showProgessPage
     */
    public static void showProgessPage() {
        CreateStage createStage = new CreateStage();
        progessStage = createStage.setStage(currentStage, ProgessResource, "");
    }   
     
    /**
     *
     * @throws InterruptedException
     */
    public static void closeProgessPage() throws InterruptedException {
        CreateStage createStage = new CreateStage();
        mainStage = createStage.setStage(currentStage, MainResource, TITLE_NAME + VERSION);
        mainStage.show();
    }
}
