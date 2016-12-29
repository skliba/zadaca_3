package com.skliba.dpatterns.singleton;

public class TerminalData {

    private static int numberOfRows;

    private static int numberOfColumns;

    private static int containerRowsNumber;

    private static TerminalData instance;

    private TerminalData() {
    }

    public static TerminalData getInstance() {
        if (instance == null) {
            instance = new TerminalData();
            return instance;
        }
        return instance;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(int numberOfRows) {
        TerminalData.numberOfRows = numberOfRows;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public void setNumberOfColumns(int numberOfColumns) {
        TerminalData.numberOfColumns = numberOfColumns;
    }

    public int getContainerRowsNumber() {
        return containerRowsNumber;
    }

    public void setContainerRowsNumber(int containerRowsNumber) {
        TerminalData.containerRowsNumber = containerRowsNumber;
    }

    public void setInstance(TerminalData instance) {
        TerminalData.instance = instance;
    }

    public void setData(int numberOfRows, int numberOfColumns, int containerRowsNumber) {
        if (numberOfRows >= 24 && numberOfRows <= 40) TerminalData.numberOfRows = numberOfRows;
        if (numberOfColumns >= 80 && numberOfColumns <= 160) TerminalData.numberOfColumns = numberOfColumns;
        if (containerRowsNumber >= numberOfRows && containerRowsNumber <= 400) TerminalData.containerRowsNumber = containerRowsNumber;
    }
}
