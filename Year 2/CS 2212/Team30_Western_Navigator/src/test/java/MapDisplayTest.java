/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 *
 * @author Team30
 */
public class MapDisplayTest {
     private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
     private static final PrintStream originalOut = System.out;
    
    public MapDisplayTest() {
         System.setOut(new PrintStream(outContent));
    }
    
    @BeforeAll
    public static void setUpClass() {
        
    }
    
    @AfterAll
    public static void tearDownClass() {
        System.out.println("tearDownClass()");
    }
    
    @BeforeEach
    public void setUp() {
        System.out.println("setUpClass()");
    }
    
    @AfterEach
    public void tearDown() {
        System.out.println("AfterEach()");
    }

    /**
     * Test of main method, of class MapDisplay.
     */
    @Test
    public void testMain() {
        MapDisplay.main(new String[]{});
    }
    
}
