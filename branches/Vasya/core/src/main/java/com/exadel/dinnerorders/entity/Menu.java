package com.exadel.dinnerorders.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.Transient;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;

/**
 * User: Василий Силин
 * Date: 16.7.12
 * I have big trouble with Map<Weekday, List<MenuItem>> and Hibernate. -> My solution is new field: List<MenuItem>
 */

@Entity
@Table(name = "menu", catalog = "dinnerorders")
public class Menu {
    private Long id;
    private String cafeName;
    private Timestamp dateStart;
    private Timestamp dateEnd;
    private Map<Weekday, List<MenuItem>> items;
    private List<MenuItem> allItems;

    public Menu () {

    }

    public Menu(Long id, String cafeName, Timestamp dateStart, Timestamp dateEnd, Map<Weekday, List<MenuItem>> items) {
        this.id = id;
        this.cafeName = cafeName;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.items = items;
        allItems = new ArrayList<MenuItem>();
        for(int i = 0;i < 7;i++){
            if(items.get(Weekday.getWeekday(i + 1)) != null){
                allItems.addAll(items.get(Weekday.getWeekday(i + 1)));
            }
        }
    }

    @Id
    @Column(name = "menu_id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "cafename", unique = false, nullable = false)
    public String getCafeName() {
        return cafeName;
    }

    @Column(name = "date_start", unique = false, nullable = false)
    public Timestamp getDateStart() {
        return dateStart;
    }

    @Column(name = "date_end", unique = false, nullable = false)
    public Timestamp getDateEnd() {
        return dateEnd;
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "menu_menuitem", catalog = "dinnerorders", joinColumns = { @JoinColumn(name = "menu_id")},
            inverseJoinColumns = { @JoinColumn(name = "menuitem_id")})
    public List<MenuItem> getAllItems() {
        return allItems;
    }

    @Transient
    public Map<Weekday, List<MenuItem>> getItems() {
        if(items == null){
            items = new HashMap<Weekday, List<MenuItem>>();
        }
        return items;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCafeName(String cafeName) {
        this.cafeName = cafeName;
    }

    public void setDateStart(Timestamp dateStart) {
        this.dateStart = dateStart;
    }

    public void setDateEnd(Timestamp dateEnd) {
        this.dateEnd = dateEnd;
    }

    public void setAllItems(List<MenuItem> allItems) {
        this.allItems = allItems;
        if(items == null){
            items = new HashMap<Weekday, List<MenuItem>>();
        }
        for(MenuItem item : allItems){
            addItem(item);
        }
    }

    public void setItems(Map<Weekday, List<MenuItem>> items) {
        this.items = items;
    }

    public void addItem(MenuItem newItem){
        List<MenuItem> menuForDay = items.get(newItem.getWeekday());
        if(menuForDay == null){
            menuForDay = new ArrayList<MenuItem>();
            menuForDay.add(newItem);
            items.put(newItem.getWeekday(), menuForDay);
        }else{
            if(!menuForDay.contains(newItem)){
                menuForDay.add(newItem);
            }
        }
        if(!allItems.contains(newItem)){
            allItems.add(newItem);
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
