package com.skliba;

import com.skliba.algorithms.Algorithm;
import com.skliba.dpatterns.factory.AlgorithmFactory;
import com.skliba.dpatterns.factory.AlgorithmInjector;
import com.skliba.parsers.Parser;
import com.skliba.dpatterns.singleton.Dive;
import com.skliba.dpatterns.singleton.TerminalData;
import com.skliba.parsers.DiversParser;
import com.skliba.parsers.GearParser;
import com.skliba.parsers.SpecialitesParser;

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
        } else {
            throw new IllegalArgumentException("Invalid number of arguments sent you've sent: " + args.length);
        }
    }

    private void checkArguments(String[] args) {
        TerminalData.getInstance().setData(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        Dive.getInstance().setDiveParams(Integer.parseInt(args[6]), Integer.parseInt(args[7]),
                Integer.parseInt(args[8]), Integer.parseInt(args[9]));

        createParser(args[3], 3);
        createParser(args[4], 4);
        createParser(args[5], 5);
    }

    private void createParser(String arg, int argNum) {
        Pattern p = Pattern.compile(FILE_REGEX);

        if (p.matcher(arg).matches()) {
            switch (argNum) {
                case 3:
                    Parser diversParser = new DiversParser();
                    diversParser.readFile(arg);
                    break;
                case 4:
                    Parser specialitiesParser = new SpecialitesParser();
                    specialitiesParser.readFile(arg);
                    break;
                case 5:
                    Parser gearParser = new GearParser();
                    gearParser.readFile(arg);
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

    }

    private void activateAlgorithm(String algName, String seed) {
        int seedInt = Integer.parseInt(seed);
        AlgorithmInjector injector = new AlgorithmFactory();
        Algorithm algorithm = injector.inject(algName, seedInt);
        algoritmOutputMap.put(algName, algorithm.setupAlgorithm());
    }
}
