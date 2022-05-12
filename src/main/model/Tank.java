package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a tank and its characteristics such as what type of tank, its name, armor, firepower, mobility,
// and health that are needed for the game
public class Tank implements Writable {
    // All final Strings below are different types of tanks
    public static final String LIGHT = "Light Tank";
    public static final String MEDIUM = "Medium Tank";
    public static final String HEAVY = "Heavy Tank";
    public static final String DESTROYER = "Tank Destroyer";

    private String type;   // type of tank
    private String name;   // name of the tank
    private int armor;     // armor value for tank
    private int firepower; // firepower value for tank
    private int mobility;  // mobility value for tank
    private int health;    // health value for tank

    // REQUIRES: type must equal to one of the given final Strings; armor, firepower, and health must be > 0;
    //           mobility must be in the range [0, 100] inclusive
    // EFFECTS: creates a new tank with its type, name, armor, firepower, mobility, and health
    public Tank(String type, String name, int armor, int firepower, int mobility, int health) {
        this.type = type; // stub
        this.name = name;
        this.armor = armor;
        this.firepower = firepower;
        this.mobility = mobility;
        this.health = health;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getArmor() {
        return armor;
    }

    public int getFirepower() {
        return firepower;
    }

    public int getMobility() {
        return mobility;
    }

    public int getHealth() {
        return health;
    }

    // REQUIRES: type is one of the given final Strings
    // MODIFIES: this
    // EFFECTS: changes the type
    public void setType(String type) {
        this.type = type; // stub
    }

    public void setName(String name) {
        this.name = name;
    }

    // REQUIRES: armor is > 0
    // MODIFIES: this
    // EFFECTS: changes value of armor
    public void setArmor(int armor) {
        this.armor = armor;
    }

    // REQUIRES: firepower is > 0
    // MODIFIES: this
    // EFFECTS: changes value of firepower
    public void setFirepower(int firepower) {
        this.firepower = firepower;
    }

    // REQUIRES: mobility is in the range [0, 100] inclusive
    // MODIFIES: this
    // EFFECTS: changes value of mobility
    public void setMobility(int mobility) {
        this.mobility = mobility;
    }

    // REQUIRES: health is > 0
    // MODIFIES: this
    // EFFECTS: changes value of health
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    // Based on toJson from Thingy in JsonSerialization
    // EFFECTS: converts tank to a JSON object, and returns JSON object
    public JSONObject toJson() {
        JSONObject jsonTank = new JSONObject();
        jsonTank.put("type", type);
        jsonTank.put("name", name);
        jsonTank.put("armor", armor);
        jsonTank.put("firepower", firepower);
        jsonTank.put("mobility", mobility);
        jsonTank.put("health", health);

        return jsonTank;
    }
}
