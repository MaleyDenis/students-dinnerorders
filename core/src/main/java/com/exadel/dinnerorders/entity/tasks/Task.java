package com.exadel.dinnerorders.entity.tasks;

import com.exadel.dinnerorders.service.DateUtils;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.concurrent.Callable;

public abstract class Task implements Callable<Boolean> {
    protected int minutes;
    protected int hours;
    protected int dayOfMonth;
    protected int month;
    protected int dayOfWeek;
    protected Timestamp lastExecutionTime;

    public int getMinutes() {
        return minutes;
    }

    public int getHours() {
        return hours;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public int getMonth() {
        return month;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Timestamp getLastExecutionTime() {
        return lastExecutionTime;
    }

    public boolean isTimeToServe() {
        boolean everyTimeFlag = isEveryTime();
        if (everyTimeFlag) {
            lastExecutionTime = new Timestamp(System.currentTimeMillis());
        }
        return everyTimeFlag || isAppropriateTime();
    }

    protected boolean isAppropriateTime() {
        boolean answer;
        answer = minutes == -1 || DateUtils.getMinutes() == minutes;
        answer = answer && (hours == -1 || DateUtils.getHours() == hours);
        answer = answer && (dayOfMonth == -1 || DateUtils.getDayOfMonth() == dayOfMonth);
        answer = answer && (month == -1 || DateUtils.getMonth() == month);
        answer = answer && (dayOfWeek == -1 || DateUtils.getDayOfWeek() == dayOfWeek);
        return answer && !isAlreadyExecuted();
    }

    protected boolean isAlreadyExecuted() {
        if (getLastExecutionTime() == null) {
            return false;
        }
        Calendar lastExecutionCalendar = Calendar.getInstance();
        lastExecutionCalendar.setTime(lastExecutionTime);
        boolean answer;
        answer = DateUtils.getMinutes() == lastExecutionCalendar.get(Calendar.MINUTE);
        int am_pm = lastExecutionCalendar.get(Calendar.AM_PM);
        answer = answer && DateUtils.getHours() == lastExecutionCalendar.get(Calendar.HOUR) + 12 * am_pm;
        answer = answer && DateUtils.getDayOfMonth() == lastExecutionCalendar.get(Calendar.DAY_OF_MONTH);
        answer = answer && DateUtils.getMonth() == lastExecutionCalendar.get(Calendar.MONTH) + 1;
        answer = answer && DateUtils.getDayOfWeek() == lastExecutionCalendar.get(Calendar.DAY_OF_WEEK) - 1;
        return answer;
    }

    protected boolean isEveryTime() {
        boolean answer;
        answer = minutes == -1;
        answer = answer && hours == -1;
        answer = answer && dayOfMonth == -1;
        answer = answer && month == -1;
        answer = answer && dayOfWeek == -1;
        return answer && !isAlreadyExecuted();
    }
}