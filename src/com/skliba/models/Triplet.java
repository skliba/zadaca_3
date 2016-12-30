package com.skliba.models;

import com.skliba.dpatterns.mvc.model.Diver;

public class Triplet<FirstPerson, SecondPerson, ThirdPerson> {

    private FirstPerson firstDiver;
    private SecondPerson secondDiver;
    private ThirdPerson thirdDiver;

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

    public ThirdPerson getThirdDiver() {
        return thirdDiver;
    }

    public void setThirdDiver(ThirdPerson thirdDiver) {
        this.thirdDiver = thirdDiver;
    }

    public boolean isDiverInTriplet(String name) {
        return firstDiver != null && secondDiver != null && thirdDiver != null &&
                (((Diver) firstDiver).getName().equals(name)
                || ((Diver) secondDiver).getName().equals(name)
                || ((Diver) thirdDiver).getName().equals(name));
    }

    public int availableSlots() {
        int numbOfAvailableSpaces = 0;
        if (firstDiver == null) numbOfAvailableSpaces++;
        if (secondDiver == null) numbOfAvailableSpaces++;
        if (thirdDiver == null) numbOfAvailableSpaces++;

        return numbOfAvailableSpaces;
    }
}
