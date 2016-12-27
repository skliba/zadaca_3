package com.skliba.models;

import com.skliba.dpatterns.visitor.Diver;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DiverDiveInformation {

    private Date diveDateParticipation;
    private Date diveTimeParticipation;

    private ArrayList<Diver> partners;
    private int diveDepth;

    public DiverDiveInformation(Date diveDateParticipation, Date diveTimeParticipation, ArrayList<Diver> partners, int diveDepth) {
        this.diveDateParticipation = diveDateParticipation;
        this.diveTimeParticipation = diveTimeParticipation;
        this.partners = partners;
        this.diveDepth = diveDepth;
    }

    public String getDiveDateParticipation() {
        DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(diveDateParticipation);
    }


    public String getDiveTimeParticipationString() {
        DateFormat sdf = new SimpleDateFormat("hh:mm");
        return sdf.format(diveTimeParticipation);
    }

    public ArrayList<Diver> getPartners() {
        return partners;
    }

    public int getDiveDepth() {
        return diveDepth;
    }
}
