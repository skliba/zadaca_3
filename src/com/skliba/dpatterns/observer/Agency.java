package com.skliba.dpatterns.observer;

import com.skliba.dpatterns.visitor.Visitable;
import com.skliba.dpatterns.visitor.Visitor;
import com.skliba.dpatterns.visitor.Diver;

import java.util.ArrayList;

public class Agency implements Observer, Visitable {

    private String name;

    private ArrayList<Diver> diverInAgency = new ArrayList<>();

    private float avgDepth;

    private int totalDepth;

    public Agency(String name) {
        this.name = name;
        this.avgDepth = 0;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Diver> getDiverInAgency() {
        return diverInAgency;
    }

    @Override
    public void update(Diver diver, int diveDepth) {
        if (diver.getAgencyName().equals(name)) {
            diverInAgency.add(diver);
            calculateAvgDepth(diveDepth);
        }
    }

    private void calculateAvgDepth(int diveDepth) {
        totalDepth += diveDepth;
        avgDepth = totalDepth / diverInAgency.size();
    }

    public float getAvgDepth() {
        return avgDepth;
    }

    public int getNumOfDiversPerCategory(String categoryName) {
        int num = 0;
        for (Diver d : diverInAgency) {
            if (d.getCertType().equals(categoryName)) {
                num++;
            }
        }
        return num;
    }


    @Override
    public void accept(Visitor visitor) {
        visitor.visitAgency(this);
    }
}
