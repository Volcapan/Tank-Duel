package ui;

import model.Tank;
import ui.exceptionsui.InvalidInputException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ui.TankDuelGUImain.*;

public class TankDuelGUIviewEdit implements ActionListener {
    private JFrame viewEditWindow;
    private JPanel viewEditPanel;
    private JButton viewTanks;
    private JButton newTank;
    private JButton editTank;
    private JButton removeTank;

    public TankDuelGUIviewEdit() {
        setUpViewEditWindow();
    }

    // MODIFIES: this
    // EFFECTS: sets up the window for the view/edit tank list menu
    public void setUpViewEditWindow() {
        viewEditWindow = new JFrame();
        viewEditWindow.setBounds(borderHeight, borderWidth, borderHeight, borderWidth);
        viewEditWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        viewEditWindow.setTitle("View/Edit Tank List");
        viewEditWindow.pack();
        viewEditWindow.setVisible(true);

        setUpViewEditPanel();
        viewEditWindow.add(viewEditPanel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: sets up the content panel for the view/edit tank list window
    public void setUpViewEditPanel() {
        viewEditPanel = new JPanel();
        viewEditPanel.setBorder(BorderFactory.createEmptyBorder(borderHeight, borderWidth,
                borderHeight, borderWidth));
        // setLayout line based on corresponding line from Sunil Luitel at
        // https://stackoverflow.com/questions/13510641/
        // add-controls-vertically-instead-of-horizontally-using-flow-layout
        viewEditPanel.setLayout(new BoxLayout(viewEditPanel, BoxLayout.Y_AXIS));

        setUpViewEditButtons();
        viewEditPanel.add(viewTanks);
        viewEditPanel.add(newTank);
        viewEditPanel.add(editTank);
        viewEditPanel.add(removeTank);
    }

    // MODIFIES: this
    // EFFECTS: sets up the buttons for the view/edit tank list window
    public void setUpViewEditButtons() {
        viewTanks = new JButton("View tank list");
        viewTanks.setActionCommand("viewTanks");
        viewTanks.addActionListener(this);

        newTank = new JButton("Add a new tank");
        newTank.setActionCommand("newTank");
        newTank.addActionListener(this);

        editTank = new JButton("Edit an already existing tank");
        editTank.setActionCommand("editTank");
        editTank.addActionListener(this);

        removeTank = new JButton("Remove a tank");
        removeTank.setActionCommand("removeTank");
        removeTank.addActionListener(this);
    }

    // EFFECTS: Displays tank list
    public void viewTankList() {
        JOptionPane.showMessageDialog(null, tankDuelGame.printTankList(),
                "Tank List", JOptionPane.INFORMATION_MESSAGE);
    }

    // EFFECTS: gets characteristics of a tank and then sends them off to create a tank; displays error message
    //          if any user input is invalid
    public void createNewTank() {
        try {
            String type = userInputType("Select one of the types below");
            String name = userInputName("Enter the name of the new tank");
            int armor = userInputAFH("Enter the armor value for the new tank \nNote: the value must be greater than 0");
            int firepower = userInputAFH("Enter the firepower value for the new tank"
                    + "\nNote: the value must be greater than 0");
            int mobility = userInputM("Enter the mobility value for the new tank "
                    + "\nNote: the value must be between 0 and 100, inclusive");
            int health = userInputAFH("Enter the health value for the new tank "
                    + "\nNote: the value must be greater than 0");

            tankDuelGame.addTank(type, name, armor, firepower, mobility, health);
        } catch (InvalidInputException e) {
            JOptionPane.showMessageDialog(null, "Invalid input, please try again",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // EFFECTS: has user select tank type and returns selection; throws InvalidInputException if user closes dialog
    //          or presses cancel button
    public String userInputType(String message) throws InvalidInputException {
        Object[] typeSelection = {Tank.LIGHT, Tank.MEDIUM, Tank.HEAVY, Tank.DESTROYER};
        String type;

        type = (String) JOptionPane.showInputDialog(null, message,
                "Select Type", JOptionPane.PLAIN_MESSAGE, null, typeSelection, Tank.LIGHT);

        if (type == null) {
            throw new InvalidInputException();
        }

        return type;
    }

    // EFFECTS: has user enter name of new tank and returns it; throws InvalidInputException if user closes dialog
    //          or presses cancel button
    public String userInputName(String message) throws InvalidInputException {
        String name = (String) JOptionPane.showInputDialog(null, message, "Select Name",
                JOptionPane.PLAIN_MESSAGE, null, null, "Tank");

        if (name == null) {
            throw new InvalidInputException();
        }

        return name;
    }

    // EFFECTS: has user input value for armor, firepower, or health and returns conversion of input or throws
    //          InvalidInputException
    public int userInputAFH(String message) throws InvalidInputException {
        String value = (String) JOptionPane.showInputDialog(null, message,
                "Select AFH", JOptionPane.PLAIN_MESSAGE, null, null, "999");

        return convertInputToIntAFH(value); // stub
    }

    // EFFECTS: has user input value for mobility and returns conversion of input or throws InvalidInputException
    public int userInputM(String message) throws InvalidInputException {
        String value = (String) JOptionPane.showInputDialog(null, message,
                "Select Mobility", JOptionPane.PLAIN_MESSAGE, null, null, "100");

        return convertInputToIntM(value); // stub
    }

    // EFFECTS: checks if response is only numbers and is greater than 0; if one of the requirements is not
    //          met, throws InvalidInputException; otherwise, returns response as integer
    public int convertInputToIntAFH(String response) throws InvalidInputException {
        if (response == null) {
            throw new InvalidInputException();
        }

        if (response.matches("[0-9]+") && Integer.parseInt(response) > 0) {
            return Integer.parseInt(response);
        } else {
            throw new InvalidInputException();
        }
    }

    // EFFECTS: checks if response is only numbers and between 0 and 100, inclusive; if one of the requirements is not
    //          met, throws InvalidInputException; otherwise, returns response as integer
    public int convertInputToIntM(String response) throws InvalidInputException {
        if (response == null) {
            throw new InvalidInputException();
        }

        if (response.matches("[0-9]+") && Integer.parseInt(response) >= 0 && Integer.parseInt(response) <= 100) {
            return Integer.parseInt(response);
        } else {
            throw new InvalidInputException();
        }
    }

    // EFFECTS: displays tank list, has user select tank, and then sends off selection; shows error message if
    //          selection is invalid
    public void selectTankToEdit() {
        String selection = (String) JOptionPane.showInputDialog(null, tankDuelGame.printTankList()
                        + "Enter in # of tank to be edited", "Select Tank to Edit", JOptionPane.PLAIN_MESSAGE,
                null, null, "1");

        int index = convertInputToIntTankSelection(selection);

        if (index == -1) {
            JOptionPane.showMessageDialog(null, "Invalid input, please try again",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            editExistingTankValues(index);
        }
    }

    // REQUIRES: index is a valid index in tank list
    // EFFECTS: extracts selected tank from list and has user enter in new values for selected tank;
    //          sends them off for editing; displays error message if any of input is invalid
    public void editExistingTankValues(int index) {
        Tank tankToEdit = tankDuelGame.getTank(index);

        try {
            String type = userInputType("Original type: " + tankToEdit.getType() + "\nSelect a new type below; "
                    + "to keep the type the same, re-select the original type");
            String name = userInputName("Original name: " + tankToEdit.getName() + "\nEnter a new name below; "
                    + "to keep the name the same, re-enter the original name");
            int armor = userInputAFH("Original armor value: " + tankToEdit.getArmor() + "\nEnter the new value below; "
                    + "to keep the value the same, re-enter the original value "
                    + "\nNote: the value must be greater than 0");
            int firepower = userInputAFH("Original firepower value: " + tankToEdit.getFirepower()
                    + "\nEnter the new value below; to keep the value the same, re-enter the original value"
                    + "\nNote: the value must be greater than 0");
            int mobility = userInputM("Original mobility value: " + tankToEdit.getMobility()
                    + "\nEnter the new value below; to keep the value the same, re-enter the original value"
                    + "\nNote: the value must be between 0 and 100, inclusive");
            int health = userInputAFH("Original health value: " + tankToEdit.getHealth()
                    + "\nEnter the new value below; to keep the value the same, re-enter the original value "
                    + "\nNote: the value must be greater than 0");

            tankDuelGame.editExistingTank(type, name, armor, firepower, mobility, health, index);
        } catch (InvalidInputException e) {
            JOptionPane.showMessageDialog(null, "Invalid input, please try again",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // EFFECTS: checks if response is only numbers and a number representing a tank in the tank list;
    //          if one of the requirements is not met, return -1
    //          if all requirements met, return response - 1
    public static int convertInputToIntTankSelection(String response) {
        if (response == null) {
            return -1;
        }

        if (response.matches("[0-9]+") && Integer.parseInt(response) >= 1
                && Integer.parseInt(response) <= tankDuelGame.getTankListSize()) {
            return Integer.parseInt(response) - 1;
        } else {
            return -1;
        }
    }

    // EFFECTS: displays tank list, has user select tank, and then deletes selection; shows error message if
    //          selection is invalid or if only one tank is left in the list
    public void deleteExistingTank() {
        if (tankDuelGame.getTankListSize() == 1) {
            JOptionPane.showMessageDialog(null,
                    "There must be at least one tank in the tank list",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String selection = (String) JOptionPane.showInputDialog(null,
                    tankDuelGame.printTankList() + "Enter in # of tank to be deleted",
                    "Select Tank to Delete", JOptionPane.PLAIN_MESSAGE, null, null,
                    "1");

            int index = convertInputToIntTankSelection(selection);

            if (index == -1) {
                JOptionPane.showMessageDialog(null, "Invalid input, please try again",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                tankDuelGame.removeTank(index);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(viewTanks.getActionCommand())) {
            viewTankList();
        } else if (e.getActionCommand().equals(newTank.getActionCommand())) {
            createNewTank();
        } else if (e.getActionCommand().equals(editTank.getActionCommand())) {
            selectTankToEdit();
        } else if (e.getActionCommand().equals(removeTank.getActionCommand())) {
            deleteExistingTank();
        }
    }
}
