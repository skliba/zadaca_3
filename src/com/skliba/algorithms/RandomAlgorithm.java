package com.skliba.algorithms;

import com.skliba.dpatterns.singleton.Dive;
import com.skliba.dpatterns.singleton.DivingClub;
import com.skliba.dpatterns.mvc.model.Diver;
import com.skliba.helpers.ReportHelper;
import com.skliba.models.*;

import java.util.*;

public class RandomAlgorithm implements Algorithm {

    private ArrayList<Divings> divingses = new ArrayList<>();
    private ArrayList<Divings> divingsArrayList = (ArrayList<Divings>) DivingClub.getInstance().getDivings().clone();
    private ArrayList<Dive> allDivings = new ArrayList<>();
    private ArrayList<Diver> allDiversInADive;
    private int seed;

    public RandomAlgorithm(int seedInt) {
        this.seed = seedInt;
    }

    @Override
    public ArrayList<Dive> setupAlgorithm() {
        initalizeParams();
        return allDivings;
    }

    private void initalizeParams() {
        int maxDiverNumber;

        for (int i = 0; i < divingsArrayList.size(); i++) {
            maxDiverNumber = divingsArrayList.get(i).getNumberOfDivers();

            boolean shouldReplace = false;
            boolean found = false;
            int foundReplacementAt = -1;

            if (!divingses.isEmpty()) {
                for (int j = 0; j < divingses.size(); j++) {
                    if (divingsArrayList.get(i).getDiveDate().equals(divingses.get(j).getDiveDate())) {
                        found = true;
                        if (maxDiverNumber > divingses.get(j).getNumberOfDivers()) {
                            shouldReplace = true;
                            foundReplacementAt = j;
                        }
                    }
                }
            }

            if (!found) {
                divingses.add(divingsArrayList.get(i));
                continue;
            }

            if (shouldReplace) {
                if (foundReplacementAt != -1) {
                    divingses.remove(foundReplacementAt);
                    divingses.add(divingsArrayList.get(i));
                }
            }
        }

        addDiversToDates();
        DivingClub.getInstance().setEachDiveData(allDivings);
    }

    private void addDiversToDates() {
        ArrayList<Divings> startDivings = (ArrayList<Divings>) DivingClub.getInstance().getDivings().clone();
        for (Divings dive : divingses) {
            ArrayList<Diver> divers = (ArrayList<Diver>) DivingClub.getInstance().getDivers().clone();
            generateDivers(dive.getNumberOfDivers(), dive.getDiveTime(), dive.getDiveDate(), divers, dive.getDiveDepth());
        }

        ArrayList<Dive> maxAllDivings = (ArrayList<Dive>) allDivings.clone();

        for (Divings dive : startDivings) {
            for (Dive d : maxAllDivings) {
                generateDivers(dive.getNumberOfDivers(), dive.getDiveTime(),
                        dive.getDiveDate(), d.getAllDiversTogether(), dive.getDiveDepth());
            }
        }
        for (Dive dive : allDivings) {
            ReportHelper.setDiverInformation(dive);
        }
    }

    private void generateDivers(int numberOfDivers, Date diveTime, Date diveDate, ArrayList<Diver> givenDivers, int diveDepth) {
        ArrayList<Triplet> triplets = new ArrayList<>();
        allDiversInADive = new ArrayList<>();
        ArrayList<Diver> divers = (ArrayList<Diver>) givenDivers.clone();
        Dive diveWithDivers = Dive.getInstance();
        int random = seed * new Random().nextInt(100);
        Collections.shuffle(givenDivers, new Random(seed * random));

        if (numberOfDivers % 2 == 1) {

            Triplet triplet = new Triplet<>();
            triplet.setFirstDiver(divers.get(0));
            triplet.setSecondDiver(divers.get(1));
            triplet.setThirdDiver(divers.get(2));

            allDiversInADive.add(divers.get(0));
            allDiversInADive.add(divers.get(1));
            allDiversInADive.add(divers.get(2));

            divers.remove(0);
            divers.remove(0);
            divers.remove(0);
            triplets.add(triplet);

            diveWithDivers.setTriplets(triplets);
            numberOfDivers = numberOfDivers - 3;

            diveWithDivers.setPairs(generatePairs(numberOfDivers, divers));
        } else {
            diveWithDivers.setPairs(generatePairs(numberOfDivers, divers));
        }
        diveWithDivers.setAllDiversTogether((ArrayList<Diver>) allDiversInADive.clone());
        diveWithDivers.setDiveDepth(diveDepth);
        allDivings.add(diveWithDivers);
        allDiversInADive.clear();
    }

    private ArrayList<Pair> generatePairs(int numberOfDivers, ArrayList<Diver> divers) {
        ArrayList<Pair> pairs = new ArrayList<>();
        for (int i = 0; i < numberOfDivers; i++) {
            Pair<Diver, Diver> pair = new Pair<>();
            pair.setFirstDiver(divers.get(i));
            pair.setSecondDiver(divers.get(i + 1));
            allDiversInADive.add(divers.get(i));
            allDiversInADive.add(divers.get(i + 1));
            pairs.add(pair);
            i++;
        }
        return pairs;
    }


}
