package com.skliba.algorithms;

import com.skliba.dpatterns.singleton.DivingClub;
import com.skliba.dpatterns.visitor.Diver;
import com.skliba.helpers.ReportHelper;
import com.skliba.models.*;

import java.util.*;

public class MaxDepthAlgorithm implements Algorithm {

    private ArrayList<Divings> divingListWithJoinedDates;
    private Map<String, List<Diver>> certificateDiverMap = new LinkedHashMap<>();
    private List<Diver> diverList = (List<Diver>) DivingClub.getInstance().getDivers().clone();
    private List<Divings> initialDivingsList = (List<Divings>) DivingClub.getInstance().getDivings().clone();
    private ArrayList<Dive> outputDiveList = new ArrayList<>();

    @Override
    public ArrayList<Dive> setupAlgorithm() {
        initializeParams();
        createCertificateNameMap();
        makePairsAndTriplets();
        addDiversInformation();
        calculateSecurityMeasures();
        return outputDiveList;
    }

    private void addDiversInformation() {
        outputDiveList.forEach(ReportHelper::setDiverInformation);
    }

    /**
     * Initialization of the parameters to begin the algorithm, creation of lists etc.
     */
    private void initializeParams() {

        ArrayList<Divings> initialDivingList = (ArrayList<Divings>) DivingClub.getInstance().getDivings().clone();
        divingListWithJoinedDates = new ArrayList<>();

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
        for (Divings diving : initialDivingsList) {
            Dive dive = new Dive();
            dive.setDiveDepth(diving.getDiveDepth());
            List<Diver> allDiversInADive = dive.getAllDiversTogether();
            dive.setDiveDate(diving.getDiveDate());
            dive.setDiveTime(diving.getDiveTime());
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

    private Triplet findTriplet(Map<String, List<Diver>> certificateDiverMap) {
        Triplet triplet = new Triplet();
        Iterator mapIterator = certificateDiverMap.entrySet().iterator();
        while (mapIterator.hasNext()) {
            Map.Entry mapEntry = (Map.Entry) mapIterator.next();
            List<Diver> divers = (List<Diver>) mapEntry.getValue();
            if (divers.size() >= 2) {
                triplet.setFirstDiver(divers.get(0));
                divers.remove(0);
                triplet.setSecondDiver(divers.get(0));
                divers.remove(0);
                findThirdDiver(certificateDiverMap, (String) mapEntry.getKey(), triplet);

                break;
            } else if (divers.size() == 1) {
                triplet.setFirstDiver(divers.get(0));
                divers.remove(0);
                findSecondAndThirdDiver(certificateDiverMap, (String) mapEntry.getKey(), triplet);
            }
        }
        return triplet;
    }

    private void findSecondAndThirdDiver(Map<String, List<Diver>> certificateDiverMap, String key, Triplet triplet) {
        int currentKeyPosition = 0;
        for (int i = 0; i < DivingClub.getInstance().getCertificateList().size(); i++) {
            if (DivingClub.getInstance().getCertificateList().get(i).equals(key)) {
                currentKeyPosition = i;
                if (currentKeyPosition == DivingClub.getInstance().getCertificateList().size() - 1) {
                    currentKeyPosition = -1;
                }
            }
        }

        for (int i = currentKeyPosition + 1; i < DivingClub.getInstance().getCertificateList().size(); i++) {
            List<Diver> diverList = certificateDiverMap.get(DivingClub.getInstance().getCertificateList().get(i));
            if (diverList != null) {
                if (diverList.size() >= 2 && triplet.availableSlots() == 2) {
                    //Transfer the diver from the list to the triplet
                    triplet.setSecondDiver(diverList.get(0));
                    diverList.remove(0);
                    triplet.setThirdDiver(diverList.get(0));
                    diverList.remove(0);
                    return;
                } else if (diverList.size() == 1) {
                    if (triplet.getSecondDiver() == null) {
                        triplet.setSecondDiver(diverList.get(0));
                        diverList.remove(0);
                    } else {
                        triplet.setThirdDiver(diverList.get(0));
                        diverList.remove(0);
                    }
                    if (triplet.getSecondDiver() != null && triplet.getThirdDiver() != null) {
                        return;
                    }
                }
            }
        }

    }

    private void findThirdDiver(Map<String, List<Diver>> certificateDiverMap, String key, Triplet triplet) {
        int currentKeyPosition = 0;
        for (int i = 0; i < DivingClub.getInstance().getCertificateList().size(); i++) {
            if (DivingClub.getInstance().getCertificateList().get(i).equals(key)) {
                currentKeyPosition = i;
                if (currentKeyPosition == DivingClub.getInstance().getCertificateList().size() - 1) {
                    currentKeyPosition = -1;
                }
            }
        }

        for (int i = currentKeyPosition + 1; i < DivingClub.getInstance().getCertificateList().size(); i++) {
            List<Diver> diversOfTheSameCert = certificateDiverMap.get(DivingClub.getInstance().getCertificateList().get(i));
            if (diversOfTheSameCert != null && diversOfTheSameCert.size() > 0) {
                //Transfer the diver from the list to the triplet
                triplet.setThirdDiver(diversOfTheSameCert.get(0));
                diversOfTheSameCert.remove(0);
                return;
            }

            /**
             * If a valid triplet is not found, retry the search and randomly choose one
             */
            if (i == DivingClub.getInstance().getCertificateList().size() - 1) {
                i = 0;
            }
        }
    }

    private Pair findPair(Map<String, List<Diver>> certificateDiverMap) {
        Pair pair = new Pair();
        Iterator mapIterator = certificateDiverMap.entrySet().iterator();
        while (mapIterator.hasNext()) {
            Map.Entry mapEntry = (Map.Entry) mapIterator.next();
            List<Diver> divers = (List<Diver>) mapEntry.getValue();
            if (divers.size() >= 1) {
                pair.setFirstDiver(divers.get(0));
                divers.remove(0);
                findSecondDiver(certificateDiverMap, mapEntry.getKey(), pair);
                break;
            }
        }
        return pair;
    }

    private void findSecondDiver(Map<String, List<Diver>> certificateDiverMap, Object key, Pair pair) {
        int currentKeyPosition = 0;
        for (int i = 0; i < DivingClub.getInstance().getCertificateList().size(); i++) {
            if (DivingClub.getInstance().getCertificateList().get(i).equals(key)) {
                currentKeyPosition = i;
                if (currentKeyPosition == DivingClub.getInstance().getCertificateList().size() - 1) {
                    currentKeyPosition = -1;
                }
            }
        }

        for (int i = currentKeyPosition + 1; i < DivingClub.getInstance().getCertificateList().size(); i++) {
            List<Diver> diversOfTheSameCert = certificateDiverMap.get(DivingClub.getInstance().getCertificateList().get(i));
            if (diversOfTheSameCert != null && diversOfTheSameCert.size() > 0) {
                //Transfer the diver from the list to pair
                pair.setSecondDiver(diversOfTheSameCert.get(0));
                diversOfTheSameCert.remove(0);
                return;
            }

            /**
             * If a valid pair is not found, retry the search because it appears that a valid diver isn't found so we just randomly choose one
             */
            if (i == DivingClub.getInstance().getCertificateList().size() - 1) {
                i = 0;
            }
        }
    }

    private void calculateSecurityMeasures() {
        for (Dive dive : outputDiveList) {
            dive.calculateOverallSecurityMeasure();
        }
    }
}

