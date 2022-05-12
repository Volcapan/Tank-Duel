package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.Tank;
import model.TankList;
import org.json.*;

// Based on JsonReader from JsonSerializationDemo
// Represents something that is able to retrieve saved data for a tank list
public class JsonReaderTL extends JsonReader {

    // EFFECTS: constructs reader to read from source file
    public JsonReaderTL(String source) {
        super(source);
    }

    // REQUIRES: source links to a file that represents a tank list
    // EFFECTS: reads tankList from file and returns it;
    // throws IOException if an error occurs reading data from file
    public TankList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTankList(jsonObject);
    }

    // EFFECTS: parses tank list from JSON object and returns it
    private TankList parseTankList(JSONObject jsonObject) {
        TankList tl = new TankList();
        addTanks(tl, jsonObject);
        return tl;
    }

    // MODIFIES: tl
    // EFFECTS: parses tanks from JSON object and adds them to tl
    private void addTanks(TankList tl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("tankList");
        for (Object json : jsonArray) {
            JSONObject nextTank = (JSONObject) json;
            addTank(tl, nextTank);
        }
    }

    // MODIFIES: tl
    // EFFECTS: parses tank from JSON object and adds it to tl
    private void addTank(TankList tl, JSONObject jsonObject) {
        String type = jsonObject.getString("type");
        String name = jsonObject.getString("name");
        int armor = jsonObject.getInt("armor");
        int firepower = jsonObject.getInt("firepower");
        int mobility = jsonObject.getInt("mobility");
        int health = jsonObject.getInt("health");
        Tank tank = new Tank(type, name, armor, firepower, mobility, health);
        tl.addTank(tank);
    }
}
