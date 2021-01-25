/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author RW Simmons
 */
public class CreateFileClass {

    private static Map<String, ArrayList> mpCreateFile;
    private static ArrayList<String> lstSearchWords;
    private static String strFileName;
    private static String strCatogeryName;
    private static boolean bHasBeenInitialize = false; 

    CreateFileClass() {}

    public static void initialize(){
        mpCreateFile = new LinkedHashMap<String, ArrayList>();
        lstSearchWords = new ArrayList<String>();
        
        bHasBeenInitialize = true;
        System.out.println(bHasBeenInitialize);
    }  
    
    public static void setCatogery(String catogery){
        strCatogeryName = catogery;
        mpCreateFile.put(strCatogeryName, lstSearchWords);        
        System.out.println("------------Set catogery = " + strCatogeryName);
    } 
    
    public static String getCategory(){
        return strCatogeryName;       
    }     
    
    public static void addNewCategory(){
        lstSearchWords = new ArrayList<String>();
    }
    
    public static void setCatogeryWord(String word) {
        if(bHasBeenInitialize && !strCatogeryName.isEmpty()){
            mpCreateFile.get(strCatogeryName).add(word); 
        }        
        tester();
    }    
    
    public static void setFileName(String name){
        if(!name.isEmpty() && bHasBeenInitialize){
            strFileName = name;
            System.out.println("------------strFileName  = " + strFileName);
        } else{
            System.out.println("initialized " + bHasBeenInitialize + " and text value" + strFileName);
        }        
    }
    
    public static String getFileName(){  
        System.out.println("------------Return = " + strFileName);
        return strFileName;        
    }
    
    private static void tester(){
        
         // using iterators 
        Iterator<Map.Entry<String, ArrayList>> itr = mpCreateFile.entrySet().iterator();
          
        while(itr.hasNext()) 
        { 
             Map.Entry<String, ArrayList> entry = itr.next(); 
             System.out.println("Key = " + entry.getKey());  
             ArrayList list = entry.getValue();
             
             for( int ii = 0 ; ii < list.size(); ii++){
                 String value = (String) list.get(ii);
                 System.out.println("Value = " + value);  
             }
        } 
    }
}
