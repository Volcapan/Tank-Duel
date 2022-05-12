package persistence;

import model.Tank;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Based on JsonTest from JsonSerializationDemo
// Tests each individual tank a part of a tank list
public class JsonTankTest {
    @Test
    public static void testJsonReaderTank(String type, String name, int armor, int firepower, int mobility, int health,
                                   Tank tank) {
        assertEquals(type, tank.getType());
        assertEquals(name, tank.getName());
        assertEquals(armor, tank.getArmor());
        assertEquals(firepower, tank.getFirepower());
        assertEquals(mobility, tank.getMobility());
        assertEquals(health, tank.getHealth());
    }
}
