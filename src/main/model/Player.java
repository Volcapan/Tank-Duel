package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

// Represents a player during a game; a player has a tank the player is playing as and health that measures
// how far the player is from losing the game
public class Player implements Writable {
    private final Tank playerTank; // player's tank
    private int playerHealth;      // player's health during a game

    // EFFECTS: creates a Player with health of Tank playing as Tank
    public Player(Tank tank) {
        playerTank = tank;
        playerHealth = tank.getHealth();
    }

    // EFFECTS: creates a Player with given health playing as Tank
    public Player(Tank tank, int health) {
        playerTank = tank;
        playerHealth = health;
    }

    // REQUIRES: damage >= 0
    // MODIFIES: this
    // EFFECTS: subtracts damage amount from Player's health
    public void takeDamage(int damage) {
        playerHealth -= damage;
    }

    // EFFECTS: returns true if Player's health is <= 0; false otherwise
    public boolean hasLost() {
        return playerHealth <= 0;
    }

    public int getHealth() {
        return playerHealth;
    }

    public String getTankType() {
        return playerTank.getType();
    }

    public String getTankName() {
        return playerTank.getName();
    }

    public int getTankArmor() {
        return playerTank.getArmor();
    }

    public int getTankFirepower() {
        return playerTank.getFirepower();
    }

    public int getTankMobility() {
        return playerTank.getMobility();
    }

    @Override
    // EFFECTS: converts player to a JSON object, and returns JSON object
    public JSONObject toJson() {
        JSONObject jsonPlayer = new JSONObject();
        JSONArray jsonPlayerTank = new JSONArray();
        jsonPlayerTank.put(playerTank.toJson());
        jsonPlayer.put("playerTank", jsonPlayerTank);
        jsonPlayer.put("playerHealth", playerHealth);

        return jsonPlayer;
    }
}
