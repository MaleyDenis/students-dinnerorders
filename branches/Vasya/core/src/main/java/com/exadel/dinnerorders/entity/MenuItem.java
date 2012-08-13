package com.exadel.dinnerorders.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;

import java.io.Serializable;

/**
 * User: Василий Силин
 * Date: 16.7.12
 */

@Entity
@Table(name = "menuitem", catalog = "dinnerorders")
public class MenuItem implements Serializable {
    private Long id;
    private Weekday weekday;
    private String description;
    private Double cost;

    public MenuItem(){

    }

    public MenuItem(Long id, Weekday weekday, String description, Double cost) {
        this.id = id;
        this.weekday = weekday;
        this.description = description;
        this.cost = cost;
    }

    @Id
    @Column(name = "menuitem_id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "weekday", unique = false, nullable = false)
    public Weekday getWeekday() {
        return weekday;
    }

    @Column(name = "description", unique = false, nullable = false)
    public String getDescription() {
        return description;
    }

    @Column(name = "cost", unique = false, nullable = false)
    public Double getCost() {
        return cost;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setWeekday(Weekday weekday) {
        this.weekday = weekday;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        MenuItem menuItem = (MenuItem) o;
        return cost.equals(menuItem.cost) && description.equals(menuItem.description) &&
                id.equals(menuItem.id) && weekday == menuItem.weekday;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + weekday.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + cost.hashCode();
        return result;
    }
}
