package com.skliba.helpers;

import com.skliba.dpatterns.factory.AlgorithmFactory;
import com.skliba.dpatterns.observer.Agency;
import com.skliba.dpatterns.singleton.DivingClub;
import com.skliba.dpatterns.visitor.ConcreteVisitor;
import com.skliba.dpatterns.visitor.Diver;
import com.skliba.models.*;

import java.util.*;

public class ReportHelper {

    public static void setDiverInformation(Dive dive) {
        ArrayList<Diver> divers = (ArrayList<Diver>) dive.getAllDiversTogether().clone();
        for (Diver diver : dive.getAllDiversTogether()) {
            int maxDepth = 0;
            divers.remove(diver);
            if (dive.getTriplets() != null) {
                for (Triplet triple : dive.getTriplets()) {
                    if (triple.isDiverInTriplet(diver.getName())) {
                        maxDepth = determineMaxDepthOfTriple(triple);
                    }
                }
            }
            if (dive.getPairs() != null) {
                for (Pair pair : dive.getPairs()) {
                    if (pair.isDiverInPair(diver.getName())) {
                        maxDepth = determineMaxDepthOfPair(pair);
                    }
                }
            }

            diver.addASingleDiverInfo(new DiverDiveInformation(dive.getDiveDate(), dive.getDiveTime(),
                    (ArrayList<Diver>) divers.clone(), maxDepth));
            divers.add(diver);
            for (Diver d : DivingClub.getInstance().getDivers()) {
                if (d.getName().equals(diver.getName()) && diver.getYearOfBirth() == d.getYearOfBirth()) {
                    d.setDiverDiveInformation(diver.getDiverDiveInformation());
                }
            }
        }
    }

    private static int determineMaxDepthOfTriple(Triplet triple) {
        int maxDepth = 0;
        String rank1 = String.valueOf(((Diver) triple.getFirstDiver()).getCertType().charAt(1));
        String mark1 = String.valueOf(((Diver) triple.getFirstDiver()).getCertType().charAt(0));

        String rank2 = String.valueOf(((Diver) triple.getFirstDiver()).getCertType().charAt(1));
        String mark2 = String.valueOf(((Diver) triple.getFirstDiver()).getCertType().charAt(0));

        String rank3 = String.valueOf(((Diver) triple.getFirstDiver()).getCertType().charAt(1));
        String mark3 = String.valueOf(((Diver) triple.getFirstDiver()).getCertType().charAt(0));

        if (Objects.equals(mark1, "R")) {
            switch (rank1) {
                case "0":
                    maxDepth = 3;
                    break;
                case "1":
                    maxDepth = 10;
                    break;
                case "2":
                    maxDepth = 20;
                    break;
                case "3":
                    maxDepth = 30;
                    break;
                case "4":
                case "5":
                    maxDepth = 40;
                    break;
                default:
                    break;
            }
        } else if (Objects.equals(mark2, "R")) {
            switch (rank2) {
                case "0":
                    maxDepth = 3;
                    break;
                case "1":
                    maxDepth = 10;
                    break;
                case "2":
                    maxDepth = 20;
                    break;
                case "3":
                    maxDepth = 30;
                    break;
                case "4":
                case "5":
                    maxDepth = 40;
                    break;
                default:
                    break;
            }
        } else if (Objects.equals(mark3, "R")) {
            switch (rank3) {
                case "0":
                    maxDepth = 3;
                    break;
                case "1":
                    maxDepth = 10;
                    break;
                case "2":
                    maxDepth = 20;
                    break;
                case "3":
                    maxDepth = 30;
                    break;
                case "4":
                case "5":
                    maxDepth = 40;
                    break;
                default:
                    break;
            }
        } else {
            maxDepth = 40;
        }

        return maxDepth;
    }

    private static int determineMaxDepthOfPair(Pair pair) {
        int maxDepth = 0;
        String rank1 = String.valueOf(((Diver) pair.getFirstDiver()).getCertType().charAt(1));
        String mark1 = String.valueOf(((Diver) pair.getFirstDiver()).getCertType().charAt(0));

        String rank2 = String.valueOf(((Diver) pair.getFirstDiver()).getCertType().charAt(1));
        String mark2 = String.valueOf(((Diver) pair.getFirstDiver()).getCertType().charAt(0));


        if (Objects.equals(mark1, "R")) {
            switch (rank1) {
                case "0":
                    maxDepth = 3;
                    break;
                case "1":
                    maxDepth = 10;
                    break;
                case "2":
                    maxDepth = 20;
                    break;
                case "3":
                    maxDepth = 30;
                    break;
                case "4":
                case "5":
                    maxDepth = 40;
                    break;
                default:
                    break;

            }
        } else if (Objects.equals(mark2, "R")) {
            switch (rank2) {
                case "0":
                    maxDepth = 3;
                    break;
                case "1":
                    maxDepth = 10;
                    break;
                case "2":
                    maxDepth = 20;
                    break;
                case "3":
                    maxDepth = 30;
                    break;
                case "4":
                case "5":
                    maxDepth = 40;
                    break;
                default:
                    break;
            }
        } else {
            maxDepth = 40;
        }

        return maxDepth;
    }

    public static ArrayList<Dive> findDivesWithSmallestSecurityMeasure(Map<String, ArrayList<Dive>> algorithmResultsMap) {
        DiveComparator diveComparator = new DiveComparator();
        ArrayList<Dive> outputList = new ArrayList<>();
        ArrayList<Dive> allAlgorithmDive = new ArrayList<>();
        for (int i = 0; i < DivingClub.getInstance().getDivings().size(); i++) {
            Dive randomDive = algorithmResultsMap.get(AlgorithmFactory.RANDOM).get(i);
            Dive maxPartnerDive = algorithmResultsMap.get(AlgorithmFactory.MAX_PARTNER).get(i);
            Dive maxDepthDive = algorithmResultsMap.get(AlgorithmFactory.MAX_DEPTH).get(i);

            allAlgorithmDive.add(randomDive);
            allAlgorithmDive.add(maxPartnerDive);
            allAlgorithmDive.add(maxDepthDive);

            Collections.sort(allAlgorithmDive, diveComparator);

            Dive smallestSecurityMeasureDive = allAlgorithmDive.get(0);

            //Set all security measures for the output
            smallestSecurityMeasureDive.setRandomSecurityMeasure(randomDive.getSecurityMeasure());
            smallestSecurityMeasureDive.setMaxDepthSecurityMeasure(maxDepthDive.getSecurityMeasure());
            smallestSecurityMeasureDive.setMaxPartnerSecurityMeasure(maxPartnerDive.getSecurityMeasure());

            outputList.add(smallestSecurityMeasureDive);
            DivingClub.getInstance().notifyObservers(smallestSecurityMeasureDive);
            allAlgorithmDive.clear();
        }
        ConcreteVisitor concreteVisitor = DivingClub.getInstance().getConcreteVisitor();
        for (Dive dive : outputList) {
            for (Diver diver : dive.getAllDiversTogether()) {
                diver.accept(concreteVisitor);
            }
        }

        for (com.skliba.dpatterns.observer.Observer o : DivingClub.getInstance().getObservers()) {
            if (o instanceof Agency) {
                concreteVisitor.visitAgency((Agency) o);
            }
        }

        concreteVisitor.sortRankingsMap();

        return outputList;
    }
}
