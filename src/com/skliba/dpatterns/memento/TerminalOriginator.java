package com.skliba.dpatterns.memento;

import com.skliba.dpatterns.visitor.Diver;

import java.util.ArrayList;

public class TerminalOriginator {

    private ArrayList<Diver> diversSavedState = new ArrayList<>();

    public void setState(ArrayList<Diver> savedState) {
        this.diversSavedState = savedState;
    }

    public TerminalState saveToMemento() {
        return new TerminalState(diversSavedState);
    }

    public void restoreFromMemento(TerminalState memento) {
        diversSavedState = memento.getSavedDiverState();
    }

    public ArrayList<Diver> getDiversSavedState() {
        return diversSavedState;
    }
}
