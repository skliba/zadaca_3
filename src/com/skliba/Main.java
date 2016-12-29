package com.skliba;

import com.skliba.parsers.Parser;
import com.skliba.dpatterns.singleton.Dive;
import com.skliba.dpatterns.singleton.TerminalData;
import com.skliba.parsers.DiversParser;
import com.skliba.parsers.GearParser;
import com.skliba.parsers.SpecialitesParser;

import java.util.regex.Pattern;

public class Main {

    private static final String FILE_REGEX = "^.*\\.(txt)";

    private static final int REQUIRED_ARGS_NUMBER = 10;

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
                default:
                    break;
            }
        } else {
            throw new IllegalArgumentException("Invalid first argument passed, should be a filename that matches: '" + FILE_REGEX +
                    "' and you sent: '" + arg + "'");
        }
    }
}
