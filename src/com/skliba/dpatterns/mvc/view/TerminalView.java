package com.skliba.dpatterns.mvc.view;

import com.skliba.dpatterns.mvc.model.Diver;

public interface TerminalView {

    void initView();

    void clearScreen();

    void printNames(int yCoordinate, String name);

    void printNames(int yCoordinate, Diver name);

    void addCommandLine(int numberOfRows);

    String getUserInputFromScanner();

    void onNewStage();

}
