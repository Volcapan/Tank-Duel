package persistence;

import model.Player;
import model.Tank;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Tests JsonReaderPlayer
public class JsonReaderPlayerTest {
    @Test
    public void testJsonReaderNonexistentFile() {
        JsonReaderPlayer reader = new JsonReaderPlayer("./data/doesNotExist");

        try {
            reader.read();
            fail("Expected IOException");
        } catch (IOException e) {
        }
    }

    @Test
    public void testJsonReaderDefaultFile() {
        JsonReaderPlayer reader = new JsonReaderPlayer("./data/testReadDefaultPlayer");

        try {
            Player player = reader.read();
            assertEquals(Tank.LIGHT, player.getTankType());
            assertEquals("M24 Chaffee", player.getTankName());
            assertEquals(40, player.getTankArmor());
            assertEquals(120, player.getTankFirepower());
            assertEquals(40, player.getTankMobility());
            assertEquals(140, player.getHealth());
        } catch (IOException e) {
            fail("Could not read file");
        }
    }
}
