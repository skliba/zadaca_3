package com.skliba.parsers;

import com.skliba.dpatterns.singleton.DivingClub;
import com.skliba.dpatterns.mvc.model.Diver;

import java.util.ArrayList;

public class DiversParser extends Parser {

    private static final String SEPARATOR = ";";

    private static final int DATA_PER_ROW = 4;

    private ArrayList<Diver> divers = new ArrayList<>();


    @Override
    public void parseData(String line) {
        String[] diverData = line.split(SEPARATOR);

        if (diverData.length != DATA_PER_ROW) {
            throw new IllegalStateException("Formatting inside the file seems to be invalid, check if data is separated with a ';'");
        }

        Diver diver = new Diver();
        diver.setName(diverData[0]);
        diver.setAgencyName(diverData[1]);

        diver.setCertType(diverData[2]);
        diver.setYearOfBirth(Integer.parseInt(diverData[3]));
        divers.add(diver);
    }

    @Override
    void parseComplete() {
        DivingClub.getInstance().setDivers(divers);
    }
}
