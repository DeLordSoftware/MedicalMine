/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MedicalMineFxMain;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author RW Simmons
 */
public class UtlityClassTest {
    
    public UtlityClassTest() {
    }    
  

    /**
     * Test of checkDirectory method, of class UtlityClass.
     */
    @Test
    public void testCheckDirectory() {
        System.out.println("checkDirectory");
        boolean expResult = true;
        boolean result = UtlityClass.checkDirectory();
        assertEquals(expResult, result);
    }

    /**
     * Test of checkLicense method, of class UtlityClass.
     */
   @Test
   public void testCheckLicense() {
        System.out.println("checkLicense");
        boolean expResult = true;
        boolean result = UtlityClass.checkLicense();
        assertEquals(expResult, result);        
    }    
}
