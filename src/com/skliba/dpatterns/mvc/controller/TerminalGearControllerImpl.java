package com.skliba.dpatterns.mvc.controller;

import com.skliba.dpatterns.mvc.model.Diver;
import com.skliba.dpatterns.mvc.view.TerminalGearView;
import com.skliba.dpatterns.singleton.DivingClub;
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
    }

    @Override
    public void printDiversWithGear() {
        view.clearScreen();
        System.out.print("Divers with gear");
        for (int i = 0; i < workingCopyOfDivers.size(); i++) {
            if (i == numberOfTerminalRows) {
                view.printNames(numberOfTerminalRows - i, workingCopyOfDivers.get(i));
                break;
            } else {
                view.printNames(numberOfTerminalRows - i, workingCopyOfDivers.get(i));
            }
        }
    }
}
