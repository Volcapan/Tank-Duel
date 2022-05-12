package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Tests Player class
public class PlayerTest {
    private Tank tank;
    private Player testPlayer;

    @BeforeEach
    public void setUp() {
        tank = new Tank(Tank.MEDIUM, "M4a3e8 Sherman", 60, 120, 30, 360);
        testPlayer = new Player(tank);
    }

    @Test
    public void testPlayer() {
        assertEquals(tank.getType(), testPlayer.getTankType());
        assertEquals(tank.getName(), testPlayer.getTankName());
        assertEquals(tank.getArmor(), testPlayer.getTankArmor());
        assertEquals(tank.getFirepower(), testPlayer.getTankFirepower());
        assertEquals(tank.getMobility(), testPlayer.getTankMobility());
        assertEquals(tank.getHealth(), testPlayer.getHealth());
    }

    @Test
    public void testOtherPlayer() {
        Player otherPlayer = new Player(tank, 10);
        assertEquals(10, otherPlayer.getHealth());
    }

    @Test
    public void testTakeDamage() {
        assertEquals(tank.getHealth(), testPlayer.getHealth());

        testPlayer.takeDamage(0);
        assertEquals(tank.getHealth(), testPlayer.getHealth());

        testPlayer.takeDamage(50);
        assertEquals(tank.getHealth() - 50, testPlayer.getHealth());
    }

    @Test
    public void testHasLost() {
        assertFalse(testPlayer.hasLost());

        testPlayer.takeDamage(tank.getHealth() / 2);
        assertFalse(testPlayer.hasLost());

        testPlayer.takeDamage((tank.getHealth() / 2) - 1);
        assertFalse(testPlayer.hasLost());

        testPlayer.takeDamage(1);
        assertTrue(testPlayer.hasLost());

        testPlayer.takeDamage(50);
        assertTrue(testPlayer.hasLost());
    }

    @Test
    public void testGetTankType() {
        assertEquals(tank.getType(), testPlayer.getTankType());
    }

    @Test
    public void testGetTankName() {
        assertEquals(tank.getName(), testPlayer.getTankName());
    }

    @Test
    public void testGetTankArmor() {
        assertEquals(tank.getArmor(), testPlayer.getTankArmor());
    }

    @Test
    public void testGetTankFirepower() {
        assertEquals(tank.getFirepower(), testPlayer.getTankFirepower());
    }

    @Test
    public void testGetTankMobility() {
        assertEquals(tank.getMobility(), testPlayer.getTankMobility());
    }
}
