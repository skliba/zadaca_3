package com.skliba.dpatterns.singleton;

import com.skliba.dpatterns.mvc.model.Diver;
import com.skliba.models.Pair;
import com.skliba.models.Triplet;

import java.util.ArrayList;

public class Dive {

    private static Dive instance;
    private ArrayList<Pair> pairs = new ArrayList<>();
    private ArrayList<Triplet> triplets = new ArrayList<>();
    private int diveDepth;
    private ArrayList<Diver> allDiversTogether = new ArrayList<>();
    private int waterTemperature;
    private boolean isNightDive;
    private int numberOfRecorders;
    private boolean drySuitDive = false;

    private Dive() {
    }

    public static Dive getInstance() {
        if (instance == null) {
            instance = new Dive();
            return instance;
        }
        return instance;
    }

    public ArrayList<Pair> getPairs() {
        return pairs;
    }

    public void setPairs(ArrayList<Pair> pairs) {
        this.pairs = pairs;
    }

    public ArrayList<Triplet> getTriplets() {
        return triplets;
    }

    public void setTriplets(ArrayList<Triplet> triplets) {
        this.triplets = triplets;
    }

    public ArrayList<Diver> getAllDiversTogether() {
        return allDiversTogether;
    }

    public void setAllDiversTogether(ArrayList<Diver> allDiversTogether) {
        this.allDiversTogether = allDiversTogether;
    }

    public int getDiveDepth() {
        return diveDepth;
    }

    public void setDiveDepth(int diveDepth) {
        this.diveDepth = diveDepth;
    }

    public void setDiveParams(int diveDepth, int waterTemperature, int nightTime, int numberOfRecorders) {
        if (diveDepth >= 5 && diveDepth <= 40) this.diveDepth = diveDepth;
        if (waterTemperature >= 0 && waterTemperature <= 35) this.waterTemperature = waterTemperature;
        if (nightTime == 0 || nightTime == 1) {
            if (nightTime == 0) this.isNightDive = false;
            if (nightTime == 1) this.isNightDive = true;
        }
        if (waterTemperature < 15) drySuitDive = true;
        this.numberOfRecorders = numberOfRecorders;
    }

    public int getWaterTemperature() {
        return waterTemperature;
    }

    public boolean isNightDive() {
        return isNightDive;
    }

    public int getNumberOfRecorders() {
        return numberOfRecorders;
    }

    public boolean isDrySuitDive() {
        return drySuitDive;
    }
}
