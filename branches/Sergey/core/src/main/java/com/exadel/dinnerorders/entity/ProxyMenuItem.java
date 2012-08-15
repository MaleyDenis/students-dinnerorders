package com.exadel.dinnerorders.entity;

import org.apache.solr.client.solrj.beans.Field;

public class ProxyMenuItem {
    @Field
    private String id;
    @Field
    private String weekday;
    @Field
    private String description;
    @Field
    private String cost;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public String getId() {
        return id;
    }

    public String getWeekday() {
        return weekday;
    }

    public MenuItem createSimpleMenuItem() {
        Long miID = Long.parseLong(id);
        Weekday miWeekday = Weekday.valueOf(weekday);
        MenuItem created = new MenuItem(miID, miWeekday, description, Double.parseDouble(cost));
        return created;
    }

    public boolean isBadData() {
        return (id == null || description == null || weekday == null || cost == null);
    }
}
