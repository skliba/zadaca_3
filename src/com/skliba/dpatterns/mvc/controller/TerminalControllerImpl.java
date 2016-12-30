package com.skliba.dpatterns.mvc.controller;

import com.skliba.dpatterns.memento.TerminalCaretaker;
import com.skliba.dpatterns.memento.TerminalOriginator;
import com.skliba.dpatterns.mvc.model.Diver;
import com.skliba.dpatterns.mvc.view.TerminalView;
import com.skliba.dpatterns.singleton.DivingClub;
import com.skliba.dpatterns.singleton.TerminalData;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TerminalControllerImpl implements TerminalController {

    private final String COMMAND_NAME_REGEX = "^N [A-Za-z]+$";

    private int numberOfRows = 0;

    private int numberOfColumns = 0;

    private int numberOfContainerRows = 0;

    private int numberOfTerminalRows = 0;

    private ArrayList<Diver> currentlyVisibleDivers = new ArrayList<>();

    private ArrayList<Diver> clonedDiverList = new ArrayList<>();

    private int movedUpSpaces = 0;

    private TerminalCaretaker terminalCaretaker = new TerminalCaretaker();

    private TerminalOriginator terminalOriginator = new TerminalOriginator();

    private int numOfSavedStates = 0;

    private TerminalView view;

    public TerminalControllerImpl(TerminalView view) {
        this.view = view;
    }

    @Override
    public void init() {
        numberOfRows = TerminalData.getInstance().getNumberOfRows();
        numberOfTerminalRows = TerminalData.getInstance().getNumberOfRows() - 1;
        numberOfColumns = TerminalData.getInstance().getNumberOfColumns();
        numberOfContainerRows = TerminalData.getInstance().getContainerRowsNumber();

        ArrayList<Diver> initialDiverList = (ArrayList<Diver>) DivingClub.getInstance().getDiversCapableForDive();

        clonedDiverList = new ArrayList<>();

        for (Diver d : initialDiverList) {
            try {
                clonedDiverList.add((Diver) d.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void printInitialList() {
        view.clearScreen();

        for (int i = 0; i < clonedDiverList.size(); i++) {

            currentlyVisibleDivers.add(clonedDiverList.get(i));

            if (i == numberOfTerminalRows) {
                view.printNames(numberOfTerminalRows - i, clonedDiverList.get(i));
                break;
            } else {
                view.printNames(numberOfTerminalRows - i, clonedDiverList.get(i));
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
        view.addCommandLine(numberOfRows);
        initTerminal();
    }

    private void initTerminal() {

        String command = view.getUserInputFromScanner();

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

            if (clonedDiverList.size() > numberOfTerminalRows) {
                moveScreenUp(clonedDiverList, currentlyVisibleDivers);

            } else {
                resetScreen();
            }

        } else if (command.equals("D")) {

            if (clonedDiverList.size() > numberOfTerminalRows) {
                moveScreenDown(clonedDiverList, currentlyVisibleDivers);
            } else {
                resetScreen();

            }

        } else if (command.equals("Q")) {

        } else if (command.equals("N")) {
            DivingClub.getInstance().setWorkingDiversList(clonedDiverList);
            view.onNewStage();
        } else {

            //If deleting diver is triggered
            for (Diver d : clonedDiverList) {
                if (command.equals(d.getName())) {
                    deleteDiverFromList(d);
                    return;
                }
            }
            resetCommandLine();
        }
    }

    private void deleteDiverFromList(Diver d) {
        view.clearScreen();
        clonedDiverList.remove(d);
        currentlyVisibleDivers.remove(d);
        for (int i = 0; i < currentlyVisibleDivers.size(); i++) {
            if (i == numberOfTerminalRows) {
                view.printNames(numberOfTerminalRows - i, currentlyVisibleDivers.get(i));
                break;
            } else {
                view.printNames(numberOfTerminalRows - i, currentlyVisibleDivers.get(i));
            }
        }

        terminalOriginator.setState(clonedDiverList);
        terminalCaretaker.addMemento(terminalOriginator.saveToMemento());
        numOfSavedStates++;

        view.addCommandLine(numberOfRows);
        initTerminal();
    }

    private void resetCommandLine() {
        view.clearScreen();

        for (int i = 0; i < currentlyVisibleDivers.size(); i++) {
            if (i == numberOfTerminalRows) {
                view.printNames(numberOfTerminalRows - i, currentlyVisibleDivers.get(i));
                break;
            } else {
                view.printNames(numberOfTerminalRows - i, currentlyVisibleDivers.get(i));
            }
        }
        view.addCommandLine(numberOfRows);
        initTerminal();
    }

    private void printInitialListState() {
        terminalOriginator.restoreFromMemento(terminalCaretaker.getMemento(numOfSavedStates - 1));
        ArrayList<Diver> restoredDivers = terminalOriginator.getDiversSavedState();
        view.clearScreen();
        currentlyVisibleDivers.clear();

        for (int i = 0; i < restoredDivers.size(); i++) {

            currentlyVisibleDivers.add(restoredDivers.get(i));

            if (i == numberOfTerminalRows) {
                view.printNames(numberOfTerminalRows - i, restoredDivers.get(i));
                break;
            } else {
                view.printNames(numberOfTerminalRows - i, restoredDivers.get(i));
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        view.addCommandLine(numberOfRows);
        initTerminal();

    }

    private void printDroppedDivers() {
        ArrayList<Diver> droppedDivers = (ArrayList<Diver>) DivingClub.getInstance().getDiversNotCapableForDive();
        view.clearScreen();
        currentlyVisibleDivers.clear();

        for (int i = 0; i < droppedDivers.size(); i++) {

            currentlyVisibleDivers.add(droppedDivers.get(i));

            if (i == numberOfTerminalRows) {
                view.printNames(numberOfTerminalRows - i, droppedDivers.get(i));
                break;
            } else {
                view.printNames(numberOfTerminalRows - i, droppedDivers.get(i));
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        view.addCommandLine(numberOfRows);
        initTerminal();
    }

    private void changeGearJoiningType(String command) {
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

    private void resetScreen() {
        view.clearScreen();
        for (int i = 0; i < currentlyVisibleDivers.size(); i++) {
            view.printNames(numberOfTerminalRows - i, currentlyVisibleDivers.get(i));
        }
        view.addCommandLine(numberOfRows);
        initTerminal();
    }


    private void moveScreenUp(ArrayList<Diver> divers, ArrayList<Diver> currentlyVisibleDivers) {
        if (currentlyVisibleDivers.get(0).equals(divers.get(0))) {
            view.clearScreen();
            movedUpSpaces++;
            currentlyVisibleDivers.remove(0);
            currentlyVisibleDivers.add(divers.get(numberOfTerminalRows + movedUpSpaces));

            for (int i = 0; i < currentlyVisibleDivers.size(); i++) {
                view.printNames(numberOfTerminalRows - i, currentlyVisibleDivers.get(i));
            }
            view.addCommandLine(numberOfRows);
        }
    }

    private void moveScreenDown(ArrayList<Diver> divers, ArrayList<Diver> currentlyVisibleDivers) {
        if (movedUpSpaces > 0) {
            view.clearScreen();
            movedUpSpaces--;
            currentlyVisibleDivers.add(0, divers.get(movedUpSpaces));
            currentlyVisibleDivers.remove(currentlyVisibleDivers.size() - 1);
            for (int i = 0; i < currentlyVisibleDivers.size(); i++) {
                view.printNames(numberOfTerminalRows - i, currentlyVisibleDivers.get(i));
            }
            view.addCommandLine(numberOfRows);
        }
    }
}
