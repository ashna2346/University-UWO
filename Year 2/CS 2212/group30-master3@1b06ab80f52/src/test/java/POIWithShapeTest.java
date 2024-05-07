/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import java.awt.Shape;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Team30
 */
public class POIWithShapeTest {
    
    public POIWithShapeTest() {
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
     * Test of getPOI method, of class POIWithShape.
     */
    @Test
    public void testGetPOI()
    {
        System.out.println("getPOI");
        PointOfInterest poi = new PointOfInterest(1234, "Middlesex", "UCC", "Ashna", 8172, 980, "blue", "science", 78, false, true);
        POIWithShape poiWithShape = new POIWithShape(poi, new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0));
        assertEquals(poi, poiWithShape.getPOI(), "POI retrieved does not match expected POI");
    }

    /**
     * Test of getShape method, of class POIWithShape.
     */
    @Test
    public void testGetShape() {
        System.out.println("getShape");
         PointOfInterest poi = new PointOfInterest(1234, "Middlesex", "UCC", "Ashna", 8172, 980, "blue", "science", 78, false, true);
        Shape shape = new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0);
        POIWithShape poiWithShape = new POIWithShape(poi, shape);
        assertEquals(shape, poiWithShape.getShape(), "Shape retrieved does not match expected Shape");
    }
    
}
