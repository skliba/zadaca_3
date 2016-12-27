package com.skliba.models;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Divings {

    private Date diveDate;
    private String stringDiveDate;
    private Date diveTime;
    private String stringDiveTime;
    private int diveDepth;
    private int numberOfDivers;

    public Date getDiveDate() {
        return diveDate;
    }

    public void setDiveDate(String diveDate) {
        DateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        try {
            this.diveDate = sdf.parse(diveDate);
            this.stringDiveDate = sdf.format(this.diveDate);
        } catch (ParseException e) {
            //TODO another exception
            throw new IllegalArgumentException("Cannot parse the date you passed, are you sure its in the correct format? Passed date: "
                    + diveDate);
        }
    }

    public Date getDiveTime() {
        return diveTime;
    }

    public void setDiveTime(String diveTime) {
        DateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            this.diveTime = sdf.parse(diveTime);
            this.stringDiveTime = sdf.format(this.diveTime);
        } catch (ParseException e) {
            //TODO another exception
            throw new IllegalArgumentException("Cannot parse the time you passed, are you sure its in the correct format? Passed time: "
                    + diveDate);
        }
    }

    public int getDiveDepth() {
        return diveDepth;
    }

    public void setDiveDepth(String diveDepth) {
        int diveDepthNumber = Integer.parseInt(diveDepth);
        if (diveDepthNumber <= 40 && diveDepthNumber > 0) {
            this.diveDepth = diveDepthNumber;
        } else {
            throw new IllegalArgumentException("Dive depth cannot be set to: " + diveDepthNumber);
        }
    }

    public int getNumberOfDivers() {
        return numberOfDivers;
    }

    public void setNumberOfDivers(String divers) {
        int numberOfDivers = Integer.parseInt(divers);
        if (numberOfDivers > 0) {
            this.numberOfDivers = numberOfDivers;
        } else {
            throw new IllegalArgumentException("Number of divers cannot be less than 0, you passed: " + numberOfDivers);
        }
    }
}
