/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MedicalMineFxMain;

import java.util.HashMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

/**
 *
 * @author RW Simmons
 */
public class ScreenController extends StackPane{
    
    private HashMap<String, Node> screens = new HashMap<>();
    
    public ScreenController(){
        super();
    }  
    
    public boolean loadScreen (String name, String resource){
        try{
            // Loads the fxml file, and the screen to the screens collections and 
            // finally injects the screenPane to the controller
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource(resource));
            Parent loadScreen =(Parent) myLoader.load();
            ControlledScreen myScreenControler =((ControlledScreen) myLoader.getController());
            myScreenControler.setScreenParent(this);
            addScreen(name, loadScreen);           
            return true;
        } catch(Exception e){
            System.out.println("ERROR 1: " + e.getMessage());
            return false;
        }
    }
    
    public boolean setScreen(final String name){    
        try{
        screens.get(name);
        if(getChildren().size() > 0){
            getChildren().remove(0);
        }
        
        getChildren().add(0, screens.get(name));
        } catch(Exception e){
            System.out.println("ERROR 3: " + e.getMessage());
            return false;
        }
        return true;
    }
    
    public boolean unloadScreen(String name){
        screens.remove(name);
        return true;
    }
    
     public void addScreen(String name, Node screen) {
        screens.put(name, screen);
    }
    
     // Returns the Node with the appropriate name
    public Node getScreen(String name){
        return screens.get(name);
    }   
}
