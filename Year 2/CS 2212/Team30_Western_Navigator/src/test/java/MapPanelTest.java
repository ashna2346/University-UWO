/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import javax.swing.*;


/**
 *
 * @author Team30
 */
public class MapPanelTest {
    
    public MapPanelTest() {
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
     * Test of setPOIjson method, of class MapPanel.
     */
    /*
    @Test
    public void testSetPOIjson() {
        System.out.println("setPOIjson");
        String filepath = "src\\poi.json";
        MapPanel instance = new MapPanel(new Map());
        instance.setPOIjson(filepath);
    }/*

    /**
     * Test of mouseWheelMoved method, of class MapPanel.
     */
    
    @Test
    public void testMouseWheelMoved() {
        System.out.println("mouseWheelMoved");
        //MouseWheelEvent e = null;
        //MapPanel instance = new MapPanel(new Map());
        //instance.mouseWheelMoved(e);
        
        MapPanel instance = new MapPanel(new Map());
        instance.setFloorPlanImage(new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB));

        // Test zooming in
        MouseWheelEvent zoomInEvent = new MouseWheelEvent(instance, MouseWheelEvent.WHEEL_UNIT_SCROLL, System.currentTimeMillis(), 0, 0, 0, 0, false, MouseWheelEvent.WHEEL_BLOCK_SCROLL, 1,-1);
        instance.mouseWheelMoved(zoomInEvent);
        assertEquals(1.1, instance.getScale(), 0.01);

        // Test zooming out
        MouseWheelEvent zoomOutEvent = new MouseWheelEvent(instance, MouseWheelEvent.WHEEL_UNIT_SCROLL, System.currentTimeMillis(), 0, 0, 0, 0, false, MouseWheelEvent.WHEEL_BLOCK_SCROLL, 1, 1);
        instance.mouseWheelMoved(zoomOutEvent);
        assertEquals(1.0, instance.getScale(), 0.01);
    }

    /**
     * Test of setFloorPlanImage method, of class MapPanel.
     */
    /*
    @Test
    public void testSetFloorPlanImage() {
        System.out.println("setFloorPlanImage");
        BufferedImage floorPlanImage = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
        MapPanel instance = new MapPanel(new Map());
        instance.setFloorPlanImage(floorPlanImage);
        assertEquals(floorPlanImage, instance.getFloorPlanImage());
    }
    */

    /**
     * Test of getFloorPlanImage method, of class MapPanel.
     */
    @Test
    public void testGetFloorPlanImage() {
        System.out.println("getFloorPlanImage");
        BufferedImage expResult = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        MapPanel instance = new MapPanel(new Map());
        instance.setFloorPlanImage(expResult);
        BufferedImage result = instance.getFloorPlanImage();
        assertEquals(expResult, result);
    }

    /**
     * Test of centerPOI method, of class MapPanel.
     */
    /*
    @Test
    public void testCenterPOI() {
        System.out.println("centerPOI");
      
        Map map = new Map();
        PointOfInterest poi = new PointOfInterest(1, "MC 17", "17", "Middlesex College", 1593, 1145, "Lecture room in Middlesex College with the seating capacity of 48 people", "classroom", 1, false, false);
        MapPanel instance = new MapPanel(map);
        instance.centerPOI(poi);

        assertEquals(1.25, instance.getScale(), 0.01);
        assertEquals(poi.getX_coordinate(), instance.offsetX * -1);
        assertEquals(poi.getY_coordinate(), instance.offsetY * -1);
        assertTrue(instance.isPOISelected);
    }
    */

    /**
     * Test of setSelectedPOI method, of class MapPanel.
     */
    @Test
    public void testSetSelectedPOI() {
        System.out.println("setSelectedPOI");
        int x = 250;
        int y = 250;
        MapPanel instance = new MapPanel(new Map());
        instance.setSelectedPOI(x, y);
    }

    /**
     * Test of clearSelectedPOI method, of class MapPanel.
     */
    /*
    @Test
    public void testClearSelectedPOI() {
        System.out.println("clearSelectedPOI");
        MapPanel instance = new MapPanel(new Map());
        instance.clearSelectedPOI();
    }
    */
    /**
     * Test of displayPOIPanel method, of class MapPanel.
     */
    @Test
    public void testDisplayPOIPanel() {
        System.out.println("displayPOIPanel");
        PointOfInterest poi = new PointOfInterest(1, "MC 17", "17", "Middlesex College", 1593, 1145, "Lecture room in Middlesex College with the seating capacity of 48 people", "classroom", 1, false, false);
        MapPanel instance = new MapPanel(new Map());
        instance.displayPOIPanel(poi);
    }

    /**
     * Test of drawCircle method, of class MapPanel.
     */
    /*
    @Test
    public void testDrawCircle() {
        System.out.println("drawCircle");
        Graphics2D g2d = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB).createGraphics();
        int x = 250;
        int y = 250;
        Color color = Color.RED;
        MapPanel instance = new MapPanel(new Map());
        instance.drawCircle(g2d, x, y, color);
    }
    */

    /**
     * Test of getScale method, of class MapPanel.
     */
    @Test
    public void testGetScale() {
        System.out.println("getScale");
        MapPanel instance = new MapPanel(new Map());
        double expResult = 0.65;
        double result = instance.getScale();
        assertEquals(expResult + 0.1, result);
    }

    /**
     * Test of displayAllPOIs method, of class MapPanel.
     */
    /*
    @Test
    public void testDisplayAllPOIs() {
        System.out.println("displayAllPOIs");
        String currentBuilding = "Middlesex College";
        int currentFloor = 1;
        MapPanel instance = new MapPanel(new Map());
        instance.displayAllPOIs(currentBuilding, currentFloor);
    }
    */

    /**
     * Test of updatePOIDisplay method, of class MapPanel.
     */
    @Test
    public void testUpdatePOIDisplay() {
        System.out.println("updatePOIDisplay");
        Set<String> selectedTypes = new HashSet<>();
        String building = "UCC";
        int floor = 1;
        boolean showFavorites = false;
        boolean userCreated = false;
        MapPanel instance = new MapPanel(new Map());
        instance.updatePOIDisplay(selectedTypes, building, floor, showFavorites, userCreated);
    }

    /**
     * Test of clearAllPOICircles method, of class MapPanel.
     */
    /*
    @Test
    public void testClearAllPOICircles() {
        System.out.println("clearAllPOICircles");
        MapPanel instance = new MapPanel(new Map());
        instance.clearAllPOICircles();
    } 
*/
}
