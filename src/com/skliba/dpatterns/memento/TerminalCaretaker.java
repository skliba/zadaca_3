package com.skliba.dpatterns.memento;

import java.util.ArrayList;

public class TerminalCaretaker {

    private ArrayList<TerminalState> savedStates = new ArrayList<>();

    public void addMemento(TerminalState m) {
        savedStates.add(m);
    }

    public TerminalState getMemento(int index) {
        return savedStates.get(index);
    }

}
