package com.skliba.dpatterns.mvc.view;

import com.skliba.dpatterns.factory.MVCFactory;
import com.skliba.dpatterns.mvc.controller.TerminalGearController;
import com.skliba.dpatterns.mvc.model.Diver;

import java.util.Scanner;

public class TerminalGearViewImpl implements TerminalGearView {

    private TerminalGearController controller;

    private final String ANSI_ESC = "\033[";

    @Override
    public void initView() {
        controller = MVCFactory.getController(this);
        controller.init();
        controller.printDiversWithGear();
    }

    @Override
    public void clearScreen() {
        System.out.print(ANSI_ESC + "2J");
    }

    @Override
    public void printNames(int yCoordinate, String name) {

    }

    @Override
    public int printNames(int yCoordinate, Diver name) {
        System.out.print(ANSI_ESC + yCoordinate + ";1f");
        System.out.print(name);
        for (int i = 0; i < name.getInventoryItems().size(); i++) {
            System.out.print(ANSI_ESC + (yCoordinate - 1) + ";1f");
            System.out.print(name.getInventoryItems().get(i));
        }
        return name.getInventoryItems().size();
    }

    @Override
    public void addCommandLine(int numberOfRows) {
        System.out.print(ANSI_ESC + numberOfRows + ";1f");
        System.out.print("----------------------------------------------------------------------------------------");
        System.out.println();
        System.out.print("Your command: ");
    }

    @Override
    public String getUserInputFromScanner() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().trim();
    }

    @Override
    public void onPreviousStage() {

    }

    @Override
    public void onGearAddedToDivers() {

    }
}
