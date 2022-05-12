package persistence;

import model.Player;
import model.Tank;
import model.TankList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

// Based on JsonWriterTest from JsonSerializationDemo
// Tests JsonWriter
public class JsonWriterTest {
    private JsonWriter testWriter;

    @BeforeEach
    public void setUp() {
        testWriter = new JsonWriter();
    }

    @Test
    public void testWriteTlInvalidFile() {
        TankList tankList = new TankList();
        try {
            testWriter.writeTL(tankList, "./data\0cannotExist");
            fail("Expected IOException");
        } catch (IOException e) {
        }
    }

    @Test
    public void testWriteTlEmptyFile() {
        TankList tankList = new TankList();
        try {
            testWriter.writeTL(tankList, "./data/testWriteTankList");
        } catch (IOException e) {
            fail("Did not expect exception");
        }

        JsonReaderTL reader = new JsonReaderTL("./data/testWriteTankList");
        try {
            tankList = reader.read();
            assertEquals(0, tankList.getSize());
        } catch (IOException e) {
            fail("Did not expect exception");
        }
    }

    @Test
    public void testWriteTlDefaultFile() {
        TankList tankList = new TankList();
        tankList.addTank(new Tank(Tank.LIGHT, "a", 1, 1, 1, 1));
        tankList.addTank(new Tank(Tank.DESTROYER, "b", 2, 2, 2, 2));
        try {
            testWriter.writeTL(tankList, "./data/testWriteTankList");
        } catch (IOException e) {
            fail("Did not expect exception");
        }

        JsonReaderTL reader = new JsonReaderTL("./data/testWriteTankList");
        try {
            tankList = reader.read();
            assertEquals(2, tankList.getSize());
            JsonTankTest.testJsonReaderTank(Tank.LIGHT, "a", 1,1, 1, 1, tankList.getTank(0));
            JsonTankTest.testJsonReaderTank(Tank.DESTROYER, "b", 2,2, 2, 2, tankList.getTank(1));
        } catch (IOException e) {
            fail("Did not expect exception");
        }
    }

    @Test
    public void testWritePInvalidFile() {
        Player player = new Player(new Tank(Tank.LIGHT, "a", 1, 1, 1, 1));
        try {
            testWriter.writeP(player, "./data\0cannotExist");
            fail("Expected IOException");
        } catch (IOException e) {
        }
    }

    @Test
    public void testWritePDefaultFile() {
        Player player = new Player(new Tank(Tank.LIGHT, "a", 1, 1, 1, 1), 2);
        try {
            testWriter.writeP(player, "./data/testWritePlayer");
        } catch (IOException e) {
            fail("Did not expect exception");
        }

        JsonReaderPlayer reader = new JsonReaderPlayer("./data/testWritePlayer");

        try {
            player = reader.read();
            assertEquals(Tank.LIGHT, player.getTankType());
            assertEquals("a", player.getTankName());
            assertEquals(1, player.getTankArmor());
            assertEquals(1, player.getTankFirepower());
            assertEquals(1, player.getTankMobility());
            assertEquals(2, player.getHealth());
        } catch (IOException e) {
            fail("Did not expect exception");
        }
    }

    @Test
    public void testWriteSavedTurnInvalidFile() {
        try {
            testWriter.writeSavedTurn(true, "./data\0cannotExist");
            fail("Expected IOException");
        } catch (IOException e) {
        }
    }

    @Test
    public void testWriteSavedTurn() {
        try {
            testWriter.writeSavedTurn(true, "./data/testWriteSavedTurn");
            JsonReaderTurn reader = new JsonReaderTurn("./data/testWriteSavedTurn");
            assertTrue(reader.read());
            testWriter.writeSavedTurn(false, "./data/testWriteSavedTurn");
            assertFalse(reader.read());
        } catch (IOException e) {
            fail("Did not expect exception");
        }
    }

    @Test
    public void testWriteGamePausedInvalidFile() {
        try {
            testWriter.writeGamePaused(true, "./data\0cannotExist");
            fail("Expected IOException");
        } catch (IOException e) {
        }
    }

    @Test
    public void testWriteGamePaused() {
        try {
            testWriter.writeGamePaused(true, "./data/testWriteGamePaused");
            JsonReaderPause reader = new JsonReaderPause("./data/testWriteGamePaused");
            assertTrue(reader.read());
            testWriter.writeGamePaused(false, "./data/testWriteGamePaused");
            assertFalse(reader.read());
        } catch (IOException e) {
            fail("Did not expect exception");
        }
    }
}
