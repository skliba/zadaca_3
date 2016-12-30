package com.skliba.dpatterns.singleton;

import com.skliba.dpatterns.observer.Observer;
import com.skliba.dpatterns.visitor.ConcreteVisitor;
import com.skliba.dpatterns.mvc.model.Diver;
import com.skliba.models.Divings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DivingClub {

    private static DivingClub instance;
    private ConcreteVisitor concreteVisitor = new ConcreteVisitor();
    private ArrayList<Diver> divers;
    private ArrayList<Divings> divings;

    private List<Diver> diversCapableForDive;
    private List<Diver> diversNotCapableForDive;

    private List<Diver> workingDiversList;

    private List<String> certificateList = Collections.unmodifiableList(Arrays.asList(
            "R0",
            "R1",
            "R2",
            "R3",
            "R4",
            "R5",
            "I1",
            "I2",
            "I3",
            "I4",
            "I5",
            "I6"));

    private ArrayList<Observer> observers = new ArrayList<>();

    private ArrayList<Dive> eachDiveData;

    public static DivingClub getInstance() {
        if (instance == null) {
            instance = new DivingClub();
            return instance;
        }
        return instance;
    }

    public ConcreteVisitor getConcreteVisitor() {
        return concreteVisitor;
    }

    public void setConcreteVisitor(ConcreteVisitor concreteVisitor) {
        this.concreteVisitor = concreteVisitor;
    }

    /**
     * Private constructor in order to assure that the object cannot be instantiated outside the class itself.
     */
    private DivingClub() {
    }

    public ArrayList<Observer> getObservers() {
        return observers;
    }

    public ArrayList<Diver> getDivers() {
        return divers;
    }

    public void setDivers(ArrayList<Diver> divers) {
        this.divers = divers;
    }

    public ArrayList<Divings> getDivings() {
        return divings;
    }

    public void setDivings(ArrayList<Divings> divings) {
        this.divings = divings;
    }

    public void setEachDiveData(ArrayList<Dive> eachDiveData) {
        this.eachDiveData = eachDiveData;
    }

    public List<String> getCertificateList() {
        return certificateList;
    }

    public List<Diver> getDiversNotCapableForDive() {
        return diversNotCapableForDive;
    }

    public void setDiversNotCapableForDive(List<Diver> diversNotCapableForDive) {
        this.diversNotCapableForDive = diversNotCapableForDive;
    }

    public void setDiversCapableForDive(List<Diver> diversCapableForDive) {
        this.diversCapableForDive = diversCapableForDive;
    }

    public List<Diver> getDiversCapableForDive() {
        return diversCapableForDive;
    }

    public List<Diver> getWorkingDiversList() {
        return workingDiversList;
    }

    public void setWorkingDiversList(List<Diver> workingDiversList) {
        this.workingDiversList = workingDiversList;
    }
}
