package com.skliba.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.skliba.dpatterns.visitor.Diver;

public class Dive {

    private ArrayList<Pair> pairs = new ArrayList<>();
    private ArrayList<Triplet> triplets = new ArrayList<>();
    private Date diveDate;
    private Date diveTime;
    private int diveDepth;
    private float securityMeasure;
    private float randomSecurityMeasure;
    private float maxDepthSecurityMeasure;
    private float maxPartnerSecurityMeasure;
    private ArrayList<Diver> allDiversTogether = new ArrayList<>();


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

    public float getSecurityMeasure() {
        return securityMeasure;
    }

    public float getRandomSecurityMeasure() {
        return randomSecurityMeasure;
    }

    public void setRandomSecurityMeasure(float randomSecurityMeasure) {
        this.randomSecurityMeasure = randomSecurityMeasure;
    }

    public float getMaxDepthSecurityMeasure() {
        return maxDepthSecurityMeasure;
    }

    public void setMaxDepthSecurityMeasure(float maxDepthSecurityMeasure) {
        this.maxDepthSecurityMeasure = maxDepthSecurityMeasure;
    }

    public float getMaxPartnerSecurityMeasure() {
        return maxPartnerSecurityMeasure;
    }

    public void setMaxPartnerSecurityMeasure(float maxPartnerSecurityMeasure) {
        this.maxPartnerSecurityMeasure = maxPartnerSecurityMeasure;
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

    public void calculateOverallSecurityMeasure() {
        for (Pair pair : pairs) {
            securityMeasure += calculateSecurityMeasureOfAPair(pair);
        }
        for (Triplet triplet : triplets) {
            securityMeasure += calculateSecurityMeasureOfATriplet(triplet);
        }
    }

    private float calculateSecurityMeasureOfAPair(Pair pair) {
        int maxDepth = calculateMaxDepthOfPair(pair);
        Diver firstDiver = (Diver) pair.getFirstDiver();
        Diver secondDiver = (Diver) pair.getSecondDiver();
        float categoryDifference = Math.abs(firstDiver.getCeritificateTypeAsInteger() - secondDiver
                .getCeritificateTypeAsInteger()) + 1f;
        return maxDepth / categoryDifference;
    }

    private float calculateSecurityMeasureOfATriplet(Triplet triplet) {
        int maxDepth = calculateMaxDepthOfTriplet(triplet);
        Diver firstDiver = (Diver) triplet.getFirstDiver();
        Diver secondDiver = (Diver) triplet.getSecondDiver();
        Diver thirdDiver = (Diver) triplet.getThirdDiver();
        int maxCertificate = findMaximumCertificate(firstDiver.getCeritificateTypeAsInteger(), secondDiver
                .getCeritificateTypeAsInteger(), thirdDiver.getCeritificateTypeAsInteger());
        int minCertificate = findMinimumCertificate(firstDiver.getCeritificateTypeAsInteger(), secondDiver
                .getCeritificateTypeAsInteger(), thirdDiver.getCeritificateTypeAsInteger());
        float categoryDifference = maxCertificate - minCertificate + 1f;
        return maxDepth / categoryDifference;
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
}
