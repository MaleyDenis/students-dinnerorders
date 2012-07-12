package com.exadel.dinnerorders.entity;

public class MenuItem {
    private Long id;
    private Weekday weekday;
    private String description;
    private Double cost;

    public MenuItem(Long id, Weekday weekday, String description, Double cost) {
        this.id = id;
        this.weekday = weekday;
        this.description = description;
        this.cost = cost;
    }

    public Long getId() {
        return id;
    }

    public Weekday getWeekday() {
        return weekday;
    }

    public String getDescription() {
        return description;
    }

    public Double getCost() {
        return cost;
    }
}
