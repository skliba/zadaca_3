package com.skliba.helpers;

import java.util.*;

public class ValueComparator implements Comparator {

    Map<String, Float> map = new LinkedHashMap<>();

    public ValueComparator(Map<String, Float> map) {
        this.map.putAll(map);
    }

    /**
     * We're returning a negative value because we want the map to be ordered in descending order
     * @param s1
     * @param s2
     * @return
     */
    @Override
    public int compare(Object s1, Object s2) {
        return -Float.compare(map.get(s1), map.get(s2));
    }
}
