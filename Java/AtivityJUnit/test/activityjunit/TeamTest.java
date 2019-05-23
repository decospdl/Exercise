/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package activityjunit;

import java.util.ArrayList;
import java.util.HashMap;
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
public class TeamTest {  

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test of isTeamFull method, of class Team.
     */
    @Test
    public void testIsTeamFull() {
        System.out.println("isTeamFull");
        Team instance = null;
        boolean expResult = false;
        boolean result = instance.isTeamFull();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addPlayer method, of class Team.
     */
    @Test
    public void testAddPlayer() {
        System.out.println("addPlayer");
        Player player = null;
        Team instance = null;
        instance.addPlayer(player);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removePlayer method, of class Team.
     */
    @Test
    public void testRemovePlayer() {
        System.out.println("removePlayer");
        Player player = null;
        Team instance = null;
        instance.removePlayer(player);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTotalSkill method, of class Team.
     */
    @Test
    public void testGetTotalSkill() {
        System.out.println("getTotalSkill");
        Team instance = null;
        int expResult = 0;
        int result = instance.getTotalSkill();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getResults method, of class Team.
     */
    @Test
    public void testGetResults() {
        System.out.println("getResults");
        Team instance = null;
        HashMap<Result, Integer> expResult = null;
        HashMap<Result, Integer> result = instance.getResults();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPlayers method, of class Team.
     */
    @Test
    public void testGetPlayers() {
        System.out.println("getPlayers");
        Team instance = null;
        ArrayList<Player> expResult = null;
        ArrayList<Player> result = instance.getPlayers();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of showResults method, of class Team.
     */
    @Test
    public void testShowResults() {
        System.out.println("showResults");
        Team instance = null;
        instance.showResults();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
