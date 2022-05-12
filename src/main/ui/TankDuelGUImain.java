package ui;

import model.Event;
import model.EventLog;
import ui.exceptionsui.InvalidInputException;
import ui.exceptionsui.PauseGameException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// Based on code from "Java GUI Tutorial - Make a GUI in 13 Minutes" https://www.youtube.com/watch?v=5o3fMLPY7qY
// Based on code from ButtonDemo from https://docs.oracle.com/javase/tutorial/uiswing/components/button.html
// Based on code from DialogDemo from https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
// Based on code from LabelDemo from https://docs.oracle.com/javase/tutorial/uiswing/components/icon.html
// Represents the Graphical user interface for Tank Duel as well as the starting point where the program is run
public class TankDuelGUImain extends WindowAdapter implements ActionListener {
    protected static int borderHeight = 400;
    protected static int borderWidth = 640;

    protected static TankDuelBackend tankDuelGame;

    private JFrame tankDuelWindow;
    private JPanel tankDuelPanel;
    private JButton start;
    private JButton resume;
    private JButton rules;
    private JButton viewEdit;
    private JButton save;
    private JButton load;

    // EFFECTS: creates a game, frame, and panel
    public TankDuelGUImain() {
        tankDuelGame = new TankDuelBackend();
        tankDuelWindow = new JFrame();
        tankDuelPanel = new JPanel();

        setUpPanel();
        setUpWindow();
    }

    // EFFECTS: runs TankDuelGUImain
    public static void main(String[] args) {
        new TankDuelGUImain();
    }

    // EFFECTS: prints out all events in event log onto console
    public void printEventLog() {
        for (Event e : EventLog.getInstance()) {
            System.out.println(e.toString() + "\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: sets up panel
    public void setUpPanel() {
        tankDuelPanel.setBorder(BorderFactory.createEmptyBorder(borderHeight, borderWidth,
                borderHeight, borderWidth));
        // setLayout line based on corresponding line from Sunil Luitel at
        // https://stackoverflow.com/questions/13510641/
        // add-controls-vertically-instead-of-horizontally-using-flow-layout
        tankDuelPanel.setLayout(new BoxLayout(tankDuelPanel, BoxLayout.Y_AXIS));
        tankDuelPanel.add(new JLabel("Tank Duel"));
        tankDuelPanel.add(new JLabel("",
                new ImageIcon("./data/tank-photo-for-tank-duel-resized.jpg"), JLabel.CENTER));

        setUpButtons();
        tankDuelPanel.add(start);
        tankDuelPanel.add(resume);
        tankDuelPanel.add(rules);
        tankDuelPanel.add(viewEdit);
        tankDuelPanel.add(save);
        tankDuelPanel.add(load);
    }

    // MODIFIES: this
    // EFFECTS: sets up all main menu buttons
    public void setUpButtons() {
        start = new JButton("Start a new game");
        start.setActionCommand("start");
        start.addActionListener(this);

        resume = new JButton("Resume paused game");
        resume.setActionCommand("resume");
        resume.addActionListener(this);

        rules = new JButton("Read rules");
        rules.setActionCommand("rules");
        rules.addActionListener(this);

        viewEdit = new JButton("View/edit list of tanks");
        viewEdit.setActionCommand("view/edit");
        viewEdit.addActionListener(this);

        save = new JButton("Save data");
        save.setActionCommand("save");
        save.addActionListener(this);

        load = new JButton("Load saved data");
        load.setActionCommand("load");
        load.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: sets up the window for the Tank Duel application
    public void setUpWindow() {
        tankDuelWindow.add(tankDuelPanel, BorderLayout.CENTER);
        tankDuelWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tankDuelWindow.setTitle("Tank Duel");
        tankDuelWindow.pack();
        tankDuelWindow.setVisible(true);
        tankDuelWindow.addWindowListener(this);
    }

    // EFFECTS: gets the tank indices for player 1 and 2, sends them off to instance the tanks, and starts game
    public void setUpGame() {
        try {
            int player1 = tankSelection("Enter the # of the tank for Player 1");
            int player2 = tankSelection("Enter the # of the tank for Player 2");
            tankDuelGame.setUpTankDuel(player1, player2);
            playGame();
        } catch (InvalidInputException e) {
            JOptionPane.showMessageDialog(null, "Invalid input, please try again",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // EFFECTS: has user select tank they wish to play; loops until user enters valid index for a tank
    public int tankSelection(String message) throws InvalidInputException {
        int index = -1;

        while (index == -1) {
            String selection = (String) JOptionPane.showInputDialog(null, tankDuelGame.printTankList()
                            + message, "Select Tank to Play", JOptionPane.PLAIN_MESSAGE,
                    null, null, "1");

            index = TankDuelGUIviewEdit.convertInputToIntTankSelection(selection);

            if (index == -1) {
                throw new InvalidInputException();
            }
        }

        return index;
    }

    // EFFECTS: runs the game until player 1 or 2 wins ;
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void playGame() {
        boolean player1IsFirst = tankDuelGame.whoGoesFirst();
        int player;
        boolean continueGame = true;
        Object[] choices = {"Attack", "Pause", "Surrender"};

        while (continueGame) {
            String text = printPlayerData();

            if (player1IsFirst) {
                text += "It is Player 1's turn";
                player = 1;
                player1IsFirst = false;
            } else {
                text += "It is player 2's turn";
                player = 2;
                player1IsFirst = true;
            }

            String selection = (String) JOptionPane.showInputDialog(null, text,
                    "Tank Duel", JOptionPane.PLAIN_MESSAGE, null, choices, "Attack");
            try {
                tankDuelGame.selectAction(player, selection);
                continueGame = !tankDuelGame.gameOver();
                if (!continueGame) {
                    victoryMessage();
                }
            } catch (PauseGameException e) {
                JOptionPane.showMessageDialog(null, "Game has been paused");
                continueGame = false;
            }
        }
    }

    // EFFECTS: displays message for whoever won the game
    public void victoryMessage() {
        int victor = tankDuelGame.whoLost();

        if (victor == 1) {
            JOptionPane.showMessageDialog(null, "Player 1 has won!", "Player 1 won", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Player 2 has won!", "Player 2 won", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // EFFECTS: returns data regarding both players' tanks during a game as a String
    public String printPlayerData() {
        String toReturn = "";

        for (int p = 1; p <= 2; p++) {
            toReturn += "Player " + p + "'s tank: " + tankDuelGame.tankGame.getPlayer(p).getTankName()
                    + " // " + tankDuelGame.tankGame.getPlayer(p).getTankType() + "\n";
            toReturn += "    Health: " + tankDuelGame.tankGame.getPlayer(p).getHealth() + "\n";
            toReturn += "    Armor: " + tankDuelGame.tankGame.getPlayer(p).getTankArmor() + "\n";
            toReturn += "    Firepower: " + tankDuelGame.tankGame.getPlayer(p).getTankFirepower() + "\n";
            toReturn += "    Mobility: " + tankDuelGame.tankGame.getPlayer(p).getTankMobility() + "\n";
        }

        toReturn += "\nPlease select one of the actions below\n";

        return toReturn;
    }

    // EFFECTS: resumes previous game if no one has won it yet; if no previous game exists or someone has won the
    //          previous game, print out error message
    public void resumeGame() {
        if (tankDuelGame.tankGame != null
                && !(tankDuelGame.tankGame.playerHasLost(1) || tankDuelGame.tankGame.playerHasLost(2))) {
            playGame();
        } else {
            JOptionPane.showMessageDialog(null, "No previous game to resume",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // EFFECTS: saves game data and displays corresponding message
    public void saveGameData() {
        JOptionPane.showMessageDialog(null, tankDuelGame.saveData(), "Save Data",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // EFFECTS: loads game data and displays corresponding message
    public void loadGameData() {
        JOptionPane.showMessageDialog(null, tankDuelGame.loadData(), "Load Data",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // EFFECTS: displays game rules
    public void gameRules() {
        JOptionPane.showMessageDialog(null, displayRules(), "Tank Duel Rules",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // EFFECTS: returns rules of the game for display
    public String displayRules() {
        String rules = "";

        rules += "Rules of TANK DUEL:\n";
        rules += "- Player with the tank with the highest mobility value goes first\n";
        rules += "- Player whose health reaches at or below 0 loses\n";
        rules += "- When a player decides to attack, the defending player's mobility represents the\n";
        rules += "  chance out of 100 of the attacking player's shot missing\n";
        rules += "      - Should the attacking player's shot hit, then the defending player takes damage\n";
        rules += "        equal to the attacking players firepower value minus the defending player's\n";
        rules += "        armor value; should the calculation of the damage be below 0, the damage\n";
        rules += "        simply equals to 0";

        return rules;
    }

    @Override
    // EFFECTS: determines what to do with the given input for main menu
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(start.getActionCommand())) {
            setUpGame();
        } else if (e.getActionCommand().equals(resume.getActionCommand())) {
            resumeGame();
        } else if (e.getActionCommand().equals(save.getActionCommand())) {
            saveGameData();
        } else if (e.getActionCommand().equals(load.getActionCommand())) {
            loadGameData();
        } else if (e.getActionCommand().equals(viewEdit.getActionCommand())) {
            new TankDuelGUIviewEdit();
        } else if (e.getActionCommand().equals(rules.getActionCommand())) {
            gameRules();
        }
    }

    @Override
    // EFFECTS: when user closes main window, prints event log
    public void windowClosing(WindowEvent e) {
        printEventLog();
    }
}
