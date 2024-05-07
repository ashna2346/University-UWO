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

/**
 *
 * @author Team30
 */
public class PointOfInterestTest {
    
    public PointOfInterestTest() {
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
     * Test of getId method, of class PointOfInterest.
     */
    @Test
    public void testGetId() {
        System.out.println("getId()"); // prints out "getId"
        PointOfInterest instance = new PointOfInterest(1234, "Middlesex", "UCC", "Ashna", 8172, 980, "blue", "science", 78, false, true);
        int expResult = 1234;
        int result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of setId method, of class PointOfInterest.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        int id = 0;
        PointOfInterest instance = new PointOfInterest(1234, "Middlesex", "UCC", "Ashna", 8172, 980, "blue", "science", 78, false, true);
        instance.setId(id);
    }

    /**
     * Test of getName method, of class PointOfInterest.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        PointOfInterest instance = new PointOfInterest(1234, "Middlesex", "34", "UCC", 8172, 980, "blue", "science", 78, false, true);
        String expResult = "Middlesex";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of setName method, of class PointOfInterest.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "";
        PointOfInterest instance = new PointOfInterest(1234, "Middlesex", "34", "UCC", 8172, 980, "blue", "science", 4, false, true);
        instance.setName(name);
    }

    /**
     * Test of getRoom_number method, of class PointOfInterest.
     */
    @Test
    public void testGetRoom_number() {
        System.out.println("getRoom_number");
        PointOfInterest instance = new PointOfInterest(1234, "Middlesex", "34", "UCC", 8172, 980, "blue", "science", 4, false, true);
        String expResult = "34";
        String result = instance.getRoom_number();
        assertEquals(expResult, result);
    }

    /**
     * Test of setRoom_number method, of class PointOfInterest.
     */
    @Test
    public void testSetRoom_number() {
        System.out.println("setRoom_number");
        String room_number = "";
        PointOfInterest instance = new PointOfInterest(1234, "Middlesex", "34", "UCC", 8172, 980, "blue", "science", 4, false, true);
        instance.setRoom_number(room_number);
    }

    /**
     * Test of getBuilding method, of class PointOfInterest.
     */
    @Test
    public void testGetBuilding() {
        System.out.println("getBuilding");
        PointOfInterest instance = new PointOfInterest(1234, "Middlesex", "34", "UCC", 8172, 980, "blue", "science", 4, false, true);
        String expResult = "UCC";
        String result = instance.getBuilding();
        assertEquals(expResult, result);
    }

    /**
     * Test of setBuilding method, of class PointOfInterest.
     */
    @Test
    public void testSetBuilding() {
        System.out.println("setBuilding");
        String building = "";
        PointOfInterest instance = new PointOfInterest(1234, "Middlesex", "34", "UCC", 8172, 980, "blue", "science", 4, false, true);
        instance.setBuilding(building);
    }

    /**
     * Test of getX_coordinate method, of class PointOfInterest.
     */
    @Test
    public void testGetX_coordinate() {
        System.out.println("getX_coordinate");
        PointOfInterest instance = new PointOfInterest(1234, "Middlesex", "34", "UCC", 8172, 980, "blue", "science", 4, false, true);
        int expResult = 8172;
        int result = instance.getX_coordinate();
        assertEquals(expResult, result);
    }

    /**
     * Test of setX_coordinate method, of class PointOfInterest.
     */
    @Test
    public void testSetX_coordinate() {
        System.out.println("setX_coordinate");
        int x_coordinate = 0;
        PointOfInterest instance = new PointOfInterest(1234, "Middlesex", "34", "UCC", 8172, 980, "blue", "science", 4, false, true);
        instance.setX_coordinate(x_coordinate);
    }

    /**
     * Test of getY_coordinate method, of class PointOfInterest.
     */
    @Test
    public void testGetY_coordinate() {
        System.out.println("getY_coordinate");
        PointOfInterest instance = new PointOfInterest(1234, "Middlesex", "34", "UCC", 8172, 980, "blue", "science", 4, false, true);
        int expResult = 980;
        int result = instance.getY_coordinate();
        assertEquals(expResult, result);
    }

    /**
     * Test of setY_coordinate method, of class PointOfInterest.
     */
    @Test
    public void testSetY_coordinate() {
        System.out.println("setY_coordinate");
        int y_coordinate = 0;
        PointOfInterest instance = new PointOfInterest(1234, "Middlesex", "34", "UCC", 8172, 980, "blue", "science", 4, false, true);
        instance.setY_coordinate(y_coordinate);
    }

    /**
     * Test of getDescription method, of class PointOfInterest.
     */
    @Test
    public void testGetDescription() {
        System.out.println("getDescription");
        PointOfInterest instance = new PointOfInterest(1234, "Middlesex", "34", "UCC", 8172, 980, "blue", "science", 4, false, true);
        String expResult = "blue";
        String result = instance.getDescription();
        assertEquals(expResult, result);
    }

    /**
     * Test of setDescription method, of class PointOfInterest.
     */
    @Test
    public void testSetDescription() {
        System.out.println("setDescription");
        String description = "";
        PointOfInterest instance = new PointOfInterest(1234, "Middlesex", "34", "UCC", 8172, 980, "blue", "science", 4, false, true);
        instance.setDescription(description);
    }

    /**
     * Test of getType method, of class PointOfInterest.
     */
    @Test
    public void testGetType() {
        System.out.println("getType");
        PointOfInterest instance = new PointOfInterest(1234, "Middlesex", "34", "UCC", 8172, 980, "blue", "science", 4, false, true);
        String expResult = "science";
        String result = instance.getType();
        assertEquals(expResult, result);
    }

    /**
     * Test of setType method, of class PointOfInterest.
     */
    @Test
    public void testSetType() {
        System.out.println("setType");
        String type = "";
        PointOfInterest instance = new PointOfInterest(1234, "Middlesex", "34", "UCC", 8172, 980, "blue", "science", 4, false, true);
        instance.setType(type);
    }

    /**
     * Test of getFloor method, of class PointOfInterest.
     */
    @Test
    public void testGetFloor() {
        System.out.println("getFloor");
        PointOfInterest instance = new PointOfInterest(1234, "Middlesex", "34", "UCC", 8172, 980, "blue", "science", 4, false, true);
        int expResult = 4;
        int result = instance.getFloor();
        assertEquals(expResult, result);
    }

    /**
     * Test of setFloor method, of class PointOfInterest.
     */
    @Test
    public void testSetFloor() {
        System.out.println("setFloor");
        int floor = 0;
        PointOfInterest instance = new PointOfInterest(1234, "Middlesex", "34", "UCC", 8172, 980, "blue", "science", 4, false, true);
        instance.setFloor(floor);
    }

    /**
     * Test of getFavorite method, of class PointOfInterest.
     */
    @Test
    public void testGetFavorite() {
        System.out.println("getFavorite");
        PointOfInterest instance = new PointOfInterest(1234, "Middlesex", "34", "UCC", 8172, 980, "blue", "science", 4, false, true);
        boolean expResult = false;
        boolean result = instance.getFavorite();
        assertEquals(expResult, result);
    }

    /**
     * Test of setFavorite method, of class PointOfInterest.
     */
    @Test
    public void testSetFavorite() {
        System.out.println("setFavorite");
        boolean favorite = false;
        PointOfInterest instance = new PointOfInterest(1234, "Middlesex", "34", "UCC", 8172, 980, "blue", "science", 4, false, true);
        instance.setFavorite(favorite);
    }

    /**
     * Test of setUserCreated method, of class PointOfInterest.
     */
    @Test
    public void testSetUserCreated() {
        System.out.println("setUserCreated");
        boolean userCreated = false;
        PointOfInterest instance = new PointOfInterest(1234, "Middlesex", "34", "UCC", 8172, 980, "blue", "science", 4, false, true);
        instance.setUserCreated(userCreated);
    }

    /**
     * Test of getUserCreated method, of class PointOfInterest.
     */
    @Test
    public void testGetUserCreated() {
        System.out.println("getUserCreated");
        PointOfInterest instance = new PointOfInterest(1234, "Middlesex", "UCC", "UCC", 8172, 980, "blue", "science", 4, false, true);
        boolean expResult = true;
        boolean result = instance.getUserCreated();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class PointOfInterest.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        PointOfInterest instance = new PointOfInterest(1234, "Middlesex", "34", "UCC", 8172, 980, "blue", "science", 4, false, true);
        String expResult = "PointOfInterest{id=1234, name='Middlesex', room_number='34', building='UCC', floor=4, x_coordinate=8172, y_coordinate=980, description='blue', type='science'}";
        //String expResult = "PointOfInterest{id=1234,name='Middlesex',room_number='34',building='UCC',x_coordinate=8172,y_coordinate=980,description='blue',type='science',floor=4,favorite=false,userCreated=true}";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
}