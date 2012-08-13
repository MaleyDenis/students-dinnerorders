package com.exadel.dinnerorders.entity;

import com.exadel.dinnerorders.entity.MenuItem;

import java.util.Comparator;

/**
 * User: Василий Силин
 * Date: 3.8.12
 */

public class WeekdayComparator implements Comparator{
    @Override
    public int compare(Object o1, Object o2) {
        MenuItem item = (MenuItem) o1;
        int o1Day = item.getWeekday().getN();
        item = (MenuItem) o2;
        int o2Day = item.getWeekday().getN();
        return (o1Day - o2Day);
    }
}
