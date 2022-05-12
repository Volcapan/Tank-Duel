package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Tests TankGame class
public class TankGameTest {
    private Tank tank1;
    private Tank tank2;
    private TankGame testTankGame;

    @BeforeEach
    public void setUp() {
        tank1 = new Tank(Tank.MEDIUM, "M4a3e8 Sherman", 60, 120, 30, 240);
        Player player1 = new Player(tank1);

        tank2 = new Tank(Tank.HEAVY, "Tiger II", 110, 180, 15, 280);
        Player player2 = new Player(tank2);

        testTankGame = new TankGame(player1, player2);
    }

    @Test
    public void testTankGame() {
        assertEquals(tank1.getType(), testTankGame.getPlayer(1).getTankType());
        assertEquals(tank1.getName(), testTankGame.getPlayer(1).getTankName());
        assertEquals(tank1.getArmor(), testTankGame.getPlayer(1).getTankArmor());
        assertEquals(tank1.getFirepower(), testTankGame.getPlayer(1).getTankFirepower());
        assertEquals(tank1.getMobility(), testTankGame.getPlayer(1).getTankMobility());
        assertEquals(tank1.getHealth(), testTankGame.getPlayer(1).getHealth());

        assertEquals(tank2.getType(), testTankGame.getPlayer(2).getTankType());
        assertEquals(tank2.getName(), testTankGame.getPlayer(2).getTankName());
        assertEquals(tank2.getArmor(), testTankGame.getPlayer(2).getTankArmor());
        assertEquals(tank2.getFirepower(), testTankGame.getPlayer(2).getTankFirepower());
        assertEquals(tank2.getMobility(), testTankGame.getPlayer(2).getTankMobility());
        assertEquals(tank2.getHealth(), testTankGame.getPlayer(2).getHealth());
    }

    @Test
    public void testAttack() {
        assertEquals(tank1.getHealth(), testTankGame.getPlayer(1).getHealth());
        assertEquals(tank2.getHealth(), testTankGame.getPlayer(2).getHealth());

        tank1.setMobility(0);
        testTankGame.attack(2);
        assertEquals(tank1.getHealth() - (tank2.getFirepower() - tank1.getArmor()),
                testTankGame.getPlayer(1).getHealth());
        assertEquals(tank2.getHealth(), testTankGame.getPlayer(2).getHealth());

        tank2.setMobility(100);
        testTankGame.attack(1);
        assertEquals(tank1.getHealth() - (tank2.getFirepower() - tank1.getArmor()),
                testTankGame.getPlayer(1).getHealth());
        assertEquals(tank2.getHealth(), testTankGame.getPlayer(2).getHealth());
    }

    @Test
    public void testAttackButNothingHappens() {
        tank2.setMobility(0);
        tank1.setFirepower(tank2.getArmor());
        testTankGame.attack(1);
        assertEquals(tank1.getHealth(), testTankGame.getPlayer(1).getHealth());
        assertEquals(tank2.getHealth(), testTankGame.getPlayer(2).getHealth());

        tank1.setFirepower(1);
        testTankGame.attack(1);
        assertEquals(tank1.getHealth(), testTankGame.getPlayer(1).getHealth());
        assertEquals(tank2.getHealth(), testTankGame.getPlayer(2).getHealth());

        tank1.setMobility(100);
        testTankGame.attack(2);
        assertEquals(tank1.getHealth(), testTankGame.getPlayer(1).getHealth());
        assertEquals(tank2.getHealth(), testTankGame.getPlayer(2).getHealth());
    }

    @Test
    public void testPlayerHasLost() {
        assertFalse(testTankGame.playerHasLost(1));
        assertFalse(testTankGame.playerHasLost(2));

        testTankGame.getPlayer(2).takeDamage(tank2.getHealth() - 1);
        assertFalse(testTankGame.playerHasLost(1));
        assertFalse(testTankGame.playerHasLost(2));

        testTankGame.getPlayer(2).takeDamage(1);
        assertFalse(testTankGame.playerHasLost(1));
        assertTrue(testTankGame.playerHasLost(2));

        testTankGame.getPlayer(2).takeDamage(1);
        assertFalse(testTankGame.playerHasLost(1));
        assertTrue(testTankGame.playerHasLost(2));
    }

    @Test
    public void testWhoGoesFirst() {
        assertTrue(testTankGame.whoGoesFirst());

        tank1.setMobility(tank2.getMobility());
        assertTrue(testTankGame.whoGoesFirst());

        tank1.setMobility(tank2.getMobility() - 1);
        assertFalse(testTankGame.whoGoesFirst());
    }
}
