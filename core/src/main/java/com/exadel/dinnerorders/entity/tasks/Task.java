package com.exadel.dinnerorders.entity.tasks;

import java.sql.Timestamp;
import java.util.concurrent.Callable;

public abstract class Task implements Callable<Boolean> {
    protected int minutes;
    protected int hours;
    protected int dayOfMonth;
    protected int month;
    protected int dayOfWeek;
    protected Timestamp lastExecutionTime;

    public abstract boolean isTimeToServe();

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
}