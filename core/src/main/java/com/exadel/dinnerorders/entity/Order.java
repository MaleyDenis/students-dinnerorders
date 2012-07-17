package com.exadel.dinnerorders.entity;

import java.util.Date;
import java.util.List;


public class Order {
    private Long id;
    private Long userID;
    private double cost;
    private Date datePayment;
    private Date dateOrder;
    private List<MenuItem> menuItemList;

    public Order(Long id, Long userID, double cost, Date dateOrder, Date datePayment) {
        this.id = id;
        this.userID = userID;
        this.cost = cost;
        this.dateOrder = dateOrder;
        this.datePayment = datePayment;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Date getDatePayment() {
        return datePayment;
    }

    public void setDatePayment(Date datePayment) {
        this.datePayment = datePayment;
    }

    public Date getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(Date dateOrder) {
        this.dateOrder = dateOrder;
    }

    public long getUserID() {
        return userID;
    }

    public long getId() {
        return id;
    }
}
