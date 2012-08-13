package com.exadel.dinnerorders.entity;

/**
 * User: Василий Силин
 * Date: 16.7.12
 */

public enum Weekday {
    MONDAY(1),
    TUESDAY(2),
    WEDNESDAY(3),
    THURSDAY(4),
    FRIDAY(5),
    SATURDAY(6),
    SUNDAY(7);

    private int n;

    private Weekday(int n) {
        this.n = n;
    }

    public static Weekday getWeekday(int i){
        switch(i){
            case 1:{
                return Weekday.MONDAY;
            }
            case 2:{
                return Weekday.TUESDAY;
            }
            case 3:{
                return Weekday.WEDNESDAY;
            }
            case 4:{
                return Weekday.THURSDAY;
            }
            case 5:{
                return Weekday.FRIDAY;
            }
            case 6:{
                return Weekday.SATURDAY;
            }
            case 7:{
                return Weekday.SUNDAY;
            }
            default: return null;
        }
    }

    public int getN() {
        return n;
    }
}
