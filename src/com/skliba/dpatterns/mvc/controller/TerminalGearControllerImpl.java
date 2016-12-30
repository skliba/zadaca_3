package com.skliba.dpatterns.mvc.controller;

import com.skliba.DiverInventoryLevel;
import com.skliba.dpatterns.composite.Item;
import com.skliba.dpatterns.mvc.model.Diver;
import com.skliba.dpatterns.mvc.view.TerminalGearView;
import com.skliba.dpatterns.singleton.DivingClub;
import com.skliba.dpatterns.singleton.InventoryData;
import com.skliba.dpatterns.singleton.TerminalData;

import java.util.ArrayList;
import java.util.List;


public class TerminalGearControllerImpl implements TerminalGearController {

    private TerminalGearView view;

    private List<Object> buffer = new ArrayList<>();

    private int numberOfRows = 0;

    private int numberOfColumns = 0;

    private int numberOfContainerRows = 0;

    private int numberOfTerminalRows = 0;

    private List<Diver> workingCopyOfDivers;

    private List<String> currentlyVisibleInfo;

    public TerminalGearControllerImpl(TerminalGearView view) {
        this.view = view;
    }

    @Override
    public void init() {
        numberOfRows = TerminalData.getInstance().getNumberOfRows();
        numberOfTerminalRows = TerminalData.getInstance().getNumberOfRows() - 1;
        numberOfColumns = TerminalData.getInstance().getNumberOfColumns();
        numberOfContainerRows = TerminalData.getInstance().getContainerRowsNumber();

        workingCopyOfDivers = DivingClub.getInstance().getWorkingDiversList();
        currentlyVisibleInfo = new ArrayList<>();
    }

    @Override
    public void printDiversWithGear() {
        view.clearScreen();
        for (int i = 0; i < workingCopyOfDivers.size(); i++) {
            if (workingCopyOfDivers.get(i).getDiverInventoryLevel() != DiverInventoryLevel.NOT_EQUIPPED) {
                currentlyVisibleInfo.add(String.valueOf(workingCopyOfDivers.get(i)));
                if (i == numberOfTerminalRows) {
                    view.printNames(numberOfTerminalRows - i, workingCopyOfDivers.get(i));
                    break;
                } else {
                    view.printNames(numberOfTerminalRows - i, workingCopyOfDivers.get(i));
                }
            }
        }
        view.addCommandLine(numberOfRows);
        initTerminal();
    }

    @Override
    public void returnToPreviousStep() {

    }


    private void initTerminal() {

        String command = view.getUserInputFromScanner();

        if (command.equals("O")) {
            printRequiredGearForDive();
        } else if (command.equals("V")) {
            returnToPreviousStep();
        } else if (command.equals("G")) {

            if (workingCopyOfDivers.size() > numberOfTerminalRows) {
                // moveScreenUp(workingCopyOfDivers, currentlyVisibleDivers);
            } else {
                resetScreen();
            }

        } else if (command.equals("D")) {

            if (workingCopyOfDivers.size() > numberOfTerminalRows) {
                //moveScreenDown(clonedDiverList, currentlyVisibleDivers);
            } else {
                resetScreen();
            }

        } else if (command.equals("Q")) {

        } else {
            for (Diver d : workingCopyOfDivers) {
                if (command.equals(d.getName())) {
                    printDiverGear(d);
                    return;
                }
            }
            resetCommandLine();
        }
    }

    private void resetCommandLine() {
        view.clearScreen();

        for (int i = 0; i < currentlyVisibleInfo.size(); i++) {
            if (i == numberOfTerminalRows) {
                view.printNames(numberOfTerminalRows - i, currentlyVisibleInfo.get(i));
                break;
            } else {
                view.printNames(numberOfTerminalRows - i, currentlyVisibleInfo.get(i));
            }
        }
        view.addCommandLine(numberOfRows);
        initTerminal();
    }

    private void resetScreen() {
        view.clearScreen();
        for (int i = 0; i < currentlyVisibleInfo.size(); i++) {
            view.printNames(numberOfTerminalRows - i, currentlyVisibleInfo.get(i));
        }
        view.addCommandLine(numberOfRows);
        initTerminal();
    }

    private void printRequiredGearForDive() {
        view.clearScreen();
        List<Item> diveItems = InventoryData.getInstance().getItems();
        for (int i = 0, size = diveItems.size(); i < size; i++) {
            currentlyVisibleInfo.add(String.valueOf(diveItems.get(i)));
            if (i == numberOfTerminalRows) {
                view.printNames(numberOfTerminalRows - i, diveItems.get(i).toString());
                break;
            } else {
                view.printNames(numberOfTerminalRows - i, diveItems.get(i).toString());
            }
        }
        view.addCommandLine(numberOfRows);
        initTerminal();
    }

    private void printDiverGear(Diver diver) {
        view.clearScreen();
        List<Item> diveItems = diver.getInventoryItems();
        for (int i = 0, size = diveItems.size(); i < size; i++) {
            currentlyVisibleInfo.add(diveItems.get(i).getSpecialStringRepresentation());
            if (i == numberOfTerminalRows) {
                view.printNames(numberOfTerminalRows - i, diveItems.get(i).getSpecialStringRepresentation());
                break;
            } else {
                view.printNames(numberOfTerminalRows - i, diveItems.get(i).getSpecialStringRepresentation());
            }
        }
        view.addCommandLine(numberOfRows);
        initTerminal();
    }
}
