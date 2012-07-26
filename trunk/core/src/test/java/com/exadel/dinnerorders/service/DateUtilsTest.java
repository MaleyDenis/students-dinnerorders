package com.exadel.dinnerorders.service;

import junit.framework.Assert;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Calendar;

public class DateUtilsTest {

    @Test
    public void testGetDateOfThisMonday() {
        int actualDate = DateUtils.getDateOfThisMonday();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(DateUtils.getCurrentMondayTime().getTime());
        int expectedDate = calendar.get(Calendar.DATE);
        Assert.assertEquals(expectedDate, actualDate);
    }

    @Test
    public void testGetMonth() {
        int actualMonth = DateUtils.getMonth();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(DateUtils.getCurrentTime().getTime());
        int expectedMonth = calendar.get(Calendar.MONTH) + 1;
        Assert.assertEquals(expectedMonth, actualMonth);
    }

    @Test
    public void testGetYear() {
        int actualYear = DateUtils.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(DateUtils.getCurrentTime().getTime());
        int expectedYear = calendar.get(Calendar.YEAR);
        Assert.assertEquals(expectedYear, actualYear);
    }

    @Test
    public void getDateOfThisFriday() {
        int actualDate = DateUtils.getDateOfThisFriday();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(DateUtils.getCurrentFridayTime().getTime());
        int expectedDate = calendar.get(Calendar.DATE);
        Assert.assertEquals(expectedDate, actualDate);
    }

    @Test
    public void testGetCurrentMondayDate() {
        Timestamp mondayDate = DateUtils.getCurrentMondayTime();
        Calendar actualCalendar = Calendar.getInstance();
        actualCalendar.setTimeInMillis(mondayDate.getTime());
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int upToMonday = Calendar.MONDAY - dayOfWeek;
        calendar.setTimeInMillis(calendar.getTimeInMillis() + upToMonday * DateUtils.MILLISECONDS_IN_DAY);
        Assert.assertEquals(actualCalendar.get(Calendar.MONTH), calendar.get(Calendar.MONTH));
        Assert.assertEquals(actualCalendar.get(Calendar.DATE), calendar.get(Calendar.DATE));
        Assert.assertEquals(actualCalendar.get(Calendar.YEAR), calendar.get(Calendar.YEAR));
        Assert.assertEquals(actualCalendar.get(Calendar.DAY_OF_WEEK), calendar.get(Calendar.DAY_OF_WEEK));
    }

    @Test
    public void testGetCurrentFridayDate() {
        Timestamp fridayDate = DateUtils.getCurrentFridayTime();
        Calendar actualCalendar = Calendar.getInstance();
        actualCalendar.setTimeInMillis(fridayDate.getTime());
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int upToFriday = Calendar.FRIDAY - dayOfWeek;
        calendar.setTimeInMillis(calendar.getTimeInMillis() + upToFriday * DateUtils.MILLISECONDS_IN_DAY);
        Assert.assertEquals(actualCalendar.get(Calendar.MONTH), calendar.get(Calendar.MONTH));
        Assert.assertEquals(actualCalendar.get(Calendar.DATE), calendar.get(Calendar.DATE));
        Assert.assertEquals(actualCalendar.get(Calendar.YEAR), calendar.get(Calendar.YEAR));
        Assert.assertEquals(actualCalendar.get(Calendar.DAY_OF_WEEK), calendar.get(Calendar.DAY_OF_WEEK));
    }

    @Test
    public void testGetNextMondayDate() {
        Timestamp mondayDate = DateUtils.getNextMondayTime();
        Calendar actualCalendar = Calendar.getInstance();
        actualCalendar.setTimeInMillis(mondayDate.getTime());
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int upToMonday = Calendar.MONDAY - dayOfWeek;
        calendar.setTimeInMillis(calendar.getTimeInMillis() +
                upToMonday * DateUtils.MILLISECONDS_IN_DAY + DateUtils.MILLISECONDS_IN_DAY * DateUtils.DAYS_IN_WEEK);
        Assert.assertEquals(actualCalendar.get(Calendar.MONTH), calendar.get(Calendar.MONTH));
        Assert.assertEquals(actualCalendar.get(Calendar.DATE), calendar.get(Calendar.DATE));
        Assert.assertEquals(actualCalendar.get(Calendar.YEAR), calendar.get(Calendar.YEAR));
        Assert.assertEquals(actualCalendar.get(Calendar.DAY_OF_WEEK), calendar.get(Calendar.DAY_OF_WEEK));
    }

    @Test
    public void testGetNextFridayDate() {
        Timestamp fridayDate = DateUtils.getNextFridayTime();
        Calendar actualCalendar = Calendar.getInstance();
        actualCalendar.setTimeInMillis(fridayDate.getTime());
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int upToFriday = Calendar.FRIDAY - dayOfWeek;
        calendar.setTimeInMillis(calendar.getTimeInMillis() +
                upToFriday * DateUtils.MILLISECONDS_IN_DAY + DateUtils.MILLISECONDS_IN_DAY * DateUtils.DAYS_IN_WEEK);
        Assert.assertEquals(actualCalendar.get(Calendar.MONTH), calendar.get(Calendar.MONTH));
        Assert.assertEquals(actualCalendar.get(Calendar.DATE), calendar.get(Calendar.DATE));
        Assert.assertEquals(actualCalendar.get(Calendar.YEAR), calendar.get(Calendar.YEAR));
        Assert.assertEquals(actualCalendar.get(Calendar.DAY_OF_WEEK), calendar.get(Calendar.DAY_OF_WEEK));
    }
}
