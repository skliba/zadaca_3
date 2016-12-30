package com.skliba.models;

import com.skliba.dpatterns.mvc.model.Diver;

public class Pair<FirstPerson, SecondPerson> {

    private FirstPerson firstDiver;
    private SecondPerson secondDiver;

    public FirstPerson getFirstDiver() {
        return firstDiver;
    }

    public void setFirstDiver(FirstPerson firstDiver) {
        this.firstDiver = firstDiver;
    }

    public SecondPerson getSecondDiver() {
        return secondDiver;
    }

    public void setSecondDiver(SecondPerson secondDiver) {
        this.secondDiver = secondDiver;
    }

    public boolean isDiverInPair(String name) {
        return firstDiver != null && secondDiver != null && (((Diver) firstDiver).getName().equals(name)
                || ((Diver) secondDiver).getName().equals(name));
    }

    public int availableSlots() {
        int numbOfAvailableSlots = 0;
        if (firstDiver == null) numbOfAvailableSlots++;
        if (secondDiver == null) numbOfAvailableSlots++;
        return numbOfAvailableSlots;
    }
}
