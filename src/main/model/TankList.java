package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a list of Tanks
public class TankList implements Writable {
    ArrayList<Tank> tankList; // List of tanks

    // EFFECTS: creates an empty list of tanks
    public TankList() {
        tankList = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a tank to the list
    public void addTank(Tank tank) {
        tankList.add(tank);

        EventLog.getInstance().logEvent(new Event("Added new " + tank.getType() + " called "
                + tank.getName() + " to TankList"));
    }

    // REQUIRES: index is a valid index position
    // MODIFIES: this
    // EFFECTS: removes Tank from given index
    public void removeTank(int index) {
        EventLog.getInstance().logEvent(new Event("Removed " + getTank(index).getType()
                + " " + getTank(index).getName() + " from TankList"));

        tankList.remove(index);
    }

    // REQUIRES: index is a valid index position; given characteristics are valid for a tank
    // MODIFIES: this
    // EFFECTS: edits characteristics of tank at given index
    public void editTank(String type, String name, int armor, int firepower, int mobility, int health, int index) {
        Tank tankToEdit = getTank(index);
        tankToEdit.setType(type);
        tankToEdit.setName(name);
        tankToEdit.setArmor(armor);
        tankToEdit.setFirepower(firepower);
        tankToEdit.setMobility(mobility);
        tankToEdit.setHealth(health);

        int tankPosition = ++index;
        EventLog.getInstance().logEvent(new Event("Edited Tank #" + tankPosition + " in TankList"));
    }

    // MODIFIES: this
    // EFFECTS: clears entire tank list
    public void clearTankList() {
        int originalSize = getSize();
        for (int x = 0; x < originalSize; x++) {
            removeTank(0);
        }
    }

    // REQUIRES: index is a valid index position
    // EFFECTS: returns Tank at given index
    public Tank getTank(int index) {
        return tankList.get(index);
    }

    // EFFECTS: returns size of the list
    public int getSize() {
        return tankList.size();
    }

    @Override
    // Based on toJson from WorkRoom in JsonSerializationDemo
    // EFFECTS: converts tank list to a JSON object, and returns JSON object
    public JSONObject toJson() {
        JSONObject jsonTL = new JSONObject();
        jsonTL.put("tankList", tanksToJson());
        
        return jsonTL;
    }

    // Based on thingiesToJson from WorkRoom in JsonSerializationDemo
    // EFFECTS: adds tanks to JSON array, and returns JSON array
    private JSONArray tanksToJson() {
        JSONArray jsonTanks = new JSONArray();

        for (Tank t : tankList) {
            jsonTanks.put(t.toJson());
        }

        return jsonTanks;
    }
}
