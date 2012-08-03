package com.exadel.dinnerorders.entity;

import java.util.Scanner;
import java.util.regex.Pattern;

public class ParameterContainer {
    private int minutes;
    private int hours;
    private int daysOfMonth;
    private int month;
    private int dayOfWeek;
    private String className;

    public ParameterContainer(Scanner scanner) {
        parseMinutes(scanner.next());
        parseHours(scanner.next());
        parseDaysOfMonth(scanner.next());
        parseMonth(scanner.next());
        parseDaysOfWeek(scanner.next());
        className = scanner.next(Pattern.compile("[\\S]{1,}"));
    }

    private void parseMinutes(String strValue) {
        if (strValue.charAt(0) == '*') {
            minutes = -1;
        } else {
            minutes = Integer.parseInt(strValue);
        }
    }

    private void parseHours(String strValue) {
        if (strValue.charAt(0) == '*') {
            hours = -1;
        } else {
            hours = Integer.parseInt(strValue);
        }
    }

    private void parseDaysOfMonth(String strValue) {
        if (strValue.charAt(0) == '*') {
            daysOfMonth = -1;
        } else {
            daysOfMonth = Integer.parseInt(strValue);
        }
    }

    private void parseMonth(String strValue) {
        if (strValue.charAt(0) == '*') {
            month = -1;
        } else {
            month = Integer.parseInt(strValue);
        }
    }

    private void parseDaysOfWeek(String strValue) {
        if (strValue.charAt(0) == '*') {
            dayOfWeek = -1;
        } else {
            dayOfWeek = Integer.parseInt(strValue);
        }
    }

    public String getClassName() {
        return className;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getHours() {
        return hours;
    }

    public int getDaysOfMonth() {
        return daysOfMonth;
    }

    public int getMonth() {
        return month;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }
}
