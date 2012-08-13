package com.exadel.dinnerorders.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.CascadeType;

@Entity
@Table(name = "order", catalog = "dinnerorders")
public class Order {
    private Long id;
    private Long userID;
    private Double cost;
    private Date datePayment;
    private Date dateOrder;
    private List<MenuItem> menuItemList;

    public Order(Long id, Long userID, Double cost, Date dateOrder, Date datePayment) {
        this.id = id;
        this.userID = userID;
        this.cost = cost;
        this.dateOrder = dateOrder;
        this.datePayment = datePayment;
    }

    public Order () {

    }

    @Id
    @Column(name = "order_id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "user_id", nullable = false, unique = false)
    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    @Column(name = "cost", nullable = false, unique = false)
    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_payment", nullable = true, unique = false)
    public Date getDatePayment() {
        return datePayment;
    }

    public void setDatePayment(Date datePayment) {
        this.datePayment = datePayment;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_order", nullable = false, unique = false)
    public Date getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(Date dateOrder) {
        this.dateOrder = dateOrder;
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "order_menuitem", catalog = "dinnerorders", joinColumns = { @JoinColumn(name = "order_id")},
            inverseJoinColumns = { @JoinColumn(name = "menuitem_id")})
    public List<MenuItem> getMenuItemList() {
        if(menuItemList == null){
            menuItemList = new ArrayList<MenuItem>();
        }
        return menuItemList;
    }

    public void setMenuItemList(List<MenuItem> menuItemList) {
        this.menuItemList = menuItemList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Order order = (Order) o;
        if (!cost.equals(order.cost)){
            return false;
        }
        if (!dateOrder.equals(order.dateOrder)){
            return false;
        }
        if (!datePayment.equals(order.datePayment)){
            return false;
        }
        if (!id.equals(order.id)){
            return false;
        }
        if (!menuItemList.equals(order.menuItemList)){
            return false;
        }
        if (!userID.equals(order.userID)){
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + userID.hashCode();
        result = 31 * result + cost.hashCode();
        result = 31 * result + datePayment.hashCode();
        result = 31 * result + dateOrder.hashCode();
        result = 31 * result + menuItemList.hashCode();
        return result;
    }
}
