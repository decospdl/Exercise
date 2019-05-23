/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package activityjunit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Administrator
 */
public class PlayerTest {
    
    public PlayerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getGols method, of class Player.
     */
    @Test
    public void testGetGols() {
        System.out.println("getGols");
        Player instance = null;
        int expResult = 0;
        int result = instance.getGols();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addGols method, of class Player.
     */
    @Test
    public void testAddGols() {
        System.out.println("addGols");
        int gols = 0;
        Player instance = null;
        instance.addGols(gols);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAge method, of class Player.
     */
    @Test
    public void testGetAge() {
        System.out.println("getAge");
        Player instance = null;
        int expResult = 0;
        int result = instance.getAge();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNumber method, of class Player.
     */
    @Test
    public void testGetNumber() {
        System.out.println("getNumber");
        Player instance = null;
        int expResult = 0;
        int result = instance.getNumber();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTeam method, of class Player.
     */
    @Test
    public void testSetTeam() {
        System.out.println("setTeam");
        Team team = null;
        Player instance = null;
        instance.setTeam(team);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTeam method, of class Player.
     */
    @Test
    public void testGetTeam() {
        System.out.println("getTeam");
        Player instance = null;
        Team expResult = null;
        Team result = instance.getTeam();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSkill method, of class Player.
     */
    @Test
    public void testGetSkill() {
        System.out.println("getSkill");
        Player instance = null;
        int expResult = 0;
        int result = instance.getSkill();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of validateSkills method, of class Player.
     */
    @Test
    public void testValidateSkills() {
        System.out.println("validateSkills");
        int skill = 0;
        int expResult = 0;
        int result = Player.validateSkills(skill);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class PlayerImpl extends Player {

        public PlayerImpl() {
            super(null, 0);
        }

        public int getSkill() {
            return 0;
        }
    }
    
}
