package com.skliba.dpatterns.observer;

import com.skliba.dpatterns.visitor.Visitable;
import com.skliba.dpatterns.visitor.Visitor;
import com.skliba.dpatterns.mvc.model.Diver;

import java.util.ArrayList;

public class Agency implements Visitable {

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
