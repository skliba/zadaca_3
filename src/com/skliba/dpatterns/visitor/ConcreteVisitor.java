package com.skliba.dpatterns.visitor;

import com.skliba.models.DiverDiveInformation;
import com.skliba.dpatterns.observer.Agency;

import java.util.*;

public class ConcreteVisitor implements Visitor {

    private List<Diver> usedDivers = new ArrayList<>();
    private float overallAverageDistanceToMaxDepth;
    private int overallNumberOfDivers;
    private Map<String, Float> agencyRankingsMap = new LinkedHashMap<>();
    private TreeMap<String, Float> sortedMap;

    @Override
    public void visitDiver(Diver diver) {

        if (usedDivers.contains(diver)) {
            return;
        }

        overallNumberOfDivers++;

        float overallDepth = 0f;
        for (DiverDiveInformation diverDiveInformation : diver.getDiverDiveInformation()) {
            overallDepth += diverDiveInformation.getDiveDepth();
        }
        float averageDepth = overallDepth / diver.getDiverDiveInformation().size();
        overallAverageDistanceToMaxDepth += diver.getMaxDepth() - averageDepth;
        usedDivers.add(diver);
    }

    public float getAverageDiversEfficiency() {
        return overallAverageDistanceToMaxDepth / overallNumberOfDivers;

    }

    public TreeMap<String, Float> getSortedMap() {
        return sortedMap;
    }

    @Override
    public void visitAgency(Agency agency) {
        float totalRanking = 0;
        float avgRanking;
        for (Diver d : agency.getDiverInAgency()) {
            totalRanking += d.getCeritificateTypeAsInteger();
        }
        avgRanking = totalRanking / agency.getDiverInAgency().size();

        agencyRankingsMap.put(agency.getName(), avgRanking);
    }

}
