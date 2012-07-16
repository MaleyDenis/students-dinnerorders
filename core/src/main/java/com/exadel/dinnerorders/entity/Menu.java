package com.exadel.dinnerorders.entity;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;

public class Menu {
    private Long id;
    private String cafeName;
    private Timestamp dateStart;
    private Timestamp dateEnd;
    private Map<Weekday, List<MenuItem>> items;

    public Menu(Long id, String cafeName, Timestamp dateStart, Timestamp dateEnd, Map<Weekday, List<MenuItem>> items) {
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

    public Timestamp getDateStart() {
        return dateStart;
    }

    public Timestamp getDateEnd() {
        return dateEnd;
    }

    public Map<Weekday, List<MenuItem>> getItems() {
        if(items == null){
            items = new HashMap<Weekday, List<MenuItem>>();
        }
        return items;
    }
}
