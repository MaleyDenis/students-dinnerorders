package com.exadel.dinnerorders.entity;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.util.Date;

public class Menu {
    private final int id;
    private String cafeName;
    private Date dateStart;
    private Date dateEnd;
    private Map<Weekday, MenuItem> items;

    public Menu(int id, String cafeName, Date dateStart, Date dateEnd, Map<Weekday, MenuItem> items)
            throws IncorrectDataException {
        if(id < 0){
            throw new IncorrectDataException("Incorrect ID!!!");
        }
        if(cafeName == null){
            throw new IncorrectDataException("Indicate cafe name!!!");
        }
        if(cafeName.isEmpty()){
            throw new IncorrectDataException("Indicate cafe name!!!");
        }
        if(dateStart == null){
            throw new IncorrectDataException("Indicate date!!!");
        }
        if(dateStart.after(dateEnd)){
            throw new IncorrectDataException("Incorrect date!!! Start date, after end date!!!");
        }
        if(dateEnd == null){
            throw new IncorrectDataException("Indicate date!!!");
        }
        if(dateStart.after(dateEnd)){
            throw new IncorrectDataException("Incorrect date!!! Start date, after end date!!!");
        }
        if(items == null){
            throw new IncorrectDataException("Incorrect items map!!!");
        }
        if(items.isEmpty()){
            throw new IncorrectDataException("Items map is empty!!!");
        }
        this.id = id;
        this.cafeName = cafeName;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.items = items;
    }

    public Menu(int id, String cafeName, Date dateStart, Date dateEnd)throws IncorrectDataException {
        if(id < 0){
            throw new IncorrectDataException("Incorrect ID!!!");
        }
        if(cafeName == null){
            throw new IncorrectDataException("Indicate cafe name!!!");
        }
        if(cafeName.isEmpty()){
            throw new IncorrectDataException("Indicate cafe name!!!");
        }
        if(dateStart == null){
            throw new IncorrectDataException("Indicate date!!!");
        }
        if(dateStart.after(dateEnd)){
            throw new IncorrectDataException("Incorrect date!!! Start date, after end date!!!");
        }
        if(dateEnd == null){
            throw new IncorrectDataException("Indicate date!!!");
        }
        if(dateStart.after(dateEnd)){
            throw new IncorrectDataException("Incorrect date!!! Start date, after end date!!!");
        }
        this.id = id;
        this.cafeName = cafeName;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.items = new HashMap<Weekday, MenuItem>();
    }

    public int getId() {
        return id;
    }

    public String getCafeName() {
        return cafeName;
    }

    public void setCafeName(String cafeName)throws IncorrectDataException {
        if(cafeName == null){
            throw new IncorrectDataException("Indicate cafe name!!!");
        }
        if(cafeName.isEmpty()){
            throw new IncorrectDataException("Indicate cafe name!!!");
        }
        this.cafeName = cafeName;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart)throws IncorrectDataException {
        if(dateStart == null){
            throw new IncorrectDataException("Indicate date!!!");
        }
        if(dateStart.after(dateEnd)){
            throw new IncorrectDataException("Incorrect date!!! Start date, after end date!!!");
        }
        if(dateStart.before(new Date())){
            throw new IncorrectDataException("Incorrect date!!! Start date before now!!!");
        }
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd)throws IncorrectDataException {
        if(dateEnd == null){
            throw new IncorrectDataException("Indicate date!!!");
        }
        if(dateStart.after(dateEnd)){
            throw new IncorrectDataException("Incorrect date!!! Start date, after end date!!!");
        }
        this.dateEnd = dateEnd;
    }

    public Map<Weekday, MenuItem> getItems() {
        return Collections.unmodifiableMap(items);
    }

    public void setItems(Map<Weekday, MenuItem> items)throws IncorrectDataException {
        if(items == null){
            throw new IncorrectDataException("Incorrect items map!!!");
        }
        if(items.isEmpty()){
            throw new IncorrectDataException("Items map is empty!!!");
        }
        this.items = items;
    }

    public void removeItem(MenuItem item)throws IncorrectDataException{
        if(item == null){
            throw new IncorrectDataException("Indicate item!!!");
        }
        if(items.remove(item.getWeekday()) == null){
            throw new IncorrectDataException("com.exadel.menu.Menu have no this item!!!");
        }
    }
}
