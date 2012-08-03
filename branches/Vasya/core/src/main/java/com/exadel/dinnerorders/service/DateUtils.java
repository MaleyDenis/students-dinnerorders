package com.exadel.dinnerorders.service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

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
    public static final long MILLISECONDS_IN_MINUTE = MILLISECONDS_IN_SECOND * SECONDS_IN_MINUTE;

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
        calendar.setTimeInMillis(System.currentTimeMillis() - elapsedDays * MILLISECONDS_IN_DAY);
        calendar.set(Calendar.AM_PM, 0);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    public static Timestamp getCurrentFridayTime() {
        long mondayTime = getCurrentMondayTime().getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mondayTime + MILLISECONDS_IN_DAY * (DEFAULT_WORK_DAYS - 1));
        calendar.set(Calendar.HOUR, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTimeInMillis());
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

    public static long convertToMillis(long value, TimeUnit fromValue) {
        switch (fromValue) {
            case DAYS:
                return value * DateUtils.MILLISECONDS_IN_DAY;
            case HOURS:
                return value * DateUtils.MILLISECONDS_IN_HOUR;
            case MINUTES:
                return value * DateUtils.MILLISECONDS_IN_MINUTE;
            case SECONDS:
                return value * DateUtils.MILLISECONDS_IN_SECOND;
            case MILLISECONDS:
                return value;
            case MICROSECONDS:
                return (long)(value * 10e-3f);
            case NANOSECONDS:
                return (long)(value * 10e-9f);
            default:
                return 0;
        }
    }
}
