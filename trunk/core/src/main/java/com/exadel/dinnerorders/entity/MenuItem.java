package com.exadel.dinnerorders.entity;

public class MenuItem {
    private final int id;
    private Weekday weekday;
    private String description;
    private int cost;

    public MenuItem(int id, Weekday weekday, String description, int cost)throws IncorrectDataException {
        if(id < 0){
            throw new IncorrectDataException("Incorrect ID!!!");
        }
        if(weekday == null){
            throw new IncorrectDataException("Indicate weekday!!!");
        }
        if(description == null){
            throw new IncorrectDataException("Indicate description!!!");
        }
        if(description.isEmpty()){
            throw new IncorrectDataException("Indicate description!!!");
        }
        if(cost <= 0){
            throw new IncorrectDataException("Incorrect cost!!!");
        }
        this.id = id;
        this.weekday = weekday;
        this.description = description;
        this.cost = cost;
    }

    public MenuItem(int id, String weekday, String description, int cost)throws IncorrectDataException {
        if(id < 0){
            throw new IncorrectDataException("Incorrect ID!!!");
        }
        if(weekday == null){
            throw new IncorrectDataException("Indicate weekday!!!");
        }
        if(weekday.isEmpty()){
            throw new IncorrectDataException("Indicate weekday!!!");
        }
        if(description == null){
            throw new IncorrectDataException("Indicate description!!!");
        }
        if(description.isEmpty()){
            throw new IncorrectDataException("Indicate description!!!");
        }
        if(cost <= 0){
            throw new IncorrectDataException("Incorrect cost!!!");
        }
        this.id = id;
        this.weekday = Weekday.valueOf(weekday);
        this.description = description;
        this.cost = cost;
    }

    public Weekday getWeekday() {
        return weekday;
    }

    public void setWeekday(Weekday weekday)throws IncorrectDataException  {
        if(weekday == null){
            throw new IncorrectDataException("Indicate weekday!!!");
        }
        this.weekday = weekday;
    }

    public void setWeekday(String weekday)throws IncorrectDataException {
        if(weekday == null){
            throw new IncorrectDataException("Indicate weekday!!!");
        }
        if(weekday.isEmpty()){
            throw new IncorrectDataException("Indicate weekday!!!");
        }
        this.weekday = Weekday.valueOf(weekday);
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description)throws IncorrectDataException {
        if(description == null){
            throw new IncorrectDataException("Indicate description!!!");
        }
        if(description.isEmpty()){
            throw new IncorrectDataException("Indicate description!!!");
        }
        this.description = description;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost)throws IncorrectDataException {
        if(cost <= 0){
            throw new IncorrectDataException("Incorrect cost!!!");
        }
        this.cost = cost;
    }
}
