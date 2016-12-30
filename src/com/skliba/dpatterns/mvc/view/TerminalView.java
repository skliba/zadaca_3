package com.skliba.dpatterns.mvc.view;

public interface TerminalView {

    void initView();

    void clearScreen();

    void printNames(int yCoordinate, String name);

    void addCommandLine(int numberOfRows);

    String getUserInputFromScanner();

    void onNewStage();

}
