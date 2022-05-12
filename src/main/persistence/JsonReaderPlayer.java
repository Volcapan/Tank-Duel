package persistence;

import model.Player;
import model.Tank;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Based on JsonReader from JsonSerializationDemo
// Represents something that is able to retrieve saved data for a player
public class JsonReaderPlayer extends JsonReader {

    // EFFECTS: constructs reader to read from source file
    public JsonReaderPlayer(String source) {
        super(source);
    }

    // REQUIRES: source links to file that represents a player
    // EFFECTS: reads player from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Player read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return getPlayer(jsonObject);
    }

    // EFFECTS: parses player from jsonObject and returns it
    private Player getPlayer(JSONObject jsonObject) {
        Tank playerTank = new Tank(Tank.LIGHT, "", 0, 0, 0, 0);

        JSONArray jsonArray = jsonObject.getJSONArray("playerTank");
        for (Object json : jsonArray) {
            JSONObject tank = (JSONObject) json;
            playerTank = getTank(tank);
        }

        int playerHealth = jsonObject.getInt("playerHealth");

        return new Player(playerTank, playerHealth);
    }

    // EFFECTS: parses tank from JSON object and returns it
    private Tank getTank(JSONObject jsonObject) {
        String type = jsonObject.getString("type");
        String name = jsonObject.getString("name");
        int armor = jsonObject.getInt("armor");
        int firepower = jsonObject.getInt("firepower");
        int mobility = jsonObject.getInt("mobility");
        int health = jsonObject.getInt("health");
        return new Tank(type, name, armor, firepower, mobility, health);
    }
}
