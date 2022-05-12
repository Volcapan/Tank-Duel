package persistence;

import model.Tank;
import model.TankList;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static persistence.JsonTankTest.testJsonReaderTank;

// Based on JsonReaderTest from JsonSerializationDemo
// Tests JsonReaderTL
public class JsonReaderTLTest {
    @Test
    public void testJsonReaderNonexistentFile() {
        JsonReaderTL reader = new JsonReaderTL("./data/doesNotExist");

        try {
            reader.read();
            fail("Expected IOException");
        } catch (IOException e) {
        }
    }

    @Test
    public void testJsonReaderEmptyFile() {
        JsonReaderTL reader = new JsonReaderTL("./data/testReadEmptyTankList");

        try {
            TankList tankList = reader.read();
            assertEquals(0, tankList.getSize());
        } catch (IOException e) {
            fail("Could not read file");
        }
    }

    @Test
    public void testJsonReaderDefaultFile() {
        JsonReaderTL reader = new JsonReaderTL("./data/testReadDefaultTankList");

        try {
            TankList tankList = reader.read();
            assertEquals(4, tankList.getSize());
            testJsonReaderTank(Tank.LIGHT, "M24 Chaffee", 40, 120, 40, 140, tankList.getTank(0));
            testJsonReaderTank(Tank.MEDIUM, "Comet", 70, 140, 30, 280, tankList.getTank(1));
            testJsonReaderTank(Tank.HEAVY, "Tiger II", 100, 210, 15, 320, tankList.getTank(2));
            testJsonReaderTank(Tank.DESTROYER, "SU-100", 70, 200, 20, 210, tankList.getTank(3));
        } catch (IOException e) {
            fail("Could not read file");
        }
    }
}
