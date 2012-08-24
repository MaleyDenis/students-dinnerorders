package com.exadel.dinnerorders.vaadinwindow.layouts.panels;

import com.exadel.dinnerorders.entity.Order;
import com.exadel.dinnerorders.service.OrderService;
import com.exadel.dinnerorders.service.UserService;
import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.guitoolkits.OrderMenuLayout;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class TableOrderPanel extends Panel {
    private Table tableOrder;
    private PopupView popupView;
    private PopupDateField datePayment;

    public TableOrderPanel(Application application){
        super();
        application.getEventBus().register(this);
        initTableOrder();
        initComponent();
        setSizeFull();

    }

    public void initTableOrder(){
        tableOrder = new Table();
        tableOrder.addContainerProperty("User",String.class,null);
        tableOrder.addContainerProperty("Cost",Double.class,null);
        tableOrder.addContainerProperty("Date order", Date.class,null);
        tableOrder.addContainerProperty("Date payment",HorizontalLayout.class,null);
        tableOrder.addContainerProperty("Description",PopupView.class,null);
        tableOrder.setSizeFull();
        tableOrder.setSortDisabled(true);
        createTable();
        addComponent(tableOrder);
    }

    private void initComponent() {
        datePayment = new PopupDateField();
        datePayment.setValue(new Date());
    }

    private HorizontalLayout getDatePaymentLayout(Date datePayment){
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponent(new Label(datePayment.toString()));
        horizontalLayout.addComponent((new PopupDateField()));
        return horizontalLayout;
    }

    public void createTable(){
        Collection<Order> orderCollection = OrderService.getAllSortedOrders(); ArrayList<String> menuItemOrder;
        int i=1;
        for (Order order : orderCollection){
            tableOrder.addItem(new Object[]{UserService.findUserByID(order.getUserID()).getUserName(),order.getCost(),
                    order.getDateOrder(),getDatePaymentLayout(order.getDatePayment()),viewOrderMenu(order)},i);
            i++;
        }
    }

    public PopupView viewOrderMenu(Order order){
        Panel panel = new Panel();
        panel.setWidth(50,UNITS_PERCENTAGE);
        panel.setHeight(50,UNITS_PERCENTAGE);
        panel.addComponent(new OrderMenuLayout(order));
        popupView = new PopupView("Description",panel);
        return popupView;
    }

}
