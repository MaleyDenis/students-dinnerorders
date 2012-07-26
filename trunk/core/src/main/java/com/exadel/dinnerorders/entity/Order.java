package com.exadel.dinnerorders.entity;

import com.exadel.dinnerorders.dao.OrderDAO;

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

    public Order(Long userID, double cost, Date dateOrder, Date datePayment) {
        OrderDAO orderDAO = new OrderDAO();
        id = orderDAO.getID();
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

    public Long getUserID() {
        return userID;
    }

    public Long getId() {
        return id;
    }

    public List<MenuItem> getMenuItemList() {
        return menuItemList;
    }

    public void setMenuItemList(List<MenuItem> menuItemList) {
        this.menuItemList = menuItemList;
    }
}
