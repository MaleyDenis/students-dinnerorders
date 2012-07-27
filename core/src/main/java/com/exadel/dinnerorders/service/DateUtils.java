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
    public static final int DEFAULT_WORK_DAYS = 5;
    public static final int DAYS_IN_WEEK = 7;
    private static final long MILLISECONDS_IN_WEEK = DAYS_IN_WEEK * MILLISECONDS_IN_DAY;

    public static int getDateOfThisMonday() {
        Calendar calendar = Calendar.getInstance();
        long mondayTime = getCurrentMondayTime().getTime();
        calendar.setTimeInMillis(mondayTime);
        return calendar.get(Calendar.DATE);
    }

    public static int getMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static int getYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    public static int getDateOfThisFriday() {
        Calendar calendar = Calendar.getInstance();
        long mondayTime = getCurrentMondayTime().getTime();
        long fridayTime = mondayTime + MILLISECONDS_IN_DAY * (DEFAULT_WORK_DAYS - 1);
        calendar.setTimeInMillis(fridayTime);
        return calendar.get(Calendar.DATE);
    }

    public static Timestamp getCurrentMondayTime() {
        Calendar calendar = Calendar.getInstance();
        int firstDayOfWeek = calendar.getFirstDayOfWeek();
        int elapsedDays = calendar.get(Calendar.DAY_OF_WEEK) - firstDayOfWeek;
        return new Timestamp(System.currentTimeMillis() - elapsedDays * MILLISECONDS_IN_DAY);
    }

    public static Timestamp getCurrentFridayTime() {
        long mondayTime = getCurrentMondayTime().getTime();
        int currentHours = Calendar.getInstance().get(Calendar.HOUR);
        int am = Calendar.getInstance().get(Calendar.AM_PM);
        long leftHours = HOURS_IN_DAY - currentHours - 1 - HOURS_IN_DAY / 2 * am;
        return new Timestamp( mondayTime + MILLISECONDS_IN_DAY * (DEFAULT_WORK_DAYS - 1) + leftHours * MILLISECONDS_IN_HOUR);
    }

    public static Timestamp getCurrentTime() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static Timestamp getNextMondayTime() {
        Timestamp thisMonday = getCurrentMondayTime();
        return new Timestamp(thisMonday.getTime() + MILLISECONDS_IN_WEEK);
    }

    public static Timestamp getNextFridayTime() {
        Timestamp thisFriday = getCurrentFridayTime();
        return new Timestamp(thisFriday.getTime() + MILLISECONDS_IN_WEEK);
    }
}
