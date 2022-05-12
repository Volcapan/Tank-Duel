package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Tests TankList class
public class TankListTest {
    private TankList testList;

    @BeforeEach
    public void setUp() {
        testList = new TankList();
    }

    @Test
    public void testTankList() {
        assertEquals(0, testList.getSize());
    }

    @Test
    public void testAddTank() {
        assertEquals(0, testList.getSize());

        Tank tankA = new Tank(Tank.DESTROYER, "a", 1, 1, 1, 1);
        testList.addTank(tankA);
        assertEquals(1, testList.getSize());

        Tank tankAt0 = testList.getTank(0);
        assertEquals(tankA.getType(), tankAt0.getType());
        assertEquals(tankA.getName(), tankAt0.getName());
        assertEquals(tankA.getArmor(), tankAt0.getArmor());
        assertEquals(tankA.getFirepower(), tankAt0.getFirepower());
        assertEquals(tankA.getMobility(), tankAt0.getMobility());
        assertEquals(tankA.getHealth(), tankAt0.getHealth());
    }

    @Test
    public void testAddTankMultiple() {
        Tank tankA = new Tank(Tank.DESTROYER, "a", 1, 1, 1, 1);
        Tank tankB = new Tank(Tank.LIGHT, "b", 2, 2, 2, 2);

        testList.addTank(tankA);
        testList.addTank(tankB);
        assertEquals(2, testList.getSize());

        testList.addTank(tankA);
        assertEquals(3, testList.getSize());
    }

    @Test
    public void testRemoveTank() {
        Tank tankA = new Tank(Tank.DESTROYER, "a", 1, 1, 1, 1);
        Tank tankB = new Tank(Tank.LIGHT, "b", 2, 2, 2, 2);

        testList.addTank(tankA);
        testList.addTank(tankB);
        assertEquals(2, testList.getSize());

        testList.removeTank(0);
        assertEquals(1, testList.getSize());

        Tank tankAt0 = testList.getTank(0);
        assertEquals(tankB.getType(), tankAt0.getType());
        assertEquals(tankB.getName(), tankAt0.getName());
        assertEquals(tankB.getArmor(), tankAt0.getArmor());
        assertEquals(tankB.getFirepower(), tankAt0.getFirepower());
        assertEquals(tankB.getMobility(), tankAt0.getMobility());
        assertEquals(tankB.getHealth(), tankAt0.getHealth());
    }

    @Test
    public void testEditTank() {
        Tank tankA = new Tank(Tank.DESTROYER, "a", 1, 1, 1, 1);
        testList.addTank(tankA);

        testList.editTank(Tank.HEAVY, "b", 2, 2, 2, 2, 0);
        assertEquals(Tank.HEAVY, tankA.getType());
        assertEquals("b", tankA.getName());
        assertEquals(2, tankA.getArmor());
        assertEquals(2, tankA.getFirepower());
        assertEquals(2, tankA.getMobility());
        assertEquals(2, tankA.getHealth());
    }

    @Test
    public void testClearTankList() {
        Tank tankA = new Tank(Tank.DESTROYER, "a", 1, 1, 1, 1);
        Tank tankB = new Tank(Tank.LIGHT, "b", 2, 2, 2, 2);
        testList.addTank(tankA);
        testList.addTank(tankB);

        testList.clearTankList();
        assertEquals(0, testList.getSize());
    }
}
