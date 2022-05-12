package persistence;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

// Tests JsonReaderTurn
public class JsonReaderTurnTest {
    @Test
    public void testJsonReaderNonexistentFile() {
        JsonReaderTurn reader = new JsonReaderTurn("./data/doesNotExist");

        try {
            reader.read();
            fail("Expected IOException");
        } catch (IOException e) {
        }
    }

    @Test
    public void testJsonReaderTrue() {
        JsonReaderTurn reader = new JsonReaderTurn("./data/testReadTurnTrue");

        try {
            assertTrue(reader.read());
        } catch (IOException e) {
            fail("Could not read file");
        }
    }

    @Test
    public void testJsonReaderFalse() {
        JsonReaderTurn reader = new JsonReaderTurn("./data/testReadTurnFalse");

        try {
            assertFalse(reader.read());
        } catch (IOException e) {
            fail("Could not read file");
        }
    }
}
