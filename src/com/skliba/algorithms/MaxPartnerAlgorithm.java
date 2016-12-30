package com.skliba.algorithms;

import com.skliba.helpers.ReportHelper;
import com.skliba.dpatterns.singleton.Dive;
import com.skliba.dpatterns.mvc.model.Diver;
import com.skliba.dpatterns.singleton.DivingClub;
import com.skliba.models.Divings;
import com.skliba.models.Pair;
import com.skliba.models.Triplet;

import java.util.*;

public class MaxPartnerAlgorithm implements Algorithm {

    private List<Divings> initialDivingList = (List<Divings>) DivingClub.getInstance().getDivings().clone();
    private List<Diver> diverList = (List<Diver>) DivingClub.getInstance().getDivers().clone();
    private ArrayList<Divings> divingListWithJoinedDates = new ArrayList<>();
    private Map<String, List<Diver>> certificateDiverMap = new HashMap<>();
    private List<Dive> outputDiveList = new ArrayList<>();

    @Override
    public ArrayList<Dive> setupAlgorithm() {
        initializeParams();
        createCertificateNameMap();
        makePairsAndTriplets();
        addDiversInformation();

        return (ArrayList<Dive>) outputDiveList;
    }

    private void addDiversInformation() {
        outputDiveList.forEach(ReportHelper::setDiverInformation);
    }

    private void initializeParams() {
        int maxDiverNumber;

        for (int i = 0; i < initialDivingList.size(); i++) {
            maxDiverNumber = initialDivingList.get(i).getNumberOfDivers();

            boolean shouldReplace = false;
            boolean found = false;
            int foundReplacementAt = -1;

            if (!divingListWithJoinedDates.isEmpty()) {
                for (int j = 0; j < divingListWithJoinedDates.size(); j++) {
                    if (initialDivingList.get(i).getDiveDate().equals(divingListWithJoinedDates.get(j).getDiveDate())) {
                        found = true;
                        if (maxDiverNumber > divingListWithJoinedDates.get(j).getNumberOfDivers()) {
                            shouldReplace = true;
                            foundReplacementAt = j;
                        }
                    }
                }
            }

            if (!found) {
                divingListWithJoinedDates.add(initialDivingList.get(i));
                continue;
            }

            if (shouldReplace) {
                if (foundReplacementAt != -1) {
                    divingListWithJoinedDates.remove(foundReplacementAt);
                    divingListWithJoinedDates.add(initialDivingList.get(i));
                }
            }
        }
    }

    private void createCertificateNameMap() {
        certificateDiverMap.clear();
        Collections.shuffle(diverList);
        for (Diver diver : diverList) {
            if (certificateDiverMap.containsKey(diver.getCertType())) {
                certificateDiverMap.get(diver.getCertType()).add(diver);
            } else {
                List<Diver> diversWithSameCertificate = new ArrayList<>();
                diversWithSameCertificate.add(diver);
                certificateDiverMap.put(diver.getCertType(), diversWithSameCertificate);
            }
        }
    }

    private void makePairsAndTriplets() {
        for (Divings diving : initialDivingList) {
            Dive dive = Dive.getInstance();
            dive.setDiveDepth(diving.getDiveDepth());
            List<Diver> allDiversInADive = dive.getAllDiversTogether();
            Map<String, List<Diver>> certificateDiverMapCopy = (Map<String, List<Diver>>) ((HashMap) certificateDiverMap).clone();
            int numberOfAddedDivers = 0;
            if (diving.getNumberOfDivers() % 2 != 0) {
                Triplet triplet = findTriplet(certificateDiverMapCopy);
                dive.getTriplets().add(triplet);
                numberOfAddedDivers += 3;
                allDiversInADive.add((Diver) triplet.getFirstDiver());
                allDiversInADive.add((Diver) triplet.getSecondDiver());
                allDiversInADive.add((Diver) triplet.getThirdDiver());
            }
            while (diving.getNumberOfDivers() > numberOfAddedDivers) {
                Pair pair = findPair(certificateDiverMapCopy);
                dive.getPairs().add(pair);
                numberOfAddedDivers += 2;
                allDiversInADive.add((Diver) pair.getFirstDiver());
                allDiversInADive.add((Diver) pair.getSecondDiver());
            }
            outputDiveList.add(dive);
            createCertificateNameMap();
        }
    }

    private Pair findPair(Map<String, List<Diver>> certificateDiverMap) {
        Pair pair = new Pair();
        boolean pairFound = false;
        Iterator mapIterator = certificateDiverMap.entrySet().iterator();
        while (mapIterator.hasNext()) {
            Map.Entry mapEntry = (Map.Entry) mapIterator.next();
            List<Diver> divers = (List<Diver>) mapEntry.getValue();
            if (divers.size() >= 2) {
                pairFound = true;
                pair.setFirstDiver(divers.get(0));
                pair.setSecondDiver(divers.get(1));
                divers.remove(0);
                divers.remove(0);
                if (divers.isEmpty()) {
                    mapIterator.remove();
                }
                break;
            }
        }
        if (!pairFound) {
            pair = findBestAvailablePair(certificateDiverMap);
        }
        return pair;
    }

    private Pair findBestAvailablePair(Map<String, List<Diver>> certificateDiverMap) {
        Pair pair = new Pair();
        for (List<Diver> diverList : certificateDiverMap.values()) {
            if (pair.getFirstDiver() == null) {
                pair.setFirstDiver(diverList.get(0));
                continue;
            }
            if (pair.getSecondDiver() == null) {
                pair.setSecondDiver(diverList.get(0));
                break;
            }
        }
        return pair;
    }


    private Triplet findTriplet(Map<String, List<Diver>> certificateDiverMap) {
        Triplet triplet = new Triplet();
        boolean tripletFound = false;
        Iterator mapIterator = certificateDiverMap.entrySet().iterator();
        while (mapIterator.hasNext()) {
            Map.Entry mapEntry = (Map.Entry) mapIterator.next();
            List<Diver> divers = (List<Diver>) mapEntry.getValue();
            if (divers.size() >= 3) {
                tripletFound = true;
                triplet.setFirstDiver(divers.get(0));
                divers.remove(0);
                triplet.setSecondDiver(divers.get(0));
                divers.remove(0);
                triplet.setThirdDiver(divers.get(0));
                divers.remove(0);
                if (divers.isEmpty()) {
                    mapIterator.remove();
                }
                break;
            }
        }

        if (!tripletFound) {
            triplet = findBestAvailableTriplet(certificateDiverMap);
        }
        return triplet;
    }

    private Triplet findBestAvailableTriplet(Map<String, List<Diver>> certificateDiverMap) {
        Triplet triplet = new Triplet();
        for (List<Diver> diverList : certificateDiverMap.values()) {
            if (triplet.getFirstDiver() == null) {
                triplet.setFirstDiver(diverList.get(0));
                continue;
            }
            if (triplet.getSecondDiver() == null) {
                triplet.setSecondDiver(diverList.get(0));
                continue;
            }
            if (triplet.getThirdDiver() == null) {
                triplet.setThirdDiver(diverList.get(0));
                break;
            }
        }
        return triplet;
    }

}
