package com.skliba.dpatterns.memento;

import com.skliba.dpatterns.mvc.model.Diver;

import java.util.ArrayList;

public class TerminalState {

    private ArrayList<Diver> savedDiverState = new ArrayList<>();

    public TerminalState(ArrayList<Diver> savedDiverState) {
        this.savedDiverState = savedDiverState;
    }

    public ArrayList<Diver> getSavedDiverState() {
        return savedDiverState;
    }
}
