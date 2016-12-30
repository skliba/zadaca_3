package com.skliba.dpatterns.mvc.view;

import com.skliba.dpatterns.factory.MVCFactory;
import com.skliba.dpatterns.mvc.controller.TerminalController;

import java.util.Scanner;

public class TerminalViewImpl implements TerminalView {

    private final String ANSI_ESC = "\033[";

    private TerminalController terminalController;

    @Override
    public void initView() {
        terminalController = MVCFactory.getController(this);
        terminalController.init();
        terminalController.printInitialList();
    }

    @Override
    public void clearScreen() {
        System.out.print(ANSI_ESC + "2J");
    }

    @Override
    public void printNames(int yCoordinate, String name) {
        System.out.print(ANSI_ESC + (yCoordinate) + ";1f");
        System.out.print(name);
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
}
