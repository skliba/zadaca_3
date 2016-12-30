package com.skliba.helpers;

import com.skliba.dpatterns.memento.TerminalCaretaker;
import com.skliba.dpatterns.memento.TerminalOriginator;
import com.skliba.dpatterns.singleton.DivingClub;
import com.skliba.dpatterns.singleton.TerminalData;
import com.skliba.dpatterns.mvc.model.Diver;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TerminalHelper {

    private static final String ANSI_ESC = "\033[";

    private static final String COMMAND_NAME_REGEX = "^N [A-Za-z]+$";

    private static int numberOfRows = 0;

    private static int numberOfColumns = 0;

    private static int numberOfContainerRows = 0;

    private static int numberOfTerminalRows = 0;

    private static String command = "";

    private static ArrayList<Diver> currentlyVisibleDivers = new ArrayList<>();

    private static ArrayList<Diver> clonedDiverList = new ArrayList<>();

    private static ArrayList<Diver> initialDiverList = new ArrayList<>();

    private static int movedUpSpaces = 0;

    private static TerminalCaretaker terminalCaretaker = new TerminalCaretaker();

    private static TerminalOriginator terminalOriginator = new TerminalOriginator();

    private static int numOfSavedStates = 0;

    public static void init() {
        numberOfRows = TerminalData.getInstance().getNumberOfRows();
        numberOfTerminalRows = TerminalData.getInstance().getNumberOfRows() - 1;
        numberOfColumns = TerminalData.getInstance().getNumberOfColumns();
        numberOfContainerRows = TerminalData.getInstance().getContainerRowsNumber();

        initialDiverList = (ArrayList<Diver>) DivingClub.getInstance().getDiversCapableForDive();

        clonedDiverList = new ArrayList<>();

        for (Diver d : initialDiverList) {
            try {
                clonedDiverList.add((Diver) d.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void printInitialList() {
        System.out.print(ANSI_ESC + "2J");

        for (int i = 0; i < clonedDiverList.size(); i++) {

            currentlyVisibleDivers.add(clonedDiverList.get(i));

            if (i == numberOfTerminalRows) {
                System.out.print(ANSI_ESC + (numberOfTerminalRows - i) + ";1f");
                System.out.print(clonedDiverList.get(i).getName());
                break;
            } else {
                System.out.print(ANSI_ESC + (numberOfTerminalRows - i) + ";1f");
                System.out.print(clonedDiverList.get(i).getName());
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        terminalOriginator.setState(clonedDiverList);
        terminalCaretaker.addMemento(terminalOriginator.saveToMemento());
        numOfSavedStates++;
        addCommandLine();
        initTerminal();
    }

    private static void initTerminal() {

        while (true) {
            Scanner scanner = new Scanner(System.in);
            command = scanner.nextLine().trim();

            Pattern p = Pattern.compile(COMMAND_NAME_REGEX);
            Matcher m = p.matcher(command);

            if (m.matches()) {
                changeGearJoiningType(command);
                resetScreen();
            } else if (command.equals("P")) {
                printDroppedDivers();
            } else if (command.equals("V")) {
                printInitialListState();
            } else if (command.equals("G")) {

                if (initialDiverList.size() > numberOfTerminalRows) moveScreenUp(initialDiverList, currentlyVisibleDivers);

                else {
                    resetScreen();
                    break;
                }

            } else if (command.equals("D")) {

                if (initialDiverList.size() > numberOfTerminalRows) moveScreenDown(initialDiverList, currentlyVisibleDivers);

                else {
                    resetScreen();
                    break;
                }

            } else if (command.equals("Q")) {
                break;
            } else {
                System.out.println("Invalid command");
            }
        }
    }

    private static void printInitialListState() {
        terminalOriginator.restoreFromMemento(terminalCaretaker.getMemento(numOfSavedStates - 1));
        ArrayList<Diver> restoredDivers = terminalOriginator.getDiversSavedState();
        System.out.print(ANSI_ESC + "2J");

        currentlyVisibleDivers.clear();

        for (int i = 0; i < restoredDivers.size(); i++) {

            currentlyVisibleDivers.add(restoredDivers.get(i));

            if (i == numberOfTerminalRows) {
                System.out.print(ANSI_ESC + (numberOfTerminalRows - i) + ";1f");
                System.out.print(restoredDivers.get(i).getName());
                break;
            } else {
                System.out.print(ANSI_ESC + (numberOfTerminalRows - i) + ";1f");
                System.out.print(restoredDivers.get(i).getName());
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        addCommandLine();
        initTerminal();

    }

    private static void printDroppedDivers() {
        ArrayList<Diver> droppedDivers = (ArrayList<Diver>) DivingClub.getInstance().getDiversNotCapableForDive();
        System.out.print(ANSI_ESC + "2J");
        currentlyVisibleDivers.clear();

        for (int i = 0; i < droppedDivers.size(); i++) {

            currentlyVisibleDivers.add(droppedDivers.get(i));

            if (i == numberOfTerminalRows) {
                System.out.print(ANSI_ESC + (numberOfTerminalRows - i) + ";1f");
                System.out.print(droppedDivers.get(i).getName());
                break;
            } else {
                System.out.print(ANSI_ESC + (numberOfTerminalRows - i) + ";1f");
                System.out.print(droppedDivers.get(i).getName());
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        addCommandLine();
        initTerminal();
    }

    private static void changeGearJoiningType(String command) {
        String[] splitCommand = command.split(" ");
        for (Diver d : clonedDiverList) {
            if (d.getName().equals(splitCommand[1])) {
                if (d.isMinimalGear()) d.setMinimalGear(false);
                else d.setMinimalGear(true);
            }
        }

        terminalOriginator.setState(clonedDiverList);
        terminalCaretaker.addMemento(terminalOriginator.saveToMemento());
        numOfSavedStates++;
    }

    private static void resetScreen() {
        System.out.print(ANSI_ESC + "2J");
        for (int i = 0; i < currentlyVisibleDivers.size(); i++) {
            System.out.print(ANSI_ESC + (numberOfTerminalRows - i) + ";1f");
            System.out.print(currentlyVisibleDivers.get(i).getName());
        }
        addCommandLine();
        initTerminal();
    }

    private static void addCommandLine() {
        System.out.print(ANSI_ESC + numberOfRows + ";1f");
        System.out.print("----------------------------------------------------------------------------------------");
        System.out.println();
        System.out.print("Your command: ");
    }

    private static void moveScreenUp(ArrayList<Diver> divers, ArrayList<Diver> currentlyVisibleDivers) {
        if (currentlyVisibleDivers.get(0).equals(divers.get(0))) {
            System.out.print(ANSI_ESC + "2J");
            movedUpSpaces++;
            currentlyVisibleDivers.remove(0);
            currentlyVisibleDivers.add(divers.get(numberOfTerminalRows + movedUpSpaces));

            for (int i = 0; i < currentlyVisibleDivers.size(); i++) {
                System.out.print(ANSI_ESC + (numberOfTerminalRows - i) + ";1f");
                System.out.print(currentlyVisibleDivers.get(i).getName());
            }
            addCommandLine();
        }
    }

    private static void moveScreenDown(ArrayList<Diver> divers, ArrayList<Diver> currentlyVisibleDivers) {
        if (movedUpSpaces > 0) {
            System.out.print(ANSI_ESC + "2J");
            movedUpSpaces--;
            currentlyVisibleDivers.add(0, divers.get(movedUpSpaces));
            currentlyVisibleDivers.remove(currentlyVisibleDivers.size() - 1);
            for (int i = 0; i < currentlyVisibleDivers.size(); i++) {
                System.out.print(ANSI_ESC + (numberOfTerminalRows - i) + ";1f");
                System.out.print(currentlyVisibleDivers.get(i).getName());
            }

            addCommandLine();
        }
    }


}
