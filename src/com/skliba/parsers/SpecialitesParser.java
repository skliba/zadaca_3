package com.skliba.parsers;

import com.skliba.SpecialDivingSkill;
import com.skliba.dpatterns.singleton.DivingClub;
import com.skliba.dpatterns.visitor.Diver;

import java.util.ArrayList;

public class SpecialitesParser extends Parser {

    private static final String SEPARATOR = ";";

    private static final int DATA_PER_ROW = 2;

    @Override
    public void parseData(String line) {
        String[] specialties = line.split(SEPARATOR);

        if (specialties.length != DATA_PER_ROW) {
            return;
        }

        ArrayList<Diver> divers = DivingClub.getInstance().getDivers();

        for (Diver diver : divers) {
            if (diver.getName().equals(specialties[0])) {
                diver.addSpecificDivingSkill(SpecialDivingSkill.getFromString(specialties[1]));
            }
        }
    }

    @Override
    void parseComplete() {

    }
}
