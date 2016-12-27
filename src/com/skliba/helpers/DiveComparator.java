package com.skliba.helpers;

import com.skliba.models.Dive;

import java.util.Comparator;

public class DiveComparator implements Comparator<Dive> {

    @Override
    public int compare(Dive o1, Dive o2) {
        return Float.compare(o1.getSecurityMeasure(), o2.getSecurityMeasure());
    }
}
