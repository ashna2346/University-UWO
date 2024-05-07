/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javax.swing.*;

/**
 *
 * @author Team30
 */
public class MapTest {
    
    public MapTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
        System.out.println("setUpClass()");
    }
    
    @AfterAll
    public static void tearDownClass() {
        System.out.println("tearDownClass");
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
     * Test of setPOIjson method, of class Map.
     */
    @Test
    public void testSetPOIjson() {
        System.out.println("setPOIjson");
        String file = "";
        Map instance = new Map();
        instance.setPOIjson(file);
    }

    /**
     * Test of updateFavoritesMenu method, of class Map.
     */
    @Test
    public void testUpdateFavoritesMenu() {
        System.out.println("updateFavoritesMenu");
        Map instance = new Map();
        JMenu favoritesMenu = new JMenu();
        assertEquals(0, favoritesMenu.getMenuComponentCount());
    }


    /**
     * Test of getBuildingAndFloorName method, of class Map.
     */
    @Test
    public void testGetBuildingAndFloorName() {
        System.out.println("getBuildingAndFloorName");
        Map instance = new Map();
        instance.changeBuilding(2);
        String buildingAndFloorName = instance.getBuildingAndFloorName();
        assertEquals("University Community Centre Floor 1", buildingAndFloorName);
    }

    /**
     * Test of getBuilding method, of class Map.
     */
    @Test
    public void testGetBuilding() {
        System.out.println("getBuilding");
        Map instance = new Map();
        instance.changeBuilding(1);
        String buildingName = instance.getBuilding(); // Act
        assertEquals("Middlesex College", buildingName); // assert
    }

    /**
     * Test of getFloor method, of class Map.
     */
    @Test
    public void testGetFloor() {
        System.out.println("getFloor");
        Map instance = new Map();
        instance.changeBuilding(2);
        int floor = instance.getFloor();
        assertEquals(1, floor);
    }

    /**
     * Test of getNewBuilding method, of class Map.
     */
    @Test
    public void testGetNewBuilding() {
        System.out.println("getNewBuilding");
        //int buildingIndex = 0;
        Map instance = new Map();
        String buildingName1 = instance.getNewBuilding(0);
        String buildingName2 = instance.getNewBuilding(1);
        String buildingName3 = instance.getNewBuilding(2);
        String buildingName4 = instance.getNewBuilding(3);
        String buildingName5 = instance.getNewBuilding(4);
        //String expResult = "";
        //String result = instance.getNewBuilding(buildingIndex);
        //assertEquals(expResult, result);
        assertEquals("Campus Map", buildingName1);
        assertEquals("Middlesex College", buildingName2);
        assertEquals("University Community Centre", buildingName3);
        assertEquals("Natural Science Centre", buildingName4);
        assertEquals("", buildingName5);
    }

    /**
     * Test of changeBuilding method, of class Map.
     */
    @Test
    public void testChangeBuilding() {
        System.out.println("changeBuilding");
        int newBuildingIndex = 1;
        Map instance = new Map();
        instance.changeBuilding(newBuildingIndex);
        assertEquals(newBuildingIndex, instance.currentBuilding);
        assertEquals(0, instance.currentFloor);
        assertNotNull(instance.pois);
        assertTrue(!instance.selectedTypes.isEmpty());
    }

    /**
     * Test of refreshScreen method, of class Map.
     */
    @Test
    public void testRefreshScreen() {
    System.out.println("refreshScreen");
    Map instance = new Map();
    //String building = "Middlesex College";
    
    
    instance.refreshScreen("Middlesex College", 2);
    assertEquals(0, instance.currentBuilding);
    assertEquals(0, instance.currentFloor);
    }
    
}