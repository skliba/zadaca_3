package com.skliba.dpatterns.observer;

import com.skliba.dpatterns.observer.Observer;
import com.skliba.dpatterns.visitor.Diver;

import java.util.ArrayList;

public class Institution implements Observer {

    private String name;

    private ArrayList<Diver> diversInAnInstitution = new ArrayList<>();

    private float avgDepth;

    private float totalDepth;

    public Institution(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Diver> getDiversInAnInstitution() {
        return diversInAnInstitution;
    }

    public float getAvgDepth() {
        return avgDepth;
    }

    @Override
    public void update(Diver diver, int diveDepth) {
        diversInAnInstitution.add(diver);
        calculateAvgDepth(diveDepth);
    }

    private void calculateAvgDepth(int diveDepth) {
        totalDepth += diveDepth;
        avgDepth = totalDepth / diversInAnInstitution.size();
    }

    public int getNumOfDiversPerCategory(String categoryName) {
        int num = 0;
        for (Diver d : diversInAnInstitution) {
            if (d.getCertType().equals(categoryName)) {
                num++;
            }
        }
        return num;
    }
}
