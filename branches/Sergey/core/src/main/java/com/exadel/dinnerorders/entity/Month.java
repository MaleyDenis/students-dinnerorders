package com.exadel.dinnerorders.entity;

import java.util.HashMap;
import java.util.Map;

public enum Month {
    JANUARY,
    FEBRUARY,
    MARCH,
    APRIL,
    MAY,
    JUNE,
    JULY,
    AUGUST,
    SEPTEMBER,
    OCTOBER,
    NOVEMBER,
    DECEMBER;

    private static final Map<Month, Integer> MONTH_DAY_MAP = createMonthMap();
    private static HashMap<Month, Integer> createMonthMap() {
        HashMap<Month, Integer> map = new HashMap<Month, Integer>();
        map.put(JANUARY, 31);
        map.put(FEBRUARY, 28);
        map.put(MARCH, 31);
        map.put(APRIL, 30);
        map.put(MAY, 31);
        map.put(JUNE, 30);
        map.put(JULY, 31);
        map.put(AUGUST, 31);
        map.put(SEPTEMBER, 30);
        map.put(OCTOBER, 31);
        map.put(NOVEMBER, 30);
        map.put(DECEMBER, 31);
        return map;
    }

    public static int getDaysInMonth(Month month) {
        return MONTH_DAY_MAP.get(month);
    }

    public static String[] getMonths() {
        return new String[] {JANUARY.name(), FEBRUARY.name(),
                      MARCH.name(), APRIL.name(), MAY.name(),
                      JUNE.name(), JULY.name(), AUGUST.name(),
                      SEPTEMBER.name(), OCTOBER.name(), NOVEMBER.name(),
                      DECEMBER.name()};
    }

    public static Month getMonthByNumber(int index) {
        switch (index) {
            case 1:
                return JANUARY;
            case 2:
                return FEBRUARY;
            case 3:
                return MARCH;
            case 4:
                return APRIL;
            case 5:
                return MAY;
            case 6:
                return JUNE;
            case 7:
                return JULY;
            case 8:
                return AUGUST;
            case 9:
                return SEPTEMBER;
            case 10:
                return OCTOBER;
            case 11:
                return NOVEMBER;
            case 12:
                return DECEMBER;
            default:
                throw new IllegalArgumentException();
        }
    }
}
