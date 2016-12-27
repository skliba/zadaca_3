package com.skliba.helpers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private static final DateFormat timeFormat = new SimpleDateFormat("hh:mm");

    public static String getTimeAsString(Date date) {
        return timeFormat.format(date);
    }
}
