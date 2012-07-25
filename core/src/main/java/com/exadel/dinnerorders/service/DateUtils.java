package com.exadel.dinnerorders.service;

import java.sql.Timestamp;
import java.util.Calendar;

public class DateUtils {
    public static final long MILLISECONDS_IN_SECOND = 1000;
    public static final long SECONDS_IN_MINUTE = 60;
    public static final long MINUTES_IN_HOUR = 60;
    public static final long HOURS_IN_DAY = 24;
    public static final long MINUTES_IN_DAY = HOURS_IN_DAY * MINUTES_IN_HOUR;
    public static final long SECONDS_IN_DAY = MINUTES_IN_DAY * SECONDS_IN_MINUTE;
    public static final long SECONDS_IN_HOUR = MINUTES_IN_HOUR * SECONDS_IN_MINUTE;
    public static final long MILLISECONDS_IN_HOUR = MILLISECONDS_IN_SECOND * SECONDS_IN_HOUR;
    public static final long MILLISECONDS_IN_DAY = SECONDS_IN_DAY * MILLISECONDS_IN_SECOND;
    public static final long DEFAULT_TIME_SHIFT = 9 * MILLISECONDS_IN_HOUR;
    public static final int DEFAULT_WORK_DAYS = 5;
    private static final long MILLISECONDS_IN_WEEK = 7 * MILLISECONDS_IN_DAY;

    private static Calendar calendar;

    static {
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis() - DEFAULT_TIME_SHIFT);
    }

    public static int getDateOfFirstDayOfWeek() {
        int firstDayOfWeek = calendar.getFirstDayOfWeek();
        int elapsedDays = calendar.get(Calendar.DAY_OF_WEEK) - firstDayOfWeek;
        return calendar.get(Calendar.DATE) - elapsedDays;
    }

    public static int getMonth() {
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static int getYear() {
        return calendar.get(Calendar.YEAR);
    }

    public static int getDateOfLastDayOfWeek() {
        int firstDayOfWeek = calendar.getFirstDayOfWeek();
        int leftDays = calendar.get(Calendar.DAY_OF_WEEK) - firstDayOfWeek + DEFAULT_WORK_DAYS - 2;
        return calendar.get(Calendar.DATE) + leftDays;
    }

    public static Timestamp getCurrentWeekFirstDate() {
        int firstDayOfWeek = calendar.getFirstDayOfWeek();
        int elapsedDays = calendar.get(Calendar.DAY_OF_WEEK) - firstDayOfWeek;
        return new Timestamp(System.currentTimeMillis() - DEFAULT_TIME_SHIFT - elapsedDays * MILLISECONDS_IN_DAY);
    }

    public static Timestamp getCurrentWeekLastDate() {
        int firstDayOfWeek = calendar.getFirstDayOfWeek();
        int leftDays = calendar.get(Calendar.DAY_OF_WEEK) - firstDayOfWeek + DEFAULT_WORK_DAYS - 2;
        return new Timestamp(System.currentTimeMillis() - DEFAULT_TIME_SHIFT + MILLISECONDS_IN_DAY * leftDays);
    }

    public static Timestamp getCurrentTime() {
        return new Timestamp(System.currentTimeMillis() - DEFAULT_TIME_SHIFT);
    }

    public static Timestamp getNextWeekFirstDate() {
        int firstDayOfWeek = calendar.getFirstDayOfWeek();
        int elapsedDays = calendar.get(Calendar.DAY_OF_WEEK) - firstDayOfWeek;
        return new Timestamp(System.currentTimeMillis() -
                DEFAULT_TIME_SHIFT - elapsedDays * MILLISECONDS_IN_DAY + MILLISECONDS_IN_WEEK);
    }

    public static Timestamp getNextWeekLastDate() {
        int firstDayOfWeek = calendar.getFirstDayOfWeek();
        int leftDays = calendar.get(Calendar.DAY_OF_WEEK) - firstDayOfWeek + DEFAULT_WORK_DAYS - 2;
        return new Timestamp(System.currentTimeMillis() -
                DEFAULT_TIME_SHIFT + MILLISECONDS_IN_DAY * leftDays + MILLISECONDS_IN_WEEK);
    }
}
