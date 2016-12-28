package com.skliba.dpatterns.singleton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.skliba.dpatterns.visitor.Diver;
import com.skliba.models.Pair;
import com.skliba.models.Triplet;

public class Dive {

    private static Dive instance;
    private ArrayList<Pair> pairs = new ArrayList<>();
    private ArrayList<Triplet> triplets = new ArrayList<>();
    private Date diveDate;
    private Date diveTime;
    private int diveDepth;
    private ArrayList<Diver> allDiversTogether = new ArrayList<>();
    private int waterTemperature;
    private boolean isNightDive;
    private int numberOfRecorders;

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

    public String getDiveDateString() {
        DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(diveDate);
    }


    public Date getDiveDate() {
        return diveDate;
    }

    public void setDiveDate(Date diveDate) {
        this.diveDate = diveDate;
    }

    public Date getDiveTime() {
        return diveTime;
    }

    public void setDiveTime(Date diveTime) {
        this.diveTime = diveTime;
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


    private int findMaximumCertificate(int... values) {
        int max = Integer.MIN_VALUE;
        for (int value : values) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    private int findMinimumCertificate(int... values) {
        int min = Integer.MAX_VALUE;
        for (int value : values) {
            if (value < min) {
                min = value;
            }
        }
        return min;
    }

    private int calculateMaxDepthOfTriplet(Triplet triplet) {
        Diver firstDiver = (Diver) triplet.getFirstDiver();
        Diver secondDiver = (Diver) triplet.getSecondDiver();
        Diver thirdDiver = (Diver) triplet.getThirdDiver();

        int diversAllowedDepth = Math.min(firstDiver.getMaxDepth(), Math.min(secondDiver.getMaxDepth(), thirdDiver.getMaxDepth())) + 10 <=

                Math.max(firstDiver.getMaxDepth(), Math.max(secondDiver.getMaxDepth(), thirdDiver.getMaxDepth()))
                ?
                Math.min(firstDiver.getMaxDepth(),
                        Math.min(secondDiver.getMaxDepth(), thirdDiver.getMaxDepth())) + 10
                :
                Math.min(firstDiver.getMaxDepth(), Math.min(secondDiver.getMaxDepth(), thirdDiver.getMaxDepth()));

        if (getDiveDepth() > diversAllowedDepth) {
            return diversAllowedDepth;
        } else if (getDiveDepth() == diversAllowedDepth) {
            return diversAllowedDepth;
        } else {
            return getDiveDepth();
        }
    }

    private int calculateMaxDepthOfPair(Pair pair) {
        int diversAllowedDepth = Math.min(((Diver) pair.getFirstDiver()).getMaxDepth(),
                ((Diver) pair.getSecondDiver()).getMaxDepth()) + 10 <=
                Math.max(((Diver) pair.getFirstDiver()).getMaxDepth(), ((Diver) pair.getSecondDiver()).getMaxDepth())
                ?
                (((Diver) pair.getFirstDiver()).getMaxDepth()) + 10 : (((Diver) pair.getFirstDiver()).getMaxDepth());

        if (getDiveDepth() > diversAllowedDepth) {
            return diversAllowedDepth;
        } else if (getDiveDepth() == diversAllowedDepth) {
            return diversAllowedDepth;
        } else {
            return getDiveDepth();
        }
    }

    public void setDiveParams(int diveDepth, int waterTemperature, int nightTime, int numberOfRecorders) {
        if (diveDepth >= 5 && diveDepth <= 40) this.diveDepth = diveDepth;
        if (waterTemperature >= 0 && waterTemperature <= 35) this.waterTemperature = waterTemperature;
        if (nightTime == 0 || nightTime == 1) {
            if (nightTime == 0) this.isNightDive = false;
            if (nightTime == 1) this.isNightDive = true;
        }
        this.numberOfRecorders = numberOfRecorders;
    }
}
