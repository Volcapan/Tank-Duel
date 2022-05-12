package ui;

import java.io.IOException;
import java.util.Scanner;

import model.Player;
import model.Tank;
import model.TankGame;
import model.TankList;
import persistence.*;
import ui.exceptionsui.PauseGameException;

// Represents the backend of Tank Duel
public class TankDuelBackend {
    private TankList tankList;
    private Scanner userInput;
    protected TankGame tankGame;
    protected boolean savedTurn;
    private boolean gamePaused;
    private JsonReaderTL readerTL;
    private JsonReaderPlayer readerP1;
    private JsonReaderPlayer readerP2;
    private JsonReaderPause readerPaused;
    private JsonReaderTurn readerTurn;

    // EFFECTS: creates a default list of tanks and instantiates a scanner for user input; directs to the main menu
    public TankDuelBackend() {
        tankList = new TankList();
        userInput = new Scanner(System.in);
        readerTL = new JsonReaderTL("./data/tankList");
        readerP1 = new JsonReaderPlayer("./data/player1");
        readerP2 = new JsonReaderPlayer("./data/player2");
        readerPaused = new JsonReaderPause("./data/gamePaused");
        readerTurn = new JsonReaderTurn("./data/savedTurn");

        Tank lightT = new Tank(Tank.LIGHT, "M24 Chaffee", 40, 120, 40, 140);
        Tank mediumT = new Tank(Tank.MEDIUM, "Comet", 70, 140, 30, 280);
        Tank heavyT = new Tank(Tank.HEAVY, "Tiger II", 100, 210, 15, 320);
        Tank tankD = new Tank(Tank.DESTROYER, "SU-100", 70, 200, 20, 210);
        tankList.addTank(lightT);
        tankList.addTank(mediumT);
        tankList.addTank(heavyT);
        tankList.addTank(tankD);
    }

    // EFFECTS: returns size of tank list
    public int getTankListSize() {
        return tankList.getSize(); // stub
    }

    // REQUIRES: index is a valid index
    // EFFECTS: returns tank at given index
    public Tank getTank(int index) {
        return tankList.getTank(index);
    }

    // REQUIRES: index is a valid index and tank list size > 1
    // EFFECTS: deletes tank from tank list
    public void removeTank(int index) {
        tankList.removeTank(index);
    }

    // REQUIRES: index is a valid index in tank list; given characteristics for a tank are valid for a tank
    // EFFECTS: edits selected tank with given values
    public void editExistingTank(String type, String name, int armor, int firepower, int mobility, int health,
                                 int index) {
        tankList.editTank(type, name, armor, firepower, mobility, health, index);
    }

    // EFFECTS: saves data for tank list, both players, and state of a game; returns message of how saving went
    public String saveData() {
        String saveMessage = "";
        JsonWriter writer = new JsonWriter();

        try {
            writer.writeTL(tankList, "./data/tankList");
            saveMessage += "Tank list saved successfully";
        } catch (IOException e) {
            saveMessage += "Error, could not save tank list";
        }

        try {
            writer.writeP(tankGame.getPlayer(1), "./data/player1");
            writer.writeP(tankGame.getPlayer(2), "./data/player2");
            saveMessage += "\nPlayers 1 and 2 saved successfully";
        } catch (Exception e) {
            saveMessage += "\nError, could not save player 1 or 2";
        }

        try {
            writer.writeSavedTurn(savedTurn, "./data/savedTurn");
            writer.writeGamePaused(gamePaused, "./data/gamePaused");
            saveMessage += "\nGame state saved successfully";
        } catch (IOException e) {
            saveMessage += "\nError, could not save game state";
        }

        return saveMessage;
    }

    // MODIFIES: this
    // EFFECTS: loads previously saved data for tank list, both players, and state of a game; returns message of how
    //          loading went
    public String loadData() {
        try {
            tankGame = new TankGame(readerP1.read(), readerP2.read());
            tankList.clearTankList();
            tankList = readerTL.read();
            savedTurn = readerTurn.read();
            gamePaused = readerPaused.read();

            return "Data loaded successfully";
        } catch (IOException e) {
            return "Error, could not load saved data";
        }
    }

    // REQUIRES: player1 and player2 are valid indices for tank list
    // MODIFIES: this
    // EFFECTS: creates new players with tanks given from parameters; creates a new TankGame with the players
    public void setUpTankDuel(int player1, int player2) {
        Tank player1Choice = instanceTank(tankList.getTank(player1));
        Tank player2Choice = instanceTank(tankList.getTank(player2));
        tankGame = new TankGame(new Player(player1Choice), new Player(player2Choice));
    }

    // EFFECTS: creates a copy of the given tank and returns the copy
    public Tank instanceTank(Tank tank) {
        return new Tank(tank.getType(), tank.getName(), tank.getArmor(),
                tank.getFirepower(), tank.getMobility(), tank.getHealth());
    }

    // MODIFIES: this
    // EFFECTS: determines which player gets to go first; if a paused game is being resumed;
    //          the player who paused the game goes first
    public boolean whoGoesFirst() {
        if (gamePaused) {
            gamePaused = false;
            return savedTurn;
        } else {
            return tankGame.whoGoesFirst();
        }
    }

    // EFFECTS: returns true if either player has lost; false otherwise
    public boolean gameOver() {
        return tankGame.playerHasLost(1) || tankGame.playerHasLost(2);
    }

    // REQUIRES: either Player 1 or Player 2 has won
    // EFFECTS: returns 1 if player 1 has won; 2 if player 2 has won
    public int whoLost() {
        if (tankGame.playerHasLost(1)) {
            return 2;
        } else {
            return 1;
        }
    }

    // REQUIRES: player must be 1 or 2; selection must either be "Attack", "Pause", "Surrender", or null
    // MODIFIES: this
    // EFFECTS: accepts and interprets user input; if "Attack," current player attacks other player; if "Surrender,"
    //          other player automatically wins; if "Pause" or null, current player pauses the game
    public void selectAction(int player, String selection) throws PauseGameException {
        if (selection == null || selection.equals("Pause")) {
            pauseTheGame();
        } else if (selection.equals("Attack")) {
            tankGame.attack(player);
        } else if (selection.equals("Surrender")) {
            tankGame.getPlayer(player).takeDamage(tankGame.getPlayer(player).getHealth());
        }
    }

    // MODIFIES: this
    // EFFECTS: prints out statement saying game has been paused and throws an exception
    public void pauseTheGame() throws PauseGameException {
        gamePaused = true;
        throw new PauseGameException();
    }

    // MODIFIES: viewEditPanel
    // EFFECTS: makes tank list a readable string and returns it
    public String printTankList() {
        String tankListString = "";

        for (int index = 0; index < tankList.getSize(); index++) {
            tankListString += "Tank #" + (index + 1) + ": " + tankList.getTank(index).getName();
            tankListString += " // Class: " + tankList.getTank(index).getType();
            tankListString += " // Armor: " + tankList.getTank(index).getArmor();
            tankListString += " // Firepower: " + tankList.getTank(index).getFirepower();
            tankListString += " // Mobility: " + tankList.getTank(index).getMobility();
            tankListString += " // Health: " + tankList.getTank(index).getHealth() + "\n\n";
        }

        return tankListString;
    }

    // MODIFIES: this
    // EFFECTS: creates a new tank and adds it to tank list
    public void addTank(String type, String name, int armor, int firepower, int mobility, int health) {
        tankList.addTank(new Tank(type, name, armor, firepower, mobility, health));
    }

}
