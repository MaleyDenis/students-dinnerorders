package com.exadel.dinnerorders.entity;

import com.exadel.dinnerorders.dao.OrderDAO;

import java.sql.Timestamp;
import java.util.List;


public class Order  implements Entity{
    @Export(column = "OrderId")
    private Long id;
    @Export(column = "UserId")
    private Long userID;
    @Export(column = "Cost")
    private double cost;
    @Export(column = "Date of payment")
    private Timestamp datePayment;
    @Export(column = "Date of order")
    private Timestamp dateOrder;

    private List<MenuItem> menuItemList;

    public Order(Long id, Long userID, double cost, Timestamp dateOrder, Timestamp datePayment) {
        this.id = id;
        this.userID = userID;
        this.cost = cost;
        this.dateOrder = dateOrder;
        this.datePayment = datePayment;
    }

    public Order(Long userID, double cost, Timestamp dateOrder, Timestamp datePayment) {
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

    public Timestamp getDatePayment() {
        return datePayment;
    }

    public void setDatePayment(Timestamp datePayment) {
        this.datePayment = datePayment;
    }

    public Timestamp getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(Timestamp dateOrder) {
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

    public void setId(Long id) {
        this.id = id;
    }
}
