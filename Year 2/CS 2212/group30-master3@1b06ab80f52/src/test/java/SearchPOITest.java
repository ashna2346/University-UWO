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
public class SearchPOITest {
    
    public SearchPOITest() {
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
     * Test of searchForPOI method, of class SearchPOI.
     */
    @Test
    public void testSearchForPOI() {
        System.out.println("searchForPOI");
        String query = "MC 17";
        SearchPOI instance = new SearchPOI("src\\poi.json");
        List<PointOfInterest> expResult = instance.searchForPOI("MC 17");
        List<PointOfInterest> result = instance.searchForPOI(query);
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class SearchPOI.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        String filepath = "src\\poi.json";
        SearchPOI searchPOI = new SearchPOI(filepath);
        
        String expected = searchPOI.getAllPOIs().toString() + " ";
        String actual = searchPOI.toString();
        
        assertEquals(expected, actual);
    }

    /**
     * Test of getAllPOIs method, of class SearchPOI.
     */
    @Test
    public void testGetAllPOIs() {
        System.out.println("getAllPOIs");
        SearchPOI instance = new SearchPOI("src\\poi.json");
        List<PointOfInterest> expResult = instance.getAllPOIs();
        List<PointOfInterest> result = instance.getAllPOIs();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPOIsByBuildingAndFloor method, of class SearchPOI.
     */
    @Test
    public void testGetPOIsByBuildingAndFloor() {
        System.out.println("getPOIsByBuildingAndFloor");
        String building = "Middlesex College";
        int floor = 1;
        SearchPOI instance = new SearchPOI("src\\poi.json");
        List<PointOfInterest> expResult = instance.getPOIsByBuildingAndFloor("Middlesex College", 1);
        List<PointOfInterest> result = instance.getPOIsByBuildingAndFloor(building, floor);
        assertEquals(expResult, result);
    }

    /**
     * Test of searchPOIs method, of class SearchPOI.
     */
    @Test
    public void testSearchPOIs() {
        /*String filePath = "path/to/file.json";
        SearchPOI searchPOI = new SearchPOI(filePath);
        List<PointOfInterest> pointsOfInterest = searchPOI.searchPOIs("MC 17");
        // Add an assertion to test the contents of the pointsOfInterest list
        assertEquals(1, pointsOfInterest.size());*/
        String filePath = "src//poi.json";
        SearchPOI searchPOI = new SearchPOI(filePath);
        List<PointOfInterest> expResult = new ArrayList<>();
        expResult.add(new PointOfInterest(1, "MC 17", "17", "Middlesex College", 1593, 1145, "Lecture room in Middlesex College with the seating capacity of 48 people", "classroom", 1, false, false));
        String searchTerm = "MC 17";
        List<PointOfInterest> result = searchPOI.searchPOIs(searchTerm);
        assertEquals(expResult, result);
    }
    
}