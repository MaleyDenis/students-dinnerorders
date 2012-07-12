package com.exadel.dinnerorders.entity;

import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class Menu {
    private Long id;
    private String cafeName;
    private Date dateStart;
    private Date dateEnd;
    private Map<Weekday, List<MenuItem>> items;

    public Menu(Long id, String cafeName, Date dateStart, Date dateEnd, Map<Weekday, List<MenuItem>> items) {
        this.id = id;
        this.cafeName = cafeName;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public String getCafeName() {
        return cafeName;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public Map<Weekday, List<MenuItem>> getItems() {
        if(items == null){
            items = new HashMap<Weekday, List<MenuItem>>();
            items.put(Weekday.MONDAY, new ArrayList<MenuItem>());
            items.put(Weekday.TUESDAY, new ArrayList<MenuItem>());
            items.put(Weekday.WEDNESDAY, new ArrayList<MenuItem>());
            items.put(Weekday.THURSDAY, new ArrayList<MenuItem>());
            items.put(Weekday.FRIDAY, new ArrayList<MenuItem>());
            items.put(Weekday.SATURDAY, new ArrayList<MenuItem>());
            items.put(Weekday.SUNDAY, new ArrayList<MenuItem>());
        }
        return items;
    }
}
