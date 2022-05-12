package persistence;

import model.Player;
import model.TankList;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Based on JsonWriter from JsonSerializationDemo
// Represents something that can write data that represents a tank list, a player, or part of a saved game state
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    private void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of tank list to file
    public void writeTL(TankList tl, String destination) throws FileNotFoundException {
        this.destination = destination;

        open();

        JSONObject json = tl.toJson();
        saveToFile(json.toString(TAB));

        close();
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of a player to file
    public void writeP(Player p, String destination) throws FileNotFoundException {
        this.destination = destination;

        open();

        JSONObject json = p.toJson();
        saveToFile(json.toString(TAB));

        close();
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of a boolean to file for a saved turn
    public void writeSavedTurn(Boolean b, String destination) throws FileNotFoundException {
        this.destination = destination;

        open();

        JSONObject json = new JSONObject();
        json.put("savedTurn", b);
        saveToFile(json.toString(TAB));

        close();
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of a boolean to file for a paused game
    public void writeGamePaused(Boolean b, String destination) throws FileNotFoundException {
        this.destination = destination;

        open();

        JSONObject json = new JSONObject();
        json.put("gamePaused", b);
        saveToFile(json.toString(TAB));

        close();
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    private void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
