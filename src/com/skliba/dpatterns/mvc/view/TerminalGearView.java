package com.skliba.dpatterns.mvc.view;

import com.skliba.dpatterns.mvc.model.Diver;

public interface TerminalGearView {

    void initView();

    void clearScreen();

    void printNames(int yCoordinate, String name);

    int printNames(int yCoordinate, Diver name);

    void addCommandLine(int numberOfRows);

    String getUserInputFromScanner();

    void onPreviousStage();

    void onGearAddedToDivers();
}
