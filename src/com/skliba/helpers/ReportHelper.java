package com.skliba.helpers;

import com.skliba.dpatterns.singleton.Dive;
import com.skliba.dpatterns.singleton.DivingClub;
import com.skliba.dpatterns.mvc.model.Diver;
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

            diver.addASingleDiverInfo(new DiverDiveInformation(null, null,
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

}
