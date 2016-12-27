package com.skliba;

import com.skliba.algorithms.Algorithm;
import com.skliba.dpatterns.factory.AlgorithmFactory;
import com.skliba.dpatterns.factory.AlgorithmInjector;
import com.skliba.helpers.FileUtil;
import com.skliba.helpers.ReportHelper;
import com.skliba.models.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Main {

    private static final String FILE_REGEX = "^.*\\.(txt)";

    private static final String MAX_DEPTH = "MaxDepth";

    private static final String MAX_PARTNER = "MaxPartner";

    private static final String RANDOM = "Random";

    private static final int REQUIRED_ARGS_NUMBER = 10;

    private String outputFileName;

    private Map<String, ArrayList<Dive>> algoritmOutputMap = new HashMap<>();

    private int numberOfRows;

    private int numberOfColumns;

    private int containerRowsNumber;

    public static void main(String[] args) {
        if (args != null && args.length != 0 && args.length == REQUIRED_ARGS_NUMBER) {
            Main main = new Main();
            main.checkArguments(args);
            main.checkFileParam(args[1], 1);
            main.checkFileParam(args[2], 2);
            main.checkFileParam(args[6], 6);
            main.checkAlgorithms(args[3], args[0], args[4], args[5]);
        } else {
            throw new IllegalArgumentException("Invalid number of arguments sent");
        }
    }

    private void checkArguments(String[] args) {
        numberOfRows = Integer.parseInt(args[0]);
        numberOfColumns = Integer.parseInt(args[1]);
        containerRowsNumber = Integer.parseInt(args[2]);

        createParser(args[3], 3);
    }

    private void createParser(String arg, int argNum) {
        
    }

    private void checkFileParam(String arg, int argNum) {
        Pattern p = Pattern.compile(FILE_REGEX);

        if (p.matcher(arg).matches()) {
            switch (argNum) {
                case 1:
                    File inputFile = new File(arg);
                    if (inputFile.exists()) {
                        FileUtil.readFileAndAddDivers(inputFile);
                    }
                    break;
                case 2:
                    File divesFile = new File(arg);
                    if (divesFile.exists()) {
                        FileUtil.readFileAndAddDates(divesFile);
                    }
                    break;
                case 6:
                    outputFileName = arg;
                    break;
                default:
                    break;
            }
        } else {
            throw new IllegalArgumentException("Invalid first argument passed, should be a filename that matches: '" + FILE_REGEX +
                    "' and you sent: '" + arg + "'");
        }
    }

    private void checkAlgorithms(String algorithm1, String seed, String algorithm2, String algorithm3) {

        /**
         * Check if there are at least two same algorithms out of three - if so, throw and exception
         */
        if (algorithm1.equals(algorithm2)) {
            throw new IllegalArgumentException("Invalid names of algorithms passed");
        } else if (algorithm1.equals(algorithm3)) {
            throw new IllegalArgumentException("Invalid names of algorithms passed");
        } else if (algorithm2.equals(algorithm3)) {
            throw new IllegalArgumentException("Invalid names of algorithms passed");
        }

        if (algorithm1.equals(RANDOM) || algorithm1.equals(MAX_PARTNER) || algorithm1.equals(MAX_DEPTH)) {
            activateAlgorithm(algorithm1, seed);
        }

        if (algorithm2.equals(RANDOM) || algorithm2.equals(MAX_PARTNER) || algorithm2.equals(MAX_DEPTH)) {
            activateAlgorithm(algorithm2, seed);
        }

        if (algorithm3.equals(RANDOM) || algorithm3.equals(MAX_PARTNER) || algorithm3.equals(MAX_DEPTH)) {
            activateAlgorithm(algorithm3, seed);
        }

        writeReport(ReportHelper.findDivesWithSmallestSecurityMeasure(algoritmOutputMap));
    }

    private void activateAlgorithm(String algName, String seed) {
        int seedInt = Integer.parseInt(seed);
        AlgorithmInjector injector = new AlgorithmFactory();
        Algorithm algorithm = injector.inject(algName, seedInt);
        algoritmOutputMap.put(algName, algorithm.setupAlgorithm());

    }

    private void writeReport(ArrayList<Dive> dives) {
        FileUtil.writeReport(outputFileName, dives);
    }
}
