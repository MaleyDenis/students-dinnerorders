package com.exadel.dinnerorders.entity;

import org.apache.solr.client.solrj.beans.Field;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProxyMenu {
    @Field
    private String id;
    @Field
    private String cafename;
    @Field
    private String date_start;
    @Field
    private String date_end;
    private Map<String, List<ProxyMenuItem>> itemsMap;

    public ProxyMenu() {
        super();
        itemsMap = new HashMap<String , List<ProxyMenuItem>>();
    }

    public String getDate_start() {
        return date_start;
    }

    public void setDate_start(String date_start) {
        this.date_start = date_start;
    }

    public String getDate_end() {
        return date_end;
    }

    public void setDate_end(String date_end) {
        this.date_end = date_end;
    }

    public String getId() {
        return id;
    }

    public String getCafename() {
        return cafename;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCafename(String cafename) {
        this.cafename = cafename;
    }

    @Override
    public String toString(){
        return "ID: " + id + "\n"
                + "Cafe: " + cafename + "\n"
                + "From: " + date_start + "\n"
                + "To: " + date_end;
    }

    public void setItemsMap(Map<String, List<ProxyMenuItem>> itemsMap) {
        this.itemsMap = itemsMap;
    }

    public Map<String, List<ProxyMenuItem>> getItemsMap() {
        return itemsMap;
    }

    public void addItem(ProxyMenuItem proxyMenuItem) {
        List<ProxyMenuItem> proxyMenuForDay = itemsMap.get(proxyMenuItem.getWeekday());
        if(proxyMenuForDay == null){
            proxyMenuForDay = new ArrayList<ProxyMenuItem>();
            proxyMenuForDay.add(proxyMenuItem);
            itemsMap.put(proxyMenuItem.getWeekday(), proxyMenuForDay);
        } else {
            proxyMenuForDay.add(proxyMenuItem);
        }
    }

    public Menu convertToSimpleMenu() {
        Long mID = Long.parseLong(id);
        Timestamp mStartDate = Timestamp.valueOf(date_start);
        Timestamp mEndDate = Timestamp.valueOf(date_end);
        Menu created = new Menu(mID, cafename, mStartDate, mEndDate, new HashMap<Weekday, List<MenuItem>>());
        for (String weekdayKey: itemsMap.keySet()) {
            for (ProxyMenuItem proxyMenuItem: itemsMap.get(weekdayKey)) {
                created.addItem(proxyMenuItem.createSimpleMenuItem());
            }
        }
        return created;
    }

    public boolean isBadData() {
        return (id == null || cafename == null || date_start == null || date_end == null);
    }
}
