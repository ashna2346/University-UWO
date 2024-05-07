/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

/**
 *
 * @author Team30
 */
public class POIfilereaderTest {
    
    public POIfilereaderTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
        System.out.println("setUpClass()");
    }
    
    @AfterAll
    public static void tearDownClass() {
        System.out.println("tearDownClass()");
    }
    
    @BeforeEach
    public void setUp() {
        System.out.println("setUp()");
    }
    
    @AfterEach
    public void tearDown() {
        System.out.println("tearDown()");
    }

    /**
     * Test of readPOIsFromFile method, of class POIfilereader.
     */
    @Test
    public void testReadPOIsFromFile()
    {
        System.out.println("readPOIsFromFile");
        String filePath = "src//poi.json";
        List<PointOfInterest> result = POIfilereader.readPOIsFromFile(filePath);
        // Add assertions to verify that the result is as expected
        assertNotNull(result);
        assertEquals(135, result.size());
    }
}