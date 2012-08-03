package com.exadel.dinnerorders.entity;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;

/**
 * User: Василий Силин
 * Date: 16.7.12
 */

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

    public void setId(Long id) {
        this.id = id;
    }

    public void addItem(MenuItem newItem){
        List<MenuItem> menuForDay = items.get(newItem.getWeekday());
        if(menuForDay == null){
            menuForDay = new ArrayList<MenuItem>();
            menuForDay.add(newItem);
            items.put(newItem.getWeekday(), menuForDay);
        }else{
            menuForDay.add(newItem);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || this.getClass() != o.getClass()){
            return false;
        }
        Menu menu = (Menu) o;
        if (!cafeName.equals(menu.cafeName)){
            return false;
        }
        if (!dateEnd.equals(menu.dateEnd)){
            return false;
        }
        if (!dateStart.equals(menu.dateStart)){
            return false;
        }
        if (!id.equals(menu.id)){
            return false;
        }
        for(int i = 0;i < 7;i++){ //check items
            List<MenuItem> thisItems = this.getItems().get(Weekday.getWeekday(i + 1));
            List<MenuItem> menuItems = menu.getItems().get(Weekday.getWeekday(i + 1));
            if(thisItems != null || menuItems != null) { //check on null
                if((thisItems == null && menuItems != null) || (menuItems == null && thisItems != null) ||
                        (!thisItems.equals(menuItems))){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + cafeName.hashCode();
        result = 31 * result + dateStart.hashCode();
        result = 31 * result + dateEnd.hashCode();
        result = 31 * result + items.hashCode();
        return result;
    }
}
