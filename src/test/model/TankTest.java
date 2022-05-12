package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Tests Tank class
class TankTest {
    private Tank tank;

    @BeforeEach
    public void setUp() {
        tank = new Tank(Tank.MEDIUM, "M4a3e8 Sherman", 60, 120, 30, 360);
    }

    @Test
    public void testTank() {
        assertEquals(Tank.MEDIUM, tank.getType());
        assertEquals("M4a3e8 Sherman", tank.getName());
        assertEquals(60, tank.getArmor());
        assertEquals(120, tank.getFirepower());
        assertEquals(30, tank.getMobility());
        assertEquals(360, tank.getHealth());
    }

    @Test
    public void testSetType() {
        assertEquals(Tank.MEDIUM, tank.getType());

        tank.setType(Tank.LIGHT);
        assertEquals(Tank.LIGHT, tank.getType());

        tank.setType(Tank.HEAVY);
        assertEquals(Tank.HEAVY, tank.getType());

        tank.setType(Tank.DESTROYER);
        assertEquals(Tank.DESTROYER, tank.getType());
    }

    @Test
    public void testSetName() {
        assertEquals("M4a3e8 Sherman", tank.getName());

        tank.setName("M4a3e2 Sherman Jumbo");
        assertEquals("M4a3e2 Sherman Jumbo", tank.getName());
    }

    @Test
    public void testSetArmor() {
        assertEquals(60, tank.getArmor());

        tank.setArmor(100);
        assertEquals(100, tank.getArmor());
    }

    @Test
    public void testSetFirepower() {
        assertEquals(120, tank.getFirepower());

        tank.setFirepower(90);
        assertEquals(90, tank.getFirepower());
    }

    @Test
    public void testSetMobility() {
        assertEquals(30, tank.getMobility());

        tank.setMobility(0);
        assertEquals(0, tank.getMobility());

        tank.setMobility(20);
        assertEquals(20, tank.getMobility());

        tank.setMobility(100);
        assertEquals(100, tank.getMobility());
    }

    @Test
    public void testSetHealth() {
        assertEquals(360, tank.getHealth());

        tank.setHealth(400);
        assertEquals(400, tank.getHealth());
    }
}