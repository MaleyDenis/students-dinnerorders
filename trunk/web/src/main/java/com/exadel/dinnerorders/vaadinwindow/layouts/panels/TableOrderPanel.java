package com.exadel.dinnerorders.vaadinwindow.layouts.panels;
import com.exadel.dinnerorders.entity.User;
import com.exadel.dinnerorders.service.UserService;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;

import java.util.Collection;


public class TableOrderPanel extends Panel {
    Table tableOrder;
    Label user;
    Label cost;
    Label dateOrder;
    Label datePayment;
    public TableOrderPanel(){
        super();
        initLabels();
        initTable();
    }

    public void initLabels(){
        user = new Label("User");
        cost = new Label("Cost");
        dateOrder = new Label("Date order");
        datePayment = new Label("Date payment");
    }

    public void initTable(){
        tableOrder = new Table("Order");
        tableOrder.addContainerProperty(user,String.class,null);
        tableOrder.addContainerProperty(cost,Double.class,null);
        tableOrder.addContainerProperty(dateOrder, String.class,null);
        tableOrder.addContainerProperty(datePayment,String.class,null);
        tableOrder.addItem(new Object[]{
                "Denis Maley", 445.54, "19-07-2012", "28-12-2009"}, 1);
        tableOrder.addItem(new Object[] {
                "Vasya Silin", 445.54,"28-12-1990","28-12-2009"}, 2);
        tableOrder.addItem(new Object[] {
                "Dima Shulgin", 445.54,"28-12-1990","28-12-2009"}, 3);
        tableOrder.addItem(new Object[] {
                "Yan Cacuk", 445.54,"28-12-1990","28-12-2009"}, 4);
        tableOrder.addItem(new Object[] {
                "Alexei Okunevich", 445.54,"28-12-1990","28-12-2009"}, 5);
       Collection<User> users = UserService.loadAllUsersFromLdap();
        int i = 6;
       for (User user:users){
            tableOrder.addItem(new Object[] {
                    user.getUserName(), 445.54,"28-12-1990","28-12-2009"}, i);
            i++;
        }
        tableOrder.setWidth("100%");
        addComponent(tableOrder);
    }

}
