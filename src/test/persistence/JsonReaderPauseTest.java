package persistence;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// Tests JsonReaderPause
public class JsonReaderPauseTest {
    @Test
    public void testJsonReaderNonexistentFile() {
        JsonReaderPause reader = new JsonReaderPause("./data/doesNotExist");

        try {
            reader.read();
            fail("Expected IOException");
        } catch (IOException e) {
        }
    }

    @Test
    public void testJsonReaderTrue() {
        JsonReaderPause reader = new JsonReaderPause("./data/testReadPauseTrue");

        try {
            assertTrue(reader.read());
        } catch (IOException e) {
            fail("Could not read file");
        }
    }

    @Test
    public void testJsonReaderFalse() {
        JsonReaderPause reader = new JsonReaderPause("./data/testReadPauseFalse");

        try {
            assertFalse(reader.read());
        } catch (IOException e) {
            fail("Could not read file");
        }
    }
}
