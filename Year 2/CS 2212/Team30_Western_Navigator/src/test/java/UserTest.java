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
public class UserTest {
    
    public UserTest() {
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
     * Test of getUsername method, of class User.
     */
    @Test
    public void testGetUsername() {
        System.out.println("getUsername");
        User instance = new User("Username", "password", "testfile.json");
        String expResult = "Username";
        String result = instance.getUsername();
        assertEquals(expResult, result);
    }

    /**
     * Test of setUsername method, of class User.
     */
    @Test
    public void testSetUsername() {
        System.out.println("setUsername");
        String username = "";
        User instance = new User("Username", "password", "testfile.json");
        instance.setUsername(username);
    }

    /**
     * Test of getPassword method, of class User.
     */
    @Test
    public void testGetPassword() {
        System.out.println("getPassword");
        User instance = new User("Username", "password", "testfile.json");
        String expResult = "password";
        String result = instance.getPassword();
        assertEquals(expResult, result);
    }

    /**
     * Test of setPassword method, of class User.
     */
    @Test
    public void testSetPassword() {
        System.out.println("setPassword");
        String password = "";
        User instance = new User("Username", "password", "testfile.json");
        instance.setPassword(password);
    }

    /**
     * Test of getJsonFile method, of class User.
     */
    @Test
    public void testGetJsonFile() {
        System.out.println("getJsonFile");
        User user = new User("testuser", "testpassword", "testfile.json");
        assertEquals("testfile.json", user.getJsonFile());
    }

    /**
     * Test of setJsonFile method, of class User.
     */
    @Test
    public void testSetJsonFile() {
         User user = new User();
        System.out.println("setJsonFile");
        user.setJsonFile("testfile.json");
        assertEquals("testfile.json", user.getJsonFile());
    }
    
}