package model;

import java.lang.Math;

// Represents a game with 2 Players playing against each other
public class TankGame {
    private final Player player1; // player 1
    private final Player player2; // player 2

    // EFFECTS: creates a game with two Players
    public TankGame(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    // REQUIRES: attacker is 1 or 2
    // MODIFIES: Player
    // EFFECTS: if hit, defender's health is subtracted by attacker's firepower minus defender's armor;
    //          if defender's armor > attacker's firepower, defender's health stays the same;
    //          attacker's value represents who is attacking (ex. 1 means player1 (attacker) is attacking
    //          player2 (defender));
    //          defender's mobility represents the percentage of defender being hit
    public void attack(int attacker) {
        int randomNumber = (int) (100 * Math.random()) + 1;
        boolean hasHit;

        if (attacker == 1) {
            hasHit = randomNumber > player2.getTankMobility();
        } else {
            hasHit = randomNumber > player1.getTankMobility();
        }

        if (hasHit) {
            int damage;

            if (attacker == 1) {
                damage = player1.getTankFirepower() - player2.getTankArmor();
            } else {
                damage = player2.getTankFirepower() - player1.getTankArmor();
            }

            if (damage < 0) {
                damage = 0;
            }

            if (attacker == 1) {
                player2.takeDamage(damage);
            } else {
                player1.takeDamage(damage);
            }
        }
    }

    // REQUIRES: player is 1 or 2
    // EFFECTS: returns true if specified Player's health is <= 0; false otherwise;
    //          1 represents player1; 2 represents player2
    public boolean playerHasLost(int player) {
        if (player == 1) {
            return player1.hasLost();
        } else {
            return player2.hasLost();
        }
    }

    // REQUIRES player is 1 or 2
    // EFFECTS: returns specified player;
    //          1 represents player 1; 2 represents player 2
    public Player getPlayer(int player) {
        if (player == 1) {
            return player1;
        } else {
            return player2;
        }
    }

    // EFFECTS: returns true if player 1 goes first; false if player 2 goes first
    //          player 1 goes first if their mobility value is greater than or equal to player 2's mobility value;
    //          otherwise, player 2 goes first
    public boolean whoGoesFirst() {
        return getPlayer(1).getTankMobility() >= getPlayer(2).getTankMobility();
    }
}
