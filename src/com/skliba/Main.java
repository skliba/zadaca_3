package com.skliba;

import com.skliba.dpatterns.mvc.view.TerminalGearView;
import com.skliba.dpatterns.mvc.view.TerminalGearViewImpl;
import com.skliba.dpatterns.mvc.view.TerminalView;
import com.skliba.dpatterns.mvc.view.TerminalViewImpl;
import com.skliba.dpatterns.singleton.DivingClub;
import com.skliba.dpatterns.mvc.model.Diver;
import com.skliba.listeners.StageListener;
import com.skliba.parsers.Parser;
import com.skliba.dpatterns.singleton.Dive;
import com.skliba.dpatterns.singleton.TerminalData;
import com.skliba.parsers.DiversParser;
import com.skliba.parsers.GearParser;
import com.skliba.parsers.SpecialitesParser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Main implements StageListener {

    private static final String FILE_REGEX = "^.*\\.(txt)";

    private static final int REQUIRED_ARGS_NUMBER = 10;

    private static final int TEMPERATURE_LIMIT_FOR_DRY_SUIT = 10;

    public static void main(String[] args) {
        if (args != null && args.length != 0 && args.length == REQUIRED_ARGS_NUMBER) {
            Main main = new Main();
            main.checkArguments(args);
            main.createInitialDiverList(DivingClub.getInstance().getDivers(), args);
            main.beginDrawing();
        } else {
            throw new IllegalArgumentException("Invalid number of arguments sent you've sent: " + args.length);
        }
    }

    private void beginDrawing() {
        TerminalView terminalView = new TerminalViewImpl(this);
        terminalView.initView();
    }

    private void createInitialDiverList(List<Diver> divers, String[] arguments) {

        List<Diver> diversCapableForDive = new ArrayList<>();
        List<Diver> diversNotCapableForDive = new ArrayList<>();

        int diveDepth = Integer.parseInt(arguments[6]);
        int waterTemperature = Integer.parseInt(arguments[7]);
        boolean isNightDive = "1".equals(arguments[8]);
        int numberOfUnderwaterRecorders = Integer.parseInt(arguments[9]);

        for (Diver diver : divers) {
            if (isDiverCapableForDive(diver, diveDepth, waterTemperature, isNightDive, numberOfUnderwaterRecorders)) {
                diversCapableForDive.add(diver);
                numberOfUnderwaterRecorders--;
            } else {
                diversNotCapableForDive.add(diver);
            }
        }

        DivingClub.getInstance().setDiversNotCapableForDive(diversNotCapableForDive);
        DivingClub.getInstance().setDiversCapableForDive(diversCapableForDive);
    }

    private boolean isDiverCapableForDive(Diver diver, int diveDepth, int waterTemperature, boolean isNightDive, int numberOfUnderwaterRecorders) {
        if (diver.getMaxDepth() < diveDepth) {
            return false;
        }
        if (waterTemperature < TEMPERATURE_LIMIT_FOR_DRY_SUIT && !diver.canUseDrySuit()) {
            return false;
        }
        if (isNightDive && !diver.canPerformNightDive()) {
            return false;
        }
        if (numberOfUnderwaterRecorders > 0 && !diver.isUnderWaterPhotograph()) {
            return false;
        }
        return true;
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

    @Override
    public void onNewPhasePressed() {
        InventorySupplier inventorySupplier = new InventorySupplier();
        List<Diver> workingDivers = DivingClub.getInstance().getWorkingDiversList();

        for (Diver d : workingDivers) {
            d.setDiverInventoryLevel(DiverInventoryLevel.FULLY_EQUIPPED);
        }

        for (Diver workingDiver : workingDivers) {
            inventorySupplier.supplyDiverWithInventory(workingDiver);
            if (workingDiver.getDiverInventoryLevel() == DiverInventoryLevel.NOT_EQUIPPED) {
                workingDiver.getInventoryItems().clear();
            } else {
                workingDiver.reduceInventoryQuantity();
            }
        }

        TerminalGearView terminalGearView = new TerminalGearViewImpl();
        terminalGearView.initView();
    }
}
