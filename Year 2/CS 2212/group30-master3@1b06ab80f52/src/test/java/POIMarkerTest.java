/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import java.awt.Graphics;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author Team30
 */
public class POIMarkerTest {
    
    public POIMarkerTest() {
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
     * Test of draw method, of class POIMarker.
     */
    @Test
    public void testDraw() {
        System.out.println("draw");
        POIMarker instance = new POIMarker(10, 20, 10, Color.RED);
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB); // create a graphics object
        Graphics g = image.createGraphics();
        instance.draw(g);
        // Check that the circle was correctly drawn at the specified location and size
        Color actualColor = new Color(image.getRGB(10, 20), true);
        assertEquals(Color.RED, actualColor, "Marker color is incorrect");
        int actualDiameter = getDiameter(image, 10, 20);
        assertEquals(2 * 5, actualDiameter, "Marker size is incorrect");
    }
    // helper method used to find the diameter of the filled circle at the specified location
    private int getDiameter(BufferedImage image, int x, int y)
    {
    int diameter = 0;
    int pixel = image.getRGB(x, y);
    while (image.getRGB(x + diameter, y) == pixel && x + diameter < image.getWidth())
    {
        diameter++;
    }
    return diameter;
    }

    /**
     * Test of contains method, of class POIMarker.
     */
    @Test
    public void testContains() {
        System.out.println("contains");
        POIMarker instance = new POIMarker(10,20,5, Color.GREEN);
         assertTrue(instance.contains(10, 20)); // inside the marker
         assertFalse(instance.contains(5, 15)); // outside the marker

    }
    
}
