package persistence;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Based on JsonReader from JsonSerializationDemo
// Represents something that is able to retrieve saved data for the pause state of a game
public class JsonReaderPause extends JsonReader {

    // EFFECTS: constructs reader to read from source file
    public JsonReaderPause(String source) {
        super(source);
    }

    // REQUIRES: source links to file that represents a boolean for the state of a game
    // EFFECTS: reads boolean from file and returns it;
    // throws IOException if an error occurs reading data from file
    public boolean read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return jsonObject.getBoolean("gamePaused");
    }

}
